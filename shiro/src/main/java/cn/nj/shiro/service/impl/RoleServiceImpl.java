package cn.nj.shiro.service.impl;

import cn.nj.shiro.dto.RoleMapper;
import cn.nj.shiro.entity.Role;
import cn.nj.shiro.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ：zty
 * @date ：Created in 2021/4/26 15:00
 * @description ：
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Role> findRoleByUserId(Integer userId) {
        return roleMapper.findRoleByUserId(userId);
    }
}
