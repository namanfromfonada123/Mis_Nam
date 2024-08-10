package mis.com.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "mis_call_detail_summary_report")
public class MisCallDetailSummaryReport {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	private String executionDt;
	private String userName;
	private String campaignName;
	private String leadName;
	private String totalMSISDN;
	private String validMSISDN;
	private String noOfRetry;
	private String attemptedCalls;
	private String connectedCalls;
	private String digitPressed;
	private String listenRate;
	private String totalBillSec;
	private String creditUsed;
	private String ipAddress;
	private String userType;
	private String accountName;
	private String createDate;

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getExecutionDt() {
		return executionDt;
	}

	public void setExecutionDt(String executionDt) {
		this.executionDt = executionDt;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCampaignName() {
		return campaignName;
	}

	public void setCampaignName(String campaignName) {
		this.campaignName = campaignName;
	}

	public String getLeadName() {
		return leadName;
	}

	public void setLeadName(String leadName) {
		this.leadName = leadName;
	}

	public String getTotalMSISDN() {
		return totalMSISDN;
	}

	public void setTotalMSISDN(String totalMSISDN) {
		this.totalMSISDN = totalMSISDN;
	}

	public String getValidMSISDN() {
		return validMSISDN;
	}

	public void setValidMSISDN(String validMSISDN) {
		this.validMSISDN = validMSISDN;
	}

	public String getNoOfRetry() {
		return noOfRetry;
	}

	public void setNoOfRetry(String noOfRetry) {
		this.noOfRetry = noOfRetry;
	}

	public String getAttemptedCalls() {
		return attemptedCalls;
	}

	public void setAttemptedCalls(String attemptedCalls) {
		this.attemptedCalls = attemptedCalls;
	}

	public String getConnectedCalls() {
		return connectedCalls;
	}

	public void setConnectedCalls(String connectedCalls) {
		this.connectedCalls = connectedCalls;
	}

	public String getDigitPressed() {
		return digitPressed;
	}

	public void setDigitPressed(String digitPressed) {
		this.digitPressed = digitPressed;
	}

	public String getListenRate() {
		return listenRate;
	}

	public void setListenRate(String listenRate) {
		this.listenRate = listenRate;
	}

	public String getTotalBillSec() {
		return totalBillSec;
	}

	public void setTotalBillSec(String totalBillSec) {
		this.totalBillSec = totalBillSec;
	}

	public String getCreditUsed() {
		return creditUsed;
	}

	public void setCreditUsed(String creditUsed) {
		this.creditUsed = creditUsed;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	@Override
	public String toString() {
		return "MisCallDetailSummaryReport [id=" + id + ", executionDt=" + executionDt + ", userName=" + userName
				+ ", campaignName=" + campaignName + ", leadName=" + leadName + ", totalMSISDN=" + totalMSISDN
				+ ", validMSISDN=" + validMSISDN + ", noOfRetry=" + noOfRetry + ", attemptedCalls=" + attemptedCalls
				+ ", connectedCalls=" + connectedCalls + ", digitPressed=" + digitPressed + ", listenRate=" + listenRate
				+ ", totalBillSec=" + totalBillSec + ", creditUsed=" + creditUsed + ", ipAddress=" + ipAddress
				+ ", userType=" + userType + ", accountName=" + accountName + ", createDate=" + createDate + "]";
	}

}
