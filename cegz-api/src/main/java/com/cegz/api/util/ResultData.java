package com.cegz.api.util;

/**
 * rest返回对象
 * @author lijiaxin
 * @date 2018年7月19日
 */
public class ResultData {

	/**
	 * 返回码
	 */
	private int code;
	
	/**
	 * 返回提示信息
	 */
	private String message;
	
	/**
	 * 成功标识
	 */
	private boolean success;
	
	/**
	 * 返回数据
	 */
	private Object data;
	
	/**
	 * 分页标识，是否还有其他数据
	 */
	private boolean hasNext;

	public int getCode() {
		return code;
	}

	public ResultData setCode(int code) {
		this.code = code;
		return this;
	}

	public String getMessage() {
		return message;
	}

	public ResultData setMessage(String message) {
		this.message = message;
		return this;
	}

	public boolean isSuccess() {
		return success;
	}

	public ResultData setSuccess(boolean success) {
		this.success = success;
		return this;
	}

	public Object getData() {
		return data;
	}

	public ResultData setData(Object data) {
		this.data = data;
		return this;
	}

	public boolean isHasNext() {
		return hasNext;
	}

	public ResultData setHasNext(boolean hasNext) {
		this.hasNext = hasNext;
		return this;
	}
	
}
