package com.tsingtec.mini.config.shiro;

import com.tsingtec.mini.entity.sys.Admin;
import com.tsingtec.mini.entity.sys.Menu;
import com.tsingtec.mini.entity.sys.Role;
import com.tsingtec.mini.service.AdminService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * 自定义Realm
 * @author lenovo
 *
 */
public class UserRealm extends AuthorizingRealm {

	@Autowired
	private AdminService adminService;

	/**
	 * 执行授权逻辑
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		System.out.println("执行授权逻辑");
		Admin admin = (Admin) principals.getPrimaryPrincipal();
		//给资源进行授权
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

		Set<Role> roles = admin.getRoles();

		for(Role role:roles){
			Set<String> permissions = role.getMenus().stream().map(Menu::getPerms).collect(Collectors.toSet());

			//过滤空值
			permissions = permissions.stream().filter(x -> StringUtils.isNotBlank(x)).collect(Collectors.toSet());

			info.addStringPermissions(permissions);
		}

		return info;
	}

	/**
	 * 执行认证逻辑
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken arg0) throws AuthenticationException {
		//1.判断用户名
		UsernamePasswordToken token = (UsernamePasswordToken)arg0;
		Admin admin =  adminService.findByLoginName(token.getUsername());
		if(null==admin){
			throw new AccountException("帐号或密码不正确！");
		}else if(admin.UNVALID.equals(admin.getStatus())){
			throw new DisabledAccountException("帐号已经禁止登录！");
		}else{
			adminService.save(admin);
			return new SimpleAuthenticationInfo(admin,	admin.getPassword(), ByteSource.Util.bytes(admin.getSalt()), getName());
		}
	}
}
