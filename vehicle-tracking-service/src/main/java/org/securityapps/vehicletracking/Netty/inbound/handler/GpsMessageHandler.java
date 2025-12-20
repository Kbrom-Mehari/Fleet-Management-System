package org.securityapps.vehicletracking.Netty.inbound.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.securityapps.vehicletracking.Netty.attributes.ChannelAttributes;
import org.securityapps.vehicletracking.Netty.inbound.model.GpsMessage;

import java.time.Instant;

@Slf4j
public class GpsMessageHandler extends ChannelInboundHandlerAdapter {

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

        // validating it
        // sending to queue or rabbitmq, kafka etc


    }
}
