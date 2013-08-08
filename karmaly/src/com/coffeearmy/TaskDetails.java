package com.coffeearmy;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;

import android.widget.Button;


public class TaskDetails extends FragmentActivity {

	
	private Integer mID;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.task_detail_view);
		Button btnDelete = (Button) findViewById(R.id.btnDelete);
		Button btnEdit = (Button) findViewById(R.id.btnEdit);
		setupButtonDelete(btnDelete);
		setupButtonEdit(btnEdit);
		mID=getIntent().getIntExtra("taskdetailsid", 0);
//		getIntent().getIntExtra("taskdetailnodone", 0);
//		getIntent().getIntExtra("taskdetaildone", 0);
	}

	private void setupButtonEdit(Button btnEdit2) {
		final Context activity = this;
		btnEdit2.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				editTask(mID);
				
			}
		});
	}

	protected void editTask(Integer mID2) {
		// TODO Auto-generated method stub
		
	}

	private void setupButtonDelete(Button btnDelete2) {
		final Context activity = this;
		btnDelete2.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				new AlertDialog.Builder(activity)
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
				
			}
		});
	}
	private void deleteTask(Integer mID2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

//	private void setupButton(Button btn) {
//		final Context activity = this;
//		btn.setOnClickListener(new OnClickListener() {
//			public void onClick(View v) {
//				String rewardDesc = edtRewardText.getText().toString();
//				// Boolean daily =...
//				int value = rtgComplex.getNumStars();
//				boolean hidden = cktHidden.isChecked();
//				if (null != rewardDesc && rewardDesc.length() > 0) {
//					createReward(rewardDesc, value,hidden);
//
//				} else {
//					new AlertDialog.Builder(activity)
//							.setTitle("Error")
//							.setMessage("Write something!")
//							.setNegativeButton("OK",
//									new DialogInterface.OnClickListener() {
//										public void onClick(
//												DialogInterface dialog,
//												int which) {
//											dialog.dismiss();
//										}
//									}).show();
//				}
//			}
//		}
//
//		);
//	}
}
