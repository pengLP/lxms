package lxms.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import lxms.entity.Wanted;

public interface WantedDao {
	
	public Integer add(Wanted wanted);
	
	public Integer delete(Long wid,Long uid);
	
	public Integer update(Wanted wanted);
	
	public Integer updateStatus(@Param("wid")Long wid,@Param("status")String status);
	
	public Wanted getById(Long wid);
	
	public List<Wanted> find(Map<String,Object> params);
	
	public List<Wanted> findDetail(Map<String,Object> params);
}
