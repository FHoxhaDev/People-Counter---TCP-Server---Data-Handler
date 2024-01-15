package com.example.demo.tcp5g.dispatcher;

import com.alibaba.fastjson.JSON;
import com.example.demo.tcp.constant.CmdType;
import com.example.demo.tcp5g.handler.BaseHandler5g;
import com.example.demo.tcp5g.msg.MsgVo5g;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author TRH
 * @description: Message distribution
 * @Package com.example.demo.tcp5g.dispatcher
 * @date 2023/3/27 16:19
 */
@Slf4j
public class RequestDispatcher5g {
    private static Map<Byte, BaseHandler5g> requestHandlerMap= new ConcurrentHashMap<>();


    public  static void dispatcher(ChannelHandlerContext channelHandlerContext, MsgVo5g msgVo5g){
//        Gets the message handler type
        byte code = msgVo5g.getType();
//        Find the corresponding annotation
        String command = CmdType.getDescsByCode(code);
        log.info("{} - {} ===========>{}",String.format("%02x ", code), command, JSON.toJSONString(msgVo5g));

//		Selection of methods for subsequent business processing through the acquired code
        BaseHandler5g baseHandler = (BaseHandler5g) requestHandlerMap.get(code);
        if (baseHandler == null){
            String sTemp = Integer.toHexString(0xFF & code);
            log.info("Instruction does not exist: ======>{}",sTemp);
            return;
        }
        MsgVo5g responseMsg = baseHandler.handle(msgVo5g, channelHandlerContext);
        log.info("{} - {} ===========>{}", String.format("%02x ", code), "Data response", JSON.toJSONString(responseMsg));
        if (responseMsg != null) {
//			If any, the information is returned for output
            channelHandlerContext.writeAndFlush(responseMsg);
        }
    }

    /**
     * When you start the project, store the code and the corresponding handler classes
     * @param courseMap
     */
    public static void setRequestHandlerMap(Map<Byte, BaseHandler5g> courseMap) {
        if (courseMap != null && courseMap.size() > 0) {
            for (Map.Entry<Byte, BaseHandler5g> entry : courseMap.entrySet()) {
                requestHandlerMap.put(entry.getKey(), entry.getValue());
            }
        }
    }
}
