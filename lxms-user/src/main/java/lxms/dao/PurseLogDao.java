package lxms.dao;

import java.util.List;
import java.util.Map;


import lxms.entity.PurseLog;

public interface PurseLogDao {
	public Integer add(PurseLog purseLog);
	
	
	public List<PurseLog> find(Map<String,Object> params);
}
