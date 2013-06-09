package edu.cgu.ist380.drivesafe.db;

public class Session {
	private int sessionId;
	private String sessionStart;
	private String sessionEnd;
	private boolean isDriveMode;
	
	public int getSessionId() {

		return sessionId;
	}
	public void setSessionId(int sessionId) {
		this.sessionId = sessionId;
	}
	public String getSessionStart() {
		return sessionStart;
	}
	public void setSessionStart(String sessionStart) {
		this.sessionStart = sessionStart;
	}
	public String getSessionEnd() {
		return sessionEnd;
	}
	public void setSessionEnd(String sessionEnd) {
		this.sessionEnd = sessionEnd;
	}
	public boolean isDriveMode() {
		return isDriveMode;
	}
	public void setDriveMode(boolean isDriveMode) {
		this.isDriveMode = isDriveMode;
	}	
}
