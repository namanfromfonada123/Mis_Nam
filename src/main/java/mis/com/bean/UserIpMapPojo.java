package mis.com.bean;

public class UserIpMapPojo {

	private String ipAddress;
	private String userType;
	private String accountUser;
	private String userName;
	private String campaignName;

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
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

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getAccountUser() {
		return accountUser;
	}

	public void setAccountUser(String accountUser) {
		this.accountUser = accountUser;
	}

	@Override
	public String toString() {
		return "UserIpMapPojo [ipAddress=" + ipAddress + ", userType=" + userType + ", accountUser=" + accountUser
				+ ", userName=" + userName + ", campaignName=" + campaignName + "]";
	}

}
