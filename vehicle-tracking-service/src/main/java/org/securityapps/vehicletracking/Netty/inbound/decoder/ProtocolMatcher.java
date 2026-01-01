package org.securityapps.vehicletracking.Netty.inbound.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelPipeline;

public interface ProtocolMatcher {
    //check if the incoming bytes match to this protocol
    boolean matches(ByteBuf in);

    //if matches, the implementation class(e.g TeltonikaProtocolMatcher) configures the pipeline
    void configurePipeline(ChannelPipeline pipeline, String detectorName);
}
