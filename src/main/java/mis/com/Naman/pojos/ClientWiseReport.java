package mis.com.Naman.pojos;

import java.util.Date;

public class ClientWiseReport {

    private Date execution_date;
    private String clientname;
    private String username;
    private int totalmsisdn;
    private int validmsisdn;
    private int attemptedcalls;
    private int connectedcalls;
    private int digitpressed;
    private double listenrate;
    private int totalbillsec;
    private int creditused;
    private String panelname;

    // Constructor with parameters that match the query result
    public ClientWiseReport(Date execution_date, String clientname, String username, int totalmsisdn,
                            int validmsisdn, int attemptedcalls, int connectedcalls, int digitpressed,
                            double listenrate, int totalbillsec, int creditused, String panelname) {
        this.execution_date = execution_date;
        this.clientname = clientname;
        this.username = username;
        this.totalmsisdn = totalmsisdn;
        this.validmsisdn = validmsisdn;
        this.attemptedcalls = attemptedcalls;
        this.connectedcalls = connectedcalls;
        this.digitpressed = digitpressed;
        this.listenrate = listenrate;
        this.totalbillsec = totalbillsec;
        this.creditused = creditused;
        this.panelname = panelname;
    }

    // Getters and setters
  
    
    
    public String getClientname() {
        return clientname;
    }

    public Date getExecution_date() {
		return execution_date;
	}

	public void setExecution_date(Date execution_date) {
		this.execution_date = execution_date;
	}

	public void setClientname(String clientname) {
        this.clientname = clientname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getTotalmsisdn() {
        return totalmsisdn;
    }

    public void setTotalmsisdn(int totalmsisdn) {
        this.totalmsisdn = totalmsisdn;
    }

    public int getValidmsisdn() {
        return validmsisdn;
    }

    public void setValidmsisdn(int validmsisdn) {
        this.validmsisdn = validmsisdn;
    }

    public int getAttemptedcalls() {
        return attemptedcalls;
    }

    public void setAttemptedcalls(int attemptedcalls) {
        this.attemptedcalls = attemptedcalls;
    }

    public int getConnectedcalls() {
        return connectedcalls;
    }

    public void setConnectedcalls(int connectedcalls) {
        this.connectedcalls = connectedcalls;
    }

    public int getDigitpressed() {
        return digitpressed;
    }

    public void setDigitpressed(int digitpressed) {
        this.digitpressed = digitpressed;
    }

    public double getListenrate() {
        return listenrate;
    }

    public void setListenrate(double listenrate) {
        this.listenrate = listenrate;
    }

    public int getTotalbillsec() {
        return totalbillsec;
    }

    public void setTotalbillsec(int totalbillsec) {
        this.totalbillsec = totalbillsec;
    }

    public int getCreditused() {
        return creditused;
    }

    public void setCreditused(int creditused) {
        this.creditused = creditused;
    }

    public String getPanelname() {
        return panelname;
    }

    public void setPanelname(String panelname) {
        this.panelname = panelname;
    }

	@Override
	public String toString() {
		return "ClientWiseReport [execution_date=" + execution_date + ", clientname=" + clientname + ", username="
				+ username + ", totalmsisdn=" + totalmsisdn + ", validmsisdn=" + validmsisdn + ", attemptedcalls="
				+ attemptedcalls + ", connectedcalls=" + connectedcalls + ", digitpressed=" + digitpressed
				+ ", listenrate=" + listenrate + ", totalbillsec=" + totalbillsec + ", creditused=" + creditused
				+ ", panelname=" + panelname + "]";
	}
    
    
}
