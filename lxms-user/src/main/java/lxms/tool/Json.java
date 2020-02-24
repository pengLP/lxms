	package lxms.tool;

import java.util.List;

import lxms.content.StatusCodes;

public class Json implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int code = 107;
	
	private String msg = "unknown error";

	private Object obj = null;

	private List list = null;
	
	

	
	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setCode(StatusCodes code) {
		this.code = code.getCode();
		this.msg = code.getMsg();
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
