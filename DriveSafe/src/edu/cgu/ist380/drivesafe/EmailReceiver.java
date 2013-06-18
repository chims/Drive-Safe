package edu.cgu.ist380.drivesafe;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.util.Log;

public class EmailReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent) 
    {
    	Log.i("Email", "just received an email message");
        //---get the Email message passed in---
        Bundle bundle = intent.getExtras();        
        Email[] msgs = null;
        int emailCounter = 0; 
        if (bundle != null)
        {
        	String senderEmail = null;
            //---retrieve the Email received---
            Object[] pdus = (Object[]) bundle.get("pdus");
            msgs = new Email[pdus.length];            
            for (int i=0; i<msgs.length; i++){
             //   msgs[i] = Email.createFromPdu((byte[])pdus[i]);                
              //  senderEmail=  msgs[i].getEmailFrom();                     
               
                emailCounter++;
                      
            }
            //check if the activity is running
            // if yes, then use the say function directly
            if(MainActivity.mThis != null)
            {
            	
            	MainActivity.mThis.say("From Email Receiver Class. You have received an Email from. "+senderEmail +"." + "You have." +emailCounter +  "new emails");
            }
            // if not , then start a new activity and pass the values
            else
            {
            Intent i = new Intent(context, MainActivity.class);
            i.putExtra("senderEmail",  senderEmail);
            i.putExtra("emailCounter",  emailCounter);
            context.startActivity(i);
        }
//           
            
            
         }                         
    }
}

