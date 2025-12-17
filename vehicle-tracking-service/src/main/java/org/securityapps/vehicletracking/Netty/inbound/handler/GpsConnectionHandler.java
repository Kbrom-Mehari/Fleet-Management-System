package org.securityapps.vehicletracking.Netty.inbound.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.securityapps.vehicletracking.Netty.attributes.ChannelAttributes;
import org.securityapps.vehicletracking.Netty.inbound.model.DeviceLoginMessage;
import org.securityapps.vehicletracking.Netty.inbound.model.GpsMessage;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class GpsConnectionHandler extends ChannelInboundHandlerAdapter {
    //Active device -> channel mapping, we map device imei with channel
    private static final Map<String , Channel> activeDevices = new ConcurrentHashMap<>();


    @Override
    public void channelActive(ChannelHandlerContext ctx){
        log.info("[CONNECTION] New device connected: {}", ctx.channel().remoteAddress());
    }
    @Override
    public void channelInactive(ChannelHandlerContext ctx){
        //device disconnected - clean up
        String imei = ctx.channel().attr(ChannelAttributes.IMEI).get();
        if(imei != null){
            activeDevices.remove(imei,ctx.channel());
            log.info("[DISCONNECT] Device offline: {}", imei);
        }
        else{
            log.warn("[DISCONNECT] Unknown device disconnected.");
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        // msg will be a decoded protocol message received from protocol decoder
        // it could be login message or gps data message
        if(msg instanceof DeviceLoginMessage loginMessage){
            handleLogin(ctx,loginMessage);
            return; // we don't forward login message to the next handler (gps message handler)
        }
        if(msg instanceof GpsMessage gpsData){
            String imei = ctx.channel().attr(ChannelAttributes.IMEI).get();

            if(imei == null){
                log.warn("[WARNING] device sent data before login! closing channel!");
                ctx.close();
                return;
            }
            ctx.fireChannelRead(gpsData);
            return;
        }
        //if it is unknown, pass it downstream
        ctx.fireChannelRead(msg);
    }

    private void handleLogin (ChannelHandlerContext ctx, DeviceLoginMessage msg){
        String imei = msg.getImei();

        //put method returns null if there is no existing channel and return the old value if
        //we are updating it
        Channel existing = activeDevices.put(imei, ctx.channel());

        //prevent an active device from connecting twice. we disconnect the old channel
        if(existing != null && existing != ctx.channel()){
            log.warn("[LOGIN] Duplicate connection detected for {}", imei + " Closing old channel.");
            existing.close();
        }

        // save IMEI into attribute channel
        ctx.channel().attr(ChannelAttributes.IMEI).set(imei);

        log.info("[LOGIN] Device authenticated: {}", imei);


        //handshake is handled in the protocol decoder. we do nothing about handshake here
    }

}