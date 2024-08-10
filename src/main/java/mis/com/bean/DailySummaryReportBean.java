package mis.com.bean;

public class DailySummaryReportBean {
	private String dailyAccountName;
	private String dailyUserName;
	private String dailyTotalMsisdn;
	private String dailyValidMsisdn;
	private String dailyAttemptedCalls;
	private String dailydailyConnectedCalls;
	private String dailyTotalBillSec;
	private String dailyCreditUsed;

	private String monthlyAccountName;
	private String monthlyUserName;
	private String monthyTotalMsisdn;
	private String monthyValidMsisdn;
	private String monthyAttemptedCalls;
	private String monthydailyConnectedCalls;
	private String monthyTotalBillSec;
	private String monthyCreditUsed;

	public String getDailyAccountName() {
		return dailyAccountName;
	}

	public void setDailyAccountName(String dailyAccountName) {
		this.dailyAccountName = dailyAccountName;
	}

	public String getDailyUserName() {
		return dailyUserName;
	}

	public void setDailyUserName(String dailyUserName) {
		this.dailyUserName = dailyUserName;
	}

	public String getDailyTotalMsisdn() {
		return dailyTotalMsisdn;
	}

	public void setDailyTotalMsisdn(String dailyTotalMsisdn) {
		this.dailyTotalMsisdn = dailyTotalMsisdn;
	}

	public String getDailyValidMsisdn() {
		return dailyValidMsisdn;
	}

	public void setDailyValidMsisdn(String dailyValidMsisdn) {
		this.dailyValidMsisdn = dailyValidMsisdn;
	}

	public String getDailyAttemptedCalls() {
		return dailyAttemptedCalls;
	}

	public void setDailyAttemptedCalls(String dailyAttemptedCalls) {
		this.dailyAttemptedCalls = dailyAttemptedCalls;
	}

	public String getDailydailyConnectedCalls() {
		return dailydailyConnectedCalls;
	}

	public void setDailydailyConnectedCalls(String dailydailyConnectedCalls) {
		this.dailydailyConnectedCalls = dailydailyConnectedCalls;
	}

	public String getDailyTotalBillSec() {
		return dailyTotalBillSec;
	}

	public void setDailyTotalBillSec(String dailyTotalBillSec) {
		this.dailyTotalBillSec = dailyTotalBillSec;
	}

	public String getDailyCreditUsed() {
		return dailyCreditUsed;
	}

	public void setDailyCreditUsed(String dailyCreditUsed) {
		this.dailyCreditUsed = dailyCreditUsed;
	}

	public String getMonthlyAccountName() {
		return monthlyAccountName;
	}

	public void setMonthlyAccountName(String monthlyAccountName) {
		this.monthlyAccountName = monthlyAccountName;
	}

	public String getMonthlyUserName() {
		return monthlyUserName;
	}

	public void setMonthlyUserName(String monthlyUserName) {
		this.monthlyUserName = monthlyUserName;
	}

	public String getMonthyTotalMsisdn() {
		return monthyTotalMsisdn;
	}

	public void setMonthyTotalMsisdn(String monthyTotalMsisdn) {
		this.monthyTotalMsisdn = monthyTotalMsisdn;
	}

	public String getMonthyValidMsisdn() {
		return monthyValidMsisdn;
	}

	public void setMonthyValidMsisdn(String monthyValidMsisdn) {
		this.monthyValidMsisdn = monthyValidMsisdn;
	}

	public String getMonthyAttemptedCalls() {
		return monthyAttemptedCalls;
	}

	public void setMonthyAttemptedCalls(String monthyAttemptedCalls) {
		this.monthyAttemptedCalls = monthyAttemptedCalls;
	}

	public String getMonthydailyConnectedCalls() {
		return monthydailyConnectedCalls;
	}

	public void setMonthydailyConnectedCalls(String monthydailyConnectedCalls) {
		this.monthydailyConnectedCalls = monthydailyConnectedCalls;
	}

	public String getMonthyTotalBillSec() {
		return monthyTotalBillSec;
	}

	public void setMonthyTotalBillSec(String monthyTotalBillSec) {
		this.monthyTotalBillSec = monthyTotalBillSec;
	}

	public String getMonthyCreditUsed() {
		return monthyCreditUsed;
	}

	public void setMonthyCreditUsed(String monthyCreditUsed) {
		this.monthyCreditUsed = monthyCreditUsed;
	}

	@Override
	public String toString() {
		return "DailySummaryReportBean [dailyAccountName=" + dailyAccountName + ", dailyUserName=" + dailyUserName
				+ ", dailyTotalMsisdn=" + dailyTotalMsisdn + ", dailyValidMsisdn=" + dailyValidMsisdn
				+ ", dailyAttemptedCalls=" + dailyAttemptedCalls + ", dailydailyConnectedCalls="
				+ dailydailyConnectedCalls + ", dailyTotalBillSec=" + dailyTotalBillSec + ", dailyCreditUsed="
				+ dailyCreditUsed + ", monthlyAccountName=" + monthlyAccountName + ", monthlyUserName="
				+ monthlyUserName + ", monthyTotalMsisdn=" + monthyTotalMsisdn + ", monthyValidMsisdn="
				+ monthyValidMsisdn + ", monthyAttemptedCalls=" + monthyAttemptedCalls + ", monthydailyConnectedCalls="
				+ monthydailyConnectedCalls + ", monthyTotalBillSec=" + monthyTotalBillSec + ", monthyCreditUsed="
				+ monthyCreditUsed + "]";
	}

}
