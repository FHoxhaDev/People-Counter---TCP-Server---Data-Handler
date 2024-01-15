package com.example.demo.tcp5g.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author TRH
 * @description:
 * @Package com.example.demo.tcp5g.utils
 * @date 2023/3/27 16:28
 */
@Data
public class HongWaiVoMsg {
    private Integer update;
    private String time;
    private String updateUrl;
    private String startTime;
    private String endTime;
    private int code;
    private Integer interval;
    private String msg;
}
