package com.fufang.cloud.controller;

import com.fufang.cloud.util.core.exception.BusinessException;
import com.fufang.cloud.util.core.response.FFApiResponse;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 基础控制器抽象类
 * 
 * <ul>
 * <li>统一异常处理
 * <li>统一数据返回格式
 * </ul>
 * 
 * @author minhua.wang
 *
 */
public abstract class BaseController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	protected static final String RETURNMESSAGE = "返回结果 -- code:000000(成功),code:000001(业务异常),code:100000(失败)";

	/**
	 * 异常统一处理。
	 */
	@ExceptionHandler
	@ResponseBody
	public Object handleException(HttpServletRequest request, Exception ex) {
		// root cause
		Throwable rootCause = ExceptionUtils.getRootCause(ex);
		// 记录最终需要处理的异常
		Throwable handleEx = rootCause != null ? rootCause : ex;
		// 日志记录，业务异常日志警告
		if (handleEx instanceof BusinessException) {
			String message = handleEx.getMessage();
			logger.warn(message);
		} else {
			// 非业务异常报警处理
			logger.error(handleEx.getMessage(), handleEx);
		}
		// 统一错误返回格式
		return error(handleEx);
	}

	/**
	 * 创建成功时返回结果对象
	 * 
	 * @return ApiResponse<T>
	 */
	protected <T> FFApiResponse<T> success() {
		return success(null);
	}

	/**
	 * 创建成功时返回结果对象
	 * 
	 * @param data
	 * @return ApiResponse<T>
	 */
	protected <T> FFApiResponse<T> success(T data) {
		return FFApiResponse.success(data);
	}

	/**
	 * 处理指定异常的返回结果
	 * 
	 * @param e
	 * @return ApiResponse<T>
	 */
	protected <T> FFApiResponse<T> error(Throwable e) {
		return FFApiResponse.error(e);
	}

	/**
	 * 处理误码消息的返回结果
	 * 
	 * @param message
	 * @return ApiResponse<T>
	 */
	protected <T> FFApiResponse<T> error(String message) {
		return FFApiResponse.error(message);
	}

	/**
	 * 错误消息及具体的数据
	 * 
	 * @param code
	 * @param message
	 * @param data
	 * @return ApiResponse<T>
	 */
	protected <T> FFApiResponse<T> error(String code, String message, T data) {
		return FFApiResponse.response(code, message, data);
	}

	protected <T> FFApiResponse<T> error(String code, String message) {
		return FFApiResponse.response(code, message, null);
	}

	/**
	 * 记录info级别的日志
	 * @param message
	 */
	protected void infoLog(String message) {
		logger.info(loggerMessageFormat(message));
	}
	
	/**
	 * 记录info级别的日志，并且记录参数
	 * @param message
	 * @param paramsJson
	 */
	protected void infoLog(String message, String paramsJson) {
		logger.info(loggerMessageFormat(message, paramsJson));
	}
	
	/**
	 * 记录warn级别日志
	 * @param message
	 */
	protected void warnLog(String message) {
		logger.warn(loggerMessageFormat(message));
	}
	
	/**
	 * 记录warn级别日志，并且记录参数
	 * @param message
	 * @param paramsJson
	 */
	protected void warnLog(String message, String paramsJson) {
		logger.warn(loggerMessageFormat(message, paramsJson));
	}
	
	/**
	 * 记录warn级别日志，并且记录参数
	 * @param message
	 * @param paramsJson
	 * @param e
	 */
	protected void warnLog(String message, String paramsJson, Throwable e) {
		logger.warn(loggerMessageFormat(message, paramsJson), e);
	}
	
	/**
	 * 记录error级别日志
	 * @param message
	 */
	protected void errorLog(String message) {
		logger.error(loggerMessageFormat(message));
	}
	
	/**
	 * 记录error级别日志，并且记录参数
	 * @param message
	 * @param paramsJson
	 */
	protected void errorLog(String message, String paramsJson) {
		logger.error(loggerMessageFormat(message, paramsJson));
	}
	
	/**
	 * 记录error级别日志，并且记录参数
	 * @param message
	 * @param paramsJson
	 * @param e
	 */
	protected void errorLog(String message, String paramsJson, Throwable e) {
		exceptionLog(message, paramsJson, e);
	}
	
	private String loggerMessageFormat(String message) {
		return "message:'" + message + "'";
	}
	
	private String loggerMessageFormat(String message, String paramsJson) {
		return "message:'" + message + "',params:'" + paramsJson + "'";
	}
	
	private void exceptionLog(String message, String paramsJson, Throwable ex) {		
		// 日志记录，业务异常日志警告
		if (ex instanceof BusinessException) {
			logger.warn(loggerMessageFormat(message, paramsJson), ex);
		} else {
			// 非业务异常报警处理
			logger.error(loggerMessageFormat(message, paramsJson), ex);
		}
	}
}
