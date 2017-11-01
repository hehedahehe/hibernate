package com.hb.entity;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;

/*
 * @desc
 * @author lirb
 * @datetime 2017/10/23,17:19
 */
@Entity
@Table(name = "tb_role")
public class Role {

    private String id;
    private String name;
    private String description;
    private Set<User> users;


    @Id
    @GenericGenerator(name = "generator", strategy = "uuid.hex")
    @GeneratedValue(generator = "generator")
    @Column(name = "id", unique = true, nullable = false, length = 32)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_role", joinColumns = {@JoinColumn(name = "role_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")})
    @Cascade(value = {org.hibernate.annotations.CascadeType.SAVE_UPDATE,
            org.hibernate.annotations.CascadeType.DELETE})
    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
