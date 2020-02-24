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
import lxms.annotation.RequestLimit;
import lxms.content.StatusCodes;
import lxms.entity.Route;
import lxms.entity.User;
import lxms.exception.InsertException;
import lxms.service.RouteServiceI;
import lxms.tool.Json;
import lxms.tool.PageFilter;
import lxms.web.BaseController;
@Controller
@RequestMapping("/seller/api")
public class RouteController extends BaseController{
	private Log LOG = LogFactory.getLog(this.getClass());
	@Resource
	private RouteServiceI routeService;
	/**
	 * 获取明星行程
	 * @param pageFilter
	 * @return
	 */
	@RequestMapping("/famous")
	@ResponseBody
	public Json famous(PageFilter pageFilter) {
		Json json = new Json();
		List<Route> routeList;
		try {
			routeList = routeService.find(pageFilter);
			if (routeList == null) {
				json.setCode(StatusCodes.S109);
			} else {
				json.setObj(routeList);
				json.setCode(StatusCodes.S100);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
			json.setCode(StatusCodes.S105);
		}
		return json;
	}
	/**
	 * 获取全部行程
	 * @param pageFilter
	 * @param order
	 * @return
	 */
	@RequestMapping("/allRoute")
	@ResponseBody
	public Json allRoute(PageFilter pageFilter,@RequestParam(value="order",required=false)String order) {
		Json json = new Json();
		List<Route> routeList;
		try {
			routeList = routeService.findOrder(pageFilter,order);
			if (routeList == null) {
				json.setCode(StatusCodes.S109);
			} else {
				json.setObj(routeList);
				json.setCode(StatusCodes.S100);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
			json.setCode(StatusCodes.S105);
		}
		return json;
	}
	/**
	 * 添加行程
	 * @param route
	 * @param session
	 * @param user
	 * @param request
	 * @return
	 */
	//@RequestLimit(count = 10, time = 60)
	@LoginLimit
	@RequestMapping(value="/route",method=RequestMethod.POST)
	@ResponseBody
	public Json add(Route route,HttpSession session,User user,HttpServletRequest request){
		Json json = new Json();
		route.setUser(user);
		try {
			routeService.add(route);
			json.setCode(StatusCodes.S100);
		}catch(InsertException e){
			json.setCode(StatusCodes.INSERT_ERROR);
		}catch(Exception e){
			LOG.error(e.getMessage());
			json.setCode(StatusCodes.S105);
		}
		return json;
	}
	/**
	 * 获取单个行程
	 * @param rid
	 * @return
	 */
	@RequestMapping(value="/route/{rid}",method=RequestMethod.GET)
	@ResponseBody
	public Json get(@PathVariable("rid") Long rid){
		Json json = new Json();
		try {
			Route route = routeService.getById(rid);
			if(route!=null){
				json.setCode(StatusCodes.S100);
				json.setObj(route);
			}else{
				json.setCode(StatusCodes.S109);
			}
		} catch (Exception e) {	
			LOG.error(e.getMessage());
			json.setCode(StatusCodes.S105);
		}
		return json;
	}
	/**
	 * 删除行程
	 * @param rid
	 * @param session
	 * @param user
	 * @return
	 */
	@LoginLimit
	@RequestMapping(value="/route/{rid}",method=RequestMethod.DELETE)
	@ResponseBody
	public Json delete(@PathVariable("rid") Long rid,HttpSession session,User user){
		Json json = new Json();
		try {
			routeService.delete(rid,user);
			json.setCode(StatusCodes.S100);
		}catch(InsertException e){
			json.setCode(StatusCodes.DELETE_ERROR);
		}catch(Exception e){
			LOG.error(e.getMessage());
			json.setCode(StatusCodes.S105);
		}
		return json;
	}
	/**
	 * 获取我的行程
	 * @param session
	 * @param pageFilter
	 * @param user
	 * @return
	 */
	@LoginLimit
	@RequestMapping(value="/my")
	@ResponseBody
	public Json delete(HttpSession session,PageFilter pageFilter,User user){
		Json json = new Json();
		try {
			List<Route> list = routeService.findByUser(pageFilter, user);
			if(list.size()>0){
				json.setCode(StatusCodes.S100);
				json.setObj(list);
			}else{
				json.setCode(StatusCodes.S109);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
			json.setCode(StatusCodes.S105);
		}
		return json;
	}
	/**
	 * 查询行程
	 * @param pageFilter
	 * @param query
	 * @param request
	 * @param field
	 * @param order
	 * @return
	 */
	@RequestLimit(count=20,time=60)
	@RequestMapping("/q")
	@ResponseBody
	public Json query(PageFilter pageFilter,@RequestParam(required=false,value="query")String query,HttpServletRequest request,@RequestParam(required=false,value="field")String field,@RequestParam(required=false,value="order")String order){
		Json json = new Json();
		List<Route> routeList;
		if(query==null){
			json.setCode(StatusCodes.S109);
			return json;
		}
		try {
			routeList = routeService.query(pageFilter, query,field,order);
			if(routeList!=null){
				json.setCode(StatusCodes.S100);
				json.setObj(routeList);
				
			}
		} catch (Exception e) {
			json.setCode(StatusCodes.S105);
			LOG.error(e.getMessage());
		}
		return json;
	}
}
