package com.cegz.api.util;

/**
 * 服务异常类
 * @author gaowang
 * @date 2018/04/04
 */
public class ServerException extends RuntimeException {

	private static final long serialVersionUID = 1001L;
	
	/**
	 * 返回对象，该对象中包含异常原因，异常码，成功等信息
	 */
	private ResultData resultData;
	
	/**
	 * 公共类，初始化返回对象
	 */
	public ServerException () {
		this.resultData = new ResultData();
		this.resultData.setSuccess(false)
			.setHasNext(false)
			.setMessage(null)
			.setData(null)
			.setCode(0);
	}

	/**
	 * 返回异常对象信息
	 * @author gaowang
	 * @date 2018/04/04
	 * @return
	 */
	public ResultData getResultData() {
		return resultData;
	}

	/**
	 * 设置返回码
	 * @author gaowang
	 * @date 2018/04/04
	 * @return
	 */
	public ServerException setCode(Integer code) {
		if (code != null) {
			this.resultData.setCode(code);
		}
		return this;
	}
	
	/**
	 * 设置返回信息
	 * @author gaowang
	 * @date 2018/04/04
	 * @return
	 */
	public ServerException setMessage(String message) {
		if (message != null) {
			this.resultData.setMessage(message);
		}
		return this;
	}
	
	/**
	 * 设置返回成功标识
	 * @author gaowang
	 * @date 2018/04/04
	 * @return
	 */
	public ServerException setSuccess(Boolean success) {
		if (success != null) {
			this.resultData.setSuccess(success);
		}
		return this;
	}

}
