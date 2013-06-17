package edu.cgu.ist380.drivesafe.gps;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import edu.cgu.ist380.drivesafe.MainActivity;

public class HighLowSpeed
	{
	    @Override
	    public void onReceive(Context context, Intent intent) 
	    {
	    	Log.i("SPEED", "just received speed details");
	        //---get the actualSpeed and speedLimit passed in---
	        Bundle bundle = intent.getExtras();        
	        //SmsMessage[] msgs = null;
	        if (bundle != null)
	        {
	        	final int actualSpeed;
	        	final int speedLimit;
	            //---retrieve the speed details received---
	                      
	            }
	            //check if the activity is running
	            // if yes, then use the say function directly
	            if(MainActivity.mThis != null)
	            {
	            	// if (actualSpeed > (speedLimit +5))
	            	MainActivity.mThis.say("From HighLowSpeed Activity. Please slow down. "+speedLimit +"."+"is the speed limit");

	            	// if actualSpeed < (speedLimit +5)
	            	MainActivity.mThis.say("From HighLowSpeed Activity. Please speed up. "+ speedLimit +"."+"is the speed limit");

	            }
	            // if not , then start a new activity and pass the values
	            else
	            {
	            Intent i = new Intent(context, MainActivity.class);
	            i.putExtra("actualSpeed",  actualspeed);
	            i.putExtra("speedLimit",  speedlimit);
	            context.startActivity(i);
	        }
//	           
	            
	            
	         }                         
	    }
 
