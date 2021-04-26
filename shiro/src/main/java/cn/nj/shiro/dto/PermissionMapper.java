package cn.nj.shiro.dto;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ：zty
 * @date ：Created in 2021/4/26 14:22
 * @description ： 权限操作mapper
 */
@Mapper
@Repository
public interface PermissionMapper {

    /**
     * 通过角色ID 查询权限名称
     * @param roleIds 角色ID集合
     * @return 权限名称集合
     */
    List<String> findByRoleId (List<Integer> roleIds);
}
