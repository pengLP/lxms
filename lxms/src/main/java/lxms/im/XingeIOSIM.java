package lxms.im;
import com.tencent.xinge.XingeApp;

public class XingeIOSIM {
	private static final long ACCESSID = 2200215295L;
//	private static final String ACCESSKEY = "I6Y1R64CE9YG";
	private static final String SECRETKEY = "f1977c5de58e1d31167ee4e76cd7c7a5";
	private static final int MODE = XingeApp.IOSENV_DEV;
	
	
	/**
	 * ���͸�ָ��IOS�豸
	 * @param accountId
	 * @param content
	 */
	public static void pushIOS(String accountId,String content){
		XingeApp.pushAccountIos(ACCESSID, SECRETKEY, content, accountId,MODE);
	}
	/**
	 * ���͸�����IOS�豸
	 * @param content
	 */
	public static void pushAllIOS(String content){
		XingeApp.pushAllIos(ACCESSID, SECRETKEY, "����", MODE);
	}
	/**
	 * ���͸�ָ����ǩ��IOS�豸
	 * @param content
	 * @param tag
	 */
	public static void pushTagIos(String content,String tag){
		XingeApp.pushTagIos(ACCESSID, SECRETKEY, content, tag, MODE);
	}

}	

