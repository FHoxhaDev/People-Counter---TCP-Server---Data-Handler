package com.example.demo;

import com.example.demo.tcp.server.NettyServer;
import com.example.demo.tcp5g.server.NettyTcpServer5g;
import com.example.demo.tcp5g.server.NettyTcpServer5g2;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Description: TODO
 * @Author Allen
 * @Date 2023/6/14
 * @Version V1.0
 **/
@Component
public class ServerRunner implements ApplicationRunner {

    @Resource
    NettyServer nettyServer;

    @Resource
    NettyTcpServer5g nettyTcpServer5g;
    @Resource
    NettyTcpServer5g2 nettyTcpServer5g2;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //new Thread(nettyServer).start();
        //new Thread(nettyTcpServer5g).start();
        new Thread(nettyTcpServer5g2).start();
    }

}
