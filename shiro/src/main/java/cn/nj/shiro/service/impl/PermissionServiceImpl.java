package cn.nj.shiro.service.impl;

import cn.nj.shiro.dto.PermissionMapper;
import cn.nj.shiro.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ：zty
 * @date ：Created in 2021/4/26 15:02
 * @description ：
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;



    @Override
    public List<String> findByRoleId(List<Integer> roleIds) {
        return permissionMapper.findByRoleId(roleIds);
    }
}
