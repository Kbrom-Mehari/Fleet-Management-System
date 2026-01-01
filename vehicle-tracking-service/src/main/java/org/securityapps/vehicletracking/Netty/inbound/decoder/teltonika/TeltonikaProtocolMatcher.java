package org.securityapps.vehicletracking.Netty.inbound.decoder.teltonika;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelPipeline;
import org.securityapps.vehicletracking.Netty.inbound.decoder.ProtocolMatcher;

public class TeltonikaProtocolMatcher implements ProtocolMatcher {
    private static final int TLT_HEADER_ZEROS = 4;

    public boolean matches(ByteBuf in){
        //Teltonika starts with 0x00 0x00 0x00 0x00 prefix
        if(in.readableBytes() < TLT_HEADER_ZEROS) return false; // the first 4 bytes(prefix) are needed
        int reader = in.readerIndex();
        for(int i = 0; i < TLT_HEADER_ZEROS; i++){
            if(in.getUnsignedByte(reader + i) != 0) return false;//the first 4 bytes should be 0x00
        }
        return true;
    }

    public void configurePipeline(ChannelPipeline pipeline, String detectorName){

    }
}
