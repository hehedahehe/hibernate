package com.hb.service;

import com.hb.entity.MUser;

import java.util.List;

/*
 * @desc
 * @author lirb
 * @datetime 2017/9/26,22:14
 */
public interface IUserService {

    public List<MUser> getAllUser();

    public boolean updateUser(MUser user);
 }
