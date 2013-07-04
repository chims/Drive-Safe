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
	        if (bundle != null)
	        {
	        	String phoneNumber = null;
	        	String message = null;
	        	String phoneNumberRevised = null;
	        	
	            //---retrieve the SMS message received---
	            Object[] pdus = (Object[]) bundle.get("pdus");
	            msgs = new SmsMessage[pdus.length];            
	            for (int i=0; i<msgs.length; i++){
	                msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);                
	                phoneNumber=  msgs[i].getOriginatingAddress();                     	               
	                message= msgs[i].getMessageBody().toString();
	                
	                phoneNumberRevised = phoneNumber.substring(2, 3) + " " + phoneNumber.substring(3, 4) + " " + phoneNumber.substring(4, 5) + " ." + phoneNumber.substring(5, 6) + " " + phoneNumber.substring(6, 7) + " " + phoneNumber.substring(7, 8) + " ." + phoneNumber.substring(8, 9) + " " + phoneNumber.substring(9, 10) + " " + phoneNumber.substring(10, 11) + " " + phoneNumber.substring(11, 12);
	                      
	            }
	            //check if the activity is running
	            // if yes, then use the say function directly
	            if(MainActivity.mThis != null)
	            {
	            	
	            	MainActivity.mThis.say("S.M.S. Activity.  You have received a text message from. " + phoneNumberRevised + " ." + message + " ." + "powered by Just drive");
	            }
	            // if not , then start a new activity and pass the values
	            else
	            {
	            Intent i = new Intent(context, MainActivity.class);
	            i.putExtra("phoneNumberRevised",  phoneNumberRevised);
	            i.putExtra("message",  message);
	            context.startActivity(i);
	        }
//	           
	            
	            
	         }                         
	    }
	}
 
