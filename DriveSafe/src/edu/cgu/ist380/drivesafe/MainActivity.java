package edu.cgu.ist380.drivesafe;

import android.app.Activity;
import android.content.IntentFilter;
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
	 public static MainActivity mThis =null;
	 static SmsReceiver smsReceiver=  new SmsReceiver();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mThis = this;
		Bundle extra = this.getIntent().getExtras();
		//check if smsReceiver passed valueextra.getString("phoneNumber") != null )
		if(extra != null)
		{
			say(" From Main Activity Class. You have received a text message from "+ extra.getString("phoneNumber"));
		}
		
		
		tButton = (ToggleButton) findViewById(R.id.driveSafeToggleButton1);
		tButton.setChecked(on);
		tButton.setOnCheckedChangeListener(new OnCheckedChangeListener (){

		 // register an checked event for the toggle button
			public void onCheckedChanged(CompoundButton arg0, boolean checked) {
				on = checked;
				
				if(on)
				startDrivingMode();
				else
				stopDrivingMode();
				
			}

			// stop driving mode 
			private void stopDrivingMode() {
			say("JUST drive is now disabled. Good bye!");
			 try{
				 // unregister the sms receiver
				unregisterReceiver(smsReceiver);
			 }
			 catch(Exception e)
			 {
				 Log.e("SMS","Error " +e.getMessage());
			 }
			}

			private void startDrivingMode() {
				//do something to start driving mode  
				say("JUST drive is now enabled. Drive safely!");
				// register the sms reciver 
				 registerReceiver(smsReceiver, new IntentFilter(
				            "android.provider.Telephony.SMS_RECEIVED"));
				 
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
			talker.speak(message, TextToSpeech.QUEUE_FLUSH, null);

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
		 try{
				unregisterReceiver(smsReceiver);
			 }
			 catch(Exception e)
			 {
				 Log.e("SMS","Error " +e.getMessage());
			 }
	}
	
	 
	 	
}
