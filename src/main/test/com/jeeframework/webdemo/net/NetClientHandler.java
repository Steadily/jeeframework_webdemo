/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *  
 *    http://www.apache.org/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License. 
 *  
 */
package com.jeeframework.webdemo.net;


import com.jeeframework.webdemo.net.controller.protocol.NetDataReq;
import com.jeeframework.webdemo.net.controller.protocol.NetDataResp;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

public class NetClientHandler extends IoHandlerAdapter {
	@Override
	public void sessionCreated(IoSession session) throws Exception {
		super.sessionCreated(session);
	}

	public void sessionOpened(IoSession session) {

		NetDataReq req = new NetDataReq();
		req.setDwUserUin(12341234);
		session.write(req);

	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		super.exceptionCaught(session, cause);
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		super.messageSent(session, message);
		System.err.println("发送数据包...");
	}

	public void sessionClosed(IoSession session) {
		System.err.println("会话关闭");
	}

	public void sessionIdle(IoSession session, IdleStatus status) {
	}

	public void messageReceived(IoSession session, Object message) {
		NetDataResp resp = (NetDataResp) message;
		System.out.println(new String(resp.getSOutReserve()));

		System.err.println("收到消息:  getSInReserve = " + new String(resp.getMsgs().get(0).getSInReserve()) + "， dwUserUin = " + resp.getMsgs().get(0).getDwUserUin() );
		//
		session.close();
	}
}
