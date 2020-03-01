package com.zephon.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Zephon
 * @version V1.0
 * @Package com.zephon.domain.system
 * @date 2020/2/28 下午12:31
 * @Copyright ©
 * 用户实体类
 */
@Entity
@Table(name = "tb_user")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class User implements Serializable {
    /**
     * id
     */
    @Id
    private String id;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 学号/教师号
     */
    private String stuOrTeacherId;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 启用状态：0：未启用，1：已启用
     */
    private int enableState;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 个人简介
     */
    private String introduction;
    /**
     * 在职状态：0：离职，1：在职
     */
    private int inServiceStatus;
    /**
     * 头像
     */
    private String avatar;

    /**
     * fetch = FetchType.EAGER：立即加载
     */
    @JsonIgnore
    @ManyToMany(targetEntity = Role.class,fetch = FetchType.EAGER)
    @JoinTable(name="tb_user_role",
            joinColumns={@JoinColumn(name="user_id",referencedColumnName="id")},
            inverseJoinColumns={@JoinColumn(name="role_id",referencedColumnName="id"),}
    )
    private Set<Role> roles = new HashSet<>();

    @JsonIgnore
    @ManyToMany(mappedBy="users")
    private Set<Team> teams = new HashSet<>();

}
