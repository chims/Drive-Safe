package edu.cgu.ist380.drivesafe.db;

public class Notification {
	
	public static final String TEXT = "Text Message notification";
	private int notificationId;
	private String notificationType;
	private String notificationTime;
	private int sessionId;
	public int getNotificationId() {
		return notificationId;
	}
	public void setNotificationId(int notificationId) {
		this.notificationId = notificationId;
	}
	public String getNotificationType() {
		return notificationType;
	}
	public void setNotificationType(String notificationType) {
		this.notificationType = notificationType;
	}
	public String getNotificationTime() {
		return notificationTime;
	}
	public void setNotificationTime(String notificationTime) {
		this.notificationTime = notificationTime;
	}
	public int getSessionId() {
		return sessionId;
	}
	public void setSessionId(int sessionId) {
		this.sessionId = sessionId;
	}	
}
