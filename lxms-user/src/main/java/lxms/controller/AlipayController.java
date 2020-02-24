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
	 * ������֧���ӿ�
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
			// �������صĶ�����Ϣ
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
			// �������صĶ�����Ϣ
			String result = alipayService.AlipayRsaSign(depositPay,orderId);
			json.setCode(StatusCodes.S100);
			json.setObj(result);
			return json;
		}
		return json;
	}

	/**
	 * �����ֻ��˷�������Ϣ �жϽ���״̬
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
			 * 9000 ����֧���ɹ� 8000 ���ڴ����У�֧�����δ֪���п����Ѿ�֧���ɹ��������ѯ�̻������б��ж�����֧��״̬ 4000
			 * ����֧��ʧ�� 6001 �û���;ȡ�� 6002 �������ӳ��� 6004
			 * ֧�����δ֪���п����Ѿ�֧���ɹ��������ѯ�̻������б��ж�����֧��״̬ ���� ����֧������
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
		// ��ȡ֧����POST����������Ϣ
		Map<String, String> params = new HashMap<String, String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			// ����������δ����ڳ�������ʱʹ�á����mysign��sign�����Ҳ����ʹ����δ���ת��
			// valueStr = new String(valueStr.getBytes("ISO-8859-1"),"gbk");
			params.put(name, valueStr);
		}

		// ��ȡ֧������֪ͨ���ز������ɲο������ĵ���ҳ����תͬ��֪ͨ�����б�(���½����ο�)//
		// �̻�������
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
		// �̼ҽ��ױ���
		String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
		// ֧�������׺�
		String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");
		// ����״̬
		String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");
		// ��ȡ֧������֪ͨ���ز������ɲο������ĵ���ҳ����תͬ��֪ͨ�����б�(���Ͻ����ο�)//
		//if (AlipayNotify.verify(params)) {// ��֤�ɹ�
			//////////////////////////////////////////////////////////////////////////////////////////
			// ������������̻���ҵ���߼��������

			// �������������ҵ���߼�����д�������´�������ο�������

			if (trade_status.equals("TRADE_FINISHED")) {
				// �жϸñʶ����Ƿ����̻���վ���Ѿ���������
				// ���û�������������ݶ����ţ�out_trade_no�����̻���վ�Ķ���ϵͳ�в鵽�ñʶ�������ϸ����ִ���̻���ҵ�����
				// ���������������ִ���̻���ҵ�����
				payService.addLog(alipayNotify); // ��¼��������
				if (out_trade_no.startsWith("o")) {
					orderService.updatePayStatus(alipayNotify);
				} else {
					wantedService.updatePayStatus(alipayNotify);
				}
				// ע�⣺
				// ���ֽ���״ֻ̬����������³���
				// 1����ͨ����ͨ��ʱ���ˣ���Ҹ���ɹ���
				// 2����ͨ�˸߼���ʱ���ˣ��Ӹñʽ��׳ɹ�ʱ�����𣬹���ǩԼʱ�Ŀ��˿�ʱ�ޣ��磺���������ڿ��˿һ�����ڿ��˿�ȣ���
			} else if (trade_status.equals("TRADE_SUCCESS")) {
				// �жϸñʶ����Ƿ����̻���վ���Ѿ���������
				// ���û�������������ݶ����ţ�out_trade_no�����̻���վ�Ķ���ϵͳ�в鵽�ñʶ�������ϸ����ִ���̻���ҵ�����
				// ���������������ִ���̻���ҵ�����
				payService.addLog(alipayNotify); // ��¼��������
				if (out_trade_no.startsWith("o")) {
					orderService.updatePayStatus(alipayNotify);	
				} else {
					wantedService.updatePayStatus(alipayNotify);
				}

				// ע�⣺
				// ���ֽ���״ֻ̬��һ������³��֡�����ͨ�˸߼���ʱ���ˣ���Ҹ���ɹ���
			}

			// �������������ҵ���߼�����д�������ϴ�������ο�������

			out.println("success"); // �벻Ҫ�޸Ļ�ɾ��

			//////////////////////////////////////////////////////////////////////////////////////////
		//} else {// ��֤ʧ��
		//	out.println("fail");
		//}
	}
}
