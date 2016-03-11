/*
 * @project: webdemo 
 * @package: com.jeeframework.webdemo.net
 * @title:   C2CTestClient.java 
 *
 * Copyright (c) 2016 Hyfay Limited, Inc.
 * All rights reserved.
 */
package com.jeeframework.webdemo.net;

import com.jeeframework.webdemo.net.protocol.MinaNetDataCodecFactory;
import org.apache.mina.core.RuntimeIoException;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.SocketConnector;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import java.net.ConnectException;
import java.net.InetSocketAddress;

/**
 * 描述
 *
 * @author lance
 * @version 1.0 2016-03-10 0:03
 */
public class NetMinaTestClient {

    public SocketConnector socketConnector;

    /**
     * 缺省连接超时时间
     */
    public static final int DEFAULT_CONNECT_TIMEOUT = 5;

    public static final String HOST = "127.0.0.1";

    public static final int PORT = 5050;

    public NetMinaTestClient() {
        init();
    }

    public void init() {
        socketConnector = new NioSocketConnector();

        // 长连接
        // socketConnector.getSessionConfig().setKeepAlive(true);

        socketConnector.setConnectTimeout(DEFAULT_CONNECT_TIMEOUT);

        socketConnector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new MinaNetDataCodecFactory()));

        NetClientHandler ioHandler = new NetClientHandler();
        socketConnector.setHandler(ioHandler);
    }

    public void sendMessage(final String msg) {
        InetSocketAddress addr = new InetSocketAddress(HOST, PORT);
        ConnectFuture cf = socketConnector.connect(addr);
        try {
            cf.awaitUninterruptibly();


//            cf.getSession().write(msg);
//            System.out.println("send message " + msg);
            System.out.println("发送数据完成");


            IoSession session = cf.getSession();
            session.getConfig().setUseReadOperation(true);
            session.getCloseFuture().awaitUninterruptibly();
            System.out.println("After Writing");
        } catch (RuntimeIoException e) {
            if (e.getCause() instanceof ConnectException) {
                try {
                    if (cf.isConnected()) {
                        cf.getSession().close();
                    }
                } catch (RuntimeIoException e1) {
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        NetMinaTestClient clent = new NetMinaTestClient();

        clent.sendMessage("Hello World ");
//        clent.getSocketConnector().dispose(true);
        //System.exit(0);
    }

    public SocketConnector getSocketConnector() {
        return socketConnector;
    }

    public void setSocketConnector(SocketConnector socketConnector) {
        this.socketConnector = socketConnector;
    }

}
