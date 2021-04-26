package cn.nj.shiro.service.impl;

import cn.nj.shiro.dto.UserMapper;
import cn.nj.shiro.entity.User;
import cn.nj.shiro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ：zty
 * @date ：Created in 2021/4/26 15:00
 * @description ：
 */
@Service
public class UserServiceImpl implements UserService  {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findByAccount(String account) {
        return userMapper.findByAccount(account);
    }
}
