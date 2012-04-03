package com.SDCitizenReport;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class home extends Activity implements OnClickListener {
    /** Called when the activity is first created. */
	Button Police;
	Button Fire;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        setVars();
    }

	private void setVars() {
		// TODO Auto-generated method stub
		Police = (Button)findViewById(R.id.policeButton);
		Fire   = (Button)findViewById(R.id.fireButton);
		
		Police.setOnClickListener(this);
		Fire.setOnClickListener(this);
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch(v.getId())
		{
		case R.id.policeButton:
			Intent p = new Intent("com.sdcitizenreport.POLICE");
			startActivity(p);
			break;
			
		case R.id.fireButton:
			Intent f = new Intent("com.sdcitizenreport.FIRE");
			startActivity(f);
			break;
		case R.id.potholeButton:
			Intent ph = new Intent("com.sdcitizenreport.POTHOLE");
			startActivity(ph);
			break;
		case R.id.streetlightButton:
			Intent s = new Intent("com.sdcitizenreport.STREETLIGHT");
			startActivity(s);
			break;
			
		}
		
	}
}