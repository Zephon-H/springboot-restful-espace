package com.zephon.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Zephon
 * @version V1.0
 * @Package com.zephon.domain
 * @date 2020/2/29 下午2:06
 * @Copyright ©
 */
@Getter
@Setter
@Entity
@Table(name = "tb_competition")
@NoArgsConstructor
@ToString
public class Competition implements Serializable {
    @Id
    private String id;
    /**
     * 竞赛名称
     */
    private String name;
    /**
     * 竞赛创建时间
     */
    private Date createTime;
    /**
     * 竞赛获奖情况
     */
    private String prize;
    /**
     * 竞赛类别
     */
    private String type;
    /**
     * 赛级
     */
    private int level;
    /**
     * 竞赛报名开始时间
     */
    private Date enrollStart;
    /**
     * 竞赛报名截止时间
     */
    private Date enrollEnd;
    /**
     * 竞赛开始时间
     */
    private Date compStart;
    /**
     * 竞赛结束时间
     */
    private Date compEnd;
    /**
     * 竞赛主办方
     */
    private String host;
    /**
     * 竞赛简介
     */
    private String summary;
    /**
     * 竞赛组织机构
     */
    private String institution;
    /**
     * 参赛对象
     */
    private String audience;
    /**
     * 参赛费用：每队参赛的报名费用
     */
    private Double cost;
    /**
     * 奖项设置
     */
    private String priceSetting;
    /**
     * 竞赛联系方式
     */
    private String contactInformation;
}
