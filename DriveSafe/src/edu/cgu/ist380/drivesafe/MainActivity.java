package edu.cgu.ist380.drivesafe;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;
import android.view.Menu;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends Activity implements OnInitListener{

	 static boolean on;
	 ToggleButton tButton;
	 TextToSpeech talker;
	 String test;
	 String message;
	 LocationManager mlocManager ;
     LocationListener mlocListener ;
	 private double currentSpeed;
	 public static MainActivity mThis =null;


	 static SmsReceiver smsReceiver=  new SmsReceiver();
	 static VcallReceiver callReceiver=  new VcallReceiver();

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mThis = this;
		Bundle extra = this.getIntent().getExtras();
		//check if smsReceiver passed valueextra.getString("phoneNumber") != null )
		if(extra != null)
		{
			if(extra.getString("phoneNumberRevised") != null)
			say("You have received a text message from. "+ extra.getString("phoneNumberRevised") + " ." + extra.getString("message") + " ." + "powered by Just drive");
			if(extra.getString("callerPhone") !=null)
		    say("You have received a phone call  from. "+ extra.getString("callerPhoneRevised") + " ." + "powered by Just drive");
		}
		
		
		tButton = (ToggleButton) findViewById(R.id.driveSafeToggleButton1);
		tButton.setChecked(on);
		tButton.setOnCheckedChangeListener(new OnCheckedChangeListener (){

		 // register an checked event for the toggle button
			public void onCheckedChanged(CompoundButton arg0, boolean checked) {
				on = checked;
				
				if(on)
				{
					startDrivingMode();					
				}
				else
				{
					stopDrivingMode();
				}
				
			}

			// stop driving mode 
			private void stopDrivingMode() {
				//Switch ringer to Normal mode
		    	AudioManager am = (AudioManager) getBaseContext().getSystemService(Context.AUDIO_SERVICE);
				am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
				
				//Remind user to Turn off GPS function for the phone & pop-up GPS Settings
				Toast.makeText(getApplicationContext(), "PLEASE turn OFF GPS to conserve BATTERY \nSETTINGS | Location Services \nSet Access to my Location OFF ", Toast.LENGTH_LONG).show();
			    /*Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mThis.startActivity(intent) */			
				
				// notify user: stopping drive mode	
				say("JUST drive is now disabled. . Please. . Turn OFF GPS to conserve Battery. . Good bye!");
			
			// stop listening for SMS receiver  
			try{
				 // unregister the sms receiver
				unregisterReceiver(smsReceiver);	
				unregisterReceiver(callReceiver);
			 }
			 catch(Exception e)
			 {
				 Log.e("SMS","Error " +e.getMessage());
			 }
			}					
			// start driving mode
			private void startDrivingMode() {
				//Switch ringer into Silent mode
				 AudioManager am = (AudioManager) getBaseContext().getSystemService(Context.AUDIO_SERVICE);
				am.setRingerMode(AudioManager.RINGER_MODE_SILENT);
				
				//Remind user to Turn on GPS function for the phone & pop-up GPS settings
				Toast.makeText(getApplicationContext(), "PLEASE turn ON GPS to start GPS Tracking   \nSETTINGS | Location Services \nSet Accessto my Location ON ", Toast.LENGTH_LONG).show();
			    /*Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mThis.startActivity(intent); */

				
				// notify user: starting driving mode  
				say("JUST drive is now enabled. . Please. .  Turn ON GPS NOW! . . Drive safely!");
				
				    getApplicationContext();
					mlocManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
                    mlocListener = new GPSLocationListener( );
            	    mlocManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 60000, 0, mlocListener);
				// register the SMS Receiver 
				 registerReceiver(smsReceiver, new IntentFilter(
				            "android.provider.Telephony.SMS_RECEIVED"));
				 
				 registerReceiver(callReceiver, new IntentFilter("android.intent.action.PHONE_STATE") );

			}
			
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	@Override
	public void onInit(int arg0) {
		// TODO Auto-generated method stub
		if (message != null)
			talker.speak(message, TextToSpeech.QUEUE_ADD, null);

	}

	public void say(String text2say) {
		message = text2say;
		talker = new TextToSpeech(this, this);

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mThis = null;
	
	
	}
	
	@Override 
	protected void onDestroy()
	{
		 super.onDestroy();
		 try{
				unregisterReceiver(smsReceiver);
				unregisterReceiver(callReceiver);
			 }
			 catch(Exception e)
			 {
				 Log.e("SMS","Error " +e.getMessage());
			 }
		
	}
	private void startWebServiceCall(double lat, double lng)
	{
		CallGPSServerTask task = new CallGPSServerTask(); // call service in a separate thread 
		Log.d("url", "http://134.173.236.80:6080/arcgis/rest/services/socal_roads_speed/MapServer/0/query?where=&text=&objectIds=&time=&geometry="+lng+"%2C"+lat+"&geometryType=esriGeometryPoint&inSR=4326&spatialRel=esriSpatialRelIntersects&relationParam=&outFields=speed&returnGeometry=false&maxAllowableOffset=&geometryPrecision=&outSR=&returnIdsOnly=false&returnCountOnly=false&orderByFields=&groupByFieldsForStatistics=&outStatistics=&returnZ=false&returnM=false&gdbVersion=&returnDistinctValues=false&f=json");
	    task.execute("http://134.173.236.80:6080/arcgis/rest/services/socal_roads_speed/MapServer/0/query?where=&text=&objectIds=&time=&geometry="+lng+"%2C"+lat+"&geometryType=esriGeometryPoint&inSR=4326&spatialRel=esriSpatialRelIntersects&relationParam=&outFields=speed&returnGeometry=false&maxAllowableOffset=&geometryPrecision=&outSR=&returnIdsOnly=false&returnCountOnly=false&orderByFields=&groupByFieldsForStatistics=&outStatistics=&returnZ=false&returnM=false&gdbVersion=&returnDistinctValues=false&f=json");
	 }
	
	  

	  class GPSLocationListener   implements LocationListener
	{
	          

				@Override
				public void onLocationChanged(Location loc) {
					// TODO Auto-generated method stub
					startWebServiceCall(loc.getLatitude(),loc.getLongitude());
					currentSpeed = loc.getSpeed();
					 
				}

				@Override
				public void onProviderDisabled(String arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onProviderEnabled(String arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
					// TODO Auto-generated method stub
					
				}
				
	    }

	  private class CallGPSServerTask extends AsyncTask<String, Void, String> {
		  String response = "";
		@Override
		protected String doInBackground(String... arg0) {
			 
		      for (String url : arg0) {
		        DefaultHttpClient client = new DefaultHttpClient();
		        HttpGet httpGet = new HttpGet(url);
		        try {
		          HttpResponse execute = client.execute(httpGet);
		          InputStream content = execute.getEntity().getContent();

		          BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
		          String s = "";
		          while ((s = buffer.readLine()) != null) {
		            response += s;
		          }

		        } catch (Exception e) {
		          e.printStackTrace();
		        }
		      }
		      return response;
			 }
		@Override
		protected void onPostExecute(String result) {
			 try {
				JSONObject jsonObject = new JSONObject(result);
				JSONArray fields = jsonObject.getJSONArray("features"); 
				Log.d("JSON", fields.toString());
				String speed = null;
				if(fields.length() > 0)
				speed =fields.getJSONObject(0).getJSONObject("attributes").getString("speed"); //.substring(1, 3); //---Get int(speed)
				else
					speed = "0";
				CompareSpeed(speed);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			super.onPostExecute(result);
		}
		 
	  }

	public void CompareSpeed(String speed) {
		 double speedLimit = (int)(Double.parseDouble(speed));
		 //---Convert speed from meters/seconds to miles/hour if driver's car is moving---
		 if (currentSpeed > 0) 
		currentSpeed = (int)(((currentSpeed*3600)/1000)/1.609034);
		 //---If a positive speed limit was retrieved from web service ---
		if (speedLimit > 0)
		{
	     //---and if driver is driving over speed limit - i.e. at least 5 miles above speed limit...
		 if (currentSpeed > speedLimit + 5)
			 say("Please. reduce your speed. . The speed limit is." + speedLimit + " ." + "miles per hour");
		 
		 //---but if driver is driving under speed limit - i.e. at least 5 miles below speed limit...
		 if (currentSpeed < speedLimit - 5)
		 {
			 //---and speed limit is higher than 25mph
			 if (speedLimit > 25)
			 {
				 //---and driver's current speed is above 20mph
				 if (currentSpeed > 20)	 
					 say("Please. increase your speed. . The speed limit is." + speedLimit + " ." + "miles per hour");

				 //---but if driver's speed limit = 0-20mph; driver possibly in traffic/@traffic-light/@stop-sign!
				 
			 }
			 //---and speed limit is 25mph; presumably the lowest possible speed limit
			 else
			 {
				 //---and driver's current speed is above 15mph
				 if (currentSpeed > 15)
					 say("Please. increase your speed. . The speed limit is." + speedLimit + " ." + "miles per hour");
	
				 //---but if driver's speed limit =< 20mph; driver possibly in traffic/@traffic-light/@stop-sign!				 
			 }
				 
		 }
		
		}
		//---If no speed limit is obtained from web service i.e. speed limit = 0mph 
		else
		{
			//---but driver is moving...
			if (currentSpeed > 20)
				say("Speed limit is NOT available at the moment. . Your current speed is." + (int)(currentSpeed) + " ." + "miles per hour");

			//---and driver is stationary... NOTE: Only included here for the purpose of testing - TO BE Remarked.
			if (currentSpeed < 3)
				say("Speed limit is NOT available at the moment. . No motor vehicle motion currently detected."); // + (int)(currentSpeed) + "mph");
		}
				
	}
}
