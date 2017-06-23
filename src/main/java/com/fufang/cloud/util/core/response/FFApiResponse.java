package com.fufang.cloud.util.core.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fufang.cloud.util.core.exception.BusinessException;

import java.io.Serializable;

/**
 * 定义RESTful服务数据返回格式
 * 
 * @author minhua.wang
 *
 */
public class FFApiResponse<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 成功
	 */
	public static final String SUCCESS = "000000";
	
	/**
	 * 业务异常
	 */
	public static final String EXCEPTION_BUSINESS = "000001";

	/**
	 * 失败
	 */
	public static final String ERROR = "100000";

	@JsonProperty(value = "code")
	private String code;
	
	@JsonProperty(value = "message")
	private String message;
	
	@JsonProperty(value = "data")
	private T data;

	protected FFApiResponse(String code, String message, T data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}

	/**
	 * 创建请求成功时返回结果对象。
	 * 
	 * @return
	 */
	public static <T> FFApiResponse<T> success(T data) {
		return new FFApiResponse<T>(SUCCESS, "", data);
	}

	/**
	 * 处理指定异常的返回结果。
	 * 
	 * @param e
	 * @return
	 */
	public static <T> FFApiResponse<T> error(Throwable e) {
		// 处理业务异常
		if (e instanceof BusinessException) {
			String message = e.getMessage();
			return response(EXCEPTION_BUSINESS, message);
		}
		return error(e.toString());
	}

	/**
	 * 处理错误消息的返回结果
	 * 
	 * @param message
	 * @return
	 */
	public static <T> FFApiResponse<T> error(String message) {
		return response(ERROR, message, null);
	}
	
	/**
	 * 返回消息及具体的数据
	 * 
	 * @param code
	 * @param message
	 * @return ApiResponse<T>
	 */
	public static <T> FFApiResponse<T> response(String code, String message) {
		return response(code, message, null);
	}

	/**
	 * 返回消息及具体的数据
	 * 
	 * @param code
	 * @param message
	 * @param data
	 * @return ApiResponse<T>
	 */
	public static <T> FFApiResponse<T> response(String code, String message, T data) {
		return new FFApiResponse<T>(code, message, data);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}
