package cn.nj.shiro.config;

import cn.nj.shiro.realm.UserRealm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author ：zty
 * @date ：Created in 2021/4/26 16:05
 * @description ： Shiro配置类
 */
@Configuration
public class ShiroConfig {


    @Bean
    public UserRealm userRealm() {
        return new UserRealm();
    }

    /**
     * shiro本身的依赖是返回  DefaultWebSecurityManager
     * shiro-spring-boot-web-starter对应的返回的是  SessionsSecurityManager
     */
    @Bean
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //配置realm
        securityManager.setRealm(userRealm());
        return securityManager;
    }

    /**
     * 路径过滤规则
     *
     * @param securityManager securityManager()
     * @return ShiroFilterFactoryBean
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilter(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        shiroFilterFactoryBean.setSecurityManager(securityManager);

        shiroFilterFactoryBean.setLoginUrl("/login");
        shiroFilterFactoryBean.setSuccessUrl("/");

        // 有先后顺序
        Map<String, String> linkedHashMap = new LinkedHashMap<>();
        //anon允许匿名访问
        linkedHashMap.put("login","anon");
        // 进行身份认证后才能访问
        linkedHashMap.put("/**","authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(linkedHashMap);
        return shiroFilterFactoryBean;

    }


    /**
     * 开启Shiro注解模式，可以在Controller中的方法上添加注解
     * @param securityManager  securityManager
     * @return AuthorizationAttributeSourceAdvisor
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager") DefaultWebSecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor sourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        sourceAdvisor.setSecurityManager(securityManager);
        return  sourceAdvisor;
    }









}
