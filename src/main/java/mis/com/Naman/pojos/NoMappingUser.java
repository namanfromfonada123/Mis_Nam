package mis.com.Naman.pojos;

public class NoMappingUser {

	private String clientName;
	private String userName;
	private String panelName;
	
	
	public NoMappingUser() {
		super();
	}

	

	public NoMappingUser(String clientName , String userName, String panelName) {
		super();
		this.clientName = clientName;
		this.userName = userName;
		this.panelName = panelName;
	}



	public String getclientName() {
		return clientName;
	}


	public void setclientName(String clientName) {
		this.clientName = clientName;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getPanelName() {
		return panelName;
	}


	public void setPanelName(String panelName) {
		this.panelName = panelName;
	}


	
	
}
