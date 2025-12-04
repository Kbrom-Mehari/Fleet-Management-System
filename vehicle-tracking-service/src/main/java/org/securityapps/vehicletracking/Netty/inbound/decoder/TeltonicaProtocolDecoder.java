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
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf buf) throws Exception {
        List<GpsMessage> messages = decodeTeltonicaAVL(buf);
        for(GpsMessage msg : messages){
            ctx.fireChannelRead(msg);
        }
    }
    private List<GpsMessage> decodeTeltonicaAVL(ByteBuf buf) {
        List<GpsMessage> messages = new ArrayList<>();
        if(buf.readableBytes() < 2) { // at least codec and count
            return messages; // empty (there is no data)
        }
        int codecId = buf.readUnsignedByte();
        int recordCount = buf.readUnsignedByte();
        for(int i =0; i<recordCount; i++){

            if(buf.readableBytes() < 8 + 1 + 4 + 4 + 2 + 2 + 1 + 2){ // this means no enough data for a full record
                break;
            }

            long timestamp = buf.readLong();
            int priority = buf.readUnsignedByte();
            int rawLat = buf.readInt();
            double latitude = rawLat / 1E7; // raw lat divided by 10 million
            int rawLon = buf.readInt();
            double longitude = rawLon / 1E7; // raw lon divided by 10 million
            short altitude = buf.readShort();
            int angle = buf.readUnsignedShort(); // course / direction
            int satellites = buf.readUnsignedByte();
            int speed = buf.readUnsignedShort();
            IOParsed parsedIOElements = parseIOElements(buf);

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
        int recordCount2;
        if(buf.readableBytes() >= 1){
            recordCount2 = buf.readUnsignedByte();
            if(recordCount != recordCount2){
                System.err.printf("Teltonica: Record count mismatch! First: %d, Second: %d%n",recordCount,recordCount2);
            }
        }

        if(buf.readableBytes() >= 4){
            int crc = buf.readInt(); // we consume it. validating crc isn't needed since TCP is reliable
        }

        return  messages;
    }

    // the following method returns a container with eventId, totalIO and a map of IO element's id and it's value
    private IOParsed parseIOElements(ByteBuf buf) {
        IOParsed parsed = new IOParsed();
        if(buf.readableBytes() < 2) { // we need to have at least two bytes for eventId and totalIO
            return parsed;
        }
        parsed.eventId = buf.readUnsignedByte();
        parsed.totalIO = buf.readUnsignedByte();

        // N1 - each value has 1 byte length
        int n1 = buf.readUnsignedByte();
        for(int i = 1; i < n1; i++) {
            if(buf.readableBytes() < 2) break; // at least ID + 1 byte is needed
            int id = buf.readUnsignedByte();
            int value = buf.readUnsignedByte();
            parsed.ioMap.put(id, value); // value is 1 byte short in the case of n1
        }

        // N2 - each value has 2 bytes length
        int n2 = buf.readUnsignedByte();
        for(int i = 1; i < n2; i++) {
            if(buf.readableBytes() < 3) break; // at least ID + 2 bytes is needed
            int id = buf.readUnsignedByte();
            int value = buf.readUnsignedShort();
            parsed.ioMap.put(id, value); // value is 2 bytes short in the case of n2
        }

        // N4 - each value has 4 bytes length
        int n4 = buf.readUnsignedByte();
        for(int i = 1; i < n4; i++) {
            if(buf.readableBytes() < 5) break; // at least ID + 4 bytes is needed
            int id = buf.readUnsignedByte();
            int value = buf.readInt(); // value is 4 bytes int in the case of n4
            parsed.ioMap.put(id, value);
        }
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
        final Map<Integer,Object> ioMap = new HashMap<Integer, Object>(); // map of IO element's Id and it's value
    }

}