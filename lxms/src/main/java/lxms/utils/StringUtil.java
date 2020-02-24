package lxms.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;


public class StringUtil {

	public static boolean isEmpty(String str){
		if(str==null||"".equals(str.trim())){
			return true;
		}else{
			return false;
		}
	}
	

	public static boolean isNotEmpty(String str){
		if((str!=null)&&!"".equals(str.trim())){
			return true;
		}else{
			return false;
		}
	}
	

	public static String formatLike(String str){
		if(isNotEmpty(str)){
			return "%"+str+"%";
		}else{
			return null;
		}
	}
	

	public static List<String> filterWhite(List<String> list){
		List<String> resultList=new ArrayList<String>();
		for(String l:list){
			if(isNotEmpty(l)){
				resultList.add(l);
			}
		}
		return resultList;
	}
	

	private static String string = "abcdefghijklmnopqrstuvwxyz";   
	 
	public static String getRandomString(int length){
	    StringBuffer sb = new StringBuffer();
	    for (int i = 0; i < length; i++) {
	        sb.append(string.charAt(Integer.parseInt(Math.round(Math.random() * (length-1))+"")));
	    }
	    return sb.toString();
	}
	public static void main(String[] args) {
		System.out.println(StringUtil.getRandomString(10));
	}
	
	public static String fomateCell(Cell cell){
		if(cell==null){
			return "";
		}else{
			if(cell.getCellType()==HSSFCell.CELL_TYPE_BOOLEAN){
				return String.valueOf(cell.getBooleanCellValue());
			}else if(cell.getCellType()==HSSFCell.CELL_TYPE_NUMERIC){
				return String.valueOf(cell.getNumericCellValue());
			}else{
				return cell.getStringCellValue();
			}
		}
	}
	
}
