/*
 * @project: webdemo 
 * @package: com.jeeframework.logicframework.util.server.tcp.codec
 * @title:   MinaCodecFactory.java 
 *
 * Copyright (c) 2016 Hyfay Limited, Inc.
 * All rights reserved.
 */
package com.jeeframework.webdemo.net.protocol;

import com.jeeframework.logicframework.util.server.tcp.codec.MinaServerEncoder;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

/**
 * mina服务器的解码协议
 *
 * @author lance
 * @version 1.0 2016-03-09 16:09
 */
public class MinaNetDataCodecFactory implements ProtocolCodecFactory {

    private final MinaServerEncoder encoder;
    private final MinaNetDataDecoder decoder;

    public MinaNetDataCodecFactory() {
        encoder = new MinaServerEncoder();
        decoder = new MinaNetDataDecoder();
    }

    public ProtocolEncoder getEncoder(IoSession session) throws Exception {
        return encoder;
    }

    public ProtocolDecoder getDecoder(IoSession session) throws Exception {
        return decoder;
    }

}
