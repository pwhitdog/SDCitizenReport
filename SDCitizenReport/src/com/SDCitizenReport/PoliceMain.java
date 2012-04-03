package com.SDCitizenReport;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import com.SDCitizenReport.Precinct;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class PoliceMain extends Activity implements OnClickListener{
	
	// -- Variables to be used in app -- //
    // Layout Variables
	Button Phone;
	Button Camera;
	Button Cancel;
	Button Submit;
	EditText userInput;
	String userInputText = "";
	String message;
	String coords;
	String phoneInfo;
	String ZipCode;
		
	//Numbers Variables
	final static int cameraData = 0;
	double Long;
	double Lat;
	boolean tester = true;
	boolean isPictureTaken;
	
	//Location Vars
	LocationListener ll;
	LocationManager lm;
	
	//Strings for Email
	public static final String fromAdd = "paul.whitmer@gmail.com";
	public static final String emailPasswd = "8lestat.";
	public static String toAddr = "";
	private static final int NUMBER = 911;
	
	// Variables for the camera ops
	protected String _path;
	protected boolean _taken;
	protected static final String PHOTO_TAKEN = "photo_taken";
	
	@Override
	protected void onPause(){ // To shut off GPS when app is completed
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
    	
    	isPictureTaken = false;
    	
    	lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE); 
    	ll = new mylocationListener();
    	lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, ll);
    	    	
    	_path = Environment.getExternalStorageDirectory() + "/Download/citizenphoto.jpg";
    	
    }
	
	public void onClick(View v) 
	{
		switch(v.getId())
		{
		case R.id.dialer: // Calling 911
			Intent dial = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + NUMBER));
			startActivity(dial);
			break;
		case R.id.camera: // Taking a picture
			userInputText = userInput.getText().toString();
		    startCamera();
		    break;
		case R.id.submit: // Sending information out
		    final ProgressDialog pd = ProgressDialog.show(this, "Working", "Sending your message to the police.",
		            true,false);
		    // Starting new thread to send email while spinning a progress wheel
			new Thread(new Runnable(){
			     public void run(){
			         sendEmail(); 
			         pd.dismiss();
			         Intent i = new Intent(PoliceMain.this, home.class);
	                 startActivity(i);
			     }
			 }).start();
				break;						 	
		case R.id.cancel: // Go back to home screen
			Intent can = new Intent(this, home.class);
			startActivity(can);
		}		
	}

	public String getMessage()
	{
	    // Geocoder is to get address from latitude and longitude
		Geocoder geo = new Geocoder(getBaseContext(), Locale.getDefault());
		String display = "";
		
		TelephonyManager tm = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
		String phoneNumber = tm.getLine1Number();	
		
		try {
			List<Address> address = geo.getFromLocation(Lat, Long, 1);
			if(address.size() > 0)
			{			// Get the address from List to String	
				for(int i = 0; i < address.get(0).getMaxAddressLineIndex(); i++)
					{
					display += address.get(0).getAddressLine(i).toString() +"\n";
					}
				ZipCode = address.get(0).getPostalCode(); // Get the zip
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
	
	public void startCamera(){
	    File file = new File( _path );
	    Uri outputFileUri = Uri.fromFile( file );

	    Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE );
	    intent.putExtra( MediaStore.EXTRA_OUTPUT, outputFileUri );

	    startActivityForResult( intent, 0 ); 
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
	    Log.i( "CitizenReport", "resultCode: " + resultCode );
	    switch( resultCode )
	    {
	        case 0:
	            Log.i( "CitizenReport", "User cancelled" );
	            break;

	        case -1:
	            onPhotoTaken();
	            break;
	    }	
	    //if(userInputText != ""){
	    	//userInput.setText(userInputText);
	    //}	    
	}
	
	protected void onPhotoTaken()
	{
	    _taken = true;

	    BitmapFactory.Options options = new BitmapFactory.Options();
	    options.inSampleSize = 4;

	    Bitmap bitmap = BitmapFactory.decodeFile( _path, options );
	    isPictureTaken = true;
	}
	
	@Override
	protected void onSaveInstanceState( Bundle outState ) {
	    outState.putBoolean( PoliceMain.PHOTO_TAKEN, _taken );
	}
	@Override
	protected void onRestoreInstanceState( Bundle savedInstanceState)
	{
	    Log.i( "Citizen", "onRestoreInstanceState()");
	    if( savedInstanceState.getBoolean( PoliceMain.PHOTO_TAKEN ) ) {
	        onPhotoTaken();
	    }
	}
	
	class mylocationListener implements LocationListener{ // Action Listener to get Location
		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub
			if(location != null){
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
	public void sendEmail(){  // Loading and sending out Email
	    message = getMessage();
        Precinct p = new Precinct();
        toAddr = p.getPrecinctEmailAdd("92020");
        Mail sendit = new Mail(fromAdd, emailPasswd);
        sendit.setBody(message);
        if(isPictureTaken){
        	try {
        		sendit.addAttachment(_path);
        	} catch (Exception e1) {
        		// TODO Auto-generated catch block
        		e1.printStackTrace();
        	}
        }
        sendit.setSubject("Police Blotter");
        sendit.setTo(toAddr);
        sendit.setFrom(fromAdd);
        try { // Sending the Email to police
           tester = sendit.send();
        } catch (Exception e) {
           // TODO Auto-generated catch block
           e.printStackTrace();
           /*AlertDialog.Builder alert = new AlertDialog.Builder(this);
           alert.setTitle("Error");
           alert.setMessage("Your message has NOT been sent to the police.");
           alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
               public void onClick(DialogInterface dialog, int whichButton) {
                 
                 }
               });
           alert.show();*/
        }
	}
}
