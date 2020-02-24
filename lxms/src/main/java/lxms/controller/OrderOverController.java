package lxms.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lxms.entity.OrderOver;
import lxms.service.OrderServiceI;
import lxms.tool.Grid;
import lxms.tool.Json;
import lxms.tool.PageFilter;

@Controller
@RequestMapping("/admin/orderOver")
public class OrderOverController {
	private Log LOG = LogFactory.getLog(this.getClass());
	@Resource
	private OrderServiceI orderService;
	
	@RequestMapping("")
	public String goOrderOverPage(){
		return "admin/ticketVerification";
	}
	
	@RequestMapping("/list")
	@ResponseBody
	public Grid getAllOrderOver(PageFilter pageFilter){
		Grid grid = new Grid();
		try {
			List<OrderOver> orderOverList = orderService.find(pageFilter, "dsh");
			if(orderOverList!=null){
				grid.setRows(orderOverList);
				grid.setTotal((long)orderOverList.size());
			}	
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		return grid;
	}
	
	@RequestMapping("/pass")
	@ResponseBody
	public Json pass(OrderOver orderOver){
		Json json = new Json();
		try {
			orderService.validateSuccess(orderOver);
			json.setSuccess(true);
		} catch (Exception e) {
			json.setSuccess(false);
		}
		return json;
	}
	
	@RequestMapping("/notPass")
	@ResponseBody
	public Json notPass(OrderOver orderOver){
		Json json = new Json();
		try {
			orderService.validateFalse(orderOver);
			json.setSuccess(true);
		} catch (Exception e) {
			json.setSuccess(false);
		}
		return json;
	}
	
	@RequestMapping("/lookPicture")
	public String lookPicture(String cardedPicture,String passportPicture,String airTicket,HttpServletRequest request){
		List<String> pictureList = new ArrayList<String>();
		try {
			pictureList.add(cardedPicture);
			pictureList.add(passportPicture);
			pictureList.add(airTicket);
			request.setAttribute("pictureList", pictureList);
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		return "admin/picture";
	}
}
