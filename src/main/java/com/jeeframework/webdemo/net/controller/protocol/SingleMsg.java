
package com.jeeframework.webdemo.net.controller.protocol;

import com.jeeframework.util.io.ByteStream;
import com.jeeframework.util.io.JeeSerializable;

public class SingleMsg implements JeeSerializable
{
	private long		dwUserUin		= 10110;										///<
	private byte[]	sInReserve	= "d d d d d ".getBytes();	///<

	public int unSerialize(ByteStream bs) throws Exception
	{
		this.dwUserUin = bs.popUInt();
		this.sInReserve = bs.popBytes();
		return bs.getReadLength();
	}

	public int serialize(ByteStream bs) throws Exception
	{
		bs.pushUInt(dwUserUin);
		bs.pushBytes(sInReserve);
		return bs.getWrittenLength();
	}

	public long getDwUserUin()
	{
		return dwUserUin;
	}

	public void setDwUserUin(long dwUserUin)
	{
		this.dwUserUin = dwUserUin;
	}

	public byte[] getSInReserve()
	{
		return sInReserve;
	}

	public void setSInReserve(byte[] inReserve)
	{
		sInReserve = inReserve;
	}

	public int getSize()
	{
		return 4 + 4 + ((sInReserve != null) ? sInReserve.length : 0);
	}
}
