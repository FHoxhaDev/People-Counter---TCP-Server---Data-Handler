package com.example.demo.tcp.server;


import com.example.demo.tcp.coder.MsgDecoder;
import com.example.demo.tcp.coder.MsgEncoder;
import com.example.demo.tcp.handler.MsgServerOutHandler;
import com.example.demo.tcp.handler.ServerChannelHandlerAdapter;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Component
public class NettyServer implements Runnable {

	static org.slf4j.Logger logger = LoggerFactory.getLogger(NettyServer.class);
	
    ServerBootstrap serverBootstrap = new ServerBootstrap();
    
	// Used on the server side to accept connections from clients
	EventLoopGroup bossGroup = new NioEventLoopGroup();
	// Used for network event processing
	EventLoopGroup workerGroup = new NioEventLoopGroup();
	
	/**
	 * Channel adapter
	 */
	@Resource
    private ServerChannelHandlerAdapter channelHandlerAdapter;

    private int port  = 8085;

	@Override
	public void run() {
		try {
			serverBootstrap.group(bossGroup, workerGroup)
	            .channel(NioServerSocketChannel.class)
	            .option(ChannelOption.SO_BACKLOG, 1024)
	            .option(ChannelOption.SO_KEEPALIVE, true)
	            .handler(new LoggingHandler(LogLevel.DEBUG))
	            .childHandler(new ChannelInitializer<SocketChannel>() {
	                @Override
	                public void initChannel(SocketChannel ch)throws Exception {
	                    ch.pipeline().addLast("frameDecoder", new LengthFieldBasedFrameDecoder(905969674,6,2,3,0));
	                    ch.pipeline().addLast("MsgDecoder", new MsgDecoder());
	                    ch.pipeline().addLast(new IdleStateHandler(1, 0, 0, TimeUnit.MINUTES));
	                    ch.pipeline().addLast(new MsgEncoder());
	                    ch.pipeline().addLast(channelHandlerAdapter);
	                    ch.pipeline().addLast(new MsgServerOutHandler());

	                }
	            });
            ChannelFuture f = serverBootstrap.bind(port).sync();
            logger.info("-----------------------------------------------");
            logger.info("TCP/IP Server start on port "+port+" success!");
            logger.info("-----------------------------------------------");
            f.channel().closeFuture().sync();
        }catch (Exception e) {
        	logger.info("TCP/IP Server start fail:"+e.getLocalizedMessage());
            e.printStackTrace();
        } finally {
        	logger.info("TCP/IP close");
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
	}
	
	 /**
     * Shutdown server method
     */
    @PreDestroy
    public void close() {
    	  logger.info("-----------------------------------------------");
          logger.info("------TCP/IP Server start on port "+port+" close!");
          logger.info("-----------------------------------------------");
        //Exit gracefully
    	bossGroup.shutdownGracefully();
    	workerGroup.shutdownGracefully();
    }
}
