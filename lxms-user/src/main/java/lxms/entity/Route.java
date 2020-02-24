package lxms.entity;

import java.util.Date;
import lxms.entity.User;

/**
 * �г̱�
 * 
 * @author Administrator
 *
 */
public class Route{



	private Long rid; // �г�id
	private User user; // �г̷�����
	private Date travelDate; // ����ʱ��
	private String startPlace; // ��ʼ�ص�
	private String travelPlace; // ���еص�
	private String mainCity; // ��Ҫ����
	private Date returnDate; // ����ʱ��
	private String perference; // ����ϲ��
	private Date createDate; // ����ʱ��
	private String instruction; // ����˵��
	
	public Route() {
		// TODO Auto-generated constructor stub
	}

	public Route(Long rid, User user, Date travelDate, String startPlace, String travelPlace, String mainCity,
			Date returnDate, String perference, Date createDate, String instruction) {
		super();
		this.rid = rid;
		this.user = user;
		this.travelDate = travelDate;
		this.startPlace = startPlace;
		this.travelPlace = travelPlace;
		this.mainCity = mainCity;
		this.returnDate = returnDate;
		this.perference = perference;
		this.createDate = createDate;
		this.instruction = instruction;
	}
	

	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	public Long getRid() {
		return rid;
	}

	public void setRid(Long rid) {
		this.rid = rid;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getTravelDate() {
		return travelDate;
	}

	public void setTravelDate(Date travelDate) {
		this.travelDate = travelDate;
	}

	public String getStartPlace() {
		return startPlace;
	}

	public void setStartPlace(String startPlace) {
		this.startPlace = startPlace;
	}

	public String getTravelPlace() {
		return travelPlace;
	}

	public void setTravelPlace(String travelPlace) {
		this.travelPlace = travelPlace;
	}

	public String getMainCity() {
		return mainCity;
	}

	public void setMainCity(String mainCity) {
		this.mainCity = mainCity;
	}

	public String getPerference() {
		return perference;
	}

	public void setPerference(String perference) {
		this.perference = perference;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getInstruction() {
		return instruction;
	}

	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}



}
