package com.zephon.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Zephon
 * @version V1.0
 * @Package com.zephon.domain.system
 * @date 2020/2/28 下午12:53
 * @Copyright ©
 */
@Entity
@Getter
@Setter
@Table(name="tb_role")
public class Role implements Serializable {

    /**
     * id
     */
    @Id
    private String id;
    /**
     * 角色名
     */
    private String name;
    /**
     * 角色描述
     */
    private String description;

    @JsonIgnore
    @ManyToMany(mappedBy="roles")
    private Set<User> users = new HashSet<>(0);

//    @JsonIgnore
//    @ManyToMany
//    @JoinTable(name="tb_role_permission",
//            joinColumns={@JoinColumn(name="role_id",referencedColumnName="id")},
//            inverseJoinColumns={@JoinColumn(name="permission_id",referencedColumnName="id")})
//    private Set<Permission> permissions = new HashSet<Permission>(0);

    @Override
    public String toString() {
        return "Role{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
