package cn.nj.sentinel.handler;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.entity.ContentType;
import org.springframework.stereotype.Component;
import result.ResponseObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;

/**
 * @author ：zty
 * @date ：Created in 2021/4/8 16:05
 * @description ：  针对restful   sentinel触发流控的默认错误信息
 */
@Component
public class UrlBlockHandler implements BlockExceptionHandler {


    /**
     * restful  sentinel触发流控 异常信息处理器
     *
     * @param request  请求
     * @param response 响应
     * @param e        Block异常
     * @throws Exception 异常
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, BlockException e) throws Exception {
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
        //http status 500
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType(ContentType.APPLICATION_JSON.toString());
        //ObjectMapper是内置Jackson的序列化工具类,这用于将对象转为JSON字符串
        ObjectMapper objectMapper = new ObjectMapper();
        //某个对象属性为null时不进行序列化输出
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.writeValue(response.getWriter(),new ResponseObject(e.getClass().getSimpleName(),msg));

    }
}
