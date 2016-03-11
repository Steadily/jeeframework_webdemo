
package com.jeeframework.webdemo.net.controller.protocol;

import com.jeeframework.logicframework.util.server.tcp.protocol.NetData;
import com.jeeframework.util.io.ByteStream;

import java.util.Vector;

public class NetDataResp extends NetData
{
	private long							dwMsgCnt	= 1;
	private byte[]						sOutReserve;
	private Vector<SingleMsg>	msgs;

	public long getCmdId()
	{
		return 0x88211803L;
	}

	public int serialize(ByteStream bs) throws Exception
	{
		bs.pushUInt(this.dwMsgCnt);
		bs.pushBytes(sOutReserve);
		bs.pushVector(msgs);
		return bs.getWrittenLength();
	}



	@SuppressWarnings("unchecked")
	public int unSerialize(ByteStream bs) throws Exception
	{
		dwMsgCnt = bs.popUInt();
		sOutReserve = bs.popBytes();
		//ArrayList<String>  s = (ArrayList<String>)bs.popList(ArrayList.class, String.class);
		msgs = (Vector<SingleMsg>) bs.popVector(SingleMsg.class);

		return bs.getReadLength();
	}

	public long getDwMsgCnt() {
		return dwMsgCnt;
	}

	public void setDwMsgCnt(long dwMsgCnt) {
		this.dwMsgCnt = dwMsgCnt;
	}

	public byte[] getSOutReserve() {
		return sOutReserve;
	}

	public void setSOutReserve(byte[] outReserve) {
		sOutReserve = outReserve;
	}

	public Vector<SingleMsg> getMsgs() {
		return msgs;
	}

	public void setMsgs(Vector<SingleMsg> msgs) {
		this.msgs = msgs;
	}

	public int getSize() {
		int l = 4 + 4 + ((sOutReserve != null) ? sOutReserve.length : 0) + 4;
		if (msgs != null)
		{
			for (int i = 0; i < msgs.size(); i++)
			{
				l += msgs.elementAt(i).getSize();
			}
		}

		return l;
	}
}
