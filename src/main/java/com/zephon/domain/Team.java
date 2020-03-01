package com.zephon.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Zephon
 * @version V1.0
 * @Package com.zephon.domain
 * @date 2020/2/29 下午7:23
 * @Copyright ©
 */
@Entity
@Getter
@Setter
@Table(name = "tb_team")
public class Team {
    @Id
    private String id;
    /**
     * 队伍名称
     */
    private String name;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 队伍人数
     */
    private Integer num;
    /**
     * 点赞人数
     */
    private Integer likeNum;
    /**
     * 队伍介绍
     */
    private String introduction;
    /**
     * 队伍成员
     */
    @JsonIgnore
    @ManyToMany(targetEntity = User.class)
    @JoinTable(name="tb_user_team",
            joinColumns={@JoinColumn(name="team_id",referencedColumnName="id")},
            inverseJoinColumns={@JoinColumn(name="user_id",referencedColumnName="id"),}
    )
    private Set<User> users = new HashSet<>();

}
