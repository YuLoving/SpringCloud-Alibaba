package cn.nj.shiro.dto;

import cn.nj.shiro.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ：zty
 * @date ：Created in 2021/4/26 14:40
 * @description ：角色mapper
 */

@Mapper
@Repository
public interface RoleMapper {

    /**
     * 通过用户ID查询所有的角色信息
     * @param userId   用户ID
     * @return 角色信息
     */
    List<Role> findRoleByUserId(Integer userId);





}
