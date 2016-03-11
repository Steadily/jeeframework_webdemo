/*
 * @project: webdemo 
 * @package: com.jeeframework.webdemo.net
 * @title:   NetNIOTest.java 
 *
 * Copyright (c) 2016 Hyfay Limited, Inc.
 * All rights reserved.
 */
package com.jeeframework.webdemo.net;

import com.jeeframework.logicframework.util.server.tcp.protocol.MinaProtocolHeader;
import com.jeeframework.util.io.ByteStream;
import com.jeeframework.webdemo.net.controller.protocol.NetDataReq;
import com.jeeframework.webdemo.net.controller.protocol.NetDataResp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * 描述
 *
 * @author lance
 * @version 1.0 2016-03-10 22:38
 */
public class NetNIOTest {
    final int SEND_BUF_LEN = 1024 * 1024;

    public static void main(String[] args) {
        String ip = "127.0.0.1";
        int port = 5050;
        SocketChannel sock = null;
        try {
            sock = SocketChannel.open(new InetSocketAddress(ip, port));

            sock.socket().setSoLinger(true, 0);
            sock.socket().setReuseAddress(true);

            NetDataReq msg = new NetDataReq();
            msg.setDwUserUin(12341234);

            byte[] msgdata = new byte[msg.getSize() + MinaProtocolHeader.HEADER_LEN];
            ByteStream bs = new ByteStream();
            bs.resetBuffer(msgdata, msgdata.length);
            MinaProtocolHeader header = new MinaProtocolHeader();
            header.setCmdId(msg.getCmdId());
            header.setTotalLen(msgdata.length);
            header.setRetcode(msg.getResult());

            header.serialize(bs);

            msg.serialize(bs);
            ByteBuffer buf = ByteBuffer.allocate(msgdata.length);
            buf.put(msgdata);
            buf.flip();

            sock.write(buf);

            Thread.sleep(1000);

            // 接收回包
            ByteBuffer responseHeaderBuf = ByteBuffer.allocate(MinaProtocolHeader.HEADER_LEN);
            while (sock.isConnected() && responseHeaderBuf.hasRemaining() && (sock.read(responseHeaderBuf) > 0)) {
            }
            responseHeaderBuf.flip();

            ByteStream headerBS = new ByteStream();
            headerBS.resetBuffer(responseHeaderBuf.array(), responseHeaderBuf.capacity());

            MinaProtocolHeader headerResp = new MinaProtocolHeader();
            headerResp.unSerialize(headerBS);

//            long cmdId = responseHeaderBuf.getInt();
            long msgDataTotalLen = headerResp.getTotalLen();
//            long retcode = responseHeaderBuf.getInt();


            int msgBodyLen = (int)(msgDataTotalLen - MinaProtocolHeader.HEADER_LEN);

            ByteBuffer body = ByteBuffer.allocate(msgBodyLen);
            while (sock.isConnected() && body.hasRemaining() && sock.read(body) > 0) {
            }
            body.flip();

            ByteStream responseBS = new ByteStream();
            responseBS.resetBuffer(body.array(), body.capacity());

            NetDataResp resp = new NetDataResp();
            resp.unSerialize(responseBS);
            System.out.println(" nio test     " + new String(resp.getSOutReserve()));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                sock.close();


            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }


    }
}
