package lxms.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;

import lxms.content.InsideCodes;
import lxms.dao.PurseDao;
import lxms.dao.PurseLogDao;
import lxms.dao.WithdrawDao;
import lxms.entity.Purse;
import lxms.entity.PurseLog;
import lxms.entity.Withdraw;
import lxms.exception.UpdateException;
import lxms.exception.WithdrawException;
import lxms.service.WithdrawServiceI;
import lxms.tool.PageFilter;
import lxms.utils.PageParamsUtil;

@Service
public class WithdrawServiceImpl implements WithdrawServiceI {

	@Resource
	private WithdrawDao withdrawDao;

	@Resource
	private PurseLogDao purseLogDao;
	
	@Resource
	private PurseDao purseDao;
	
	private Log LOG = LogFactory.getLog(this.getClass());

	public Workbook export() {
		Workbook workbook = null;
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("block", 0);
			List<Withdraw> withdrawList = withdrawDao.find(params);
			if (withdrawList != null && withdrawList.size() > 0) {
				workbook = new HSSFWorkbook();
				Sheet sheet = workbook.createSheet("提现信息");
				String headers[] = { "批次号", "付款日期", "付款人email", "账户名称", "总金额（元）", "总笔数" };

				int rowNum = 0;
				Row row = sheet.createRow(rowNum++);
				for (int i = 0; i < headers.length; i++) {
					Cell cell = row.createCell(i);
					cell.setCellValue(headers[i]);
				}
				row = sheet.createRow(rowNum++);

				int headcellNum = 0;
				String now = now();
				row.createCell(headcellNum++).setCellValue(now);
				row.createCell(headcellNum++).setCellValue(now.substring(0, 8));
				row.createCell(headcellNum++).setCellValue("lxmslxms@126.com");
				row.createCell(headcellNum++).setCellValue("哈尔滨旅行买手科技服务有限公司");
				Cell totalCell = row.createCell(headcellNum++);
				row.createCell(headcellNum++).setCellValue(withdrawList.size());

				row = sheet.createRow(rowNum++);
				String headers2[] = { "商户流水号", "收款人email", "收款人姓名", "付款金额（元）", "付款理由" };
				for (int i = 0; i < headers2.length; i++) {
					Cell cell = row.createCell(i);
					cell.setCellValue(headers[i]);
				}
				BigDecimal sum = new BigDecimal(0);
				
				
				params.put("block", 1);
				params.put("status", "clz");
				for (Withdraw withdraw : withdrawList) {
					int cellNum = 0;

					row = sheet.createRow(rowNum++);
					row.createCell(cellNum++).setCellValue(withdraw.getWithdrawId());
					row.createCell(cellNum++).setCellValue(withdraw.getEmail());
					row.createCell(cellNum++).setCellValue(withdraw.getRealName());
					BigDecimal total = new BigDecimal(withdraw.getTotal());
					sum = sum.add(total);
					row.createCell(cellNum++).setCellValue(total.doubleValue());
					row.createCell(cellNum++).setCellValue("奖金");
					params.put("withdrawId", withdraw.getWithdrawId());
					Integer result = withdrawDao.updateStatus(params);
					if(result==0){
						System.out.println("出错了");
						throw new UpdateException(InsideCodes.UPDATE_ERROR.getMsg());
					}
				}
				totalCell.setCellValue(sum.doubleValue());
			}else{
				throw new WithdrawException("没有可以处理的");
			}

		} catch (UpdateException e) {
			throw e;
		}catch (Exception e) {
			LOG.error(e.getMessage());
			throw new WithdrawException("出错了");
		}

		return workbook;
	}

	public void uploadAdd(InputStream inputStream) throws IOException {
		try {
			POIFSFileSystem fileSystem = new POIFSFileSystem(inputStream);
			Workbook workbook = new HSSFWorkbook(fileSystem);
			Sheet sheet = workbook.getSheetAt(0);
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				Row row = sheet.getRow(i);
				if (row == null) {
					continue;
				}
				Withdraw withdrawMoney = new Withdraw();

				// withdrawMoney.setQq(StringUtil.fomateCell(row.getCell(0)));

			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}

	}

	public List<Withdraw> listNotHandler(PageFilter pageFilter) {
		List<Withdraw> withdrawList = null;
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			PageParamsUtil.addToParams(params, pageFilter);
			params.put("block", 0);
			params.put("status", "cj");
			withdrawList = withdrawDao.find(params);
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		return withdrawList;
	}
	public List<Withdraw> listHandler(PageFilter pageFilter) {
		List<Withdraw> withdrawList = null;
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			PageParamsUtil.addToParams(params, pageFilter);
			params.put("block", 1);
			params.put("status", "clz");
			withdrawList = withdrawDao.find(params);
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		return withdrawList;
	}	

	public String now() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String now = dateFormat.format(cal.getTime());
		return now;
	}

	public void updateSuccess(Long withdrawId) {
		try {
			Withdraw withdraw = withdrawDao.getByWithdrawId(withdrawId);
			if(withdraw.getBlock()!=0){
				Map<String,Object> params = new HashMap<String, Object>();
				params.put("withdrawId", withdrawId);
				params.put("status", "wc");
				Integer withdrawResult = withdrawDao.updateStatus(params);
				
				Purse purse = purseDao.getById(withdraw.getPurseId());
				PurseLog purseLog = PurseLog.withdrawSuccess(purse, withdraw.getTotal(),"wc");
				
				Integer purseLogResult = purseLogDao.add(purseLog);
				if(withdrawResult==0||purseLogResult==0){
					throw new UpdateException(InsideCodes.UPDATE_ERROR.getMsg());
				}
			}
			
		}catch (UpdateException e) {
			LOG.error(e.getMessage());
			throw e;
		} catch (Exception e) {
			LOG.error(e.getMessage());
			throw new WithdrawException("成功出错");
		}

	}

	public void updateFalse(Long withdrawId) {
		try {
			Map<String,Object> params = new HashMap<String, Object>();
			params.put("withdrawId", withdrawId);
			params.put("block", "0");	
			params.put("status", "cj");
			Integer withdrawResult = withdrawDao.updateStatus(params); 
			if(withdrawResult==0){
				throw new UpdateException(InsideCodes.UPDATE_ERROR.getMsg());
			}
		} catch (UpdateException e) {
			LOG.error(e.getMessage());
			throw e;
		}catch (Exception e) {
			LOG.error(e.getMessage());
			throw new WithdrawException("失败出错");
		}
	}

}
