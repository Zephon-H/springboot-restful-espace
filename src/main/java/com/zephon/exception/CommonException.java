package com.zephon.exception;

import com.zephon.entity.ResultCode;
import lombok.Getter;

/**
 * @author Zephon
 * @version V1.0
 * @Package com.zephon.exception
 * @date 2020/2/29 下午10:00
 * @Copyright ©
 */
@Getter
public class CommonException extends Exception  {

    private ResultCode resultCode;

    public CommonException(ResultCode resultCode) {
        this.resultCode = resultCode;
    }
}
