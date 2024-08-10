package mis.com.Naman.Modal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "call_data")
public class CallData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "executionDate", columnDefinition = "date DEFAULT NULL ")
    private Date executionDate;

	private String userName;
    private String clientName;
    private int totalMsisdn;
    private int validMsisdn;
    private int attemptedCalls;
    private int connectedCalls;
    private int digitPressed;
    private double listenRate;
    private int totalBillSec;
    private int creditUsed;
    private String panelName;
    
    
    @Column(length = 30)
	private LocalDateTime createdOn;
	
	@Column(length = 30)
	private LocalDateTime updatedOn;
	  

	  @PrePersist
	  protected void onCreate() {
	      createdOn = LocalDateTime.now();
	      
	  }
	  
	  @PreUpdate
	  protected void onUpdate() {
	      updatedOn = LocalDateTime.now();
	  }
    
	    public String getClientName() {
			return clientName;
		}

		public void setClientName(String clientName) {
			this.clientName = clientName;
		}
		
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getExecutionDate() {
		return executionDate;
	}
	public void setExecutionDate(Date executionDate) {
		this.executionDate = executionDate;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getTotalMsisdn() {
		return totalMsisdn;
	}
	public void setTotalMsisdn(int totalMsisdn) {
		this.totalMsisdn = totalMsisdn;
	}
	public int getValidMsisdn() {
		return validMsisdn;
	}
	public void setValidMsisdn(int validMsisdn) {
		this.validMsisdn = validMsisdn;
	}
	public int getAttemptedCalls() {
		return attemptedCalls;
	}
	public void setAttemptedCalls(int attemptedCalls) {
		this.attemptedCalls = attemptedCalls;
	}
	public int getConnectedCalls() {
		return connectedCalls;
	}
	public void setConnectedCalls(int connectedCalls) {
		this.connectedCalls = connectedCalls;
	}
	public int getDigitPressed() {
		return digitPressed;
	}
	public void setDigitPressed(int digitPressed) {
		this.digitPressed = digitPressed;
	}
	public double getListenRate() {
		return listenRate;
	}
	public void setListenRate(double listenRate) {
		this.listenRate = listenRate;
	}
	public int getTotalBillSec() {
		return totalBillSec;
	}
	public void setTotalBillSec(int totalBillSec) {
		this.totalBillSec = totalBillSec;
	}
	public int getCreditUsed() {
		return creditUsed;
	}
	public void setCreditUsed(int creditUsed) {
		this.creditUsed = creditUsed;
	}
	public String getPanelName() {
		return panelName;
	}
	public void setPanelName(String panelName) {
		this.panelName = panelName;
	}

	@Override
	public String toString() {
		return "CallData [id=" + id + ", executionDate=" + executionDate + ", userName=" + userName + ", clientName="
				+ clientName + ", totalMsisdn=" + totalMsisdn + ", validMsisdn=" + validMsisdn + ", attemptedCalls="
				+ attemptedCalls + ", connectedCalls=" + connectedCalls + ", digitPressed=" + digitPressed
				+ ", listenRate=" + listenRate + ", totalBillSec=" + totalBillSec + ", creditUsed=" + creditUsed
				+ ", panelName=" + panelName + ", createdOn=" + createdOn + ", updatedOn=" + updatedOn + "]";
	}

    // Getters and setters
    
    
}


//EXECUTION_DATE	USER_NAME	TOTAL_MSISDN	VALID_MSISDN	 ATTEMPTED_CALLS	CONNECTED_CALLS	DIGIT_PRESSED	LISTEN_RATE	TOTAL_BILL_SEC	CREDIT_USED	PANEL_NAME
//7/20/2024	KTPG_Pre	1783	1779	2862	1141	144	64.13715571	15409	1515	Panel-86
//6/21/2024	Laxmi	2256	2256	16	0	0	0	0	0	Panel-86
//3/28/2020	snapdeal	20673	20673	21466	7051	6088	34.1072897	187348	16022	Panel-86
