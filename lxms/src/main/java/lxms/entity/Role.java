package lxms.entity;


public class Role {
	private Long rid;	//角色id
	private String rName;  //  角色名称
	public Role() {
		// TODO Auto-generated constructor stub
	}
	
	


	public Role(Long rid, String rName) {
		super();
		this.rid = rid;
		this.rName = rName;
	}

	public Long getRid() {
		return rid;
	}
	public void setRid(Long rid) {
		this.rid = rid;
	}
	public String getrName() {
		return rName;
	}
	public void setrName(String rName) {
		this.rName = rName;
	}

	
	
}
