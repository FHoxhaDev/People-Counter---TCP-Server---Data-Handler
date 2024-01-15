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

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@RequestHandler(type = CmdType.TIME_SYNC)
@RequiredArgsConstructor
public class TimeSyncHandler<T> implements BaseHandler {

    private Logger logger = LoggerFactory.getLogger(TimeSyncHandler.class);


    /**
     * How to handle time synchronization
     * @param msgVo
     * @param ctx
     * @return
     */
    @Override
    public MsgVo handle(MsgVo msgVo, ChannelHandlerContext ctx) {
//        The corresponding xml data files are converted to standard json data
        JSONObject jsonObject = XmlTool.documentToJSONObject(msgVo.getData());
//        Getting a unique value
        String uuid = jsonObject.getString("uuid");

        logger.info("Time synchronization reporting：" + jsonObject);


        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

        MsgVo vvv = response(uuid, 0, msgVo, sdf.format(new Date()), "", "", "","");

        logger.info("Time synchronization reply：" + XmlTool.documentToJSONObject(vvv.getData()));

        return vvv;
    }

    /**
     * This method formats the specified data
     * @param deviceId
     * @param ret
     * @param msgVo
     * @param time
     * @param uploadInterval
     * @param dataStartTime
     * @param dataEndTime
     * @return
     */
    public MsgVo response(String deviceId, int ret, MsgVo msgVo, String time, String uploadInterval, String dataStartTime, String dataEndTime,String timeZone) {
//        Pass in the device id,ret success value of 0, failure value of 1, information object, current time, upload interval, start time and end time, and format
        MsgVo responseMsg = new MsgVo();
        responseMsg.setSeq(msgVo.getSeq());
        responseMsg.setType(msgVo.getType());
        StringBuffer sb = new StringBuffer();
        sb.append("<TIME_SYSNC_RES>");
        sb.append("<uuid>").append(deviceId).append("</uuid>");
        sb.append("<ret>").append(ret).append("</ret>");
        sb.append("<time>").append(time).append("</time>");
        sb.append("<uploadInterval>").append(uploadInterval).append("</uploadInterval>");
        sb.append("<dataStartTime>").append(dataStartTime).append("</dataStartTime>");
        sb.append("<dataEndTime>").append(dataEndTime).append("</dataEndTime>");
        sb.append("<timeZone>").append(timeZone).append("</timeZone>");
        sb.append("</TIME_SYSNC_RES>");
        String pakBody = sb.toString();
        responseMsg.setLen(pakBody.length());
        responseMsg.setData(pakBody);
        return responseMsg;
    }

}
