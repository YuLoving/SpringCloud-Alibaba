package cn.nj.sentinel.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author ：zty
 * @date ：Created in 2021/4/8 14:40
 * @description ： 演示用的业务逻辑类
 */
@Service
public class SampleService {





    /**
     * 模拟创建订单业务 1.  @SentinelResource(注解用于声明 Sentinel 资源点) 资源点名称为createOrder
     *                   blockHandler 属性，这个属性指向 createOrderBlockHandler 方法名，它的作用是当 sentinel-core 触发规则异常后，自动执行 createOrderBlockHandler 方法进行异常处理。
     *               2.createOrderBlockHandler 方法的书写有两个要求：
     *                   1. 方法返回值、访问修饰符、抛出异常要与原始的 createOrder 方法完全相同。
     *                   2.createOrderBlockHandler 方法名允许自定义，但最后一个参数必须是 BlockException 对象，这是所有规则异常的父类，通过判断 BlockException 我们就知道触发了哪种规则异常。
     */

    @SentinelResource(value = "createOrder",blockHandler = "createOrderBlockHandler")
    public void createOrder() throws IllegalStateException{
        try {
            //模拟处理业务逻辑需要101毫秒
            Thread.sleep(101);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(LocalDateTime.now()+"订单已创建");
    }


    /**
     * 自定义资源点的异常处理
     *  至于 createOrderBlockHandler 方法的代码和 RESTful 异常处理基本一致，
     *  先判断规则异常的种类再对外抛出 IllegalStateException异常。SampleController 会对 IllegalStateException 异常进行捕获，对外输出为 JSON 响应。
     * @param e  BlockException
     * @throws IllegalStateException  IllegalStateException
     */
    public void createOrderBlockHandler(BlockException e) throws  IllegalStateException{

        String msg = null;
        if (e instanceof FlowException) {
            //限流异常
            msg = "接口已被限流";
        }
        if (e instanceof DegradeException) {
            //熔断异常
            msg = "接口已被熔断,请稍后再试";
        }
        if (e instanceof ParamFlowException) {
            //热点参数限流
            msg = "热点参数限流";
        }
        if (e instanceof SystemBlockException) {
            //系统规则异常
            msg = "系统规则(负载/....不满足要求)";
        }
        if (e instanceof AuthorityException) {
            //授权规则异常
            msg = "授权规则不通过";
        }

        throw new IllegalStateException(msg);

    }
}
