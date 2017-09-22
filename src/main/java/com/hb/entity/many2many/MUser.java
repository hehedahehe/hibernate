package com.hb.entity.many2many;

import javax.persistence.*;
import java.util.Set;


/*
 * @desc
 * @author lirb
 * @datetime 2017/9/19,16:40
 */
@Entity
@Table(name = "TB_MUSER")
public class MUser {

    private int id;
    private String name;
    private Set<MRole> roles;
//    private MRole role;


//    /**
//     * 最简单的关联映射, User端维护关系。
//     * @return
//     */
//    @OneToOne(cascade = CascadeType.ALL)
////    @JoinColumn(name = "ROLE_ID",unique = true)
//    @JoinColumn(name = "ROLE_ID")
//    public MRole getRole() {
//        return role;
//    }

    public MUser(){}

    public MUser(String name){
        this.name = name;
    }

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ID")
    public int getId() {
        return id;
    }

    @Column(name = "NAME")
    public String getName() {
        return name;
    }


    public void setId(int id) {
        this.id = id;
    }


    public void setName(String name) {
        this.name = name;
    }


//    public void setRole(MRole role) {
//        this.role = role;
//    }

    /**
     * 1. cascade ： 级联属性，在删除用户时，并不希望删除role的实体数据，而只希望删除掉其关联关系。
     * 2. fetch ：希望在获取用户信息的时候，同时获取其角色信息。
     * @return
     */
    @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE},fetch = FetchType.EAGER)
    @JoinTable(name="USER_ROLE", joinColumns = @JoinColumn(name = "USER_ID"),inverseJoinColumns = @JoinColumn(name ="ROLE_ID"))
    public Set<MRole> getRoles() {
        return roles;
    }


    public void setRoles(Set<MRole> roles) {
        this.roles = roles;
    }


}
