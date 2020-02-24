package lxms.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import lxms.annotation.LoginLimit;
import lxms.annotation.RequestLimit;
import lxms.content.StatusCodes;
import lxms.entity.User;
import lxms.entity.UserAddress;
import lxms.entity.UserRcertification;
import lxms.exception.DeleteException;
import lxms.exception.InsertException;
import lxms.exception.UpdateException;
import lxms.redis.RedisClientTemplate;
import lxms.service.UserAddressServiceI;
import lxms.service.UserServiceI;
import lxms.tool.Json;
import lxms.utils.SessionUtil;
import lxms.utils.sendsms;
import lxms.web.BaseController;

/**
 * 
 * @author YanLihui
 * 
 */
@Controller
@RequestMapping("/member/api")
public class UserController extends BaseController {
	private Log LOG = LogFactory.getLog(this.getClass());
	@Resource
	private UserServiceI userService;
	@Resource
	private UserAddressServiceI userAddressService;

	@Resource
	private RedisClientTemplate redisClient;

	/**
	 * �����������code�ⰴ���������¼ û��code�Ͱ������ĵ�¼
	 * 
	 * @param user
	 *            �û��绰��
	 * @param code
	 *            �ֻ���֤��
	 * @return
	 */
	// @RequestLimit(count = 1, time = 10)
	@RequestMapping("/login")
	@ResponseBody
	public Json login(User user, Integer code, HttpSession session, HttpServletRequest request,String system) {
		Json json = new Json();
		// �������֤�����ж���֤���Ƿ���ȷ
		if (code != null) {
			if (redisClient.get(user.getTel()) == null) {
				json.setCode(StatusCodes.S112);
				return json;
			}
			Integer Tcode = Integer.parseInt(redisClient.get(user.getTel()));
			if (Tcode == null && Tcode.intValue() != code.intValue()) {
				json.setCode(StatusCodes.S102);
				return json;
			}
		}
		// ��¼ ���� ��Ϊ ����֤���¼�����������¼
		User Tuser = userService.login(user, code);
		// �жϵ�¼�Ƿ�ɹ�
		if (Tuser != null) {
			json.setCode(StatusCodes.S100);
			json.setObj(Tuser);
			session.setAttribute("currentUser", Tuser);
			session.setAttribute("system", system);
			session.setMaxInactiveInterval(7 * 60 * 60 * 24);
			return json;
		} else {
			// �����¼ʧ�� �û��������򷵻ظ��ͻ����û�������
			if (userService.getByTel(user.getTel()) == null) {
				json.setCode(StatusCodes.S103);
				return json;
			}
		}
		json.setCode(StatusCodes.S104);
		return json;
	}

	/**
	 * ���ݵ绰�� ��ȡ��֤��
	 * 
	 * @param tel
	 * @param session
	 * @return
	 */
	@RequestLimit(count = 5, time = 60)
	@RequestMapping("/getCode")
	@ResponseBody
	public Json login(String tel, HttpServletRequest request) {
		Json json = new Json();
		int code = sendsms.SendMessage(tel);
		if (code != 0) {
			json.setCode(StatusCodes.S100);
			redisClient.setex(tel, 60 * 5, Integer.toString(code));
		}
		return json;
	}

	/**
	 * ��֤��ǰ��֤���Ƿ���ȷ
	 * 
	 * @param tel
	 * @param code
	 * @param session
	 * @return
	 */
	@RequestMapping("/validate")
	@ResponseBody
	public Json validate(String tel, Integer code) {
		Json json = new Json();
		if (redisClient.get(tel) == null) {
			json.setCode(StatusCodes.S112);
			return json;
		}
		Integer Tcode = Integer.parseInt(redisClient.get(tel));
		if (Tcode != null && Tcode.intValue() == code.intValue()) {
			json.setCode(StatusCodes.S100);
		} else {
			json.setCode(StatusCodes.S108);
		}
		return json;
	}

	/**
	 * ע��
	 * 
	 * @param user
	 *            �û���������
	 * @param code
	 *            ��֤�ֻ���֤���Ƿ���ȷ
	 * @param session
	 * @return
	 */
	@RequestLimit(count = 5, time = 60)
	@RequestMapping("/register")
	@ResponseBody
	public Json register(User user, Integer code, HttpSession session, HttpServletRequest request,String system) {
		Json json = new Json();
		// ����Ƿ���ڸ��û�����֤��
		if (redisClient.get(user.getTel()) == null) {
			json.setCode(StatusCodes.S112);
			return json;
		}

		// �ж���֤���Ƿ���ȷ
		Integer Tcode = Integer.parseInt(redisClient.get(user.getTel()));
		if (Tcode != null && Tcode != 0) {
			// �ж���֤���Ƿ���ȷ
			if (code.intValue() != Tcode.intValue()) {
				json.setCode(StatusCodes.S102);
				return json;
			}
			if (userService.getByTel(user.getTel()) != null) {
				json.setCode(StatusCodes.S110);
				return json;
			}
			// ����û�
			try {

				user.setName(user.getTel());
				userService.add(user);
				session.setAttribute("currentUser", user);
				session.setAttribute("system", system);
				session.setMaxInactiveInterval(7 * 60 * 60 * 24);
				json.setCode(StatusCodes.S100);
			} catch (Exception e) {
				json.setCode(StatusCodes.S105);
			}
		}
		return json;
	}

	/**
	 * ������������û���,����ͷ��
	 * 
	 * @param user
	 * @param code
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/update",method=RequestMethod.PUT)
	@ResponseBody
	public Json update(String name,String head,String oldPassword,String password,HttpSession session) {
		Json json = new Json();
		User user = SessionUtil.getCurrentUser(session);
		if (user == null) {
			json.setCode(StatusCodes.S111);
			return json;
		}	
		if (oldPassword != null&&password!=null) {
			if (user.getPassword().equals(oldPassword)) {	
				try {
					boolean flag = userService.updatePassword(user,password);
					json.setCode(StatusCodes.S100);
					if(flag){
						user.setPassword(password);
						SessionUtil.setCurrentUser(session, user);
					}
					
				} catch (Exception e) {
					LOG.error(e.getMessage());
					json.setCode(StatusCodes.S105);
				}
			}else{
				json.setCode(StatusCodes.S104);
			}
		} else if(name!=null){
			try {
				userService.updateName(user,name);
				json.setCode(StatusCodes.S100);
				SessionUtil.setCurrentUser(session, user);
			} catch (Exception e) {
				LOG.error(e.getMessage());
				json.setCode(StatusCodes.S105);
			}
		}else if(head!=null){
			try {
				userService.updateHead(user,head);		
				json.setCode(StatusCodes.S100);
				SessionUtil.setCurrentUser(session, user);
			} catch (Exception e) {
				LOG.error(e.getMessage());
				json.setCode(StatusCodes.S105);
			}
		}
		return json;
	}

	/**
	 * �˳���¼���session
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping("/logout")
	@ResponseBody
	public Json logout(HttpSession session) {
		Json json = new Json();
		if (session != null) {
			session.invalidate();
			json.setCode(StatusCodes.S100);
		}
		return json;

	}

	/**
	 * ��ȡ�û�ȫ���ջ���ַ
	 * 
	 * @param session
	 * @return
	 */
	@LoginLimit
	@RequestMapping("/allAddress")
	@ResponseBody
	public Json getAllAddress(HttpSession session, User user) {
		Json json = new Json();
		try {
			List<UserAddress> addressList = userAddressService.getByUser(user);
			if (addressList.isEmpty()) {
				json.setCode(StatusCodes.S109);
			} else {
				json.setCode(StatusCodes.S100);
				json.setObj(addressList);
			}
		} catch (Exception e) {
			json.setCode(StatusCodes.S105);
		}
		return json;
	}

	/**
	 * ����û��ջ���ַ ,method = RequestMethod.POST
	 * 
	 * @param userAddress
	 * @param session
	 * @return
	 * 
	 */
	@LoginLimit
	@RequestLimit(count = 5, time = 60)
	@RequestMapping(value = "/address")
	@ResponseBody
	public Json addAddress(UserAddress userAddress, HttpSession session, User user, HttpServletRequest request) {
		Json json = new Json();
		try {
			userAddress.setUser(user);
			userAddressService.add(userAddress);
			json.setObj(userAddress);
			json.setCode(StatusCodes.S100);
		} catch (InsertException e) {
			json.setCode(StatusCodes.INSERT_ERROR);
		} catch (Exception e2) {
			json.setCode(StatusCodes.S105);
		}
		return json;
	}

	/**
	 * ɾ���û���ַ
	 * 
	 * @param addressId
	 *            ��ַid
	 * 
	 * @param session
	 * @return
	 */
	@LoginLimit
	@RequestMapping(value = "/address/{addressId}", method = RequestMethod.DELETE)
	@ResponseBody
	public Json deleteAddress(@PathVariable("addressId") Long addressId, HttpSession session, User user) {
		Json json = new Json();
		try {
			userAddressService.delete(addressId, user);
			json.setCode(StatusCodes.S100);
		} catch (DeleteException e) {
			json.setCode(StatusCodes.DELETE_ERROR);
		} catch (Exception e2) {
			json.setCode(StatusCodes.S105);
		}
		return json;
	}

	/**
	 * �����û���ַ
	 * 
	 * @param addressId
	 *            ��ַid
	 * @param address
	 *            ��ַ��Ϣ
	 * @param session
	 * @return
	 */
	@LoginLimit
	@RequestMapping(value = "/address/{addressId}", method = RequestMethod.PUT)
	@ResponseBody
	public Json updateAddress(@PathVariable("addressId") Long addressId, UserAddress address, HttpSession session,
			User user) {
		Json json = new Json();
		try {
			address.setAddressId(addressId);
			userAddressService.update(address);
			json.setCode(StatusCodes.S100);
		} catch (UpdateException e) {
			json.setCode(StatusCodes.DELETE_ERROR);
		} catch (Exception e2) {
			json.setCode(StatusCodes.S105);
		}
		return json;
	}

	@LoginLimit
	@RequestMapping(value = "/address/setDefault")
	@ResponseBody
	public Json setDefault(Long addressId, HttpSession session, User user) {
		Json json = new Json();
		try {
			userAddressService.setDefault(addressId, user);
			json.setCode(StatusCodes.S100);
		} catch (UpdateException e) {
			json.setCode(StatusCodes.INSERT_ERROR);
		} catch (Exception e) {
			json.setCode(StatusCodes.S105);
		}
		return json;
	}

	@LoginLimit
	@RequestMapping(value = "/certification", method = RequestMethod.POST)
	@ResponseBody
	public Json certification(UserRcertification userRcertification, HttpServletRequest request, HttpSession session,
			User user) {
		Json json = new Json();
		try {
			userService.updateCertification(userRcertification, user);
			json.setCode(StatusCodes.S100);
		} catch (Exception e) {
			json.setCode(StatusCodes.S105);
		}
		return json;
	}
	@LoginLimit
	@RequestMapping(value = "/havePermission")
	@ResponseBody
	public Json havePermission(HttpServletRequest request, HttpSession session,User user) {
		Json json = new Json();
		try {
			Boolean isHave = userService.havePermission(user);
			json.setCode(StatusCodes.S100);
			json.setObj(isHave);
		} catch (Exception e) {
			json.setCode(StatusCodes.S105);
		}
		return json;
	}
		
	@LoginLimit
	@RequestMapping(value = "/user/{uid}", method = RequestMethod.GET)
	@ResponseBody
	public Json getUser(@PathVariable("uid") Long uid,HttpServletRequest request, HttpSession session,User user){
		Json json = new Json();
		try {
			User getUser = userService.getById(uid);
			if(getUser!=null){
				json.setCode(StatusCodes.S100);
				json.setObj(getUser);
			}else{
				json.setCode(StatusCodes.S103);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		return json;
	}
	

}
