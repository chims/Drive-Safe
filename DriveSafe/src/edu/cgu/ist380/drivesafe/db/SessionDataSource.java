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
public SessionDataSource(Context context) {
	  try{
  dbHelper = new MySQLiteHelper(context);
	  }
	  catch (Exception e)
	  {
		    Log.e(SessionDataSource.class.getSessionStart(), "Error opening the db "+ e.getMessage());
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
  Log.i(SessionDataSource.class.getSessionStart(), "Record : Session with id:" + session.getSessionId() +" was inserted to the db.");
  return session;
}

public void deleteSession(Session session) {
  long id = session.getSessionId();
  database.delete(MySQLiteHelper.TABLE_SESSION, MySQLiteHelper.SESSION_COLUMN_SESSION_ID
      + " = " + sessionId, null);
  Log.i(SessionDataSource.class.getSessionStart(), "Record : Session with id:" + session.getSessionId() +" was deleted from the db.");
	  
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
}

