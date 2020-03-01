package com.zephon.domain.response;

import com.zephon.domain.Role;
import com.zephon.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Zephon
 * @version V1.0
 * @Package com.zephon.domain.system.response
 * @date 2020/2/28 下午2:48
 * @Copyright ©
 */
@Getter
@Setter
@NoArgsConstructor
public class ProfileResult implements Serializable {
    private String userId;
    private String mobile;
    private String stuOrTeacherId;
    private String username;
    private String password;
    private String introduction;
    private String avatar;

    private Set<String> roles = new HashSet<>();


    public ProfileResult(User user) {
        this.userId = user.getId();
        this.mobile = user.getMobile();
        this.stuOrTeacherId = user.getStuOrTeacherId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.introduction = user.getIntroduction();
        this.avatar = user.getAvatar();
        for (Role role : user.getRoles()) {
            this.roles.add(role.getName());
        }
    }
}
