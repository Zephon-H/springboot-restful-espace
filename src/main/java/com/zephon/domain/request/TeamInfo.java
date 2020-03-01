package com.zephon.domain.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

/**
 * @author Zephon
 * @version V1.0
 * @Package com.zephon.domain.request
 * @date 2020/2/29 下午7:42
 * @Copyright ©
 */
@Getter
@Setter
public class TeamInfo implements Serializable {
    /**
     * 队伍名称
     */
    private String name;
    /**
     * 队伍介绍
     */
    private String introduction;
    /**
     * 指导老师
     */
    private Set<String> teacherIds;
    /**
     * 参赛类型
     */
    private String type;
    /**
     * 队员
     */
    private Set<String> teammateIds;
}
