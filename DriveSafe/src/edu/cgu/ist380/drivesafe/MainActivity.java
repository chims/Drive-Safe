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
import android.content.IntentFilter;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;
import android.view.Menu;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
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
			if(extra.getString("phoneNumber") != null)
			say(" From Main Activity Class. You have received a text message from "+ extra.getString("phoneNumber"));
			if(extra.getString("callerPhone") !=null)
		    say(" From Main Activity Class. You have received a phone call  from "+ extra.getString("callerPhone"));
			
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
			// notify user: stopping drive mode	
			say("JUST drive is now disabled. Good bye!");
			
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
			
			// stop listening for e-mail receiver 
			
			// stop listening for v-call receiver 

			// stop listening for v-mail receiver 
			
			// stop listening for GPS & speed receiver 
			
			
			// start driving mode
			private void startDrivingMode() {
				// notify user: starting driving mode  
				say("JUST drive is now enabled. Drive safely!");
				
				    getApplicationContext();
					mlocManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
                    mlocListener = new GPSLocationListener( );
            	    mlocManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 20000, 0, mlocListener);
				// register the sms reciever 
				 registerReceiver(smsReceiver, new IntentFilter(
				            "android.provider.Telephony.SMS_RECEIVED"));
				 
				 registerReceiver(callReceiver, new IntentFilter("android.intent.action.PHONE_STATE") );

				/*	// register the email reciever 
				 registerReceiver(smsReceiver, new IntentFilter(
				            "android.provider.Telephony.SMS_RECEIVED")); */
				 
				/*	// register the v-call reciever 
				 registerReceiver(smsReceiver, new IntentFilter(
				            "android.provider.Telephony.SMS_RECEIVED")); */
				 
					// register the GPS & speed reciever 
//				 registerReceiver(smsReceiver, new IntentFilter(
//				            "android.provider.Telephony.SMS_RECEIVED"));
				  
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
				speed =fields.getJSONObject(0).getJSONObject("attributes").getString("speed");
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
		 double sp = Double.parseDouble(speed);
		 currentSpeed = 40;
		 if (sp <= currentSpeed)
			 say("You are speeding up");
		
	}
}
