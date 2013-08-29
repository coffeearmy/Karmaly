package com.coffeearmy;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.widget.TextView;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
@SuppressLint("NewApi")
public class PrefActivity extends PreferenceActivity {

	
	 @TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("NewApi")
	@Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        addPreferencesFromResource(R.xml.preferences);
	        final int titleId = 
				    Resources.getSystem().getIdentifier("action_bar_title", "id", "android");
			 TextView title = (TextView) getWindow().findViewById(titleId);
			 Typeface type = Typeface.createFromAsset(getAssets(),"VampiroOne.ttf"); 	
			 title.setTypeface(type);
			 title.setTextSize(24);
			 title.setTextColor(getResources().getColor(R.color.Tabs_color));
			 getActionBar().setDisplayShowHomeEnabled(false);
			getActionBar().setDisplayHomeAsUpEnabled(true);
	    }
}
