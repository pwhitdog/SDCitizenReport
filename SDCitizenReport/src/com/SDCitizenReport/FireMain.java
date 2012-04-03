package com.SDCitizenReport;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class FireMain extends Activity implements OnClickListener{

	
	// -- Variables to be used in app -- //
	Button Phone;
	Button Camera;
	Button Cancel;
	Button Submit;
	
	String message;
	String coords;
	String phoneInfo;
	
	EditText userInput;
	
	public static final String emailAdd = "paul.whitmer@gmail.com";
	public static final String emailPasswd = "8lestat.";
	public static final String toAddr = "paul.whitmer@gmail.com";
	private static final int NUMBER = 911;
	
	boolean tester = true; 

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fireview);
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

		case R.id.submit:
			 getStrings();
			 Mail sendit = new Mail(emailAdd, emailPasswd);
			 sendit.setBody(message);
			 sendit.setSubject("Fire Blotter");
			 sendit.setTo(toAddr);
			 sendit.setFrom("paul.whitmer@gmail.com");
			 try {
				tester = sendit.send();
				AlertDialog.Builder alert = new AlertDialog.Builder(this);
				alert.setTitle("Notice");
				alert.setMessage("Your message has been sent to the police.");
				alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
					  userInput.setText("");
					  Intent i = new Intent(FireMain.this, home.class);
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
			 
		}
		
	}

	public void getStrings()
	{
		message = userInput.getText().toString();
	}
}