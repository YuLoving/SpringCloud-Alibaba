package cn.nj.sentinel.controller;

import cn.nj.sentinel.service.SampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import result.ResponseObject;

import java.time.LocalDateTime;

/**
 * @author ：zty
 * @date ：Created in 2021/4/2 12:15
 * @description ：  演示 Sentinel 限流规则 类
 */
@RestController
public class SentinelSampleController {

    @Autowired
    private SampleService sampleService;





    @GetMapping("/test_flow_rule")
    public ResponseObject testFlowRule(){
        System.out.println(LocalDateTime.now()+"进来了");
        //code=0 代表服务器处理成功
        return new ResponseObject();
    }


    /**
     * 熔断测试接口
     */
    @GetMapping("/test_degrade_rule")
    public ResponseObject testDegradeRule(){
        try {
            sampleService.createOrder();
        }catch (IllegalStateException e){
            //当 createOrder 业务处理过程中产生错误时会抛出IllegalStateException
            //IllegalStateException 是 JAVA 内置状态异常，在项目开发时可以更换为自己项目的自定义异常
            //出现错误后将异常封装为响应对象后JSON输出
            return new ResponseObject(e.getClass().getSimpleName(),e.getMessage());
        }
        return new ResponseObject();
    }





}
