package com.example.demo.tcp.constant;



public class MsgVo {

	/**
	 * The starting byte is three bytes
	 */
	private byte[] head = new byte[] { (byte) 0xFA, (byte) 0xF5, (byte) 0xF6 };
	/**
	 * The pipeline number is 2 bytes
	 */
	private int seq;
	/**
	 * The message type is 1 byte
	 */
	private byte type;
	/**
	 * The length of the packet body data is 4 bytes
	 */
	private int len;
	/**
	 * The package body contains the data
	 */
	private String data;
	/**
	 * The end byte is 3 bytes
	 */
	private byte[] tail = new byte[] { (byte) 0xFA, (byte) 0xF6, (byte) 0xF5 };

	public MsgVo() {

	}

	public MsgVo(byte[] head, int seq, byte type, int len, String data, byte[] tail) {
		this.head = head;
		this.seq = seq;
		this.type = type;
		this.len = len;
		this.data = data;
		this.tail = tail;
	}

	public byte[] getHead() {
		return head;
	}

	public void setHead(byte[] head) {
		this.head = head;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
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

	public byte[] getTail() {
		return tail;
	}

	public void setTail(byte[] tail) {
		this.tail = tail;
	}

	public MsgVo(short seq, byte type, int len, String data) {
		this.seq = seq;
		this.type = type;
		this.len = len;
		this.data = data;
	}

	public static String addXmlHead(String content) {
		return "<?xml version=\"1.0\" encoding=\" UTF-8\" ?>" + content;
	}
}
