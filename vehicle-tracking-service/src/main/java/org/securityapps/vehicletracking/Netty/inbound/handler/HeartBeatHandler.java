package org.securityapps.vehicletracking.Netty.inbound.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HeartBeatHandler extends ChannelInboundHandlerAdapter {
    private static final AttributeKey<String> IMEI = AttributeKey.valueOf("imei");

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception{
        if(evt instanceof IdleStateEvent event){
            if(event.state() == IdleState.READER_IDLE){
                Channel channel = ctx.channel();
                String imei = channel.attr(IMEI).get();
                if(imei != null){
                    log.info("[HEARTBEAT] Device idle, closing connection: {}", imei);
                }
                else
                    log.warn("[HEARTBEAT] Unknown device idle, closing connection: {}", channel.remoteAddress());

                ctx.close(); // this triggers channel inactive cleanup in GpsConnectionHandler
            }
        }
        super.userEventTriggered(ctx, evt); //continue event propagation even after this handler
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause){
        log.error("[HEARTBEAT] Connection error for device : {} ",ctx.channel().attr(IMEI),cause);
        ctx.close();
    }
}
