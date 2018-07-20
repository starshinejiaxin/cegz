package com.cegz.api.controller.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cegz.api.util.Constant;
import com.cegz.api.util.ResultData;
import com.cegz.api.util.ServerException;

/**
 * 控制层异常处理类，控制层所有抛出异常都由该类进行处理
 * @author gaowang
 * @date 2018/04/04
 */
@ControllerAdvice
public class ExceptionController {

	/**
	 * 异常处理方法
	 * @author gaowang
	 * @date 2018/04/04
	 * @return
	 */
	@ResponseBody
	@ExceptionHandler(Exception.class)
	public ResultData restExceptionHandle(Exception e){
		if(e instanceof ServerException) {  
			ServerException businessException = (ServerException)e;  
            return businessException.getResultData();
        }  
		e.printStackTrace();
      return new ResultData()
    		  .setSuccess(false)
    		  .setCode(Constant.RETURN_CODE_DATABASE_EXCEPTION)
    		  .setMessage("服务器繁忙，请稍后重试");
    } 
}
