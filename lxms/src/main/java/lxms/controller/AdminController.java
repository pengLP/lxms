package lxms.controller;



import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lxms.entity.Admin;
import lxms.entity.User;
import lxms.service.AdminServiceI;
import lxms.tool.Json;
import lxms.web.BaseController;

/**
 * 
 * @author YanLihui 2016.6.29 开始编写登录功能 2016.6.30 加入退出登录 优化了登录和更新的逻辑 2016.7.4 改版
 *         使用了redis存储验证码 登录的api接口 2016.7.14 添加了 用户收货地址
 */
@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {
	private Log LOG = LogFactory.getLog(this.getClass());
	@Resource
	private AdminServiceI adminService;



	@RequestMapping("/login")
	public String login(Admin admin, HttpServletRequest request) {
		Subject currentUser = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(admin.getUsername(), admin.getPassword());
		try {
			currentUser.login(token);
			return "redirect:/index";
		} catch (Exception e) {
			request.setAttribute("user", admin);
			request.setAttribute("error","帐号密码错误");
			return "admin/Login";
		}

	}
	@RequestMapping("/loginPage")
	public String loginPage() {
		return "admin/Login";
	}

	@RequestMapping("/modifiy")
	@ResponseBody
	public Json update(Admin admin){
		Json json = new Json();
		try {
			adminService.update(admin);
			json.setMsg("修改成功");
			json.setSuccess(true);
		} catch (Exception e) {
			json.setMsg("修改失败");
			json.setSuccess(false);
		}
		
		return json;
	}
	@RequestMapping("/logout")
	public String logout(){
		SecurityUtils.getSubject().logout(); 
		return "redirect:/index";
	}
	
	

}
