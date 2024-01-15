package com.example.demo.tcp5g.handler;

import com.example.demo.tcp5g.msg.MsgVo5g;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * @author TRH
 * @description:
 * @Package com.example.testdemo.tcpkeliu1.handler
 * @date 2023/3/22 14:41
 */
@Slf4j
public class TcpServerHandler5g extends SimpleChannelInboundHandler<Object> {
    /**
     * Use skip lists to store connection channels
     */
    public static Map<Integer, Map<String, Channel>> channelSkipMap = new ConcurrentSkipListMap<>();

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("Application's listening channel exception!");
        cause.printStackTrace();
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        // Get the ip of each client connection
        InetSocketAddress ipSocket = (InetSocketAddress) channel.remoteAddress();
        String clientIp = ipSocket.getAddress().getHostAddress();
        InetSocketAddress localSocket = (InetSocketAddress) channel.localAddress();
        // The local port is the key
        int localPort = localSocket.getPort();
        Map<String, Channel> channelMap = channelSkipMap.get(localPort);
        if (CollectionUtils.isEmpty(channelMap)) {
            channelMap = new HashMap<>(4);
        }
        channelMap.put(clientIp, channel);
        channelSkipMap.put(localPort, channelMap);
        log.info("The application adds a listening channel with the client：{} Connection established successfully！", clientIp);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        // Get the ip of each client connection
        Channel channel = ctx.channel();
        InetSocketAddress localSocket = (InetSocketAddress) channel.localAddress();
        int localPort = localSocket.getPort();
        InetSocketAddress ipSocket = (InetSocketAddress) channel.remoteAddress();
        String clientIp = ipSocket.getAddress().getHostAddress();
        Map<String, Channel> channelMap = channelSkipMap.get(localPort);
        channelMap.remove(clientIp);
        log.info("The application removes the listening channel with the client：{} Disconnect！", clientIp);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object msg) throws Exception {
        MsgVo5g msgVo = (MsgVo5g) msg;
        System.err.println(msgVo);
        log.info("Application data received：{}", msgVo.getData());
    }

}
