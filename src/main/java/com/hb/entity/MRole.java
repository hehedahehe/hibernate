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
     * 在角色端，则不包含任何的级联属性，即Role的任何操作，都不会影响到。
     * User实体
     * 1. 此处，将fetchtype设置为LZAY(默认值)，而不是EAGER，而在MUser端则设置为EAGER
     * 2. 原因：应用场景决定的。在加载用户时，希望加载其角色信息。当加载了Role时，此时若将Role对
     *      User的关联设置为EAGER的话，则会将该Role下的所有用户信息进行加载，而应用并不希望获取
     *      其他用户的信息。
     * @return
     */
    @ManyToMany(mappedBy = "roles",fetch = FetchType.LAZY)
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
