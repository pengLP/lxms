package lxms.controller;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.instrument.classloading.SimpleLoadTimeWeaver;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tenpay.client.ClientResponseHandler;

import com.tenpay.util.PayCommonUtil;
import com.tenpay.util.XMLUtil;

import lxms.annotation.LoginLimit;

import lxms.content.StatusCodes;
import lxms.entity.DepositPay;
import lxms.entity.User;
import lxms.entity.pay.WeiPayNotify;
import lxms.entity.pay.WeiPayReturn;
import lxms.service.DepositPayServiceI;
import lxms.service.OrderServiceI;
import lxms.service.PayServiceI;
import lxms.service.WantedServiceI;
import lxms.service.impl.WeiPayServiceImpl;
import lxms.tool.Json;
import lxms.utils.DateUtil;

@Controller
@RequestMapping("/weiPay")
public class WeiPayController {
	@Resource
	private WantedServiceI wantedService;
	@Resource
	private WeiPayServiceImpl weiPayService;
	@Resource
	private PayServiceI payService;
	@Resource
	private OrderServiceI orderService;
	@Resource
	private DepositPayServiceI depositPayService;
	private Log LOG = LogFactory.getLog(this.getClass());

//	@RequestLimit(count = 10, time = 60)
	@LoginLimit
	@RequestMapping("/wantedPay")
	@ResponseBody
	public Json payRequest(String id, HttpServletResponse response, HttpServletRequest request, HttpSession session,
			User user) {
		Json json = new Json();
		if (id == null || id.isEmpty()) {
			json.setCode(StatusCodes.S113);
			return json;
		}
		// ---------------------------------------------------------
		// 微信支付请求
		// ---------------------------------------------------------
		WeiPayReturn weiPayReturn = new WeiPayReturn();
		int retcode;
		try {
			retcode = weiPayService.packageWeiPayRequest(weiPayReturn, id, request, response);
			if (retcode == 0) {
				json.setCode(StatusCodes.S100);
				json.setObj(weiPayReturn);
			}else{
				json.setCode(StatusCodes.S105);
				json.setObj(weiPayReturn);
			}
		} catch (Exception e) {
			json.setCode(StatusCodes.S105);
			json.setObj(weiPayReturn);
			LOG.error(e.getMessage());
		}

		return json;
	}
	@LoginLimit
	@RequestMapping("/depositPay")
	@ResponseBody
	public Json orderPayRequest(Long id, HttpServletResponse response, HttpServletRequest request, HttpSession session,
			User user) {
		Json json = new Json();
		if (id == null) {
			json.setCode(StatusCodes.S113);
			return json;
		}
		// ---------------------------------------------------------
		// 微信支付请求
		// ---------------------------------------------------------
		WeiPayReturn weiPayReturn = new WeiPayReturn();
		DepositPay depositPay = depositPayService.getByOrderId(id);
		int retcode;
		try {
			retcode = weiPayService.packageWeiPayRequest(weiPayReturn,"o"+id, request, response);
			if (retcode == 0) {
				json.setCode(StatusCodes.S100);
				json.setObj(weiPayReturn);
			}else{
				json.setCode(StatusCodes.S105);
				json.setObj(weiPayReturn);
			}
		} catch (Exception e) {
			json.setCode(StatusCodes.S105);
			json.setObj(weiPayReturn);
			LOG.error(e.getMessage());
		}

		return json;
	}

	@RequestMapping("/Notify")
	@ResponseBody
	public void weiNotify(HttpServletResponse response, HttpServletRequest request) throws Exception {

        InputStream inStream = request.getInputStream();
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outSteam.write(buffer, 0, len);
        }
        outSteam.close();
        inStream.close();
        String result  = new String(outSteam.toByteArray(),"utf-8");//获取微信调用我们notify_url的返回信息
        Map<String, String> map = XMLUtil.doXMLParse(result);
        if (map.get("result_code").toString().equalsIgnoreCase("SUCCESS")) {
            //TODO 对数据库的操作        	
        	WeiPayNotify weiPayNotify = new WeiPayNotify();
			// 取结果参数做业务处理

        	BeanUtils.populate(weiPayNotify, map);

			// ------------------------------
			// 处理业务开始
			// ------------------------------
			payService.addLog(weiPayNotify); // 记录交易详情
			if (weiPayNotify.getOut_trade_no().startsWith("o")) {
				orderService.updatePayStatus(weiPayNotify);
			} else {
				wantedService.updatePayStatus(weiPayNotify);
			}
            response.getWriter().write(PayCommonUtil.setXML("SUCCESS", ""));   //告诉微信服务器，我收到信息了，不要在调用回调action了

        }
	}

	
}
