package com.example.demo.tcp.coder;

import com.example.demo.tcp.constant.MsgVo;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Message decoder
 * @author allen
 * August 7, 2018
 */
public class MsgDecoder extends ByteToMessageDecoder{

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	protected void decode(ChannelHandlerContext paramChannelHandlerContext,
						  ByteBuf in, List<Object> out) throws Exception {
		// TODO Auto-generated method stub
		ByteBuf hbuf = in.readBytes(3);
		byte[] head = new byte[hbuf.readableBytes()];
		short seq = in.readShort();
		byte type = in.readByte();
		short len = in.readShort();
		ByteBuf bodybuf = in.readBytes(len);
		byte[] req = new byte[bodybuf.readableBytes()];
		bodybuf.readBytes(req);
		String body = new String(req, "UTF-8");
		ByteBuf tbuf = in.readBytes(3);
		byte[] tail = new byte[tbuf.readableBytes()];

		MsgVo msgVo = new MsgVo();
		msgVo.setHead(head);
		msgVo.setSeq(seq  & 0x0FFFF);
		msgVo.setType(type);
		msgVo.setLen(len);
		msgVo.setData(body);
		msgVo.setTail(tail);
		out.add(msgVo);
		//Releasing resources
		hbuf.release();
		bodybuf.release();
		tbuf.release();
	}



	public static String strTo16(String s) {
		String str = "";
		for (int i = 0; i < s.length(); i++) {
			int ch = (int) s.charAt(i);
			String s4 = Integer.toHexString(ch);
			str = str + s4;
		}
		return str;
	}
}
