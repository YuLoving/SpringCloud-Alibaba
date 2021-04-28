package cn.nj.seatarmorder.service;

import cn.nj.seatarmorder.entity.Order;
import cn.nj.seatarmorder.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ：zty
 * @date ：Created in 2021/4/27 17:07
 * @description ：
 */
@Service
public class OrderService {

    private final Logger log= LoggerFactory.getLogger(getClass());

    @Autowired
    private OrderMapper mapper;


    /**
     * 创建订单
     *    注意在 createOrder 方法上必须增加 @Transactional 注解，Seata 客户端对这个注解进行扩展支持了分布式事务。
     * @param order  Order
     */
    @Transactional(rollbackFor = Exception.class)
    public void createOrder(Order order){
        int insert = mapper.create(order);
        if(insert>0){
            log.info("创建订单成功,插入条数:{}",insert);
        }else{
            log.info("创建订单失败");
        }
    }



}
