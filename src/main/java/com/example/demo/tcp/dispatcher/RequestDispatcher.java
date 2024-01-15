package com.example.demo.tcp.dispatcher;


import com.alibaba.fastjson.JSON;
import com.example.demo.tcp.constant.CmdType;
import com.example.demo.tcp.constant.MsgVo;
import com.example.demo.tcp.handler.BaseHandler;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RequestDispatcher {

	private static Map<Byte, BaseHandler> requestHandlerMap = new ConcurrentHashMap<>();

	static Logger logger = LoggerFactory.getLogger(RequestDispatcher.class);
	static Logger DATA_LOGGER = LoggerFactory.getLogger("UPDATE_DATA");

	/**
	 * Message flow processing, classifying messages
	 *
	 * @param channelHandlerContext
	 * @param
	 */
	public static void dispatcher(ChannelHandlerContext channelHandlerContext, MsgVo msg) {
//		Get the type of the message, with hexadecimal 22 for time synchronization and 21 for data reporting
		byte code = msg.getType();
//		Use the code obtained above to find the type
		String command = CmdType.getDescsByCode(code);
		logger.info("{} - {} ===========>{}",String.format("%02x ", code), command, JSON.toJSONString(msg));
//		Selection of methods for subsequent business processing through the acquired code
		BaseHandler baseHandler = (BaseHandler) requestHandlerMap.get(code);
		MsgVo responseMsg = baseHandler.handle(msg, channelHandlerContext);
		logger.info("{} - {} ===========>{}", String.format("%02x ", code), "数据响应", JSON.toJSONString(responseMsg));
		if (responseMsg != null) {
//			If any, the information is returned for output
			channelHandlerContext.writeAndFlush(responseMsg);
		}

	}

	/**
	 * When you start the project, store the code and the corresponding handler classes
	 * @param courseMap
	 */
	public static void setRequestHandlerMap(Map<Byte, BaseHandler> courseMap) {
		if (courseMap != null && courseMap.size() > 0) {
			for (Map.Entry<Byte, BaseHandler> entry : courseMap.entrySet()) {
				requestHandlerMap.put(entry.getKey(), entry.getValue());
			}
		}
	}

}
