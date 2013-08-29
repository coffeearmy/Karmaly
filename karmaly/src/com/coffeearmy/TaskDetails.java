package com.coffeearmy;



import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.coffeearmy.adapters.DetailListAdapter;
import com.coffeearmy.bd.DatabaseManager;
import com.coffeearmy.model.Event;
import com.coffeearmy.model.Reward;
import com.coffeearmy.model.Task;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.database.MatrixCursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;



@SuppressLint("NewApi")
public class TaskDetails extends FragmentActivity {

	
	private static final String ID = "_id";
	private static final String DONE = "mDone";
	private static final String TIMESTAMP = "mTimestamp";
	private Integer mID;
	private ListView mListView;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.task_detail_view);
		//Changing the title in the bar with the custom font.
		final int titleId = 
			    Resources.getSystem().getIdentifier("action_bar_title", "id", "android");
		 TextView title = (TextView) getWindow().findViewById(titleId);
		 Typeface type = Typeface.createFromAsset(getAssets(),"VampiroOne.ttf"); 	
		 title.setTypeface(type);
		 title.setTextSize(24);
		 title.setTextColor(getResources().getColor(R.color.Tabs_color));
		 getActionBar().setDisplayShowHomeEnabled(false);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		mID=getIntent().getIntExtra("taskdetailsid", 0);
		TextView edtDone = (TextView) findViewById(R.id.txtDoneDetail);
		TextView edtNoDone= (TextView) findViewById(R.id.txtNotDoneDetail);
		Task task=DatabaseManager.getInstance().getTask(mID);
		if(task!=null){ //It's imposible 
			edtDone.setText(task.getmNumDone()+"");
			edtNoDone.setText(task.getmNumNotDone()+"");
		}
		mListView = (ListView) findViewById(R.id.ltvEvents);
		setupListView(mListView); 
		
	}

	private void deleteTask(Integer id) {
		DatabaseManager.getInstance().deleteTask(id);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.task_detail_menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		   case android.R.id.home:
		      finish();
		      return true;
		      default:
		new AlertDialog.Builder(this)
		.setTitle("Caution")
		.setMessage("You are going to delete this task! Are you sure?")
		.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(
							DialogInterface dialog,
							int which) {
						dialog.dismiss();
					}
				})
				.setPositiveButton("OK",
				new DialogInterface.OnClickListener() {
					public void onClick(
							DialogInterface dialog,
							int which) {
						deleteTask(mID);
						dialog.dismiss();
						finish();
					      
					}							
				}).show();
		}
		return super.onOptionsItemSelected(item);
	}
	   private void setupListView(ListView lv) {
	        final List<Event> eventLists = DatabaseManager.getInstance().getAllEvents(mID);
	        String[] col = new String[] { ID, DONE, TIMESTAMP };
			MatrixCursor cursor = new MatrixCursor(col);
	        
	        
	        for (Event el : eventLists) {

	            cursor.addRow(new Object[] {el.getmId(),el.getmIsDone(),el.getmTimestamp()});
	        }

	       
	        lv.setAdapter(new DetailListAdapter(this, cursor));

	        

	    }


}
