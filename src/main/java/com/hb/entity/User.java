package com.hb.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;

/*
 * @desc
 * @author lirb
 * @datetime 2017/10/23,17:20
 */
@Entity
@Table(name = "tb_user")
public class User {

    private String id;
    private String name;
    private String description;
    private Set<Role> roles;
    private Grade grade;

//    private Employee employee;


    public User(){}

    public User(String username){
        this.name = username;
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


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_role", joinColumns = {@JoinColumn(name = "user_id")},inverseJoinColumns = {@JoinColumn(name = "role_id")})
    @Cascade(value = {org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    public Set<Role> getRoles() {
        return roles;
    }
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @OneToOne(fetch = FetchType.EAGER)
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.DELETE})
    @JoinColumn(name = "grade_id")
    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "employee_id")
//    @Cascade(org.hibernate.annotations.CascadeType.ALL)
//    public Employee getEmployee() {
//        return employee;
//    }
//    public void setEmployee(Employee employee) {
//        this.employee = employee;
//    }

    @Override
    public boolean equals(Object o) {

        if (o == this) return true;
        if (!(o instanceof User)) {
            return false;
        }
        User tableMap = (User) o;

        return new EqualsBuilder()
                .append(this.id, tableMap.id)
                .append(this.name, tableMap.name)
                .isEquals();
    }
    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(name)
                .toHashCode();
    }



}
