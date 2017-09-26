package com.hb.dao.impl;

import com.hb.dao.IMUserDao;
import com.hb.entity.MUser;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
 * @desc
 * @author lirb
 * @datetime 2017/9/26,22:03
 */
@Repository(value = "mUserDao")
public class MUserDao implements IMUserDao {

    @Override
    public List<MUser> getAllUserList() {
        return null;
    }
}
