package com.zephon.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Zephon
 * @version V1.0
 * @Package com.zephon.domain
 * @date 2020/2/29 下午6:04
 * @Copyright ©
 */
@Entity
@Getter
@Setter
@Table(name = "tb_project")
public class Project implements Serializable {
    @Id
    private String id;
    /**
     * 项目名
     */
    private String name;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 项目截止日期
     */
    private Date deadline;
    /**
     * 项目类型
     */
    private String type;
    /**
     * 报名开始时间
     */
    private Date enrollStart;
    /**
     * 报名截止时间
     */
    private Date enrollEnd;
    /**
     * 项目简介
     */
    private String summary;
    /**
     * 项目要求
     */
    private String requirement;
    /**
     * 联系方式
     */
    private String contact;
    /**
     * 指导老师
     */
    private String teacherId;

}
