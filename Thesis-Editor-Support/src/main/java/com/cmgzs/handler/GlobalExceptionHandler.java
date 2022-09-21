package com.cmgzs.handler;

import com.cmgzs.domain.base.ApiResult;
import com.cmgzs.exception.AuthException;
import com.cmgzs.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Auther: hzy
 * @Date: 2022/4/28
 * @Description: 全局异常处理
 * 处理整个web controller的异常
 */
@Slf4j
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {


    /**
     * 自定义异常
     */
    @ExceptionHandler(CustomException.class)
    public ApiResult CustomExceptionHandler(Exception e) {
        log.error("异常是:{}", e.getMessage());
        return ApiResult.error(e.getMessage());
    }

    /**
     * 自定义异常
     */
    @ExceptionHandler(AuthException.class)
    public ApiResult AuthExceptionHandler(AuthException e) {
        log.error("异常是:{}", e.getMessage());
        return ApiResult.error(e.getCode(), e.getMessage());
    }

    /**
     * 处理校验异常，对于对象类型的数据的校验异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResult handler(MethodArgumentNotValidException e) {
        log.error("异常是:{}", e.getMessage());
        StringBuffer sb = new StringBuffer();
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        allErrors.forEach(msg -> sb.append(msg.getDefaultMessage()).append(";"));
        return ApiResult.error(sb.toString());
    }

    /**
     * 请求方式不支持
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ApiResult handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e,
                                                         HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',不支持'{}'请求", requestURI, e.getMethod());
        return ApiResult.error(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ApiResult handler(Exception e) {
        log.error("异常是:{}", e.getMessage());
        return ApiResult.error(e.getMessage());
    }
}
