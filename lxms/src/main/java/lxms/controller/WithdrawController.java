package lxms.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import lxms.entity.Withdraw;
import lxms.service.WithdrawServiceI;
import lxms.tool.Grid;
import lxms.tool.Json;
import lxms.tool.PageFilter;

@Controller
@RequestMapping("/admin/withdraw")
public class WithdrawController {
	private Log LOG = LogFactory.getLog(this.getClass());
	@Resource
	private WithdrawServiceI withdrawService;
	
	@RequestMapping("/handle")
	public String handlerPage(){
		return "admin/withdrawComplete";
	}
	@RequestMapping("/notHandle")
	public String notHanderPage(){
		return "admin/withdrawIng";
	}
	@RequestMapping("/listNotHandle")
	@ResponseBody
	public Grid listNotHandle(PageFilter pageFilter,HttpServletRequest request){
		Grid grid = new Grid();
		try {
			List<Withdraw> withdrawMoneyList = withdrawService.listNotHandler(pageFilter);
			if(withdrawMoneyList!=null){
				grid.setRows(withdrawMoneyList);
				grid.setTotal((long)withdrawMoneyList.size());
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		return grid;
	}
	@RequestMapping("/listHandle")
	@ResponseBody
	public Grid listHandle(PageFilter pageFilter,HttpServletRequest request){
		Grid grid = new Grid();
		try {
			List<Withdraw> withdrawMoneyList = withdrawService.listHandler(pageFilter);
			if(withdrawMoneyList!=null){
				grid.setRows(withdrawMoneyList);
				grid.setTotal((long)withdrawMoneyList.size());
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		return grid;
	}
	
	@RequestMapping("/export") // 导出成xml
	@ResponseBody
	public void export(HttpServletResponse response) throws Exception {
		try {
			response.setHeader("Content-Disposition", "attachment;filename="+new String("导出文件.xls".getBytes("utf-8"),"iso8859-1"));
			response.setContentType("application/ynd.ms-excel;charset=UTF-8");
			OutputStream out=response.getOutputStream();
			Workbook workbook = withdrawService.export();
			workbook.write(out);
			out.flush();
			out.close();
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
	
		

	}
	
	@RequestMapping("/success") 
	@ResponseBody
	public Json success(Long withdrawId,HttpServletResponse response) throws Exception {
		Json json = new Json();
		try {
			withdrawService.updateSuccess(withdrawId);
			json.setSuccess(true);
		} catch (Exception e) {
			json.setSuccess(false);
			LOG.error(e.getMessage());
		}
		return json;

	}
	
	@RequestMapping("/fail") 
	@ResponseBody
	public Json fail(Long withdrawId,HttpServletResponse response) throws Exception {
		Json json = new Json();
		try {
			withdrawService.updateFalse(withdrawId);
			json.setSuccess(true);
		} catch (Exception e) {
			json.setSuccess(false);
			LOG.error(e.getMessage());
		}
		return json;

	}
	
	@RequestMapping("uploadAdd")
	@ResponseBody
	public Json uploadAdd(@RequestParam(value = "userUploadFile") MultipartFile file){
		Json json = new Json();
		try {
			InputStream inputStream = file.getInputStream();
			withdrawService.uploadAdd(inputStream);
			json.setMsg("更新成功");
			json.setSuccess(true);
		} catch (IOException e) {
			json.setMsg("更新失败");
			json.setSuccess(false);
			e.printStackTrace();
		}
		return json;
	}
}
