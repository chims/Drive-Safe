package edu.cgu.ist380.drivesafe;

 
	import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
	 
	public class SmsReceiver extends BroadcastReceiver
	{
	    @Override
	    public void onReceive(Context context, Intent intent) 
	    {
	    	Log.i("SMS", "just received an sms message");
	        //---get the SMS message passed in---
	        Bundle bundle = intent.getExtras();        
	        SmsMessage[] msgs = null;
	        String str = "";            
	        if (bundle != null)
	        {
	        	String phonenumber = null;
	        	String message = null;
	            //---retrieve the SMS message received---
	            Object[] pdus = (Object[]) bundle.get("pdus");
	            msgs = new SmsMessage[pdus.length];            
	            for (int i=0; i<msgs.length; i++){
	                msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);                
	                phonenumber=  msgs[i].getOriginatingAddress();                     
	               
	                message= msgs[i].getMessageBody().toString();
	                      
	            }
	            //check if the activity is running
	            // if yes, then use the say function directly
	            if(MainActivity.mThis != null)
	            {
	            	
	            	MainActivity.mThis.say("You have received a text message from "+phonenumber);
	            }
	            // if not , then start a new activity and pass the values
	            else
	            {
	            Intent i = new Intent(context, MainActivity.class);
	            i.putExtra("phoneNumber",  phonenumber);
	            i.putExtra("message",  message);
	            context.startActivity(i);
	        }
//	           
	            
	            
	         }                         
	    }
	}
 
