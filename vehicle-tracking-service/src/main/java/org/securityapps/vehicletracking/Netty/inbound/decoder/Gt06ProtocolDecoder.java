package org.securityapps.vehicletracking.Netty.inbound.decoder;

import io.lettuce.core.codec.CRC16;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.securityapps.vehicletracking.Netty.inbound.model.GpsMessage;
import org.securityapps.vehicletracking.Netty.util.Crc16;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class Gt06ProtocolDecoder extends SimpleChannelInboundHandler<ByteBuf> {
    private static final int CRC_LENGTH = 2;
    private static final int STOP_LENGTH = 2;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf frame) throws Exception {
        //our frame contains full gto6 frame -> from start - stop
        int reader = frame.readerIndex();

        if(frame.readableBytes() < 10)  return;  //minimal frame - 10

        int start1 = frame.getUnsignedByte(frame.readerIndex());
        int start2 = frame.getUnsignedByte(frame.readerIndex() + 1);

        if(!((start1 == 0x78 && start2 == 0x78) || (start1 == 0x79 && start2 == 0x79))){
            return;  // not gt06 frame
        }
        int length =  frame.getUnsignedShort(frame.readerIndex() + 2);
        int frameLength = length + 5;

        if(frame.readableBytes() != frameLength){
            //if provided frame is more than frame length, it's fine. but it must not be less than frame length
            if(frame.readableBytes() < frameLength) return;
        }
        //validating stop bytes
        int stopIndex = reader +  frameLength - 2;
        int stop1 = frame.getUnsignedByte(stopIndex);
        int stop2 = frame.getUnsignedByte(stopIndex + 1);

        if(!(stop1 == 0x0D && stop2 == 0x0A)){
            return;   // Invalid stop bytes
        }

        // Calculate CRC over bytes from Packet Length (reader+2) up to Information Serial Number (inclusive)
        // That means CRC covers length byte and subsequent (length - 2) bytes (since CRC itself is included in length)
        int crcStart = reader + 2;
        int crcEndExclusive = stopIndex - CRC_LENGTH;
        int crcLength =  crcEndExclusive - crcStart;

        if(crcLength <= 0) return;

        byte[] crcData = new byte[crcLength];
        frame.getBytes(crcStart, crcData);

        int expectedCrc = frame.getUnsignedShort(crcEndExclusive);
        int calcCrc = Crc16.compute(crcData);

        if (calcCrc != expectedCrc) {
            // CRC mismatch -> drop
            return;
        }

        //parse protocol number and info start at reader+3 (protocol number is after length)
        int infoIndex = reader + 3; //protocol
        int protocol = frame.getUnsignedByte(infoIndex);
        int idx = infoIndex + 1; // after protocol

        switch (protocol) {
            case 0x01 -> {  // LOGIN PACKET
                if(frame.readableBytes() < (idx - reader) + 8/*imei*/ + 2/*serial*/ + CRC_LENGTH + STOP_LENGTH) return;
                byte[] imeiBytes = new byte[8];
                frame.getBytes(idx, imeiBytes);
                idx += 8;
                // serial number is after the imei bytes
                int serial = frame.getUnsignedShort(idx + (length - 1 + 8 + 2));
                String imei = bcdToImei(imeiBytes);
                // we may generate a message or event for login (not location)
                // For now we just return null
                break;

            }
            case 0x13 -> {  // HEARTBEAT / STATUS PACKET
                // Byte structure: Terminal Info(1) Voltage(1) GSM(1) Alarm+Lang(2) Serial(2) CRC(2)
                // We already validated CRC; parse status if needed
                // idx points to first info byte
                // Ensure enough bytes exist:
                // info length = length - 1 (protocol) - 2 (serial) - 2 (crc) ??? but we already validated
                if (frame.readableBytes() >= 13) {
                    int terminalInfo = frame.getUnsignedByte(idx);
                    int voltage = frame.getUnsignedByte(idx + 1);
                    int gsm = frame.getUnsignedByte(idx + 2);
                    // ... handle status
                }
                break;
            }

            case 0x12, 0x10, 0x16 -> { // LOCATION/ALARM DATA PACKET
                GpsMessage msg = decodeLocation(frame, reader);
                if (msg != null) {
                    ctx.fireChannelRead(msg);
                }
                break;
            }
            default -> {
                // unknown protocol: ignore or log
                break;
            }

        }

    }
    private GpsMessage decodeGt06(ByteBuf buf) {

       return null;
    }

    private GpsMessage decodeLocation(ByteBuf buf,int frameReaderIndex){

        int idx = frameReaderIndex + 3; // protocol is at +2+1 => idx after protocol


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
    private String bcdToImei(byte[] bcd) {
        StringBuilder sb = new StringBuilder();

        for (byte b : bcd) {
            int hi = (b >> 4) & 0x0F;
            int lo = b & 0x0F;

            if (hi <= 9) sb.append(hi);     // valid digit
            if (lo <= 9) sb.append(lo);     // valid digit
        }

        // IMEI must be 15 digits max
        if (sb.length() > 15) {
            return sb.substring(0, 15);
        }
        return sb.toString();
    }

}
