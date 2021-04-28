package cn.nj.seatatmmall.service;

import cn.nj.seatatmmall.entity.Order;
import cn.nj.seatatmmall.entity.Points;
import cn.nj.seatatmmall.entity.Storage;
import cn.nj.seatatmmall.feign.OrderFeignClient;
import cn.nj.seatatmmall.feign.PointsFeignClient;
import cn.nj.seatatmmall.feign.StorageFeignClient;
import io.seata.spring.annotation.GlobalTransactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ：zty
 * @date ：Created in 2021/4/28 15:55
 * @description ： 开发 MallService，定义全局事务范围。这里最重要的是 @GlobalTransactional 注解，
 *                  该注解是全局事务注解，当进入 MallService.sale 方法时通知 TC 开启全局事务，sale 方法执行成功自动通知 TC 进行全局提交；sale 方法抛出异常时自动通知 TC 进行全局回滚。
 */
@Service
public class MallService {

    private final Logger logger= LoggerFactory.getLogger(getClass());

    private AtomicInteger atomicInteger=new AtomicInteger(9);

    private final OrderFeignClient orderFeignClient;
    private final PointsFeignClient pointsFeignClient;
    private final StorageFeignClient storageFeignClient;


    public MallService(OrderFeignClient orderFeignClient, PointsFeignClient pointsFeignClient, StorageFeignClient storageFeignClient) {
        this.orderFeignClient = orderFeignClient;
        this.pointsFeignClient = pointsFeignClient;
        this.storageFeignClient = storageFeignClient;
    }



    @GlobalTransactional(name = "seata-group-tx-mall",rollbackFor = Exception.class)
    public String sale(){

        Order order = new Order();
        order.setId(atomicInteger.incrementAndGet());
        order.setMemberId(1);
        order.setGoodsId(1);
        order.setPoints(20);
        order.setQuantity(10);

        Points points=new Points();
        points.setId(1);
        points.setPoints(20);

        Storage storage=new Storage();
        storage.setId(1);
        storage.setQuantity(10);

        //1.创建订单
        String orderResult = orderFeignClient.create(order);
        //2.增加积分
        String pointsResult =pointsFeignClient.add(points);
        //3.扣减库存
        String storageResult =storageFeignClient.reduce(storage);

        logger.info("orderResult:{},pointsResult:{},storageResult:{}",orderResult,pointsResult,storageResult);
        return orderResult + " / " + pointsResult + " / " + storageResult;
    }










}
