package com.example.demo.tcp5g.handler;

import com.example.demo.tcp5g.annotation.RequestHandler5g;
import com.example.demo.tcp5g.msg.CmdType5g;
import com.example.demo.tcp5g.msg.MsgVo5g;
import io.netty.channel.ChannelHandlerContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@Slf4j
@Component
@RequestHandler5g(type = CmdType5g.HEART_UPLOAD)
@RequiredArgsConstructor
public class HeartHandler<T> implements BaseHandler5g {

    @Override
    public MsgVo5g handle(MsgVo5g msgVo, ChannelHandlerContext ctx) {
        log.info("Received heartbeat: {}", msgVo.getData());
        String data = msgVo.getData();
        String[] split = data.split(",");
        String uuid = split[0];

        log.info("Device ID: {}", uuid);

        LocalDateTime now = LocalDateTime.now();
        String time = DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(now);
        String startTime = "0800";
        String endTime = "2000";
        Integer interval = 1;
        String sn = split[0];

        int isLevelUp = 0;
        String updateUrl = "0";
        int velocity = 0;
        int direction = 0;
        int length = 465;
        boolean isDisable = false;

        String verDir = "/opt/upgradeSns";
        File allUpgrade = new File(verDir + File.separator + "000000");
        if (allUpgrade.exists()) {
            if (!Constants.upSns.contains(sn)) {
                String url = readUpgradeUrl(allUpgrade);
                isLevelUp = 1;
                updateUrl = url;
                Constants.upSns.add(sn);
            }
        } else {
            File file = new File(verDir);
            ArrayList<String> sns = new ArrayList<>();
            if (file.exists()) {
                File[] snFiles = file.listFiles();
                if (snFiles != null) {
                    for (File snFile : snFiles) {
                        sns.add(snFile.getName());
                    }
                }
            }
            if (sns.contains(sn)) {
                log.info(sn + " upgrade!");
                if (!Constants.upSns.contains(sn)) {
                    String url = readUpgradeUrl(new File(verDir + File.separator + sn));
                    if (!"".equals(url)) {
                        isLevelUp = 1;
                        updateUrl = url;
                    }
                    Constants.upSns.add(sn);
                }
            }
        }

        return response(msgVo, 0, time, uuid, interval, startTime, endTime, velocity, direction, isLevelUp, updateUrl, length, isDisable);
    }
    
    public MsgVo5g response(MsgVo5g msgVo5g, int code, String time, String uuid, Integer interval, String startTime,
                            String endTime, int velocity, int direction, int update, String updateUrl, int length, boolean isDisable) {

        MsgVo5g msgvo = new MsgVo5g();
        msgvo.setType(msgVo5g.getType());
        msgvo.setParams(msgVo5g.getType());

        String jdbcUrl = "SERVER";
        String username = "USERNAME";
        String password = "PASSWORD";                     
        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
            String query = "SELECT CustomerID, TimeZone, dataStartTime, dataEndTime, uploadInterval, DeviceOrientation, TrafficDirection FROM CounterAssignment WHERE uuid = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, uuid);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        String dbInterval = resultSet.getString("uploadinterval");
                        String dbStartTime = resultSet.getString("dataStartTime");
                        String dbEndTime = resultSet.getString("dataEndTime");
                        String dbDirection = resultSet.getString("TrafficDirection");
                        StringBuilder data = new StringBuilder();
                        data.append(code).append(",").append(time).append(",").append(dbStartTime).append(",").append(dbEndTime)
                            .append(",").append(dbInterval).append(",").append(velocity).append(",").append(dbDirection)
                            .append(",").append(update);
                        
                        if (update == 1) {
                            data.append(",").append(updateUrl).append(",").append(length);
                        } else {
                            data.append(",0"); // Append zero for the update field when dbUpdate is 0
                        }
                        
                        data.append(isDisable ? ",1" : ",0");
                        
                        String s = data.toString();
                
                        msgvo.setData(s);
                        msgvo.setLen(s.length());
                        msgvo.setCrcHigh(msgVo5g.getCrcHigh());
                        msgvo.setCrcLow(msgVo5g.getCrcLow());
                
                        return msgvo;
                    }
                }
            }
        } catch (Exception e) {
            log.error("Error processing response", e);
        }

        return null;
    }

    public String readUpgradeUrl(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            return reader.readLine();
        } catch (Exception e) {
            log.error("Error reading upgrade URL", e);
        }
        return "";
    }
}