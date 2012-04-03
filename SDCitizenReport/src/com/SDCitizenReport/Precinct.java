package com.SDCitizenReport;

import java.util.HashMap;
import java.util.Map;

public class Precinct {
    
    // Emails for precincts
    private static final String CentralEmail = "sdpdcentral@pd.sandiego.gov";
    private static final String NorthernEmail = "sdpdnorthern@pd.sandiego.gov";
    private static final String EasternEmail = "sdpdeastern@pd.sandiego.gov";
    private static final String MidCityEmail = "sdpdmidcity@pd.sandiego.gov";
    private static final String NorthEasternEmail = "sdpdnortheastern@pd.sandiego.gov";
    private static final String NorthWesternEmail = "sdpdnorthwestern@pd.sandiego.gov";
    private static final String SouthernEmail = "sdpdsouthern@pd.sandiego.gov";
    private static final String SouthEasternEmail = "sdpdsoutheastern@pd.sandiego.gov";
    private static final String WesternEmail = "sdpdwestern@pd.sandiego.gov";
    private static final String ElCajonEmail = "pdwebmaster@ci.el-cajon.ca.us";
    private static final String TestEmail = "paul.whitmer@gmail.com";
    
    private Map<String, String> lookUp = new HashMap<String, String>();
    
    public Precinct() {
    	lookUp.put("92101", CentralEmail);
    	lookUp.put("92102", CentralEmail);
    	lookUp.put("92103", CentralEmail);
    	lookUp.put("92104", CentralEmail);
    	lookUp.put("92113", CentralEmail);
    	lookUp.put("92136", CentralEmail);
    	lookUp.put("92124", EasternEmail);
    	lookUp.put("92120", EasternEmail);
    	lookUp.put("92123", EasternEmail);
    	lookUp.put("92108", EasternEmail);
    	lookUp.put("92119", EasternEmail);
    	lookUp.put("92104", MidCityEmail);
    	lookUp.put("92105", MidCityEmail);
    	lookUp.put("92116", MidCityEmail);
    	lookUp.put("92115", MidCityEmail);
    	lookUp.put("92117", NorthernEmail);
    	lookUp.put("92111", NorthernEmail);
    	lookUp.put("92037", NorthernEmail);
    	lookUp.put("92109", NorthernEmail);
    	lookUp.put("92122", NorthernEmail);
    	lookUp.put("92128", NorthEasternEmail);
    	lookUp.put("92145", NorthEasternEmail);
    	lookUp.put("92126", NorthEasternEmail);
    	lookUp.put("92127", NorthEasternEmail);
    	lookUp.put("92128", NorthEasternEmail);
    	lookUp.put("92129", NorthEasternEmail);
    	lookUp.put("92131", NorthEasternEmail);
    	lookUp.put("92121", NorthWesternEmail);
    	lookUp.put("92104", NorthWesternEmail);
    	lookUp.put("92130", NorthWesternEmail);
    	lookUp.put("92127", NorthWesternEmail);
    	lookUp.put("92154", SouthernEmail);
    	lookUp.put("92173", SouthernEmail);
    	lookUp.put("92114", SouthEasternEmail);
    	lookUp.put("92139", SouthEasternEmail);
    	lookUp.put("92106", WesternEmail);
    	lookUp.put("92107", WesternEmail);
    	lookUp.put("92110", WesternEmail);
    	lookUp.put("92111", WesternEmail);
    	lookUp.put("92140", WesternEmail);    	
    	lookUp.put("92020", ElCajonEmail);
    }

    // Take in the Zipcode and return the emailAddress
    public String getPrecinctEmailAdd(String Zip){
    	if(lookUp.containsKey(Zip)) {
    		//return lookUp.get(Zip);
    		return TestEmail;
    	} else {
    		return "default";
    	}

    }    

}
