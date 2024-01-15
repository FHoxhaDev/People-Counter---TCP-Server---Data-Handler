package com.example.demo.tcp.handler;


import com.example.demo.tcp.constant.MsgVo;
import io.netty.channel.ChannelHandlerContext;

/**
 * Protocols deal with base classes
 * @author allen
 * August 13, 2018
 */
public interface BaseHandler {
	
	
	 MsgVo handle(MsgVo msgVo, ChannelHandlerContext ctx);
	
	
}
