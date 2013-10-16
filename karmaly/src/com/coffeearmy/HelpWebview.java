package com.coffeearmy;

import java.io.IOException;


import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MenuItem;

import android.webkit.WebView;

import android.widget.TextView;

public class HelpWebview extends FragmentActivity {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.help_webview);
		//Changing the title in the bar with the custom font.
		final int titleId = 
			    Resources.getSystem().getIdentifier("action_bar_title", "id", "android");
		 TextView title = (TextView) getWindow().findViewById(titleId);
		 Typeface type = Typeface.createFromAsset(getAssets(),"VampiroOne.ttf"); 	
		 title.setTypeface(type);
		 title.setTextSize(24);
		 title.setTextColor(getResources().getColor(R.color.Tabs_color));
		WebView webView = (WebView) findViewById(R.id.wvhelp);
		String webData="Sorry, there is a problem!";
		try {
			webData = getAssets().open("index.html").toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//webView.loadData(webData, "text/html", "UTF-8");
		webView.loadUrl("file:///android_asset/index.html");
		getActionBar().setDisplayHomeAsUpEnabled(true);	
	
		
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		   case android.R.id.home:
		      finish();
		      return true;		     
		}
		return super.onOptionsItemSelected(item);
	}

}
