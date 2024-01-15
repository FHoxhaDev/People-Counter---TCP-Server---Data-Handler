package com.example.demo.tcp5g.handler;

import com.example.demo.tcp5g.annotation.RequestHandler5g;
import com.example.demo.tcp5g.msg.CmdType5g;
import com.example.demo.tcp5g.msg.MsgVo5g;
import io.netty.channel.ChannelHandlerContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author TRH
 * @description: Historical data
 * @Package com.example.demo.tcp5g.handler
 * @date 2023/4/6 15:12
 */
@Component
@RequestHandler5g(type = CmdType5g.DATA_UPLOAD_HISTORY)
@Slf4j
@RequiredArgsConstructor
public class HistoryDataHandler implements BaseHandler5g{

    @Override
    public MsgVo5g handle(MsgVo5g msgVo, ChannelHandlerContext ctx) {

        String data = msgVo.getData();
        log.info("Received historical data report：{}", data);
        String[] split = data.split(",");
//sn
        String uuid = split[0];
        String in = split[2];
        String out = split[3];
        String batteryLevel = split[4];
        String batteryTxLevel = split[5];
        String warnstatus = split[6];
        String fwVersion = split[8];
        String signal = "0";
        String recType = "3";
        String time = split[1];
        String jdbcUrl = "SERVER";
        String username = "USERNAME";
        String password = "PASSWORD"; 
        
        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
            String insertQuery = "INSERT INTO UP_SENSOR_DATA_REQ (uuid, rec_type, [in], [out], time, battery_level, warn_status, batterytx_level, signal_status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
            try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                insertStatement.setString(1, uuid);
                insertStatement.setString(2, recType);
                insertStatement.setString(3, in);
                insertStatement.setString(4, out);
                insertStatement.setString(5, time);
                insertStatement.setString(6, batteryLevel);
                insertStatement.setString(7, warnstatus);
                insertStatement.setString(8, batteryTxLevel);
                insertStatement.setString(9, signal);
                // Execute the insert statement
                insertStatement.executeUpdate();
                
                String selectCustomerSql = "SELECT CustomerID, dataStartTime, dataEndTime, DeviceOrientation, TrafficDirection FROM CounterAssignment WHERE uuid = ?";
                try (PreparedStatement selectCustomerStatement = connection.prepareStatement(selectCustomerSql)) {
                    selectCustomerStatement.setString(1, uuid);
                    ResultSet resultSet = selectCustomerStatement.executeQuery();

                    if (resultSet.next()) {
                        int customerId = resultSet.getInt("CustomerID");
                        String dataStartTime = resultSet.getString("dataStartTime");
                        String dataEndTime = resultSet.getString("dataEndTime");
                        String deviceOrientation = resultSet.getString("DeviceOrientation");
                        String trafficDirection = resultSet.getString("TrafficDirection");
                    
                        // Perform the necessary calculations
                        LocalDateTime calculatedStartDateTime = LocalDateTime.parse(time, DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
                        String formattedStartDateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS").format(calculatedStartDateTime);
                        int calculatedTrafficCount = 0;
                    
                        if ("in".equals(trafficDirection) && "normal".equals(deviceOrientation)) {
                            calculatedTrafficCount = Integer.parseInt(in);
                        } else if ("out".equals(trafficDirection) && "normal".equals(deviceOrientation)) {
                            calculatedTrafficCount = Integer.parseInt(out);
                        } else if ("in".equals(trafficDirection) && "reverse".equals(deviceOrientation)) {
                            calculatedTrafficCount = Integer.parseInt(out);
                        } else if ("out".equals(trafficDirection) && "reverse".equals(deviceOrientation)) {
                            calculatedTrafficCount = Integer.parseInt(in);
                        } else if ("in/out".equals(trafficDirection)) {
                            calculatedTrafficCount = Integer.parseInt(in) + Integer.parseInt(out);
                        }
                    
                        if (calculatedTrafficCount != 0 && time.compareTo(dataStartTime) > 0 && time.compareTo(dataEndTime) < 0) {
                            String insertTrafficSql = "INSERT INTO Traffic (CustomerId, StartDateTime, TimeUnit, isHoliday, TrafficCount) VALUES (?, ?, ?, ?, ?)";
                            try (PreparedStatement insertTrafficStatement = connection.prepareStatement(insertTrafficSql)) {
                                insertTrafficStatement.setInt(1, customerId);
                                insertTrafficStatement.setString(2, formattedStartDateTime);
                                insertTrafficStatement.setInt(3, 0); // Set TimeUnit and isHoliday accordingly
                                insertTrafficStatement.setInt(4, 0);
                                insertTrafficStatement.setInt(5, calculatedTrafficCount);
                    
                                // Execute the insert statement
                                insertTrafficStatement.executeUpdate();
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            // Handle SQL exception
            e.printStackTrace();
        }
        int isLevelUp = 0;
        String updateUrl = "0";

        return response(msgVo, 0, time,isLevelUp, updateUrl);
    }


    public MsgVo5g response(MsgVo5g msgVo5g, int code, String time, int update, String updateUrl) {
        MsgVo5g msgvo = new MsgVo5g();
        msgvo.setType(msgVo5g.getType());
        msgvo.setParams(msgVo5g.getType());
        StringBuilder data = new StringBuilder();
//        Status code
        data.append(code);
        data.append(",");
//        Time
        data.append(time);
        data.append(",");
//        Upgrade or not
        data.append(update);
        data.append(",");
//        Updated path
        data.append(updateUrl);

        String s = data.toString();
        msgvo.setData(s);
        msgvo.setLen(s.length());
        msgvo.setCrcHigh(msgVo5g.getCrcHigh());
        msgvo.setCrcLow(msgVo5g.getCrcLow());
        return msgvo;
    }
}
