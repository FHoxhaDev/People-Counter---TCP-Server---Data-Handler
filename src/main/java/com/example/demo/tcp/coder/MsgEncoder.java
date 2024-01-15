package com.example.demo.tcp.coder;


import com.example.demo.tcp.constant.MsgVo;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Message encoder
 * @author allen
 * August 7, 2018
 */
public class MsgEncoder extends MessageToByteEncoder<MsgVo> {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	protected void encode(ChannelHandlerContext ctx, MsgVo msg, ByteBuf out)
			throws Exception {
		//Write header byte
		out.writeBytes(new byte[]{(byte) 0xFA,(byte) 0xF5,(byte) 0xF6});
		//Write the pipeline number
		out.writeShort(msg.getSeq());
		//Writing the message type
		out.writeByte(msg.getType());
		//Write the packet body length
		out.writeShort(msg.getLen());
		//Writing packet data
		out.writeBytes(msg.getData().getBytes());
		//End of write byte
		out.writeBytes(new byte[]{(byte) 0xFA,(byte) 0xF6,(byte) 0xF5});
		//byte[] bs = ByteObjConverter.ObjectToByte(obj);
		//out.writeBytes(bs);
		ctx.flush();
	}
	


}
