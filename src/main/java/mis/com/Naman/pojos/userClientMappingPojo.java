package mis.com.Naman.pojos;

public class userClientMappingPojo {

	private String username;
	
	private String client_name;
	
	private String panel_name;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getClient_name() {
		return client_name;
	}

	public void setClient_name(String client_name) {
		this.client_name = client_name;
	}

	public String getPanel_name() {
		return panel_name;
	}

	public void setPanel_name(String panel_name) {
		this.panel_name = panel_name;
	}

	@Override
	public String toString() {
		return "userClientMappingPojo [username=" + username + ", client_name=" + client_name + ", panel_name="
				+ panel_name + "]";
	}
	
}
