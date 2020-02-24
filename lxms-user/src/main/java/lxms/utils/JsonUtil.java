package lxms.utils;

import lxms.content.StatusCodes;
import lxms.tool.Json;

public class JsonUtil {
	public static void isSuccess(Json json,Integer result){
		if(result==1){
			json.setCode(StatusCodes.S100);
		}else{
			json.setCode(StatusCodes.S113);
		}
	}
}
