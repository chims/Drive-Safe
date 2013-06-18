package edu.cgu.ist380.drivesafe.gps;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.R;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SocalSpeedLimits extends Activity {
	
	ListView lv  ; 
	Context context; 

	/*@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main); */

		/** Called when the activity is first created. */
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_main);
	        
	        lv = (ListView)findViewById(R.id.featureslist);
	        context = this.getApplicationContext();
	       
	       NetworkTask task = new NetworkTask(); // call service in a separate thread 
	       task.execute("http://134.173.236.80:6080/arcgis/rest/services/socal_roads_speed/MapServer/0/query?where=&text=&objectIds=&time=&geometry=-117.712679%2C34.120185&geometryType=esriGeometryPoint&inSR=4326&spatialRel=esriSpatialRelIntersects&relationParam=&outFields=speed&returnGeometry=false&maxAllowableOffset=&geometryPrecision=&outSR=&returnIdsOnly=false&returnCountOnly=false&orderByFields=&groupByFieldsForStatistics=&outStatistics=&returnZ=false&returnM=false&gdbVersion=&returnDistinctValues=false&f=pjson"); // URL to Brian's service
	
	}
	
	    private class NetworkTask extends AsyncTask<String, Integer, String[]> {

		    @Override
		    protected void onPostExecute(String result[]) {
		         
		    	//Display 
		    	ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.feature,result);
		        lv.setAdapter(adapter); // link the result to the list view
		        
		       // Log.d("Result",result[0]); 
		    }

			@Override
			protected String[] doInBackground(String... urls) {
				// TODO Auto-generated method stub
				String  result [] = null;
			        HttpGet request = new HttpGet(urls[0]); // create an http get method to the service URL
			        HttpClient httpclient = new DefaultHttpClient();  // http client
			        HttpResponse httpResponse; // response from the service
			        StringBuilder builder = new StringBuilder();
			        try 
			        {
			        	
			         // Call 
			        	httpResponse = (HttpResponse)httpclient.execute(request); // execute get method
			        	Log.d("Status", httpResponse.getStatusLine().getStatusCode()+""); // get the status if 200 then OK 
			        	if(httpResponse.getStatusLine().getStatusCode() == 200)
			        	{
			        	HttpEntity entity = httpResponse.getEntity();
						InputStream content = entity.getContent();
						BufferedReader reader = new BufferedReader(
								new InputStreamReader(content));    // get response content from the service
						String line;
						while ((line = reader.readLine()) != null) {
							builder.append(line);
						}
						
						
						// Parse
						JSONObject  json= new JSONObject(builder.toString());   // parse content to JSON object 
						JSONArray fields = json.getJSONArray("features");   // get the incident array from the JSON object 
						
						
						int n = fields.length();
						result= new String [n];
						 
						for(int i=0;i<n ; i++)
						{ 
							Log.d("JSON", fields.getJSONObject(i).getJSONObject("attributes").toString());
							
							// format list view as speed
							String speed = "Speed"+fields.getJSONObject(i).getJSONObject("attributes").getString("speed") +"\n";  // get the speed
							
							
							result[i] = speed; 
							
						
						}
			        }
			        }
			        catch(Exception e)
			        {
			        	e.printStackTrace();
			        }
				return result;
			        
			        
			}
		 
	}
	    
    

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}


