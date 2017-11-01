package com.hb.entity;

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
@Table(name = "tb_grade")
public class Grade {

    private String id;
    private int score;
    private String description;
    private String userID;

    private int version;


    public Grade(){}

    public Grade(int score, String description){
        this();
        this.score = score;
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

    @Column(name = "score")
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Version
    @Column(name = "grade_version")
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Transient
    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

}
