package edu.cgu.ist380.drivesafe.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class NotificationDataSource {
	// Database fields
	  private SQLiteDatabase database;
	  private MySQLiteHelper dbHelper;
	  private String[] allColumns = { 
			  MySQLiteHelper.NOTIFICATION_COLUMN_NOTIFICATION_ID,
			  MySQLiteHelper.NOTIFICATION_COLUMN_NOTIFICATION_TYPE,
			  MySQLiteHelper.NOTIFICATION_COLUMN_NOTIFICATION_TIME,
			  MySQLiteHelper.NOTIFICATION_COLUMN_SESSION_ID_FK
	      };
public NotificationDataSource(Context context) {
	  try{
  dbHelper = new MySQLiteHelper(context);
	  }
	  catch (Exception e)
	  {
		    Log.e(NotificationDataSource.class.getNotificationTime(), "Error opening the db "+ e.getMessage());
	  }
}

public void open() throws SQLException {
  database = dbHelper.getWritableDatabase();
}

public void close() {
  dbHelper.close();
}

/*
 *  This method takes an object of type Notification and insert it to the database
 *  Note that the return type is also Notification, meaning that the inserted 
 *  object will be returned form the database
 */
public Notification createNotif(Notification notification) {
  ContentValues values = new ContentValues();
  
  values.put(   MySQLiteHelper.NOTIFICATION_COLUMN_NOTIFICATION_TYPE,notification.getNotificationType());
	values.put( MySQLiteHelper.NOTIFICATION_COLUMN_NOTIFICATION_TIME,notification.getNotificationTime());
  values.put( MySQLiteHelper.NOTIFICATION_COLUMN_SESSION_ID_FK,notification.getSessionIdFk());
  
  /* call the insert method on the database
   * 
   * Since the method only retuns a number of type "long", I need to downcast to int to be able to update 
   * the id in my notification object.  notification.setNotificationId((int)insertedNotificationId);
   */
  long insertedNotificationId = database.insert(MySQLiteHelper.TABLE_NOTIFICATION,null, values);
  notification.setNotificationId((int)insertedNotificationId);
  Log.i(NotificationDataSource.class.getNotificationTime(), "Record : Notification with id:" + notification.getNotificationId() +" was inserted to the db.");
  return notification;
}

public void deleteNotification(Notification notification) {
  long id = notification.getNotificationId();
  database.delete(MySQLiteHelper.TABLE_NOTIFICATION, MySQLiteHelper.NOTIFICATION_COLUMN_NOTIFICATION_ID
      + " = " + notificationId, null);
  Log.i(NotificationDataSource.class.getNotificationTime(), "Record : Notification with id:" + notification.getNotificationId() +" was deleted from the db.");
	  
}

public List<Notification> getAllNotification() {
  List<Notification> NotificationList = new ArrayList<Notification>();

  Cursor cursor = database.query(MySQLiteHelper.TABLE_NOTIFICATION,
      allColumns, null, null, null, null, null);

  cursor.moveToFirst();
  while (!cursor.isAfterLast()) {
    Notification notification = cursorToNotification(cursor);
    notificationList.add(notification);
    cursor.moveToNext();
  }
  // Make sure to close the cursor
  cursor.close();
  return notificationList;
}

private Notification cursorToNotification(Cursor cursor) {
  Notification notification = new Notification();
  // get the values from the cursor 
  long notificationId =  cursor.getLong(cursor.getColumnIndexOrThrow(MySQLiteHelper.NOTIFICATION_COLUMN_NOTIFICATION_ID));
  String notificationType=cursor.getString(cursor.getColumnIndexOrThrow(MySQLiteHelper.NOTIFICATION_COLUMN_NOTIFICATION_TYPE));
  String notificationTime = cursor.getString(cursor.getColumnIndexOrThrow(MySQLiteHelper.NOTIFICATION_COLUMN_NOTIFICATION_TIME));
  String sessionIdFk = cursor.getString(cursor.getColumnIndexOrThrow(MySQLiteHelper.NOTIFICATION_COLUMN_SESSION_ID_FK));
  notification.setNotificationId((int) notificationId);
  notification.setNotificationType(notificationType);
  notification.setNotificationTime(notificationTime);
  notification.setSessionIdFk(sessionIdFk);
  return notification;
}
}

