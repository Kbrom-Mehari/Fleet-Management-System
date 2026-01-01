package org.securityapps.vehicletracking.Netty.inbound.decoder.Gt06;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelPipeline;
import org.securityapps.vehicletracking.Netty.inbound.decoder.ProtocolMatcher;

public class Gt06ProtocolMatcher implements ProtocolMatcher {
    public boolean matches(ByteBuf in){
        // Gt06 uses 0x78 0x78 or 0x79 0x79 start bytes
        int a = in.getUnsignedByte(in.readerIndex());
        int b = in.getUnsignedByte(in.readerIndex()+1); // the first two start bytes
        return (a == 0x78 && b == 0x78) || (a == 0x79 && b == 0x79);
    }

    public void configurePipeline(ChannelPipeline pipeline, String detectorName){

    }
}
