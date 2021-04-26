package cn.nj.shiro.service;

import cn.nj.shiro.entity.User;

/**
 * @author ：zty
 * @date ：Created in 2021/4/26 14:55
 * @description ：用户服务层
 */
public interface UserService {

    /**
     * 通过账号查询用户信息
     * @param account 账号
     * @return 用户信息
     */
    User findByAccount(String account);



}
