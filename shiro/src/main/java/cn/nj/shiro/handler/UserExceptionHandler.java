package cn.nj.shiro.handler;

import cn.nj.shiro.enums.ServerResponseEnum;
import cn.nj.shiro.response.ServerResponseVO;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author ：zty
 * @date ：Created in 2021/4/26 15:40
 * @description ： 统一异常处理
 */
@RestControllerAdvice
public class UserExceptionHandler {

    /**
     * 当用户身份认证失败时，会抛出UnauthorizedException，我们可以通过统一异常处理来处理该异常
     */
    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public <T>ServerResponseVO<T> unAuthorizedExceptionHandler(UnauthorizedException e){
        return  ServerResponseVO.error(ServerResponseEnum.UNAUTHORIZED);

    }







}
