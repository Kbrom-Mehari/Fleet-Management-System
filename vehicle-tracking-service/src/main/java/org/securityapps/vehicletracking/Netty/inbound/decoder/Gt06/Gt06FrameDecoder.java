package org.securityapps.vehicletracking.Netty.inbound.decoder.Gt06;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class Gt06FrameDecoder extends ByteToMessageDecoder {
    private static final int MAX_FRAME_LENGTH = 1024 * 3;//MAX 3 KB - PROTECTS OUR SERVER FROM HUGE PACKET ATTACKS

    public void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out){
        in.markReaderIndex();
        int reader = in.readerIndex();

        if(in.readableBytes() < 8) return; // 2 start bit + 1 length + 1 protocol ... + 2 crc + 2 end bytes

        int start1 = in.getUnsignedByte(reader++);
        int start2 = in.getUnsignedByte(reader++);

        if(!((start1 == 0x78 && start2 == 0x78)|| (start1 == 0x79 && start2 == 0x79))){
            // if not Gt06, skip
            return;
        }
        int payloadLength = in.getUnsignedByte(reader);

        if(payloadLength <= 0 || payloadLength > MAX_FRAME_LENGTH){
            in.skipBytes(in.readableBytes());
            return;
        }
        /*Gt06 full frame length is - start(2 bytes) + payload length(1 byte) +
        payload[from protocol number up to crc/errorCheck](length bytes) +
        stop(2 bytes) */

        //full frameLength = start(2 bytes) + payloadLength(1 byte) + payload(length bytes) + stop(2 bytes)
        int frameLength = payloadLength + 5;

        if(in.readableBytes() < frameLength){
            return ; // if it is incomplete frame, we wait until full frame arrives
        }
        in.resetReaderIndex();
        out.add(in.readRetainedSlice(frameLength)); // pass the full frame to the next handler
    }
}
