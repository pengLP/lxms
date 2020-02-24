package lxms.content;

public enum InsideCodes {

	SUCCESS(1, "操作成功"),
    INSERT_ERROR(-1, "插入异常"),
    DELETE_ERROR(-2, "删除异常"),
    UPDATE_ERROR(-3, "更新异常");
	private int code;
	private String msg;
	private InsideCodes(int code,String msg) {
		this.code = code;
		this.msg = msg;
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
