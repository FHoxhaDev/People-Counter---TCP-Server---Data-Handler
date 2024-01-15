package com.example.demo.tcp5g.coder;

import com.example.demo.tcp5g.msg.MsgVo5g;
import com.example.demo.tcp5g.utils.CRCUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.nio.charset.StandardCharsets;

/**
 * @author TRH
 * @description:
 * @Package com.example.demo.tcp5g.coder
 * @date 2023/3/27 16:17
 */
public class NettyEncoder5g extends MessageToByteEncoder<MsgVo5g> {
    @Override
    protected void encode(ChannelHandlerContext ctx, MsgVo5g msg, ByteBuf out) throws Exception {
//Write header byte
        out.writeByte(msg.getHead());
        out.writeBytes(msg.getSn());
        out.writeByte(msg.getType());
        out.writeByte(msg.getParams());
        out.writeShort(msg.getLen());
        byte[] bytes = msg.getData().getBytes(StandardCharsets.UTF_8);
//        List data = new ArrayList<>();
        ByteBuf buf = Unpooled.buffer(bytes.length);
        for (int i = 0; i < bytes.length; i++) {
            switch (bytes[i]){
                case 0x7D:
                    buf.writeByte((byte)0x7c);
                    buf.writeByte((byte)0x5D);
                    break;
                case 0x7E:
                    buf.writeByte((byte)0x7c);
                    buf.writeByte((byte)0x5E);
                    break;
                default:
                    buf.writeByte(bytes[i]);
                    break;
            }
        }
//      Write the escaped data
        out.writeBytes(buf);


        ByteBuf crcDemo = Unpooled.buffer(bytes.length+9);
        crcDemo.writeBytes(msg.getSn());
        crcDemo.writeByte(msg.getType());
        crcDemo.writeByte(msg.getParams());
        crcDemo.writeShort(msg.getLen());
        crcDemo.writeBytes(bytes);
        byte[] crcBytes = ByteBufUtil.getBytes(crcDemo);
//        String crc = CRCUtils.getCRC(crcBytes);
//        System.err.println(bytesToHexString(crcBytes));
//        System.err.println(crc);
        byte[] bytesCrc = CRCUtils.getByteCRC(crcBytes);
//        System.err.println(bytesToHexString(bytesCrc));


        out.writeByte(bytesCrc[0]);
        out.writeByte(bytesCrc[1]);
        out.writeByte(msg.getTail());
//        release
        buf.release();
        crcDemo.release();
        ctx.flush();
    }
}
