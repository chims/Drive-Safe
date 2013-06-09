package edu.cgu.ist380.drivesafe.db;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

	
	 /* database variables */
		private static final String DATABASE_NAME = "dsafe.db";
		private static final int DATABASE_VERSION = 1;
	
		
		/* Drive Session Table */
		 
		public static final String TABLE_SESSION = "session";
		public static final String SESSION_COLUMN_SESSION_ID = "session_id";
		public static final String SESSION_COLUMN_SESSION_START = "session_start";
		public static final String SESSION_COLUMN_SESSION_END = "session_end";
		public static final String SESSION_COLUMN_DRIVE_MODE = "drive_mode";
	
		// Database <<TABLE SESSION>> creation sql statement
		private static  String DATABASE_CREATE = "create table " + TABLE_SESSION
				+ "(" 
				+ SESSION_COLUMN_SESSION_ID + " integer primary key autoincrement, "
				+ SESSION_COLUMN_SESSION_START + " text not null," 
				+ SESSION_COLUMN_SESSION_END + " text not  null," 
				+ SESSION_COLUMN_DRIVE_MODE + " text not null"   // no comma after last column
				+ "); ";		
		
		
		/* Notification Table */
		 
		public static final String TABLE_NOTIFICATION = "notification";
		public static final String NOTIFICATION_COLUMN_NOTIFICATION_ID = "notification_id";
		public static final String NOTIFICATION_COLUMN_NOTIFICATION_TYPE = "notification_type";
		public static final String NOTIFICATION_COLUMN_NOTIFICATION_TIME = "notification_time";
		public static final String NOTIFICATION_COLUMN_SESSION_ID_FK = "session_id_fk"; 
		
		
		// Database <<TABLE NOTIFICATION>> creation sql statement
		private static  String DATABASE_CREATE2 =" create table " + TABLE_NOTIFICATION
				+ "(" 
				+ NOTIFICATION_COLUMN_NOTIFICATION_ID + " integer primary key autoincrement, "
				+ NOTIFICATION_COLUMN_NOTIFICATION_TYPE + " text not null," 
				+ NOTIFICATION_COLUMN_NOTIFICATION_TIME + " text not  null," 
				+ NOTIFICATION_COLUMN_SESSION_ID_FK + " text not null"   
				+ ")";

		

	
		public MySQLiteHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}	
		
	@Override
	public void onCreate(SQLiteDatabase arg0) {
		// TODO Auto-generated method stub

		arg0.execSQL(DATABASE_CREATE);
		arg0.execSQL(DATABASE_CREATE2);
	
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

		 arg0.execSQL("DROP TABLE IF EXISTS " + TABLE_SESSION);
		 arg0.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTIFICATION);
		    onCreate(arg0);
		Log.w(MySQLiteHelper.class.getName(),
				        "Upgrading database from version " + arg1 + " to "
				            + arg2 + ", which will destroy all old data");   	

		
	}

}
