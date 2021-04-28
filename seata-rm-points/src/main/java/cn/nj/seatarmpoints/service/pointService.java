package cn.nj.seatarmpoints.service;

import cn.nj.seatarmpoints.entity.points;
import cn.nj.seatarmpoints.mapper.pointMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ：zty
 * @date ：Created in 2021/4/28 11:22
 * @description ：
 */
@Service
public class pointService {

    private final Logger logger= LoggerFactory.getLogger(getClass());

    @Autowired
    private pointMapper pointMapper;

    @Transactional(rollbackFor = Exception.class)
    public void update(points points){
        int add = pointMapper.update(points);
        if(add>0){
            logger.info("积分增加成功,条数为:{}",add);
        }else {
            logger.info("积分增加失败");
        }

    }

}
