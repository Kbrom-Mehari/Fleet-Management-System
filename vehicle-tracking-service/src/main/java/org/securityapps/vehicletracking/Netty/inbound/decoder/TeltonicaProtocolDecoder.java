package org.securityapps.vehicletracking.Netty.inbound.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.securityapps.vehicletracking.Netty.inbound.model.GpsMessage;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class TeltonicaProtocolDecoder extends SimpleChannelInboundHandler<ByteBuf> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf buf) throws Exception {
        List<GpsMessage> messages = decodeTeltonicaAVL(buf);
        for(GpsMessage msg : messages) {
            ctx.fireChannelRead(msg); // pass the messages to the pipeline (to the next handler - message handler)
        }
    }
    private List<GpsMessage> decodeTeltonicaAVL(ByteBuf buf){
        List<GpsMessage> messages = new ArrayList<>();
        buf.skipBytes(4); // the first 4 bytes in teltonica devices is packet length and we don't need it
        int codecId = buf.readUnsignedByte();  //codec Id (one byte)
        int recordCount = buf.readUnsignedByte();  // number of data (one byte)

        for(int i = 0; i < recordCount; i++) {
            long timestamp = buf.readLong();  // 8 bytes of milliseconds - long
            byte priority = buf.readByte();  // priority - 0 for low, 1 for high, 2 for panic (one byte)
            double latitude = buf.readInt()/10000000.0; //stored as integer values multiplied by 10,000,000 (4 bytes - int)
            double longitude = buf.readInt()/10000000.0; //stored as integer values multiplied by 10,000,000 (4 bytes - int)
            short altitude = buf.readShort(); // altitude in meters (2 bytes - short)
            short speed = buf.readShort(); // speed in Km/h (2 bytes - short)
            short course = buf.readShort(); // course/direction/angle in degrees (2 bytes - short)
            byte satellites =  buf.readByte(); // satellites count (number of visible satellites)
            int eventId = buf.readUnsignedByte();
            int totalIO = buf.readUnsignedByte();

            messages.add(new GpsMessage(null,
                    latitude,
                    longitude,
                    speed * 1.0,
                    altitude,
                    Instant.ofEpochMilli(timestamp)
            ));
        }
        int recordCount2 = buf.readUnsignedByte();
        if(recordCount != recordCount2) {
            System.err.println("Record count mismatch");
        }
        int crc = buf.readInt();
        return messages;
    }

}















