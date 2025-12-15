package org.securityapps.vehicletracking.Netty.inbound.decoder.teltonika;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.CorruptedFrameException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.securityapps.vehicletracking.Netty.inbound.model.GpsMessage;
import org.securityapps.vehicletracking.Netty.inbound.model.TeltonicaLoginMessage;
import org.securityapps.vehicletracking.Netty.util.Crc16;
import org.securityapps.vehicletracking.infrastructure.persistence.repository.JpaTrackerDeviceRepository;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class TeltonicaProtocolDecoder extends ChannelInboundHandlerAdapter {

    private final JpaTrackerDeviceRepository deviceRepository;


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object frame) throws Exception {

        try {
            if (frame instanceof TeltonicaLoginMessage teltonicaLoginMessage) {
                boolean isRegistered = isRegistered(teltonicaLoginMessage);
                ctx.writeAndFlush(isRegistered ? 0x01 : 0x00);
                ctx.fireChannelRead(teltonicaLoginMessage);
            }
            else if(frame instanceof ByteBuf avlBuf) {
                TeltonicaAvlResult result = decodeTeltonicaAVL(avlBuf);
                if (result != null) {
                    List<GpsMessage> messages = result.messages;
                    int numberOfData2 = result.numberOfData2;
                    if (messages != null) {
                        for (GpsMessage msg : messages) {
                            ctx.fireChannelRead(msg);
                        }
                        ByteBuf ack = Unpooled.buffer(4);
                        ack.writeInt(numberOfData2);
                        ctx.writeAndFlush(ack);
                    }
                }
            }
            else{
                ctx.fireChannelRead(frame);
            }
        }
        finally {
            if(frame instanceof ByteBuf buf){
                buf.release();
            }
        }
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
        log.warn("[PROTOCOL] Protocol error! closing connection: ",cause);
        ctx.close();
    }

    private TeltonicaAvlResult decodeTeltonicaAVL(ByteBuf frame) {
        List<GpsMessage> messages = new ArrayList<>();

        /* we need minimum of 45 bytes for meaningful data
        4 bytes of zeros + 4 bytes of dataFieldLength + 1 byte of codecId + 1 byte of numberOfData1 +
        30(min),147(max) bytes of AVL data + 1 byte of numberOfData2 + 4 bytes of crc16
        */
        if(frame.readableBytes() < 45) {
            return null; // return nothing (there is no enough data)
        }
        int zero1 = frame.readUnsignedByte();
        int zero2 = frame.readUnsignedByte();
        int zero3 = frame.readUnsignedByte();
        int zero4 = frame.readUnsignedByte();

        if(zero1 != 0 || zero2 != 0 || zero3 != 0 || zero4 != 0) return null; //invalid frame bypassed

        int dataFieldLength = frame.readInt();

        int crcStartIndex = frame.readerIndex();
        byte[] crcData = new byte[dataFieldLength];
        frame.getBytes(crcStartIndex,crcData);

        int codecId = frame.readUnsignedByte();
        int numberOfData1 = frame.readUnsignedByte();

        for(int i =0; i < numberOfData1; i++){

            int fixedRecordMinimum = 8/*timestamp*/ + 1/*priority*/ + 4/*longitude*/ + 4/*latitude*/ + 2/*altitude*/ +
                    2/*angle*/ + 1/*satellites*/  + 2/*speed*/;  //15 bytes in total

            if(frame.readableBytes() < fixedRecordMinimum){ // this means no enough data for a full record
                break; // stop
            }

            long timestamp = frame.readLong();// 8 bytes long milliseconds - epoch
            int priority = frame.readUnsignedByte();// 1 byte - 0 for low, 1 for high, 2 for panic

            int rawLat = frame.readInt();// 4 bytes signed
            double latitude = rawLat / 1e7; // raw lat divided by 10 million (precision)

            int rawLon = frame.readInt();// 4 bytes signed
            double longitude = rawLon / 1e7; // raw lon divided by 10 million (precision)

            int altitude = frame.readUnsignedShort();// 2 byes signed
            int angle = frame.readUnsignedShort(); // course / direction
            int satellites = frame.readUnsignedByte(); // 1 byte
            int speed = frame.readUnsignedShort(); // 2 bytes

            IOParsed parsedIOElements = parseIOElements(frame);

            GpsMessage message = new GpsMessage(
                    null,
                    latitude,
                    longitude,
                    speed,
                    altitude,
                    angle,
                    satellites,
                    priority,
                    parsedIOElements.eventId,
                    parsedIOElements.totalIO,
                    parsedIOElements.ioMap,
                    Instant.ofEpochMilli(timestamp)
            );
            messages.add(message);
        }
        int numberOfData2 = frame.readUnsignedByte();

        if(numberOfData1 != numberOfData2){
                log.warn("Teltonika: Record count mismatch! First: {}, Second: {}%n",numberOfData1,numberOfData2);
        }

        /*
          CRC-16 is 4 bytes, but first two are zeroes and last two are
          CRC-16 calculated for [codec id, number of data 2]
        */
        frame.skipBytes(2);  //padding(two zeroes)
        int receivedCrc = frame.readUnsignedShort(); //crc received from device
        int calculatedCrc = Crc16.compute(crcData);

        if(receivedCrc != calculatedCrc){
            messages.clear();
            throw new CorruptedFrameException("Teltonika CRC16 mismatch !");
        }

        TeltonicaAvlResult result = new TeltonicaAvlResult();
        result.messages = messages;
        result.numberOfData2 = numberOfData2;
        return result;
    }

    // the following method returns a container with eventId, totalIO and a map of IO element's id and it's value
    private IOParsed parseIOElements(ByteBuf buf) {
        IOParsed parsed = new IOParsed();
        if(buf.readableBytes() < 2) return parsed; // we need to have at least two bytes for eventId and totalIO

        parsed.eventId = buf.readUnsignedByte();
        parsed.totalIO = buf.readUnsignedByte();

        // N1 - each value has 1 byte length
        if (buf.readableBytes() < 1) return parsed;
        int n1 = buf.readUnsignedByte();
        for(int i = 0; i < n1; i++) {
            if(buf.readableBytes() < 2) break; // at least ID + 1 byte is needed
            int id = buf.readUnsignedByte();
            int value = buf.readUnsignedByte();
            parsed.ioMap.put(id, value); // value is 1 byte short in the case of n1
        }

        // N2 - each value has 2 bytes length
        if (buf.readableBytes() < 1) return parsed;
        int n2 = buf.readUnsignedByte();
        for(int i = 0; i < n2; i++) {
            if(buf.readableBytes() < 3) break; // at least ID + 2 bytes is needed
            int id = buf.readUnsignedByte();
            int value = buf.readUnsignedShort();
            parsed.ioMap.put(id, value); // value is 2 bytes short in the case of n2
        }

        // N4 - each value has 4 bytes length
        if (buf.readableBytes() < 1) return parsed;
        int n4 = buf.readUnsignedByte();
        for(int i = 0; i < n4; i++) {
            if(buf.readableBytes() < 5) break; // at least ID + 4 bytes is needed
            int id = buf.readUnsignedByte();
            int value = buf.readInt(); // value is 4 bytes int in the case of n4
            parsed.ioMap.put(id, value);
        }

        // N8 - each value has 8 bytes length
        if (buf.readableBytes() < 1) return parsed;
        int n8 = buf.readUnsignedByte();
        for(int i = 0; i < n8; i++) {
            if(buf.readableBytes() < 9) break; // at least ID + 8 bytes is needed
            int id = buf.readUnsignedByte();
            long value = buf.readLong(); // value is 8 bytes long in the case of n8
            parsed.ioMap.put(id, value);
        }

        return parsed;
    }

    // the following class is a container for parsed IO element block information
    private static class IOParsed{
        int eventId = 0;
        int totalIO = 0;
        final Map<Integer,Object> ioMap = new HashMap<>(); // map of IO element's Id and it's value
    }

    //the following is a wrapper class for returning gps messages and numberOfData2 to channelRead0 method
    private static class TeltonicaAvlResult{
        List<GpsMessage> messages;
        int numberOfData2;
    }

    private boolean isRegistered(TeltonicaLoginMessage teltonicaLoginMessage){
        String imei = teltonicaLoginMessage.getImei();
        return deviceRepository.findByImei(imei).isPresent();
    }

}