package cn.nj.shiro.response;

import cn.nj.shiro.enums.ServerResponseEnum;

import java.io.Serializable;

/**
 * @author ：zty
 * @date ：Created in 2021/4/26 15:09
 * @description ： 返回响应值包装
 */
public class ServerResponseVO<T> implements Serializable {

    private static final long serialVersionUID = 5018298072287148315L;


    /**
     *  响应码
     */
    private Integer code;

    /**
     *  描述信息
     */
    private String message;

    /**
     *  响应内容
     */
    private T data;



    public ServerResponseVO(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public ServerResponseVO(ServerResponseEnum responseEnum, T data) {
        this.code = responseEnum.getCode();
        this.message = responseEnum.getMessage();
        this.data = data;
    }

    public ServerResponseVO(ServerResponseEnum responseEnum) {
        this.code = responseEnum.getCode();
        this.message = responseEnum.getMessage();
    }


    /**
     * 返回成功信息
     * @param data 信息内容
     * @param <T> T
     * @return ServerResponseVO
     */
    public static <T> ServerResponseVO<T>  success(T data){
        return  new ServerResponseVO<>(ServerResponseEnum.SUCCESS,data);
    }

    /**
     * 返回成功信息
     * @return  ServerResponseVO
     */
    public static  <T>ServerResponseVO<T> success(){
        return new ServerResponseVO<>(ServerResponseEnum.SUCCESS);
    }


    /**
     * 返回失败
     * @return  ServerResponseVO
     */
    public static  <T>ServerResponseVO<T> error(ServerResponseEnum responseEnum){
        return new ServerResponseVO<>(responseEnum);
    }


















    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
