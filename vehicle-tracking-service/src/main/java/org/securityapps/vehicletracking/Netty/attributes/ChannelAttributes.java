package org.securityapps.vehicletracking.Netty.attributes;

import io.netty.util.AttributeKey;

//reusable channel attribute across handlers
public class ChannelAttributes {
    private ChannelAttributes () {}

    public static final AttributeKey<String> IMEI = AttributeKey.valueOf("imei");
}
