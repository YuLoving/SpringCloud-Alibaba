package cn.nj.shiro.service;

import java.util.List;

/**
 * @author ：zty
 * @date ：Created in 2021/4/26 14:54
 * @description ：
 */

public interface PermissionService {


    /**
     * 通过所有的角色ID 查询权限名称
     * @param roleIds 角色ID
     * @return 权限名称
     */
    List<String> findByRoleId(List<Integer> roleIds);
}
