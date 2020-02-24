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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import lxms.annotation.LoginLimit;
import lxms.content.StatusCodes;
import lxms.entity.Order;
import lxms.entity.OrderOver;
import lxms.entity.User;
import lxms.exception.InsertException;
import lxms.service.DepositPayServiceI;
import lxms.service.OrderServiceI;
import lxms.tool.Json;
import lxms.tool.PageFilter;

@Controller
@RequestMapping("/order")
public class OrderController {
	private Log LOG = LogFactory.getLog(this.getClass());
	@Resource
	private OrderServiceI orderService;
	@Resource
	private DepositPayServiceI depositPayService;

	@RequestMapping("/place")
	@LoginLimit
	@ResponseBody
	public Json createOrder(Long wid, HttpServletRequest request, User user,HttpSession session) {
		Json json = new Json();
		try {
			Order order = orderService.addOrder(wid, user);
			if(order!=null){
				json.setCode(StatusCodes.S100);
				json.setObj(order);
			}else{
				json.setCode(StatusCodes.S112);
			}
			
		} catch (InsertException e) {
			
			json.setCode(StatusCodes.INSERT_ERROR);
		} catch (Exception e) {
			json.setCode(StatusCodes.S105);
			LOG.error(e.getMessage());
		}

		return json;
	}
	@RequestMapping("/my")
	@LoginLimit
	@ResponseBody
	public Json myOrder(PageFilter pageFilter,HttpServletRequest request,User user,HttpSession session){
		Json json = new Json();
		try {
			List<Order> orderList = orderService.find(pageFilter, user);
			json.setCode(StatusCodes.S100);
			json.setObj(orderList);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			json.setCode(StatusCodes.S105);
		}
		return json;
	}
	@RequestMapping(value="/{orderId}",method=RequestMethod.DELETE)
	@LoginLimit
	@ResponseBody
	public Json deleteOrder(@PathVariable("orderId") Long orderId,HttpSession session,User user){
		Json json = new Json();
		try {
			orderService.delete(orderId, user);
			json.setCode(StatusCodes.S100);
		} catch (Exception e) {
			json.setCode(StatusCodes.S105);
		}
		return json;
	}
	@RequestMapping(value="/{orderId}",method=RequestMethod.GET)
	@LoginLimit
	@ResponseBody
	public Json getOrder(@PathVariable("orderId") Long orderId,HttpSession session,User user){
		Json json = new Json();
		try {
			Order order = orderService.getOrderById(orderId,user);
			if(order!=null){
				json.setCode(StatusCodes.S100);
				json.setObj(order);
			}else{
				json.setCode(StatusCodes.S109);
			}
			
		} catch (Exception e) {
			json.setCode(StatusCodes.S105);
		}
		return json;
	}
	
	@RequestMapping(value="/upload",method=RequestMethod.POST)
	@LoginLimit
	@ResponseBody
	public Json updateOrderOver(@RequestParam(value="orderOverId",required=true)Long orderOverId,@RequestParam(value="airTicket",required=true)String airTicket,@RequestParam(value="expressNumber",required=true)String expressNumber,@RequestParam(value="orderId",required=true)Long orderId,HttpSession session,User user,HttpServletRequest request){
		Json json = new Json();
		try {
			OrderOver orderOver = new OrderOver();
			orderOver.setOrderOverId(orderOverId);
			orderOver.setAirTicket(airTicket);
			orderOver.setExpressNumber(expressNumber);
			orderOver.setOrderId(orderId);
			orderService.uploadAirticket(orderOver, user);
			json.setCode(StatusCodes.S100);
		} catch (Exception e) {
			json.setCode(StatusCodes.S105);
		}
		return json;
	}
	
	@RequestMapping(value="/confirm")
	@LoginLimit
	@ResponseBody
	public Json confirm(Long orderId,HttpSession session,User user,HttpServletRequest request){
		Json json = new Json();
		try {
			orderService.confirm(orderId, user);
			json.setCode(StatusCodes.S100);
		} catch (Exception e) {
			json.setCode(StatusCodes.S105);
		}
		return json;
	}

}
