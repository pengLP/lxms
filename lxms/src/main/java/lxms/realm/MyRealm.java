package lxms.realm;


import javax.annotation.Resource;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import lxms.dao.AdminDao;
import lxms.entity.Admin;





public class MyRealm extends AuthorizingRealm {
	@Resource
	private AdminDao adminDao;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String username = (String) principals.getPrimaryPrincipal();
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		authorizationInfo.setRoles(adminDao.getRolesByUsername(username));
		return authorizationInfo;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String username = (String) token.getPrincipal();
		AuthenticationInfo authenticationInfo = null;
		Admin admin = adminDao.getByUsername(username);
		System.out.println(admin.getUsername());
		if (admin != null) {
			SecurityUtils.getSubject().getSession().setAttribute("currentUser",admin);
			authenticationInfo = new SimpleAuthenticationInfo(admin.getUsername(), admin.getPassword(), "xxx");
		} else {
			return null;
		}
		return authenticationInfo;
	}

}
