package com.coffeearmy.karmaly;

import java.util.List;

import com.coffeearmy.adapters.TaskListAdapterArray;
import com.coffeearmy.bd.DatabaseManager;
import com.coffeearmy.model.Task;
import com.coffeearmy.model.User;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

public class TaskListFragment extends ListFragment {

	private static EditText editTask;
	private Button btnSave;
	private static TaskListAdapterArray adapterCustom;
	private List<Task> taskLists;
	private static Animation animIn;

	private static TextView txtNotDone;
	private static TextView txtDone;
	private static ProgressBar pgbReward;
	private static LinearLayout lytCompose;
	private static LinearLayout lytUser;
	private static User u;

	public TaskListFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.list_view_tasks, container,
				false);
		editTask = (EditText) rootView.findViewById(R.id.etxComp);
		btnSave = (Button) rootView.findViewById(R.id.btnCompSave);
		lytCompose = (LinearLayout) rootView.findViewById(R.id.lytCompose);
		lytUser = (LinearLayout) rootView.findViewById(R.id.lytUserData);
		txtNotDone = (TextView) rootView.findViewById(R.id.txtNotDoneTask);
		txtDone = (TextView) rootView.findViewById(R.id.txtDoneTask);
		pgbReward = (ProgressBar) rootView.findViewById(R.id.pgbTask);
		animIn = AnimationUtils.loadAnimation(getActivity(),
				android.R.anim.fade_in);
		return rootView;
	}

	@Override
	public void onStart() {
		super.onStart();
		initButton(btnSave);
		initEditTask(editTask);
		setUserInfo(0);
		setupListView(getListView());
	}

	// Initiate the edittext. When the edittext loose the focus must
	// disapear.
	private void initEditTask(EditText editTask2) {
		editTask2.setOnFocusChangeListener(new OnFocusChangeListener() {

			public void onFocusChange(View v, boolean hasFocus) {
				if (!hasFocus)
					hideLayoutCompose();
			}
		});
	}

	// Initiate the button from the create task form
	private void initButton(Button btnSave2) {
		btnSave2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				String taskDesc = editTask.getText().toString();
				if (null != taskDesc && taskDesc.length() > 0) {
					createTask(taskDesc);
					hideLayoutCompose();

				} else {
					new AlertDialog.Builder(getActivity())
							.setTitle(android.R.string.dialog_alert_title)
							.setMessage(R.string.new_task_error)
							.setNegativeButton(android.R.string.ok,
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

	// Initiate the user info
	public static void setUserInfo(int side) {
		u = DatabaseManager.getInstance().getUser();
		txtDone.setText(u.getmDonePoints() + "");
		txtNotDone.setText(u.getmNotDonePoints() + "");
		pgbReward.setProgress(u.getmRewardPoints());
		if (side == 1) {// play the animation on txtDone
			txtDone.startAnimation(animIn);
		} else {
			if (side == 2) { // play animation on txtnotDone
				txtNotDone.startAnimation(animIn);
			}
		}

	}

	// Initiate the listview
	private void setupListView(ListView lv) {
		// Retrieve from the database the task.
		// the bd result is an arraylist
		taskLists = DatabaseManager.getInstance().getAllTasks();

		setAdapterCustom(new TaskListAdapterArray(this.getActivity(),
				R.layout.row_tasks, taskLists, getActivity()));
		lv.setAdapter(getAdapterCustom());
		// Add the listener: with a click its shows the tasks details
		// with a long click the list item can be delete.
		lv.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				// Start the task details activity
				Intent intent = new Intent(getActivity(),
						TaskDetailsFragment.class);
				intent.putExtra("taskdetailsid", taskLists.get(position)
						.getmId());
				startActivity(intent);
			}
		});

		lv.setOnItemLongClickListener(new OnItemLongClickListener() {
			public boolean onItemLongClick(AdapterView<?> arg0, final View v,
					int arg2, long arg3) {
				v.startAnimation(KarmalyActivity.animDelete);
				new AlertDialog.Builder(getActivity())
						.setTitle(android.R.string.dialog_alert_title)
						.setMessage(R.string.delete_task)
						.setNegativeButton(android.R.string.cancel,
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
										dialog.dismiss();
									}
								})
						.setPositiveButton(android.R.string.ok,
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
										deleteTask(((TaskListAdapterArray.ViewHolder) v
												.getTag()).idCode);
										v.startAnimation(KarmalyActivity.animTranslate);
										dialog.dismiss();
									}
								}).show();
				return false;

			}
		});

		lv.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				boolean event_consumed = false;
				if (isLayoutVisible()) {
					hideLayoutCompose();
					event_consumed = true;
				}
				return event_consumed;
			}
		});
	}

	// Create a new task and refresh the adapter
	protected void createTask(String taskDesc) {
		Task t = new Task();
		t.setmText(taskDesc);
		DatabaseManager.getInstance().addTask(t);
		taskLists = getAdapterCustom().notifyListChanged();
	}

	// Hide the newtask form and show the user data
	public static void hideLayoutCompose() {
		if (isLayoutVisible()) {
			lytCompose.setVisibility(View.GONE);
			editTask.setText("");
		}
		if (!isLayoutVisibleUser())
			lytUser.setVisibility(View.VISIBLE);
	}

	// Hide de User data and show the new task form
	public static void showLayoutCompose() {
		if (!isLayoutVisible())
			lytCompose.setVisibility(View.VISIBLE);
		if (isLayoutVisibleUser())
			lytUser.setVisibility(View.GONE);
	}

	public static Boolean isLayoutVisible() {
		return lytCompose.isShown();
	}

	public static Boolean isLayoutVisibleUser() {
		return lytUser.isShown();
	}

	private void deleteTask(int id) {
		DatabaseManager.getInstance().deleteTask(id);
		getAdapterCustom().notifyListChanged();
	}

	public static TaskListAdapterArray getAdapterCustom() {
		return adapterCustom;
	}

	public static void setAdapterCustom(
			TaskListAdapterArray taskListAdapterArray) {
		TaskListFragment.adapterCustom = taskListAdapterArray;
	}

	public static void notifyListChanged() {
		getAdapterCustom().notifyListChanged();

	}

}
