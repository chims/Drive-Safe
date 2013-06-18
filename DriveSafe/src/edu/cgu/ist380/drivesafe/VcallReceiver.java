package edu.cgu.ist380.drivesafe;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.telephony.TelephonyManager;
import android.util.Log;

public class VcallReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent) 
    {
    	Log.i("Vcall", "just received a voice call");
        //---get the voice call information passed in---
        Bundle bundle = intent.getExtras();        
        String callerPhone = null;
        int vcallCounter = 0; 
        if (bundle != null)
        {
             //---Code to retrieve the new voice call received---
                callerPhone=  bundle.getString(TelephonyManager.EXTRA_INCOMING_NUMBER);                     
               
                vcallCounter++;
                      
            }
            //check if the activity is running
            // if yes, then use the say function directly
            if(MainActivity.mThis != null)
            {
            	
            	MainActivity.mThis.say("From Vcall Receiver Class. You have received a voice call from. "+callerPhone +"." + "You have." +vcallCounter +  "new calls");
            }
            // if not , then start a new activity and pass the values
            else
            {
            Intent i = new Intent(context, MainActivity.class);
            i.putExtra("callerPhone",  callerPhone);
            i.putExtra("vcallCounter",  vcallCounter);
            context.startActivity(i);
        }
//           
            
            
         }                         
    }

