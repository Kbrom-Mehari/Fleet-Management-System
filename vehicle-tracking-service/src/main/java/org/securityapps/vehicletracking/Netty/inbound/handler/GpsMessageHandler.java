package org.securityapps.vehicletracking.Netty.inbound.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.securityapps.vehicletracking.Netty.attributes.ChannelAttributes;
import org.securityapps.vehicletracking.Netty.inbound.model.GpsMessage;
import org.securityapps.vehicletracking.Netty.inbound.model.GpsMessageAdapterFactory;
import org.securityapps.vehicletracking.Netty.publisher.GpsMessagePublisher;
import org.securityapps.vehicletracking.common.dto.NormalizedGpsMessage;
import org.securityapps.vehicletracking.common.util.GpsMessageToEventMapper;
import org.securityapps.vehicletracking.domain.event.GpsRecordedEvent;

import java.time.Instant;

@Slf4j
@RequiredArgsConstructor
public class GpsMessageHandler extends ChannelInboundHandlerAdapter {
    private final GpsMessagePublisher publisher;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg){
        String imei = ctx.channel().attr(ChannelAttributes.IMEI).get();

        if(!(msg instanceof GpsMessage gpsMessage)){
            ctx.fireChannelRead(msg);
            return;
        }

        if(imei == null){
            log.error("[GPS] Received data without login! closing channel...");
            return;
        }
        gpsMessage.setImei(imei);
        gpsMessage.setReceivedAt(Instant.now()); //server time

        NormalizedGpsMessage normalized = GpsMessageAdapterFactory.adapt(gpsMessage);
        GpsRecordedEvent event = GpsMessageToEventMapper.map(normalized);

        publisher.publish(event);
    }
}
