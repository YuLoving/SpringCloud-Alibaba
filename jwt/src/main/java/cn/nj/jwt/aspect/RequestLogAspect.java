package cn.nj.jwt.aspect;

import cn.nj.jwt.pojo.LogRequsetInfo;
import com.alibaba.fastjson.JSON;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ：zty
 * @date ：Created in 2021/6/21 10:28
 * @description ：
 */
@Component
@Aspect
public class RequestLogAspect {

    private final Logger logger = LoggerFactory.getLogger(getClass());


    /**
     * 定义切面点
     */
    @Pointcut("execution(* cn.nj.jwt.controller..*(..))")
    public void logPoint() {

        logger.info("日志切点");
    }


    /**
     * 环绕通知日志
     *
     * @param proceedingJoinPoint
     * @throws Throwable
     */
    @Around("logPoint()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        long start = System.currentTimeMillis();

        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        Object result = proceedingJoinPoint.proceed();
        logger.info("proceedingJoinPoint.proceed()={}", JSON.toJSONString(result));
        //new一个我们的日志请求类
        LogRequsetInfo logRequsetInfo = new LogRequsetInfo();

        logRequsetInfo.setIp(request.getRemoteAddr());
        logRequsetInfo.setUrl(request.getRequestURI());
        logRequsetInfo.setHttpMethod(request.getMethod());
        //获取类名
        String declaringTypeName = proceedingJoinPoint.getSignature().getDeclaringTypeName();
        //获取类的方法名
        String name = proceedingJoinPoint.getSignature().getName();
        logRequsetInfo.setClassMethod(String.format("%s.%s", declaringTypeName, name));

        //获取请求参数
        //1.参数名
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        String[] parameterNames = methodSignature.getParameterNames();
        //2.参数值
        Object[] args = proceedingJoinPoint.getArgs();

        Map<String, Object> map = new HashMap<>();

        for (int i = 0; i < parameterNames.length; i++) {

            Object value = args[i];
            //判断value是否是文件
            if (value instanceof MultipartFile) {
                MultipartFile file = (MultipartFile) value;
                //文件则直接获取文件名
                value = file.getOriginalFilename();
            }
            map.put(parameterNames[i], value);

        }
        logRequsetInfo.setRequestParams(map);
        logRequsetInfo.setResult(result);
        logRequsetInfo.setTimeCost(System.currentTimeMillis() - start);

        logger.info("Request Info      : {}", JSON.toJSONString(logRequsetInfo));
        return logRequsetInfo;
    }


}
