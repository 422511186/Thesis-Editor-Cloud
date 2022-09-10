package com.cmgzs.handler;

import com.cmgzs.domain.base.ApiResult;
import com.cmgzs.exception.UserNameExistedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

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
     * 注册异常 用户名已经存在
     *
     * @param e
     * @return
     */
    @ExceptionHandler(UserNameExistedException.class)
    public ApiResult UserNameExistedExceptionHandler(Exception e) {
        log.error("异常是:{}", e.getMessage());
        return ApiResult.error(e.getMessage());
    }

    //处理校验异常，对于对象类型的数据的校验异常
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResult handler(MethodArgumentNotValidException e) {
        log.error("异常是:{}", e.getMessage());
        StringBuffer sb = new StringBuffer();
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        allErrors.forEach(msg -> sb.append(msg.getDefaultMessage()).append(";"));
        return ApiResult.error(sb.toString());
    }


    @ExceptionHandler(Exception.class)
    public ApiResult handler(Exception e) {
        log.error("异常是:{}", e.getMessage());
        e.printStackTrace();
        return ApiResult.error("出现未知异常啦!请联系管理员查看日志。");
    }
}
