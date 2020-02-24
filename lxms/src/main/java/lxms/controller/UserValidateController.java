package lxms.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lxms.entity.UserRcertification;
import lxms.service.UserRcertificationServiceI;
import lxms.tool.Grid;
import lxms.tool.Json;
import lxms.tool.PageFilter;

@Controller
@RequestMapping("/admin/userRcertification")
public class UserValidateController {
	private Log LOG = LogFactory.getLog(this.getClass());
	@Resource
	private UserRcertificationServiceI userRcertificationService;
	
	@RequestMapping("")
	public String goWriteNews(){
		return "admin/realNameAuthentication";
	}
	
	@RequestMapping("/list")
	@ResponseBody
	public Grid getAllUserRcertification(PageFilter pageFilter){
		Grid grid = new Grid();
		try {
			List<UserRcertification> userRcertificationList = userRcertificationService.find(pageFilter);
			if(userRcertificationList!=null){
				grid.setRows(userRcertificationList);
				grid.setTotal((long)userRcertificationList.size());
			}	
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		return grid;
	}
	
	@RequestMapping("/lookPicture")
	public String lookPicture(String cardedPicture,String passportPicture,HttpServletRequest request){
		List<String> pictureList = new ArrayList<String>();
		try {
			pictureList.add(cardedPicture);
			pictureList.add(passportPicture);
			request.setAttribute("pictureList", pictureList);
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		return "admin/picture";
	}
	
	@RequestMapping("/pass")
	@ResponseBody
	public Json pass(UserRcertification userRcertification){
		Json json = new Json();
		try {
			userRcertificationService.validateSuccess(userRcertification);
			json.setSuccess(true);
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		return json;
	}
	@RequestMapping("/notPass")
	@ResponseBody
	public Json notPass(UserRcertification userRcertification){
		Json json = new Json();
		try {
			userRcertificationService.validateFalse(userRcertification);
			json.setSuccess(true);
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		return json;
	}
	
}
