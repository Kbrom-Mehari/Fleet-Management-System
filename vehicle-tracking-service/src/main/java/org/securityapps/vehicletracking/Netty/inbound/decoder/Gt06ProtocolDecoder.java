package org.securityapps.vehicletracking.Netty.inbound.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.securityapps.vehicletracking.Netty.inbound.model.GpsMessage;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class Gt06ProtocolDecoder extends SimpleChannelInboundHandler<ByteBuf> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf buf) throws Exception {
        GpsMessage message = decodeGt06(buf);
        if(message != null) {
            ctx.fireChannelRead(message);
        }
    }
    private GpsMessage decodeGt06(ByteBuf buf) {
        buf.skipBytes(2); // skip the start bytes 0x78 0x78 or 0x79 0x79
        int payloadLength = buf.readUnsignedByte();
        int protocol =  buf.readUnsignedByte();

        switch (protocol) {
            case 0x01 -> {  // LOGIN PACKET
                buf.skipBytes(8); // skip 8 bytes of IMEI
                buf.skipBytes(4); // serial(2 bytes) + crc(2 bytes)
                buf.skipBytes(2); // stop(2 bytes)

                return null;
            }

            case 0x13 -> {  // HEARTBEAT / STATUS PACKET
                int remaining = payloadLength + 2/*stop bytes*/ - 1/*protocol*/; // protocol is already consumed,
                buf.skipBytes(remaining);
                return null;

            }

            case 0x12, 0x10 -> { // LOCATION DATA PACKET
                return decodeLocation(buf);
            }
            case 0x16 -> {  // ALARM PACKET - contains same location packet
                return decodeLocation(buf);
            }
            default -> {
                int remaining = payloadLength + 2 - 1;
                buf.skipBytes(remaining);
                return null;
            }

        }
    }
    private GpsMessage decodeLocation(ByteBuf buf){
        // THE FOLLOWING IS GPS INFORMATION
        long timestamp = decodeDateTime(buf);
        int satellites = buf.readUnsignedByte();
        double latitude = buf.readInt()/1.8E6;
        double longitude = buf.readInt()/1.8E6;
        int speed = buf.readUnsignedByte();
        int courseStatus = buf.readUnsignedShort();

        // THE FOLLOWING IS LBS(Location Based Services) INFORMATION
        int MCC = buf.readUnsignedShort();   // Mobile Country Code
        int MNC = buf.readUnsignedByte();   // Mobile Network Code
        int LAC = buf.readUnsignedShort(); // Location Area Code
        //next, we have cellId with 3 bytes with Big-Endian(most significant byte comes first)
        int cellHigh = buf.readUnsignedShort(); // 2 bytes
        int cellLow = buf.readUnsignedByte(); // 1 byte (we are doing this b/c we can't parse 3 bytes at a time)
        long cellId = ((long) cellHigh << 8) | cellLow; //we shift 8 bits(for cellLow - 1 byte) then, or it with cellLow
        // next are serial number(2 bytes) and CRC /Error Check (2 bytes), we skip them
        buf.skipBytes(4);
        //the last two bytes (stop bytes) are consumed in frameDecoder class

        GpsMessage message = new GpsMessage(latitude,
                longitude,
                speed,
                (short) 0,
                courseStatus,
                satellites,
                0,
                0,
                0,
                null,
                Instant.ofEpochSecond(timestamp));

        return message;
    }

    private long decodeDateTime(ByteBuf buf){
        // Gt06 devices send timestamp in YYMMDDhhmmss format
        int year = buf.readUnsignedByte() + 2000;
        int month = buf.readUnsignedByte();
        int day = buf.readUnsignedByte();
        int hour = buf.readUnsignedByte();
        int minute = buf.readUnsignedByte();
        int second = buf.readUnsignedByte();

        LocalDateTime dt = LocalDateTime.of(year, month, day, hour, minute, second);
        return dt.toEpochSecond(ZoneOffset.UTC); // UTC time format
    }
}
