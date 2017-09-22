package com.hb.entity;

import java.util.*;

/*
 * @desc
 * @author lirb
 * @datetime 2017/9/11,10:39
 */
public class Students {
    //1. 必须为公有的类
    //2. 必须提供公有的不带参数的默认的构造方法
    //3. 属性私有
    //4. 属性setter/getter封装

    private int sid; //学号
    private String sname; //姓名
    private String gender; //性别
    private Date birthday; //出生日期
    private String address; //地址
    private Set<Subject> subjects = new HashSet<>(0);//初始化大小
    private Set<String> idimgs = new HashSet<>(0);

    public Map<String, Hobby> getHobbyMap() {
        return hobbyMap;
    }

    public void setHobbyMap(Map<String, Hobby> hobbyMap) {
        this.hobbyMap = hobbyMap;
    }

    private Map<String, Hobby> hobbyMap = new HashMap<>(0);

    public Set<String> getIdimgs() {
        return idimgs;
    }


    public void setIdimgs(Set<String> idimgs) {
        this.idimgs = idimgs;
    }

    public Set<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(Set<Subject> subjects) {
        this.subjects = subjects;
    }


    public Students() {
    }

    public Students(int sid, String sname, String gender, Date birthday, String address) {
        this.sid = sid;
        this.sname = sname;
        this.gender = gender;
        this.birthday = birthday;
        this.address = address;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Students{" +
                "sid=" + sid +
                ", sname='" + sname + '\'' +
                ", gender='" + gender + '\'' +
                ", birthday=" + birthday +
                ", address='" + address + '\'' +
                '}';
    }
}