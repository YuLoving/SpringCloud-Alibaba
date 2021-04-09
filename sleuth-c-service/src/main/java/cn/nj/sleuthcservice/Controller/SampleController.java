package cn.nj.sleuthcservice.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：zty
 * @date ：Created in 2021/4/9 10:51
 * @description ： SampleController，methodC方法产生响应字符串“-> Service C”，方法映射地址“/c”
 */
@RestController
public class SampleController {

    @GetMapping("/c")
    public String methodC(){
        return " -> Service C";
    }

}
