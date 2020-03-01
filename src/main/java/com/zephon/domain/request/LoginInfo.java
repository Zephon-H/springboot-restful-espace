package com.zephon.domain.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Zephon
 * @version V1.0
 * @Package com.zephon.domain.system.request
 * @date 2020/2/29 下午1:44
 * @Copyright ©
 */
@Getter
@Setter
public class LoginInfo implements Serializable {
    @ApiModelProperty(name="mobile",value = "手机号",required = true,example = "13211111111")
    private String mobile;
    @ApiModelProperty(name="password",value = "密码",required = true,example = "123456")
    private String password;
}
