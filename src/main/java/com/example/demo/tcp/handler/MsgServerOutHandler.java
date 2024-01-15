package com.example.demo.tcp.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.SocketAddress;

/**
 * Data sending
 * @author allen
 */
public class MsgServerOutHandler extends ChannelOutboundHandlerAdapter{
	
	private Logger logger = LoggerFactory.getLogger(MsgServerOutHandler.class);

	@Override
	public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress,
			SocketAddress localAddress, ChannelPromise promise)
			throws Exception {
		logger.warn("connect .... ");
		super.connect(ctx, remoteAddress, localAddress, promise);
	}

}
