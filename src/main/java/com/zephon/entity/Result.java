package com.zephon.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Zephon
 * @version V1.0
 * @Package com.zephon.common.entity
 * @date 2020/2/28 下午1:42
 * @Copyright ©
 */
@Data
@NoArgsConstructor
public class Result {
    /**
     * 是否成功
     */
    private boolean success;
    /**
     * 返回码
     */
    private Integer code;
    /**
     * 返回信息
     */
    private String message;
    /**
     * 返回数据
     */
    private Object data;

    public Result(ResultCode resultCode) {
        this.success = resultCode.success;
        this.code = resultCode.code;
        this.message = resultCode.message;
    }

    public Result(ResultCode resultCode, Object data) {
        this.success = resultCode.success;
        this.code = resultCode.code;
        this.message = resultCode.message;
        this.data = data;
    }
}
