package com.hb.entity;

import javax.persistence.*;
import java.util.Set;

/*
 * @desc
 * @author lirb
 * @datetime 2017/9/19,16:40
 */
@Entity
@Table(name = "TB_MROLE")
public class MRole {

    private int id;
    private String name;
    //    private MUser user;
    private Set<MUser> users;

    /**
     * 在角色端，则不包含任何的级联属性，即Role的任何操作，都不会影响到
     * User实体
     * @return
     */
    @ManyToMany(mappedBy = "roles")
    public Set<MUser> getUsers() {
        return users;
    }

    public void setUsers(Set<MUser> users) {
        this.users = users;
    }


    public MRole(){}

    public MRole(String name){
        this.name = name;

    }

//    @OneToOne(mappedBy = "role")
//    public MUser getUser() {
//        return user;
//    }
//
//    public void setUser(MUser user) {
//        this.user = user;
//    }


    //GenerationType.IDENTITY 从1开始
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ROLE_ID")
    @Id
    public int getId() {
        return id;
    }

    @Column(name = "NAME", unique = true)
    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }



    public void setName(String name) {
        this.name = name;
    }


}
