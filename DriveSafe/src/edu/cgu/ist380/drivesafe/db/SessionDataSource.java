package edu.cgu.ist380.drivesafe.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class SessionDataSource {
	// Database fields
	  private SQLiteDatabase database;
	  private MySQLiteHelper dbHelper;
	  private String[] allColumns = { 
			  MySQLiteHelper.SESSION_COLUMN_SESSION_ID,
			  MySQLiteHelper.SESSION_COLUMN_SESSION_START,
			  MySQLiteHelper.SESSION_COLUMN_SESSION_END,
			  MySQLiteHelper.SESSION_COLUMN_DRIVE_MODE
			  
	      };
	  
	  private String[] allColumns_notifi = { 
			  MySQLiteHelper.NOTIFICATION_COLUMN_NOTIFICATION_ID,
			  MySQLiteHelper.NOTIFICATION_COLUMN_NOTIFICATION_TYPE,
			  MySQLiteHelper.NOTIFICATION_COLUMN_NOTIFICATION_TIME,
			  MySQLiteHelper.NOTIFICATION_COLUMN_SESSION_ID_FK
	  };
public SessionDataSource(Context context) {
	  try{
  dbHelper = new MySQLiteHelper(context);
	  }
	  catch (Exception e)
	  {
		    Log.e(SessionDataSource.class.getName(), "Error opening the db "+ e.getMessage());
	  }
}

public void open() throws SQLException {
  database = dbHelper.getWritableDatabase();
}

public void close() {
  dbHelper.close();
}

/*
 *  This method takes an object of type Session and insert it to the database
 *  Note that the return type is also Session, meaning that the inserted 
 *  object will be returned from the database
 */
public Session createSess(Session session) {
  ContentValues values = new ContentValues();
  
  values.put(   MySQLiteHelper.SESSION_COLUMN_SESSION_START,session.getSessionStart());
  values.put( MySQLiteHelper.SESSION_COLUMN_SESSION_END,session.getSessionEnd());
  values.put( MySQLiteHelper.SESSION_COLUMN_DRIVE_MODE,session.isDriveMode());
  
  /* call the insert method on the database
   * 
   * Since the method only returns a number of type "long", I need to downcast to int to be able to update 
   * the id in my session object.  session.setSessionId((int)insertedSessionId);
   */
  long insertedSessionId = database.insert(MySQLiteHelper.TABLE_SESSION,null, values);
  session.setSessionId((int)insertedSessionId);
  Log.i(SessionDataSource.class.getName(), "Record : Session with id:" + session.getSessionId() +" was inserted to the db.");
  return session;
}

public void deleteSession(Session session) {
  long id = session.getSessionId();
  database.delete(MySQLiteHelper.TABLE_SESSION, MySQLiteHelper.SESSION_COLUMN_SESSION_ID
      + " = " + id, null);
  Log.i(SessionDataSource.class.getName(), "Record : Session with id:" + session.getSessionId() +" was deleted from the db.");
  
  
  
}

public Notification addNotification(Session session, Notification notifi)
{ 
	 
	  ContentValues values = new ContentValues();
	  
	  values.put(   MySQLiteHelper.NOTIFICATION_COLUMN_NOTIFICATION_TYPE,notifi.getNotificationType());
	  values.put( MySQLiteHelper.NOTIFICATION_COLUMN_NOTIFICATION_TIME,notifi.getNotificationTime());
	  values.put(MySQLiteHelper.NOTIFICATION_COLUMN_SESSION_ID_FK, session.getSessionEnd());
	  
	  /* call the insert method on the database
	   * 
	   * Since the method only retuns a number of type "long", I need to downcast to int to be able to update 
	   * the id in my notification object.  notification.setNotificationId((int)insertedNotificationId);
	   */
	  long insertedNotificationId = database.insert(MySQLiteHelper.TABLE_NOTIFICATION,null, values);
	  notifi.setNotificationId((int)insertedNotificationId);
	  Log.i(SessionDataSource.class.getName(), "Record : Notification with id:" + notifi.getNotificationId() +" was inserted to the db.");
	  return notifi;
	  
	 
}

public List<Session> getAllSession() {
  List<Session> sessionList = new ArrayList<Session>();

  Cursor cursor = database.query(MySQLiteHelper.TABLE_SESSION,
      allColumns, null, null, null, null, null);

  cursor.moveToFirst();
  while (!cursor.isAfterLast()) {
    Session session = cursorToSession(cursor);
    sessionList.add(session);
    cursor.moveToNext();
  }
  // Make sure to close the cursor
  cursor.close();
  return sessionList;
}

public List<Notification> getNotification(Session session)
{
	  List<Notification> notifiList = new ArrayList<Notification>();
	  String whereClause = MySQLiteHelper.NOTIFICATION_COLUMN_SESSION_ID_FK+" = "+session.getSessionId();
	  Cursor cursor = database.query(MySQLiteHelper.TABLE_SESSION,
			  allColumns_notifi, whereClause, null, null, null, null);

	  cursor.moveToFirst();
	  while (!cursor.isAfterLast()) {
	    Notification notifi = cursorToNotification(cursor);
	    session.addNotification(notifi);
	    notifiList.add(notifi);
	    cursor.moveToNext();
	  }
	  // Make sure to close the cursor
	  cursor.close();
	  return notifiList;
	
}

public void deleteNotification(Notification notification,Session session) {
	  long id = notification.getNotificationId();
	  session.getNotifications().remove(notification);
	  database.delete(MySQLiteHelper.TABLE_NOTIFICATION, MySQLiteHelper.NOTIFICATION_COLUMN_NOTIFICATION_ID
	      + " = " + id, null);
	  Log.i(SessionDataSource.class.getName(), "Record : Notification with id:" + notification.getNotificationId() +" was deleted from the db.");
		  
	}

private Session cursorToSession(Cursor cursor) {
  Session session = new Session();
  // get the values from the cursor 
  long sessionId =  cursor.getLong(cursor.getColumnIndexOrThrow(MySQLiteHelper.SESSION_COLUMN_SESSION_ID));
  String sessionStart=cursor.getString(cursor.getColumnIndexOrThrow(MySQLiteHelper.SESSION_COLUMN_SESSION_START));
  String sessionEnd = cursor.getString(cursor.getColumnIndexOrThrow(MySQLiteHelper.SESSION_COLUMN_SESSION_END));
  String driveMode = cursor.getString(cursor.getColumnIndexOrThrow(MySQLiteHelper.SESSION_COLUMN_DRIVE_MODE));
  session.setSessionId((int) sessionId);
  session.setSessionStart(sessionStart);
  session.setSessionEnd(sessionEnd);
  session.setDriveMode(Boolean.valueOf(driveMode));
  return session;
}

private Notification cursorToNotification(Cursor cursor) {
	  Notification notification = new Notification();
	  // get the values from the cursor 
	  long notificationId =  cursor.getLong(cursor.getColumnIndexOrThrow(MySQLiteHelper.NOTIFICATION_COLUMN_NOTIFICATION_ID));
	  String notificationType=cursor.getString(cursor.getColumnIndexOrThrow(MySQLiteHelper.NOTIFICATION_COLUMN_NOTIFICATION_TYPE));
	  String notificationTime = cursor.getString(cursor.getColumnIndexOrThrow(MySQLiteHelper.NOTIFICATION_COLUMN_NOTIFICATION_TIME));
	 notification.setNotificationId((int) notificationId);
	  notification.setNotificationType(notificationType);
	  notification.setNotificationTime(notificationTime);
	  return notification;
	}
}

