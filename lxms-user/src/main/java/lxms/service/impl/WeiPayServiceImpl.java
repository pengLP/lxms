package lxms.service.impl;

import java.io.IOException;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.JDOMException;

import org.springframework.stereotype.Service;

import com.tenpay.AccessTokenRequestHandler;

import com.tenpay.PackageRequestHandler;

import com.tenpay.util.CommonUtil;
import com.tenpay.util.ConstantUtil;
import com.tenpay.util.PayCommonUtil;
import com.tenpay.util.WXUtil;
import com.tenpay.util.XMLUtil;

import lxms.dao.DepositPayDao;
import lxms.dao.WantedDao;
import lxms.entity.DepositPay;
import lxms.entity.Wanted;
import lxms.entity.pay.WeiPayReturn;
import lxms.exception.WeiXinException;

@Service
public class WeiPayServiceImpl {
	@Resource
	private WantedDao wantedDao;
	@Resource
	private DepositPayDao depositPaydao;
	
	private DecimalFormat decimalFormat = new DecimalFormat("0");
	
	private Log LOG = LogFactory.getLog(this.getClass());
	// 接收财付通通知的URL
	public static String notify_url = "http://139.129.221.22:8080/lxms-user/weiPay/Notify";

	public String getWeiToken(HttpServletRequest request, HttpServletResponse response) {
		PackageRequestHandler packageReqHandler = new PackageRequestHandler(request, response);// 生成package的请求类
		packageReqHandler.setKey(ConstantUtil.PARTNER_KEY);
		String token = AccessTokenRequestHandler.getAccessToken();
		return token;
	}

	public int packageWeiPayRequest(WeiPayReturn weiPayReturn,String uniqueId, HttpServletRequest request,
			HttpServletResponse response) throws WeiXinException{
		int retcode = -1;
		String noncestr = WXUtil.getNonceStr();
		
		
		SortedMap<Object,Object> parameters = new TreeMap<Object,Object>();
		parameters.put("appid", ConstantUtil.APP_ID);
		parameters.put("body", "旅行买手订单");
		parameters.put("mch_id", ConstantUtil.PARTNER);
		parameters.put("nonce_str",noncestr);
		parameters.put("notify_url", notify_url);
		if(uniqueId.startsWith("o")){
			DepositPay depositPay = depositPaydao.getByOrderId(Long.parseLong(uniqueId.substring(1,uniqueId.length())));
			if(depositPay!=null){
				setParams(parameters,depositPay,uniqueId,request);
			}
			
		}else{
			Wanted wanted = wantedDao.getById(Long.parseLong(uniqueId));
			if(wanted!=null){
				setParams(parameters, wanted,request);
			}
		}		
		parameters.put("trade_type", "APP");		
		String sign = PayCommonUtil.createSign("UTF-8", parameters); //构建sign
		parameters.put("sign", sign);
		String requestXML = PayCommonUtil.getRequestXml(parameters);
		String result =CommonUtil.httpsRequest(ConstantUtil.GATEURL, "POST", requestXML);
		try {
			Map<String, String> map = XMLUtil.doXMLParse(result);
			Set<String> keys = map.keySet();
			for(String key:keys){
				
				System.out.println(key+":"+map.get(key));
			}
			String return_code=map.get("return_code");
			String return_msg =map.get("return_msg");
			if(return_code.equals("SUCCESS")){
				retcode=0;
				BeanUtils.populate(weiPayReturn, map);
				SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				String date = sim.format(new Date());

				Date d=sim.parse(date);
				String now = d.getTime()+"";
				weiPayReturn.setTimestamp(now.substring(0, 10));
				SortedMap<Object,Object> parameters2 = new TreeMap<Object,Object>();
				parameters2.put("appid", weiPayReturn.getAppid());
				parameters2.put("noncestr", weiPayReturn.getNonce_str());
				parameters2.put("package","Sign=WXPay");				
				parameters2.put("partnerid", weiPayReturn.getMch_id());
				parameters2.put("prepayid", weiPayReturn.getPrepay_id());
				parameters2.put("timestamp", weiPayReturn.getTimestamp());
				String sign2 = PayCommonUtil.createSign("UTF-8", parameters2); //构建sign
				weiPayReturn.setSign(sign2);
			}else{
				LOG.error(uniqueId+"微信支付问题+"+return_code+":"+return_msg);
			}
		} catch (JDOMException e) {			
			LOG.error(e.getMessage());
		} catch (IOException e) {
			LOG.error(e.getMessage());			
		} catch (IllegalAccessException e) {
			LOG.error(e);
		} catch (InvocationTargetException e) {
			LOG.error(e);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return retcode;
	}

	/**
	 * 根据悬赏设置参数
	 * 
	 * @param packageReqHandler
	 * @param wanted
	 * @param request
	 */
	public void setParams(SortedMap<Object,Object> params, Wanted wanted,HttpServletRequest request) {
		
		BigDecimal total = new BigDecimal(wanted.getTotal());
		params.put("out_trade_no","wanted"+wanted.getWid()); // 商家订单号
		params.put("spbill_create_ip",request.getRemoteAddr());  //
		params.put("total_fee",decimalFormat.format( total.multiply(new BigDecimal(100)))); // 商品金额,以分为单位

	}

	/**
	 * 根据押金设置参数
	 * 
	 * @param packageReqHandler
	 * @param depositPay
	 * @param request
	 */
	public void setParams(SortedMap<Object,Object> params, DepositPay depositPay,String uniqueId,HttpServletRequest request) {
		BigDecimal total = new BigDecimal(depositPay.getPayamount());
		params.put("out_trade_no", uniqueId); // 商家订单号
		params.put("spbill_create_ip",request.getRemoteAddr());
		params.put("total_fee", decimalFormat.format( total.multiply(new BigDecimal(100)))); // 商品金额,以分为单位
	}
}
