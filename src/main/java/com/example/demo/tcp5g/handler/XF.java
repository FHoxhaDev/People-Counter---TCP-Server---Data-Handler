package com.example.demo.tcp5g.handler;

import java.io.File;

/**
 * @Description: TODO
 * @Author Allen
 * @Date 2023/9/27
 * @Version V1.0
 **/
public class XF {

    public static void main(String[] args) {
        String verDir = "/opt/upgradeSns";
        File allUpgrade = new File(verDir + "/000000");
        if(allUpgrade.exists()){
            System.out.println("222222222");
        }else{
            System.out.println("3333333333");
        }
    }

}
