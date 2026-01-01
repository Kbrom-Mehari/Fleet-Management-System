package org.securityapps.vehicletracking.Netty.inbound.decoder.teltonika;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class TeltonikaFrameDecoder extends ByteToMessageDecoder {
    private static final int MAX_FRAME_LENGTH = 1024 * 3;//MAX 3 KB - PROTECTS OUR SERVER FROM HUGE PACKET ATTACKS
    private static final int TLT_HEADER_ZEROS = 4;
    private static final int TLT_CRC16_BYTES = 4;//CRC - error check
    private static final int TLT_LENGTH_BYTES = 4;

    public void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out){
        in.markReaderIndex(); // mark start point

        if(in.readableBytes() < TLT_HEADER_ZEROS + TLT_LENGTH_BYTES) return; //at least both are needed

        int reader = in.readerIndex();

        for(int i = 0; i < TLT_HEADER_ZEROS; i++){
            if(in.getUnsignedByte(reader + i) != 0) return ; //not teltonica frame if the first 4 are not zero
        }
        //Data field length is the length of bytes [codec id, number of data 2]. excluding crc
        int dataFieldLength = in.getInt(reader + TLT_HEADER_ZEROS); // length is found after the 4 bytes (zeros)

        if(dataFieldLength <= 0 || dataFieldLength > MAX_FRAME_LENGTH) {
            in.skipBytes(in.readableBytes()); // corrupted data. skip everything to avoid infinite loop and even connection closing
            return;
        }

        int totalPacketBytes = TLT_HEADER_ZEROS + TLT_LENGTH_BYTES + dataFieldLength + TLT_CRC16_BYTES;

        if(in.readableBytes() < totalPacketBytes) {
            return;   //wait for more bytes
        }

        /* readSlice() method slices the totalPacketBytes from the original buffer and advance
         the readerIndex of the original buffer by totalPacketBytes
        */
        in.resetReaderIndex();
        out.add(in.readSlice(totalPacketBytes)); // pass the full frame to the next handler
    }
}
