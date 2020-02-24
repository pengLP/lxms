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
		// ΢��֧������
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
		// ΢��֧������
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
        String result  = new String(outSteam.toByteArray(),"utf-8");//��ȡ΢�ŵ�������notify_url�ķ�����Ϣ
        Map<String, String> map = XMLUtil.doXMLParse(result);
        if (map.get("result_code").toString().equalsIgnoreCase("SUCCESS")) {
            //TODO �����ݿ�Ĳ���        	
        	WeiPayNotify weiPayNotify = new WeiPayNotify();
			// ȡ���������ҵ����

        	BeanUtils.populate(weiPayNotify, map);

			// ------------------------------
			// ����ҵ��ʼ
			// ------------------------------
			payService.addLog(weiPayNotify); // ��¼��������
			if (weiPayNotify.getOut_trade_no().startsWith("o")) {
				orderService.updatePayStatus(weiPayNotify);
			} else {
				wantedService.updatePayStatus(weiPayNotify);
			}
            response.getWriter().write(PayCommonUtil.setXML("SUCCESS", ""));   //����΢�ŷ����������յ���Ϣ�ˣ���Ҫ�ڵ��ûص�action��

        }
	}

	
}
