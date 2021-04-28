package cn.nj.seatarmpoints.mapper;

import cn.nj.seatarmpoints.entity.points;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author ：zty
 * @date ：Created in 2021/4/28 11:19
 * @description ：
 */
@Mapper
@Repository
public interface pointMapper {

    int update(points points);
}
