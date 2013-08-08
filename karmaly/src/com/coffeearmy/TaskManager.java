package com.coffeearmy;

import com.coffeearmy.bd.DatabaseManager;
import com.coffeearmy.model.Task;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

public class TaskManager extends FragmentActivity {
	private EditText edtText;
	private RatingBar rtgComplex;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.task_manager_view);
		Button btn = (Button) findViewById(R.id.btnSave);
		 edtText = (EditText) findViewById(R.id.edtText);
		 rtgComplex =(RatingBar) findViewById(R.id.rtbComplex);
		setupButton(btn);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void setupButton(Button btn) {
		final Context activity = this;
		btn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				String taskDesc = edtText.getText().toString();
				// Boolean daily =...
				int value = rtgComplex.getNumStars();
				if (null != taskDesc && taskDesc.length() > 0) {
					createTask(taskDesc, value);

				} else {
					new AlertDialog.Builder(activity)
							.setTitle("Error")
							.setMessage("Write something!")
							.setNegativeButton("OK",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
											dialog.dismiss();
										}
									}).show();
				}
			}
		}

		);
	}

	protected void createTask(String taskDesc, int value) {
		 Task t = new Task();
	        t.setmText(taskDesc);
	        t.setmValue(value);
	        DatabaseManager.getInstance().addTask(t);

	}
}
