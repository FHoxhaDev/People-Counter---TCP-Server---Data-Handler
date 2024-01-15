package com.example.demo.tcp.handler;


import com.example.demo.tcp.annotation.RequestHandler;
import com.example.demo.tcp.constant.CmdType;
import com.example.demo.tcp.constant.MsgVo;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author lzl
 * @description:
 * @Package com.dqwl.environmental.monitor.tcp.handler
 * @date 2021/12/27 9:16
 */
@Slf4j
@Component
@RequestHandler(type = CmdType.TRAVEL_TRACK_FLOW)
public class TravelTrackFlowHandler<T> implements BaseHandler {
//
//    @Resource
//    SysUserService sysUserService;
//
//    @Resource
//    DeviceGatewayDao deviceGatewayDao;
//
//    @Resource
//    EntityFlowService entityFlowService;

    @Override
    public MsgVo handle(MsgVo msgVo, ChannelHandlerContext ctx) {
//        TravelTrackFlowVO travelTrackFlowVO = JSONObject.parseObject(msgVo.getData(),TravelTrackFlowVO.class);
//        log.info(travelTrackFlowVO.toString());
//        EntityFlowEntity entityFlow = new EntityFlowEntity();
//        BeanUtil.copyProperties(travelTrackFlowVO,entityFlow);



//        entityFlow.setCreateTime(new Date());
//        DeviceGatewayEntity device = deviceGatewayDao.selectOne(
//                new QueryWrapper<DeviceGatewayEntity>().lambda().eq(DeviceGatewayEntity::getDeviceSn,travelTrackFlowVO.getDeviceSn()));
//        if (device == null) {
//                Cannot find this device, send error message
//            log.warn("The device does not exist on the platform：{}", travelTrackFlowVO.getDeviceSn());
//            return response(travelTrackFlowVO.getDeviceSn(), 2, msgVo);
//        }
//            Send an error message to bind an entry or entity
//        if (device.getDoorId() == null || device.getEntityId() == null) {
//            log.warn("The device is not tied to stores：{}", travelTrackFlowVO.getDeviceSn());
//            return response(travelTrackFlowVO.getDeviceSn(), 2, msgVo);
//        }
//        String timeZone = sysUserService.getById(device.getCreateUserId()).getTimeZone();
//        if (StringUtils.isNotBlank(timeZone)) {
//            entityFlow.setCreateTime(TimeZoneUtils.getDate(new Date(), timeZone));
//        }
//        Put the name and time point of the device data into the map
//        Map<String, Object> params = new HashMap<>();
//        params.put("name", entityFlow.getDeviceSn());
//        params.put("timepoint", DateUtils.formatDateTime(entityFlow.getTimepoint()));
//            This data is used to query for duplicate data,entityFlowServiceCan be replaced with the corresponding service interface of the respective gateway device flow data record table
//        List<EntityFlowEntity> entityFlowEntities = entityFlowService.queryAll(params);
//        if (entityFlowEntities.size() > 0) {
//                If the data already exists, an error message is sent
//            log.warn("Upload failed, record already exists：deviceSn={},time={}", travelTrackFlowVO.getDeviceSn(), entityFlow.getTimepoint());
//            return response(travelTrackFlowVO.getDeviceSn(), 0, msgVo);
//        }
//        Set the corresponding entity id and door id of the device
//        entityFlow.setEntityId(Long.valueOf(device.getEntityId()));
//        entityFlow.setDoorId(device.getDoorId());
//            Save this data in the flow data table,entityFlowServiceCan be replaced with the corresponding service interface of the respective gateway device flow data record table
//        boolean b = entityFlowService.save(entityFlow);
//        if(b){
//            return response(travelTrackFlowVO.getDeviceSn(),0,msgVo);
//        }
//        log.warn("Upload failed with the parameter:{}",entityFlow.toString());
//        return response(travelTrackFlowVO.getDeviceSn(), 1, msgVo);

        return response("123",1,msgVo);
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
        sb.append("{");
        sb.append("deviceSn:\"").append(deviceId).append("\"");
        sb.append("code:\"").append(ret).append("\"");
        sb.append("}");
        String pakBody = sb.toString();
        responseMsg.setLen(pakBody.length());
        responseMsg.setData(pakBody);
        return responseMsg;
    }
}
