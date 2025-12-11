package org.securityapps.vehicletracking.Netty.inbound.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.securityapps.vehicletracking.Netty.inbound.model.GpsMessage;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeltonicaProtocolDecoder extends SimpleChannelInboundHandler<ByteBuf> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf frame) throws Exception {
        //we expect buf to contain only avl data array (from codec id - second data count)
        List<GpsMessage> messages = decodeTeltonicaAVL(frame);
        for(GpsMessage msg : messages){
            ctx.fireChannelRead(msg);
        }
    }
    private List<GpsMessage> decodeTeltonicaAVL(ByteBuf frame) {
        List<GpsMessage> messages = new ArrayList<>();

        /* we need minimum of 45 bytes for meaningful data
        4 bytes of zeros + 4 bytes of dataFieldLength + 1 byte of codecId + 1 byte of numberOfData1 +
        30(min),147(max) bytes of AVL data + 1 byte of numberOfData2 + 4 bytes of crc16
        */
        if(frame.readableBytes() < 45) {
            return messages; // return empty messages (there is no enough data)
        }
        int zero1 = frame.readUnsignedByte();
        int zero2 = frame.readUnsignedByte();
        int zero3 = frame.readUnsignedByte();
        int zero4 = frame.readUnsignedByte();

        int dataFieldLength = frame.readInt();

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

            short altitude = frame.readShort();// 2 byes signed
            int angle = frame.readUnsignedShort(); // course / direction
            int satellites = frame.readUnsignedByte(); // 1 byte
            int speed = frame.readUnsignedShort(); // 2 bytes

            IOParsed parsedIOElements = parseIOElements(frame);

            GpsMessage message = new GpsMessage(
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
                System.err.printf("Teltonica: Record count mismatch! First: %d, Second: %d%n",numberOfData1,numberOfData2);
        }

        int

       /*

       */

        return  messages;
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
        for(int i = 1; i < n1; i++) {
            if(buf.readableBytes() < 2) break; // at least ID + 1 byte is needed
            int id = buf.readUnsignedByte();
            int value = buf.readUnsignedByte();
            parsed.ioMap.put(id, value); // value is 1 byte short in the case of n1
        }

        // N2 - each value has 2 bytes length
        if (buf.readableBytes() < 1) return parsed;
        int n2 = buf.readUnsignedByte();
        for(int i = 1; i < n2; i++) {
            if(buf.readableBytes() < 3) break; // at least ID + 2 bytes is needed
            int id = buf.readUnsignedByte();
            int value = buf.readUnsignedShort();
            parsed.ioMap.put(id, value); // value is 2 bytes short in the case of n2
        }

        // N4 - each value has 4 bytes length
        if (buf.readableBytes() < 1) return parsed;
        int n4 = buf.readUnsignedByte();
        for(int i = 1; i < n4; i++) {
            if(buf.readableBytes() < 5) break; // at least ID + 4 bytes is needed
            int id = buf.readUnsignedByte();
            int value = buf.readInt(); // value is 4 bytes int in the case of n4
            parsed.ioMap.put(id, value);
        }

        // N8 - each value has 8 bytes length
        if (buf.readableBytes() < 1) return parsed;
        int n8 = buf.readUnsignedByte();
        for(int i = 1; i < n8; i++) {
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
    private boolean crc16Validated(){

    }

}