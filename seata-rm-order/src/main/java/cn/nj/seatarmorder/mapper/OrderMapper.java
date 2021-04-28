package cn.nj.seatarmorder.mapper;

import cn.nj.seatarmorder.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author ：zty
 * @date ：Created in 2021/4/27 17:05
 * @description ： 使用mybatis自动的CURD
 */
@Mapper
@Repository
public interface OrderMapper {

    int create(Order order);
}
