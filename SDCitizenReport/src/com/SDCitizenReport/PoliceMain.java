package com.SDCitizenReport;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import com.SDCitizenReport.Precinct;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class PoliceMain extends Activity implements OnClickListener{
	
	// -- Variables to be used in app -- //
	Button Phone;
	Button Camera;
	Button Cancel;
	Button Submit;
	
	String message;
	String coords;
	String phoneInfo;
	String ZipCode;
	String pictureFileString = "";
		
	final static int cameraData = 0;
	double Long;
	double Lat;
	
	Intent camera;
	LocationListener ll;
	LocationManager lm;
	
	EditText userInput;
	
	Bitmap picture;
	
	public static final String fromAdd = "sdcitizenreport@gmail.com";
	public static final String emailPasswd = "8lestat.";
	public static String toAddr = "";
	private static final int NUMBER = 911;
	
	boolean tester = true; 
	
	@Override
	protected void onPause()
	{
	   super.onPause();
	   lm.removeUpdates(ll);
	   lm = null;
	 }
		
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.policeview);
        setVars();
    }
    public void setVars()
    {
    	Phone  = (Button)findViewById(R.id.dialer);
    	Camera = (Button)findViewById(R.id.camera);
    	Cancel = (Button)findViewById(R.id.cancel);
    	Submit = (Button)findViewById(R.id.submit);
    	userInput = (EditText)findViewById(R.id.userInfo);
    	
    	Phone.setOnClickListener(this);
    	Camera.setOnClickListener(this);
    	Cancel.setOnClickListener(this);
    	Submit.setOnClickListener(this);
    	
    	lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE); 
    	ll = new mylocationListener();
    	lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, ll);
    }
	
	public void onClick(View v) 
	{
		switch(v.getId())
		{
		case R.id.dialer:
			Intent dial = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + NUMBER));
			startActivity(dial);
			break;
		case R.id.camera:
			camera = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(camera, cameraData);
			break;
		case R.id.submit:
			 message = getMessage();
			 Precinct p = new Precinct();
			 toAddr = p.getPrecinctEmailAdd(ZipCode);
			 Mail sendit = new Mail(fromAdd, emailPasswd);
			 sendit.setBody(message);
			 if(pictureFileString != ""){
				 try {
					sendit.addAttachment(pictureFileString);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 }
			 sendit.setSubject("Police Blotter");
			 sendit.setTo(toAddr);
			 sendit.setFrom(fromAdd);
			 try { // Sending the Email to police
				tester = sendit.send();
				AlertDialog.Builder alert = new AlertDialog.Builder(this);
				alert.setTitle("Notice");
				alert.setMessage("Your message has been sent to the police.");
				alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
					  userInput.setText("");
					  Intent i = new Intent(PoliceMain.this, home.class);
					  startActivity(i);					  
					  }
					});
				if(tester)
				{
					alert.show();
				}
				
			 } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				AlertDialog.Builder alert = new AlertDialog.Builder(this);
				alert.setTitle("Error");
				alert.setMessage("Your message has not been sent to the police");
				alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
					  
					  }
					});
				alert.show();
				
				break;				
			 }	
		case R.id.cancel:
			Intent can = new Intent(this, home.class);
			startActivity(can);
		}		
		
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == RESULT_OK){
			Bundle extras = camera.getExtras();
			picture = (Bitmap)extras.get("data");
		}
	}

	public String getMessage()
	{
		Geocoder geo = new Geocoder(getBaseContext(), Locale.getDefault());
		String display = "";
		
		TelephonyManager tm = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
		String phoneNumber = tm.getLine1Number();	
		
		try {
			List<Address> address = geo.getFromLocation(Lat, Long, 1);
			if(address.size() > 0)
			{				
				for(int i = 0; i < address.get(0).getMaxAddressLineIndex(); i++)
					{
					display += address.get(0).getAddressLine(i).toString() +"\n";
					}
				ZipCode = address.get(0).getPostalCode();
			}
			else{
				display = "Address is not available";
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return userInput.getText().toString() + "\nPhone Number used for reporting: " +phoneNumber
				+ "\nAddress:\n" + display;
	}
	
	class mylocationListener implements LocationListener
	{
		
		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub
			if(location != null)
			{
				Long = location.getLongitude();
				Lat = location.getLatitude();			
			}			
		}
		public void StopLocationListeners(){
			
		}
		
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			
		}

	}
}
