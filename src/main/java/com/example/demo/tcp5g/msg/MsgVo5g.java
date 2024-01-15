package com.example.demo.tcp5g.msg;
import com.microsoft.sqlserver.jdbc.SQLServerDriver;
/**
 * @author TRH
 * @description:
 * @Package com.example.demo.tcp5g.msg
 * @date 2023/3/27 16:12
 */
public class MsgVo5g {

    /**
     * The starting byte is 1 byte
     */
    private byte head = (byte) 0x7E;
    /**
     * Device sn4 bytes
     */
    private byte[] sn = new byte[] { (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF };
    /**
     * The message instruction type is 1 byte
     */
    private byte type;
    /**
     * The parameter section is 1 byte
     */
    private byte params;
    /**
     * The length of the packet body data is 2 bytes
     */
    private int len;
    /**
     * The package body contains the data
     */
    private String data;

    /**
     * crc data validation is low
     */
    private byte    crcLow;

    /**
     * crc data check height
     */
    private byte    crcHigh;

    /**
     * End of frame
     */
    private Byte   tail= (byte) 0x7D;

    public MsgVo5g() {
    }

    public MsgVo5g(byte head, byte[] sn, byte type, byte params, int len, String data, byte crcLow, byte crcHigh, Byte tail) {
        this.head = head;
        this.sn = sn;
        this.type = type;
        this.params = params;
        this.len = len;
        this.data = data;
        this.crcLow = crcLow;
        this.crcHigh = crcHigh;
        this.tail = tail;
    }

    public byte getHead() {
        return head;
    }

    public void setHead(byte head) {
        this.head = head;
    }

    public byte[] getSn() {
        return sn;
    }

    public void setSn(byte[] sn) {
        this.sn = sn;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public byte getParams() {
        return params;
    }

    public void setParams(byte params) {
        this.params = params;
    }

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public byte getCrcLow() {
        return crcLow;
    }

    public void setCrcLow(byte crcLow) {
        this.crcLow = crcLow;
    }

    public byte getCrcHigh() {
        return crcHigh;
    }

    public void setCrcHigh(byte crcHigh) {
        this.crcHigh = crcHigh;
    }

    public Byte getTail() {
        return tail;
    }

    public void setTail(Byte tail) {
        this.tail = tail;
    }
}
