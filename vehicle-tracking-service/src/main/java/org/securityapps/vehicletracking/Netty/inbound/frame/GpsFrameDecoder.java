package org.securityapps.vehicletracking.Netty.inbound.frame;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class GpsFrameDecoder extends ByteToMessageDecoder {
    private static final int MAX_FRAME_LENGTH = 2048; // PROTECTS OUR SERVER FROM HUGE PACKET ATTACKS
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in , List<Object> out) throws Exception{
        // If there is no readable bytes, return
        if(in.readableBytes() < 2){
            return;
        }

        in.markReaderIndex(); // mark start point

        if(isTeltonicaFrame(in)){
            in.resetReaderIndex();
            ByteBuf frame = decodeTeltonicaFrame(in);
            if(frame != null){
                out.add(frame);
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
        // For unknown protocol, we have to discard it safely to avoid stuck pipeline
        in.resetReaderIndex();
        in.skipBytes(in.readableBytes());
    }


    private boolean isTeltonicaFrame(ByteBuf in){
        // Teltonica starts with 0x00 0x00 0x00 0x00 prefix
        if(in.readableBytes() < 4) return false; // the first 4 bytes are needed to read payload length field
        int a = in.getUnsignedByte(in.readerIndex());
        int b = in.getUnsignedByte(in.readerIndex()+1);
        return (a == 0 && b ==0);  // teltonica AVL data starts with two zeros
    }
    private ByteBuf decodeTeltonicaFrame(ByteBuf in){
        if(in.readableBytes() < 4) return null; // these 4 bytes are needed to read length field
        int length = in.getInt(in.readerIndex());
        if(length <= 0 || length > MAX_FRAME_LENGTH) {
            in.skipBytes(in.readableBytes());
            return null;
        }
        if(in.readableBytes() < length + 4) {
            return null;   //wait for more bytes
        }

        in.skipBytes(4);
        return in.readRetainedSlice(length); //extract the exact frame after the length byte and return it
    }
    private boolean isGt06Frame(ByteBuf in){   // concox Gt06 GPS devices
        // Gt06 uses 0x78 0x78 or 0x79 0x79 start bytes
        int a = in.getUnsignedByte(in.readerIndex());
        int b = in.getUnsignedByte(in.readerIndex()+1); // the first two start bytes
        return (a == 0x78 && b == 0x78) || (a == 0x79 && b == 0x79);
    }
    private ByteBuf decodeGt06Frame(ByteBuf in){
        if(in.readableBytes() < 5) return null;
        int start1 = in.getUnsignedByte(in.readerIndex());
        int start2 = in.getUnsignedByte(in.readerIndex()+1);

        int length = in.getUnsignedShort(in.readerIndex()+2);

        if(length < 0 || length > MAX_FRAME_LENGTH) {
            in.skipBytes(in.readableBytes());
            return null;
        }
        int frameLength = length + 5;
        if(in.readableBytes() < frameLength) {
            return null; // incomplete - we have to wait
        }
        return in.readRetainedSlice(frameLength);

    }
}
