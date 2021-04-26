package cn.nj.shiro.service;

import cn.nj.shiro.entity.Role;

import java.util.List;

/**
 * @author ：zty
 * @date ：Created in 2021/4/26 14:55
 * @description ：角色服务层
 */
public interface RoleService {

    /**
     * 通过用户ID 查询所有的角色
     * @param userId  用户ID
     * @return 所有的角色
     */
    List<Role> findRoleByUserId(Integer userId);
}
