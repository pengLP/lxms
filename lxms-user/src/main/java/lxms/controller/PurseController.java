package lxms.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lxms.annotation.LoginLimit;
import lxms.content.StatusCodes;
import lxms.entity.Purse;
import lxms.entity.PurseLog;
import lxms.entity.User;
import lxms.redis.RedisClientTemplate;
import lxms.service.PurseServiceI;
import lxms.tool.Json;
import lxms.tool.PageFilter;
import lxms.utils.StringUtil;

@Controller
@RequestMapping("/purse")
public class PurseController {
	@Resource
	private PurseServiceI purseService;
	@Resource
	private RedisClientTemplate redisClient;

	private Log LOG = LogFactory.getLog(this.getClass());

	@LoginLimit
	@RequestMapping("")
	@ResponseBody
	public Json myPurse(HttpServletRequest request,HttpSession session, User user) {
		Json json = new Json();
		try {
			Purse purse = purseService.getMyPurse(user);
			json.setCode(StatusCodes.S100);
			json.setObj(purse);
		} catch (Exception e) {
			json.setCode(StatusCodes.S105);
		}
		return json;
	}

	@LoginLimit
	@RequestMapping("/validate")
	@ResponseBody
	public Json validate(HttpServletRequest request,HttpSession session, String password, User user) {
		Json json = new Json();
		try {
			if (purseService.validate(password, user)) {
				String token = StringUtil.getRandomString(15);
				redisClient.setex(user.getTel() + "payPassword", 60 * 5, token);
				json.setCode(StatusCodes.S100);
				json.setObj(token);
			} else {
				json.setCode(StatusCodes.S104);
			}
		} catch (Exception e) {
			json.setCode(StatusCodes.S105);
			LOG.error(e.getMessage());
		}
		return json;
	}

	@RequestMapping(value = "/password", method = RequestMethod.PUT)
	@LoginLimit
	@ResponseBody
	public Json updatePassword(HttpServletRequest request,HttpSession session, String newPassword,
			@RequestParam(value = "token", required = true) String token, User user) {
		Json json = new Json();
		try {
			String Ttoken = redisClient.get(user.getTel() + "payPassword");
			if (Ttoken != null || token != null) {
				if (Ttoken.equals(token)) {
					purseService.updatePassword(newPassword, user);
					json.setCode(StatusCodes.S100);
					return json;
				}
			}
			json.setCode(StatusCodes.S108);
		} catch (Exception e) {
			json.setCode(StatusCodes.S105);
			LOG.error(e.getMessage());
		}
		return json;
	}

	@RequestMapping(value = "/password", method = RequestMethod.POST)
	@LoginLimit
	@ResponseBody
	public Json setPassword(HttpServletRequest request, HttpSession session,String newPassword, User user) {
		Json json = new Json();
		try {
			int result = purseService.setPassword(newPassword, user);
			if(result==1){
				json.setCode(StatusCodes.S100);
			}else{
				json.setCode(StatusCodes.S101);
			}
			
			return json;
		} catch (Exception e) {
			json.setCode(StatusCodes.S105);
			LOG.error(e.getMessage());
		}
		return json;
	}

	@RequestMapping(value = "/bindingAli", method = RequestMethod.PUT)
	@LoginLimit
	@ResponseBody
	public Json updateBindingAli(HttpServletRequest request, HttpSession session,String newAliNumber, String token, User user) {
		Json json = new Json();
		try {
			String Ttoken = redisClient.get(user.getTel() + "payPassword");
			if (Ttoken != null || token != null) {
				if (Ttoken.equals(token)) {
					purseService.updateBindingAlipay(newAliNumber, user);
					json.setCode(StatusCodes.S100);
					return json;
				}
			}
			json.setCode(StatusCodes.S108);
		} catch (Exception e) {
			json.setCode(StatusCodes.S105);
			LOG.error(e.getMessage());
		}
		return json;
	}

	@RequestMapping(value = "/bindingAli", method = RequestMethod.POST)
	@LoginLimit
	@ResponseBody
	public Json setBindingAli(HttpServletRequest request, HttpSession session,String newAliNumber, User user) {
		Json json = new Json();
		try {
			purseService.setBindingAlipay(newAliNumber, user);
			json.setCode(StatusCodes.S100);
			return json;
		} catch (
		Exception e) {
			json.setCode(StatusCodes.S105);
			LOG.error(e.getMessage());
		}
		return json;
	}
	@RequestMapping(value = "/log")
	@LoginLimit
	@ResponseBody
	public Json getlog(HttpServletRequest request, HttpSession session,PageFilter pageFilter, User user) {
		Json json = new Json();
		try {
			List<PurseLog> purseLogList = purseService.log(pageFilter, user);
			json.setCode(StatusCodes.S100);
			json.setObj(purseLogList);
			return json;
		} catch (
		Exception e) {
			json.setCode(StatusCodes.S105);
			LOG.error(e.getMessage());
		}
		return json;
	}
	
	@RequestMapping(value = "/withdraw")
	@LoginLimit
	@ResponseBody
	public Json withdraw(@RequestParam(value="realName",required=false)String realName,String password,String sum,HttpServletRequest request, HttpSession session, User user){
		
		Json json = new Json();
		try {
			String balance = purseService.withdrawMoney(password, sum, user,realName);
			json.setCode(StatusCodes.S100);
			json.setObj(balance);
		} catch (Exception e) {
			json.setCode(StatusCodes.S105);
		}
		return json;
	}

}
