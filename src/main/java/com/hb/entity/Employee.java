package com.hb.entity;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/*
 * @desc
 * @author lirb
 * @datetime 2017/10/23,17:20
 */
@Entity
@Table(name = "tb_employee")
public class Employee {
    private String id;
    private String name;
    private String description;
    private Set<User>  users = new HashSet<>();

    public Employee(){}

    public Employee(String name, String description) {
        this.name = name;
        this.description = description;
    }

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

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    public Set<User> getUsers() {
        return users;
    }
    public void setUsers(Set<User> users) {
        this.users = users;
    }


}
