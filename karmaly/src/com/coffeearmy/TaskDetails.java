package com.coffeearmy;



import com.coffeearmy.bd.DatabaseManager;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;



public class TaskDetails extends FragmentActivity {

	
	private Integer mID;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.task_detail_view);
		mID=getIntent().getIntExtra("taskdetailsid", 0);
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
					}							
				}).show();
		return super.onOptionsItemSelected(item);
	}


}
