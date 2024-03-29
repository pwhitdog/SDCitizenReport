package com.SDCitizenReport;
import android.app.Activity;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;


public class UseGps extends Activity
{

/** Called when the activity is first created. */

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.main);
	
		/* Use the LocationManager class to obtain GPS locations */
		LocationManager mlocManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		LocationListener mlocListener = new MyLocationListener();
		mlocManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 0, 0, mlocListener);
	}

	/* Class My Location Listener */
	public class MyLocationListener implements LocationListener
	{
	
		public void onLocationChanged(Location loc)
		{
			loc.getLatitude();
			loc.getLongitude();
			String Text = "The current location of the sender is: " + "Latitude = " + loc.getLatitude() + "Longitude = " + loc.getLongitude();
			Toast.makeText( getApplicationContext(), Text,Toast.LENGTH_SHORT).show();
		}

	
		public void onProviderDisabled(String provider)
		{
			Toast.makeText( getApplicationContext(),"GPS Disabled", Toast.LENGTH_SHORT ).show();
		}

	
		public void onProviderEnabled(String provider)
		{
			Toast.makeText( getApplicationContext(), "GPS Enabled", Toast.LENGTH_SHORT).show();
		}
	
		
		public void onStatusChanged(String provider, int status, Bundle extras)
		{


		}

	}/* End of Class MyLocationListener */

}/* End of UseGps Activity */