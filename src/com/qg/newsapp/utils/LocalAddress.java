package com.qg.newsapp.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class LocalAddress {
    public static  String GetIPAddress(){
        InetAddress inetAddress;
        try {
            inetAddress = InetAddress.getLocalHost();
            return inetAddress.getHostAddress();
        } catch (UnknownHostException e) {
            System.out.println("获取本地地址失败");
        }finally {
        }
        return null;

    }

    public static void main(String[] args) {
        System.out.println(LocalAddress.GetIPAddress());
    }
}
