/*
 * @project: webdemo 
 * @package: com.jeeframework.webdemo.net
 * @title:   ClientIoHandler.java 
 *
 * Copyright (c) 2016 Hyfay Limited, Inc.
 * All rights reserved.
 */
package com.jeeframework.webdemo.net;

import org.apache.mina.core.RuntimeIoException;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

/**
 * 描述
 *
 * @author lance
 * @version 1.0 2016-03-10 12:08
 */
public class ClientIoHandler extends IoHandlerAdapter {

    private void releaseSession(IoSession session) throws Exception {
        System.out.println("releaseSession");
        if (session.isConnected()) {
            session.closeOnFlush();
        }
    }

    @Override
    public void sessionOpened(IoSession session) throws Exception {
        System.out.println("sessionOpened");
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        System.out.println("sessionClosed");
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
        System.out.println("sessionIdle");
        try {
            releaseSession(session);
        } catch (RuntimeIoException e) {
        }
    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        System.out.println("Receive Server message " + message);

        super.messageReceived(session, message);

        releaseSession(session);
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        System.out.println("exceptionCaught");
        cause.printStackTrace();
        releaseSession(session);
    }

    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
        System.out.println("messageSent");
        super.messageSent(session, message);
    }

}