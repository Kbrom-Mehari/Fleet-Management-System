package org.securityapps.vehicletracking.Netty.inbound.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.AttributeKey;

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

    }
}
