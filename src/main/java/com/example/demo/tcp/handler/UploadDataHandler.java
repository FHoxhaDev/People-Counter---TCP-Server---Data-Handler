package com.example.demo.tcp.handler;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.tcp.annotation.RequestHandler;
import com.example.demo.tcp.constant.CmdType;
import com.example.demo.tcp.constant.MsgVo;
import com.example.demo.tcp.util.XmlTool;
import io.netty.channel.ChannelHandlerContext;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequestHandler(type = CmdType.DATA_UPLOAD)
@RequiredArgsConstructor
public class UploadDataHandler<T> implements BaseHandler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Data reporting process
     * @param msgVo
     * @param ctx
     * @return
     */
    @Override
    public MsgVo handle(MsgVo msgVo, ChannelHandlerContext ctx) {
//        Parse the xml format data in the data into json format
        JSONObject jsonObject = XmlTool.documentToJSONObject(msgVo.getData());

        logger.info("Passenger flow data reporting：" + jsonObject);

//        Retrieves a device unique value
        String uuid = jsonObject.getString("uuid");


//        LocalDateTime now = LocalDateTime.now();
//        time = DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(now);
//        Calendar ca = Calendar.getInstance();
//        timeZone = ca.getTimeZone().getID();

        MsgVo msgVo1 = response(uuid, 0, msgVo);

        logger.info("Passenger flow data reply：" + XmlTool.documentToJSONObject(msgVo1.getData()));

//        The specified information is returned successfully
        return msgVo1;
    }

    /**
     * Format the returned data
     * @param deviceId
     * @param ret
     * @param msgVo
     * @return
     */
    public MsgVo response(String deviceId, int ret, MsgVo msgVo) {
        MsgVo responseMsg = new MsgVo();
        responseMsg.setSeq(msgVo.getSeq());
        responseMsg.setType(msgVo.getType());
        StringBuffer sb = new StringBuffer();
        sb.append("<UP_SENSOR_DATA_RES>");
        sb.append("<uuid>").append(deviceId).append("</uuid>");
        sb.append("<ret>").append(ret).append("</ret>");
//        sb.append("<timeZone>").append(timeZone).append("</timeZone>");
        sb.append("</UP_SENSOR_DATA_RES>");
//        sb.append("<time>").append(time).append("</time>");
        String pakBody = sb.toString();
        responseMsg.setLen(pakBody.length());
        responseMsg.setData(pakBody);
        return responseMsg;
    }


}
