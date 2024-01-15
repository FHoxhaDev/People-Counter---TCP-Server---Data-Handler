package com.example.demo.tcp5g.handler;

import com.example.demo.tcp5g.msg.MsgVo5g;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author TRH
 * @description:
 * @Package com.example.demo.tcp5g.handler
 * @date 2023/3/27 16:20
 */
public interface BaseHandler5g {

    MsgVo5g handle(MsgVo5g msgVo, ChannelHandlerContext ctx);

}
