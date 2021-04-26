package cn.nj.shiro.dto;

import cn.nj.shiro.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author ：zty
 * @date ：Created in 2021/4/26 14:50
 * @description ：用户mapper
 */
@Repository
@Mapper
public interface UserMapper {

    /**
     * 根据账户去查询用户信息
     * @param account  账户
     * @return 用户信息
     */
    User findByAccount(String account);

}
