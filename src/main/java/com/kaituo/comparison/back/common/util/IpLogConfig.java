package com.kaituo.comparison.back.common.util;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @Description:
 * @Author: yedong
 * @Date: 2020/6/12 18:22
 * @Modified by:
 */

public class IpLogConfig extends ClassicConverter {


    private static String localIp = "0.0.0.0";

    @Override
    public String convert(ILoggingEvent event) {
        try {
            localIp = InetAddress.getLocalHost().getHostAddress();
            return localIp;
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }


}
