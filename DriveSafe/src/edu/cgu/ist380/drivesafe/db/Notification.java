package edu.cgu.ist380.drivesafe.db;

public class Notification {
	
	public static final String TEXT = "Text Message notification";
	private int notificationId;
	private String notificationType;
	private String notificationTime;
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
	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		return notificationId == ((Notification)o).getNotificationId();
	}
	
}
