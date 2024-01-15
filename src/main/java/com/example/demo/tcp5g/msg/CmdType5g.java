package com.example.demo.tcp5g.msg;

/**
 * @author TRH
 * @description:
 * @Package com.example.testdemo.tcpkeliu1.msg
 * @date 2023/3/23 16:42
 */
public enum CmdType5g {
    HEART_UPLOAD((byte) 0xD1, "Reporting of heartbeat"),
    DATA_UPLOAD((byte) 0xD2, "Data reporting"),
    DATA_UPLOAD_HISTORY((byte) 0xD3, "Reporting of historical data"),
    LEVEL_UPLOAD((byte) 0xD4,"Firmware upgrade"),
    ;

    private byte typeCode;
    private String descs;

    CmdType5g(byte typeCode, String descs) {
        this.typeCode = typeCode;
        this.descs = descs;
    }

    public static String getDescsByCode(byte typeCode) {
        CmdType5g[] enums = CmdType5g.values();
        for (CmdType5g e : enums) {
            if (e.typeCode == typeCode) {
                return e.descs;
            }
        }
        return null;
    }

    public byte getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(byte typeCode) {
        this.typeCode = typeCode;
    }

    public String getDescs() {
        return descs;
    }

    public void setDescs(String descs) {
        this.descs = descs;
    }

}
