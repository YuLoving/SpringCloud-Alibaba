package cn.nj.shiro.realm;

import cn.nj.shiro.entity.Role;
import cn.nj.shiro.entity.User;
import cn.nj.shiro.service.PermissionService;
import cn.nj.shiro.service.RoleService;
import cn.nj.shiro.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author ：zty
 * @date ：Created in 2021/4/26 15:48
 * @description ： 负责认证用户身份和对用户进行授权
 */
public class UserRealm extends AuthorizingRealm {


    @Autowired
    private UserService userService;


    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;


    /**
     * 用户授权
     *
     * @param principalCollection principalCollection
     * @return AuthorizationInfo
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

        //获取登录人的用户信息
        User user = (User) principalCollection.getPrimaryPrincipal();
        //获取所有的角色
        List<Role> roleList = roleService.findRoleByUserId(user.getId());

        Set<String> roleSet = new HashSet<>();
        List<Integer> roleIds = new ArrayList<>();
        roleList.forEach(a -> {
                    roleSet.add(a.getRole());
                    roleIds.add(a.getId());
                }
        );
        // 放入角色信息
        authorizationInfo.setRoles(roleSet);
        //放入权限信息
        List<String> permissionList = permissionService.findByRoleId(roleIds);
        authorizationInfo.setStringPermissions(new HashSet<>(permissionList));

        return authorizationInfo;
    }


    /**
     * 认证用户身份
     *
     * @param authenticationToken Token
     * @return AuthenticationInfo
     * @throws AuthenticationException AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //获取登录信息的token
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        //通过登录账号去查询用户信息
        User user = userService.findByAccount(token.getUsername());
        //用户不存在
        if (null == user) {
            return null;
        }
        return new SimpleAuthenticationInfo(user, user.getPassword(), getName());
    }


}
