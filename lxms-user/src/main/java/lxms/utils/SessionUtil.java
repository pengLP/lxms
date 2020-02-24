package lxms.utils;

import javax.servlet.http.HttpSession;

import lxms.entity.User;
/**
 * 
 * @author yanlihui 2016.7.9  针对session 的一系列操作
 *
 */
public class SessionUtil {
	/**
	 *	根据session获取当前登录的用户
	 * @param session
	 * @return
	 */
	public static User getCurrentUser(HttpSession session){
		return (User) session.getAttribute("currentUser");
	}
	
	public static void setCurrentUser(HttpSession session,User user){
		session.setAttribute("currentUser", user);;
	}
}
