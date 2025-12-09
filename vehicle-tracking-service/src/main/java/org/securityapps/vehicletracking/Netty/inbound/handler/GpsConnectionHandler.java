package org.securityapps.vehicletracking.Netty.inbound.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.AttributeKey;
import org.securityapps.vehicletracking.Netty.inbound.model.GpsLoginMessage;
import org.securityapps.vehicletracking.Netty.inbound.model.GpsMessage;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GpsConnectionHandler extends ChannelInboundHandlerAdapter {
    //Active device -> channel mapping
    private static final Map<String , Channel> activeDevices = new ConcurrentHashMap<>();
    //channel attribute to store imei
    private static final AttributeKey<String> IMEI = AttributeKey.valueOf("imei");

    @Override
    public void channelActive(ChannelHandlerContext ctx){
        System.out.println("[CONNECTION] New device connected: " + ctx.channel().remoteAddress());
    }
    @Override
    public void channelInactive(ChannelHandlerContext ctx){
        //device disconnected - clean up
        String imei = ctx.channel().attr(IMEI).get();
        if(imei != null){
            activeDevices.remove(imei);
            System.out.println("[DISCONNECT] Device offline: " + imei);
        }
        else{
            System.out.println("[DISCONNECT] Unknown device disconnected.");
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        // msg will be a decoded protocol message received from protocol decoder
        // it could br login message or gps data message
        if(msg instanceof GpsLoginMessage loginMessage){
            handleLogin(ctx,loginMessage);
            return; // we don't forward login message to the next handler (gps message handler)
        }
        if(msg instanceof GpsMessage gpsData){
            String imei = ctx.channel().attr(IMEI).get();

            if(imei == null){
                System.out.println("[WARNING] device sent data before login! closing channel!");
                ctx.close();
                return;
            }
            ctx.fireChannelRead(gpsData);
            return;
        }
        //if it is unknown, pass it downstream
        ctx.fireChannelRead(msg);
    }

    private void handleLogin (ChannelHandlerContext ctx, GpsLoginMessage msg){
        String imei = msg.getImei();
        // save IMEI into attribute channel
        ctx.channel().attr(IMEI).set(imei);
        // mark device as active
        activeDevices.put(imei, ctx.channel());

        System.out.println("[LOGIN] Device authenticated! " + imei);

        //handshake is handled in the protocol decoder. we do nothing about handshake here
    }

}
