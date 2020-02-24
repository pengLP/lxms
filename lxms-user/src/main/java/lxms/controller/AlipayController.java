package lxms.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alipay.config.AlipayConfig;
import com.alipay.sign.RSA;
import lxms.annotation.LoginLimit;
import lxms.annotation.RequestLimit;
import lxms.content.StatusCodes;
import lxms.entity.DepositPay;
import lxms.entity.User;
import lxms.entity.Wanted;
import lxms.entity.pay.AlipayNotifyEntity;
import lxms.service.DepositPayServiceI;
import lxms.service.OrderServiceI;
import lxms.service.PayServiceI;
import lxms.service.WantedServiceI;
import lxms.service.impl.AlipayServiceImpl;
import lxms.tool.Json;

@Controller
@RequestMapping("/alipay")
public class AlipayController {
	@Resource
	private WantedServiceI wantedService;
	@Resource
	private PayServiceI payService;
	@Resource
	private AlipayServiceImpl alipayService;
	@Resource
	private DepositPayServiceI depositPayService;
	@Resource
	private OrderServiceI orderService;
	
	
	/**
	 * 悬赏令支付接口
	 * @param wid
	 * @param session
	 * @param user
	 * @param request
	 * @return
	 */
	@LoginLimit
	@RequestLimit(count = 5,time = 60)
	@RequestMapping("/wantedPay")
	@ResponseBody
	public Json wantedPay(Long wid, HttpSession session,User user,HttpServletRequest request) {
		Json json = new Json();
		Wanted wanted = wantedService.getWanted(wid, session);
		if (wanted == null) {
			json.setCode(StatusCodes.S109);
			return json;
		}
		if (wanted.getStatus() != null && wanted.getStatus().equals("dzf")) {
			// 构建返回的订单信息
			String result = alipayService.AlipayRsaSign(wanted);
			json.setCode(StatusCodes.S100);
			json.setObj(result);
			return json;
		}
		return json;
	}
	@LoginLimit
//	@RequestLimit(count = 10, time = 60)
	@RequestMapping("/depositPay")
	@ResponseBody
	public Json depositPay(Long orderId, HttpSession session,User user,HttpServletRequest request) {
		Json json = new Json();
		DepositPay depositPay = depositPayService.getByOrderId(orderId);
		if (depositPay == null) {
			json.setCode(StatusCodes.S109);
			return json;
		}
		if (depositPay.getPaystatus() != null && depositPay.getPaystatus().equals("n")) {
			// 构建返回的订单信息
			String result = alipayService.AlipayRsaSign(depositPay,orderId);
			json.setCode(StatusCodes.S100);
			json.setObj(result);
			return json;
		}
		return json;
	}

	/**
	 * 接收手机端发来的消息 判断交易状态
	 * 
	 * @param result
	 * @param sign
	 * @param resultStatus
	 * @return
	 */
	@RequestLimit(count = 10, time = 60)
	@RequestMapping("/verify")
	@ResponseBody
	public Json validateResult(String result, String sign, String resultStatus,HttpServletRequest request) {
		Json json = new Json();
		Boolean isAliResult = RSA.verify(result, sign, AlipayConfig.ali_public_key, "utf-8");
		if (isAliResult) {
			/*
			 * 9000 订单支付成功 8000 正在处理中，支付结果未知（有可能已经支付成功），请查询商户订单列表中订单的支付状态 4000
			 * 订单支付失败 6001 用户中途取消 6002 网络连接出错 6004
			 * 支付结果未知（有可能已经支付成功），请查询商户订单列表中订单的支付状态 其它 其它支付错误
			 */
			json.setCode(StatusCodes.SIGN_SUCCESS);
		} else {
			json.setCode(StatusCodes.SIGN_FALSE);
		}
		return json;
	}

	@RequestMapping("/notify")
	@ResponseBody
	public void AceptAlipayNotify(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		// 获取支付宝POST过来反馈信息
		Map<String, String> params = new HashMap<String, String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			// 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			// valueStr = new String(valueStr.getBytes("ISO-8859-1"),"gbk");
			params.put(name, valueStr);
		}

		// 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
		// 商户订单号
		AlipayNotifyEntity alipayNotify = new AlipayNotifyEntity();
		
		try {
			BeanUtils.populate(alipayNotify, requestParams);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(alipayNotify.toString());
		// 商家交易编码
		String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
		// 支付宝交易号
		String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");
		// 交易状态
		String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");
		// 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
		//if (AlipayNotify.verify(params)) {// 验证成功
			//////////////////////////////////////////////////////////////////////////////////////////
			// 请在这里加上商户的业务逻辑程序代码

			// ——请根据您的业务逻辑来编写程序（以下代码仅作参考）——

			if (trade_status.equals("TRADE_FINISHED")) {
				// 判断该笔订单是否在商户网站中已经做过处理
				// 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
				// 如果有做过处理，不执行商户的业务程序
				payService.addLog(alipayNotify); // 记录交易详情
				if (out_trade_no.startsWith("o")) {
					orderService.updatePayStatus(alipayNotify);
				} else {
					wantedService.updatePayStatus(alipayNotify);
				}
				// 注意：
				// 该种交易状态只在两种情况下出现
				// 1、开通了普通即时到账，买家付款成功后。
				// 2、开通了高级即时到账，从该笔交易成功时间算起，过了签约时的可退款时限（如：三个月以内可退款、一年以内可退款等）后。
			} else if (trade_status.equals("TRADE_SUCCESS")) {
				// 判断该笔订单是否在商户网站中已经做过处理
				// 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
				// 如果有做过处理，不执行商户的业务程序
				payService.addLog(alipayNotify); // 记录交易详情
				if (out_trade_no.startsWith("o")) {
					orderService.updatePayStatus(alipayNotify);	
				} else {
					wantedService.updatePayStatus(alipayNotify);
				}

				// 注意：
				// 该种交易状态只在一种情况下出现——开通了高级即时到账，买家付款成功后。
			}

			// ——请根据您的业务逻辑来编写程序（以上代码仅作参考）——

			out.println("success"); // 请不要修改或删除

			//////////////////////////////////////////////////////////////////////////////////////////
		//} else {// 验证失败
		//	out.println("fail");
		//}
	}
}
