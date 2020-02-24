package lxms.im;
import com.tencent.xinge.XingeApp;

public class XingeAndroidIM {
	private static final long ACCESSID = 2100215296L;
	// private static final String ACCESSKEY = "I6Y1R64CE9YG";

	private static final String SECRETKEY = "64f4d3c251c68d73044fd248ac91e9c5";
	private static final int MODE = XingeApp.IOSENV_DEV;

	// System.out.println(XingeApp.pushTokenAndroid(000, "secretKey", "test",
	// "测试", "token"));
	// System.out.println(XingeApp.pushAccountAndroid(000, "secretKey", "test",
	// "测试", "account"));
	// System.out.println(XingeApp.pushAllAndroid(000, "secretKey", "test",
	// "测试"));
	// System.out.println(XingeApp.pushTagAndroid(000, "secretKey", "test",
	// "测试", "tag"));
	/**
	 * 推送给指定Android设备
	 * 
	 * @param accountId
	 * @param content
	 */
	public static void pushAndroid(String accountId, String title, String content) {
		XingeApp.pushAccountAndroid(ACCESSID, SECRETKEY, title, content, accountId);
	}

	/**
	 * 推送给所有Android设备
	 * 
	 * @param content
	 */
	public static void pushAllAndroid(String content, String title) {
		XingeApp.pushAllAndroid(ACCESSID, SECRETKEY, title, content);
	}

	/**
	 * 推送给指定标签的Android设备
	 * 
	 * @param content
	 * @param tag
	 */
	public static void pushTagIos(String content, String title, String tag) {
		XingeApp.pushTagAndroid(ACCESSID, SECRETKEY, title, content, tag);
	}
}
