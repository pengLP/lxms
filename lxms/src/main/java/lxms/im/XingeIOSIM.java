package lxms.im;
import com.tencent.xinge.XingeApp;

public class XingeIOSIM {
	private static final long ACCESSID = 2200215295L;
//	private static final String ACCESSKEY = "I6Y1R64CE9YG";
	private static final String SECRETKEY = "f1977c5de58e1d31167ee4e76cd7c7a5";
	private static final int MODE = XingeApp.IOSENV_DEV;
	
	
	/**
	 * 推送给指定IOS设备
	 * @param accountId
	 * @param content
	 */
	public static void pushIOS(String accountId,String content){
		XingeApp.pushAccountIos(ACCESSID, SECRETKEY, content, accountId,MODE);
	}
	/**
	 * 推送给所有IOS设备
	 * @param content
	 */
	public static void pushAllIOS(String content){
		XingeApp.pushAllIos(ACCESSID, SECRETKEY, "测试", MODE);
	}
	/**
	 * 推送给指定标签的IOS设备
	 * @param content
	 * @param tag
	 */
	public static void pushTagIos(String content,String tag){
		XingeApp.pushTagIos(ACCESSID, SECRETKEY, content, tag, MODE);
	}

}	

