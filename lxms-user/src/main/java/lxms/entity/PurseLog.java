package lxms.entity;

import java.sql.Date;

/**
 * Ǯ����־
 * 
 * @author Yanlihui
 *
 */
public class PurseLog {
	private Long purseLogId; // ��־Id
	private Purse purse; // Ǯ��
	private Double sum; // ����
	private String symbol; // ֧����������
	private String target; // Ŀ��֧����
	private Date createDate; // ����ʱ��
	private String ip; // ����ID
	private Double balance; // ���
	private String status;

	public PurseLog() {
		// TODO Auto-generated constructor stub
	}

	public static PurseLog add(Purse purse,Double sum){
		PurseLog purseLog = new PurseLog();
		purseLog.setPurse(purse);
		purseLog.setSum(sum);
		purseLog.setSymbol("+");
		purseLog.setBalance(purse.getBalance());
		purseLog.setStatus("dz");
		return purseLog;
	}
	public static PurseLog withdraw(Purse purse,Double sum){
		PurseLog purseLog = new PurseLog();
		purseLog.setPurse(purse);
		purseLog.setSum(sum);
		purseLog.setSymbol("-");
		purseLog.setBalance(purse.getBalance());
		purseLog.setStatus("txz");
		purseLog.setTarget(purse.getBindingAlipay());
		return purseLog;
	}
	public static PurseLog withdraw(Purse purse,Double sum,String target){
		PurseLog purseLog = new PurseLog();
		purseLog.setPurse(purse);
		purseLog.setSum(sum);
		purseLog.setSymbol("-");
		purseLog.setBalance(purse.getBalance());
		purseLog.setStatus("txz");
		purseLog.setTarget(target);
		return purseLog;
	}

	public PurseLog(Long purseLogId, Purse purse, Double sum, String symbol, String target, Date createDate, String ip,
			Double balance, String status) {
		super();
		this.purseLogId = purseLogId;
		this.purse = purse;
		this.sum = sum;
		this.symbol = symbol;
		this.target = target;
		this.createDate = createDate;
		this.ip = ip;
		this.balance = balance;
		this.status = status;
	}



	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}



	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public Long getPurseLogId() {
		return purseLogId;
	}

	public void setPurseLogId(Long purseLogId) {
		this.purseLogId = purseLogId;
	}

	public Purse getPurse() {
		return purse;
	}

	public void setPurse(Purse purse) {
		this.purse = purse;
	}

	public Double getSum() {
		return sum;
	}

	public void setSum(Double sum) {
		this.sum = sum;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

}
