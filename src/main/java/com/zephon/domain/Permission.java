package com.zephon.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Set;

/**
 * @author Zephon
 * @version V1.0
 * @Package com.zephon.domain.system
 * @date 2020/2/29 上午10:34
 * @Copyright ©
 */
@Getter
@Setter
@Table(name="tb_permission")
@Entity
public class Permission implements Serializable {
    @Id
    private String id;
    /**
     * 权限名
     */
    private String name;
    /**
     * 权限类型：1：菜单，2：功能，3：API
     */
    private Integer type;
    /**
     * 权限描述
     */
    private String description;
}
