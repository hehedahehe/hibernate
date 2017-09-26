package com.hb.service.impl;

import com.hb.entity.MUser;
import com.hb.service.IUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/*
 * @desc
 * @author lirb
 * @datetime 2017/9/26,22:15
 */
@Service
public class UserService implements IUserService {

    @Override
    public List<MUser> getAllUser() {
        return null;
    }

    @Override
    @Transactional
    public boolean updateUser(MUser user) {
        return false;
    }
}
