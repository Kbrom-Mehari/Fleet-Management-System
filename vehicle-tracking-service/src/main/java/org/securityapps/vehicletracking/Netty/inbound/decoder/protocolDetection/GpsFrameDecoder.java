package org.securityapps.vehicletracking.Netty.inbound.decoder.protocolDetection;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.securityapps.vehicletracking.Netty.inbound.model.teltonika.TeltonikaLoginMessage;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class GpsFrameDecoder extends ByteToMessageDecoder {

    private static final int MAX_FRAME_LENGTH = 1024 * 3;//MAX 3 KB - PROTECTS OUR SERVER FROM HUGE PACKET ATTACKS
    private static final int TLT_HEADER_ZEROS = 4;// Teltonica packet starts with four zeros
    private static final int TLT_CRC16_BYTES = 4;//CRC - error check
    private static final int TLT_LENGTH_BYTES = 4;


    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in , List<Object> out) throws Exception{
        // If there is no readable bytes, return
        if(in.readableBytes() < 2){
            return;
        }

        in.markReaderIndex(); // mark start point

        if(isTeltonikaFrame(in)){
            in.resetReaderIndex();
            ByteBuf frame = decodeTeltonikaFrame(in);
            if(frame != null){
                out.add(frame);
            }
            return;
        }

        if(isTeltonikaImei(in)){
            String imei = decodeTeltonikaImei(in);

            if(imei != null){
                TeltonikaLoginMessage teltonikaLoginMessage = new TeltonikaLoginMessage(imei);
                out.add(teltonikaLoginMessage);
            }
            return;
        }

        if(isGt06Frame(in)){
            in.resetReaderIndex();
            ByteBuf frame = decodeGt06Frame(in);
            if(frame != null){
                out.add(frame);
            }
            return;
        }
        // For unknown protocol, we have to move the readerIndex until we get proper protocol
        in.resetReaderIndex();
        in.skipBytes(1);
    }


    private boolean isTeltonikaFrame(ByteBuf in){
        // Teltonika starts with 0x00 0x00 0x00 0x00 prefix
        if(in.readableBytes() < TLT_HEADER_ZEROS) return false; // the first 4 bytes(prefix) are needed
        int reader = in.readerIndex();
        for(int i = 0; i < TLT_HEADER_ZEROS; i++){
            if(in.getUnsignedByte(reader + i) != 0) return false;//the first 4 bytes should be 0x00
        }
        return true;
    }
    private ByteBuf decodeTeltonikaFrame(ByteBuf in){
        if(in.readableBytes() < TLT_HEADER_ZEROS + TLT_LENGTH_BYTES) return null; //at least both are needed

        int reader = in.readerIndex();

        for(int i = 0; i < TLT_HEADER_ZEROS; i++){
            if(in.getUnsignedByte(reader + i) != 0) return null; //not teltonica frame if the first 4 are not zero
        }
        //Data field length is the length of bytes [codec id, number of data 2]. excluding crc
        int dataFieldLength = in.getInt(reader + TLT_HEADER_ZEROS); // length is found after the 4 bytes (zeros)

        if(dataFieldLength <= 0 || dataFieldLength > MAX_FRAME_LENGTH) {
            in.skipBytes(in.readableBytes()); // corrupted data. skip everything to avoid infinite loop and even connection closing
            return null;
        }

        int totalPacketBytes = TLT_HEADER_ZEROS + TLT_LENGTH_BYTES + dataFieldLength + TLT_CRC16_BYTES;

        if(in.readableBytes() < totalPacketBytes) {
            return null;   //wait for more bytes
        }

        /* readSlice() method slices the totalPacketBytes from the original buffer and advance
         the readerIndex of the original buffer by totalPacketBytes
        */
        return in.readSlice(totalPacketBytes);

    }
    private boolean isGt06Frame(ByteBuf in){   // concox Gt06 GPS devices
        // Gt06 uses 0x78 0x78 or 0x79 0x79 start bytes
        int a = in.getUnsignedByte(in.readerIndex());
        int b = in.getUnsignedByte(in.readerIndex()+1); // the first two start bytes
        return (a == 0x78 && b == 0x78) || (a == 0x79 && b == 0x79);
    }
    private ByteBuf decodeGt06Frame(ByteBuf in){
        if(in.readableBytes() < 8) return null; // 2 start bit + 1 length + 1 protocol ... + 2 crc + 2 end bytes

        int start1 = in.readUnsignedByte();
        int start2 = in.readUnsignedByte();

        if(!((start1 == 0x78 && start2 == 0x78)|| (start1 == 0x79 && start2 == 0x79))){
            // if not Gt06, skip
            return null;
        }
        int payloadLength = in.readUnsignedByte();

        if(payloadLength <= 0 || payloadLength > MAX_FRAME_LENGTH){
            in.skipBytes(in.readableBytes());
            return null;
        }
        /*Gt06 full frame length is - start(2 bytes) + payload length(1 byte) +
        payload[from protocol number up to crc/errorCheck](length bytes) +
        stop(2 bytes) */

        //full frameLength = start(2 bytes) + payloadLength(1 byte) + payload(length bytes) + stop(2 bytes)
        int frameLength = payloadLength + 5;

        if(in.readableBytes() < frameLength){
            return null; // if it is incomplete frame, we wait until full frame arrives
        }

        return in.readRetainedSlice(frameLength); // return the full frame
    }

    private boolean isTeltonikaImei(ByteBuf in){
        if(in.readableBytes() < 2) return false; //the first two bytes are length of the imei

        int reader = in.readerIndex();
        int length = in.getUnsignedShort(reader);

        //Teltonika IMEI is always 15 ASCII digits
        if(length != 15) return false;

        if(in.readableBytes() < 2 + length) return false;

        for(int i = 0; i < length; i++){
            int b = in.getUnsignedByte(reader + 2 + i);
            if(b < '0' || b > '9'){
                return false;
            }
        }

        return true;
    }

    private String decodeTeltonikaImei(ByteBuf in){
        int length = in.readUnsignedShort();

        if(in.readableBytes() < length)  return null;
        byte[] imeiBytes = new byte[length];
        in.readBytes(imeiBytes);

        return new String(imeiBytes, StandardCharsets.US_ASCII); //only the imei bytes
    }
}
