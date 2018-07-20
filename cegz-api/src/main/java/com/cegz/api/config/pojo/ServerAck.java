package com.cegz.api.config.pojo;

import com.cegz.api.util.Constant;
import com.cegz.api.util.ResultData;

/**
 * 返回数据类标准库
 * @author lijiaxin
 * @date 2018年3月29日
 */
public final class ServerAck {
	
	/**
	 * 成功
	 */
	@SuppressWarnings("unused")
	private ResultData success;
	
	/**
	 * 参数异常
	 */
	@SuppressWarnings("unused")
	private ResultData paramError;
	
	/**
	 * 空数据
	 */
	@SuppressWarnings("unused")
	private ResultData emptyData;
	
	/**
	 * 失败
	 */
	@SuppressWarnings("unused")
	private ResultData failure;
	
	/**
	 * 服务异常
	 */
	@SuppressWarnings("unused")
	private ResultData serverError;

	public ResultData getSuccess() {
		 return new ResultData()
				.setCode(Constant.RETURN_CODE_SUCCESS)
				.setMessage("操作成功")
				.setSuccess(true);
	}

	public ResultData getParamError() {
		 return new ResultData()
					.setCode(Constant.RETURN_CODE_NULL)
					.setMessage("参数异常")
					.setSuccess(false);
	}


	public ResultData getEmptyData() {
		 return new ResultData()
					.setCode(Constant.DATA_CODE_NULL)
					.setMessage("无数据")
					.setSuccess(true);
	}

	public ResultData getFailure() {
		 return new ResultData()
					.setCode(Constant.RETURN_CODE_VERIFY_FAILED)
					.setMessage("操作失败")
					.setSuccess(false);
	}

	public ResultData getServerError() {
		 return new ResultData()
					.setCode(Constant.RETURN_CODE_DATABASE_EXCEPTION)
					.setMessage("服务器异常")
					.setSuccess(false);
	}	
	
}
