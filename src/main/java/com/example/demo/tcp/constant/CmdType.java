package com.example.demo.tcp.constant;

public enum CmdType {

	DATA_UPLOAD((byte) 0x21, "Data reporting"),
	TIME_SYNC((byte) 0x22, "Time synchronization"),
	TRAVEL_TRACK_FLOW((byte) 0x23,"Path of travel"),
	;

	private byte typeCode;
	private String descs;

	CmdType(byte typeCode, String descs) {
		this.typeCode = typeCode;
		this.descs = descs;
	}

	public static String getDescsByCode(byte typeCode) {
		CmdType[] enums = CmdType.values();
		for (CmdType e : enums) {
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
