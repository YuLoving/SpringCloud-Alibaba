package cn.nj.shiro.controller;

import cn.nj.shiro.enums.ServerResponseEnum;
import cn.nj.shiro.response.ServerResponseVO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：zty
 * @date ：Created in 2021/4/26 16:14
 * @description ：shiro测试
 */
@RestController
public class LoginController {


    @PostMapping("login")
    public ServerResponseVO<Void> login(@RequestParam(value = "account") String account, @RequestParam(value = "password") String password) {
        //获取subject进行 登录认证
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(account,password);
        try {
            //登录认证
            subject.login(token);
            return ServerResponseVO.success();
        } catch (UnknownAccountException e) {
            return ServerResponseVO.error(ServerResponseEnum.ACCOUNT_NOT_EXIST);
        } catch (DisabledAccountException e) {
            return ServerResponseVO.error(ServerResponseEnum.ACCOUNT_IS_DISABLED);
        } catch (IncorrectCredentialsException e) {
            return ServerResponseVO.error(ServerResponseEnum.INCORRECT_CREDENTIALS);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponseVO.error(ServerResponseEnum.ERROR);
        }

    }

    @GetMapping("/login")
    public ServerResponseVO<Void> login() {
        return ServerResponseVO.error(ServerResponseEnum.NOT_LOGIN_IN);
    }


    @GetMapping("/auth")
    public String auth() {
        return "已成功登录";
    }

    @GetMapping("/role")
    @RequiresRoles("vip")
    public String role(){
        return "测试Vip角色";
    }




    @GetMapping("/permission")
    @RequiresPermissions(value = {"add","update"},logical = Logical.AND)
    public String permission(){
        return "测试Add和Update权限";
    }






}
