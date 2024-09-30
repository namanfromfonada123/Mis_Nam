package mis.com.Naman.Modal;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "username_client_mapping",  uniqueConstraints = { @UniqueConstraint(columnNames = {"username","client_name","panel_name"}) })
public class username_client_mapping {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String username;
	
	private String client_name;
	
	private String panel_name;
	
	  
    @Column(length = 30)
	private String createdOn;
	
	@Column(length = 30)
	private String updatedOn;
	  
	  

	  @PrePersist
	  protected void onCreate() {
	      createdOn = LocalDateTime.now().toString();
	      
	  }
	  
	  @PreUpdate
	  protected void onUpdate() {
	      updatedOn = LocalDateTime.now().toString();
	  }
  
	

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
	
	
}
