package lxms.utils;

import javax.servlet.http.HttpSession;

import lxms.entity.User;
/**
 * 
 * @author yanlihui 2016.7.9  ���session ��һϵ�в���
 *
 */
public class SessionUtil {
	/**
	 *	����session��ȡ��ǰ��¼���û�
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
