package lxms.content;

public enum StatusCodes {
	/**
	 * 请求成功
	 */
	S100(100,"ok"),
	/**
	 * 请求失败
	 */
	S101(101,"request faild"),
	/*
	 * 验证码错误
	 */
	S102(102,"Verification code error"),
	/**
	 * 用户不存在
	 */
	S103(103,"user does not exist"),
	/**
	 * 用户名或密码错误
	 */
	S104(104,"username or password error"),
	/**
	 * 服务器内部错误
	 */
	S105(105,"server internal error"),
	/**
	 * 服务器超时
	 */
	S106(106,"(timeout)"),
	/**
	 * 未知错误
	 */
	S107(107,"unknown error"),
	/**
	 * 验证码过期
	 */
	S108(108,"verify expired"),
	/**
	 * 没有数据
	 */
	S109(109,"no data"),
	/**
	 * 用户存在
	 */
	S110(110,"user exist"),
	/**
	 * 清登录
	 */
	S111(111,"please login"),
	/**
	 * 验证码超时
	 */
	S112(112,"Verification code timeout"),
	/**
	 * 操作失败
	 */
	S113(113,"operation failed"),
	SUCCESS(1, "操作成功"),
    INSERT_ERROR(-1, "插入异常"),
    DELETE_ERROR(-2, "删除异常"),
    UPDATE_ERROR(-3, "更新异常"),
	
	SIGN_SUCCESS(114,"sign is true"),
	SIGN_FALSE(115,"sign is error");
	private int code;
	private String msg;
	private StatusCodes(int code,String msg) {
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
