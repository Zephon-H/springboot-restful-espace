package com.zephon.handler;

import com.zephon.entity.Result;
import com.zephon.entity.ResultCode;
import com.zephon.exception.CommonException;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Zephon
 * @version V1.0
 * @Package com.zephon.handler
 * @date 2020/2/29 下午9:59
 * @Copyright ©
 */
@ControllerAdvice
public class BaseExceptionHandler {
    @ExceptionHandler(AuthorizationException.class)
    @ResponseBody
    public Result error(HttpServletRequest request, HttpServletResponse response){
        return new Result(ResultCode.UNAUTHORISE);
    }

    @ExceptionHandler(value=Exception.class)
    @ResponseBody
    public Result error(HttpServletRequest request, HttpServletResponse response, Exception e){
        e.printStackTrace();
        if(e.getClass()== CommonException.class){
            // 类型转型
            CommonException ce = (CommonException) e;
            Result result = new Result(ce.getResultCode());
            return result;
        }else{
            Result result = new Result(ResultCode.SERVER_ERROR);
            return result;
        }
    }
}
