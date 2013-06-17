package edu.cgu.ist380.drivesafe.gps;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class GPSLocationActivity extends Service implements LocationListener
{
            //@Override  
            public void onReceive(Context context, Intent intent) 
            {                
            	  LocationManager mlocManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
                  LocationListener mlocListener = new MyLocationListener(getApplicationContext());
            
            	   mlocManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 0, 0, mlocListener);

            };	
        
                
    }

/* Class My Location Listener */

 class MyLocationListener implements LocationListener{
	 
	 public MyLocationListener (Context context)
	 {
		 
	 }
	 

@Override

public void onLocationChanged(Location loc)

{

loc.getLatitude();

loc.getLongitude();

loc.getSpeed();

String Text = "My current location and speed is: " +

"\nLatitud = " + loc.getLatitude() +

"\nLongitud = " + loc.getLongitude() +

"\nSpeed = " + ((((loc.getSpeed())/3600)/1000)/1.6093); //get speed in meters/sec and convert to miles/hour

 }


@Override

public void onProviderDisabled(String provider)

{

Toast.makeText( context,

"Gps Disabled",

Toast.LENGTH_SHORT ).show();

}


@Override

public void onProviderEnabled(String provider)

{

Toast.makeText( context,

"Gps Enabled",

Toast.LENGTH_SHORT).show();

}


@Override

public void onStatusChanged(String provider, int status, Bundle extras)

{




}
/* End of Class MyLocationListener */

}/* End of UseGps Activity */
