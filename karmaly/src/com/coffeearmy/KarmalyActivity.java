package com.coffeearmy;


import java.util.List;
import java.util.Locale;


import com.coffeearmy.adapters.RewardsListAdapter;
import com.coffeearmy.adapters.TaskListAdapter;
import com.coffeearmy.bd.DatabaseManager;
import com.coffeearmy.model.Reward;
import com.coffeearmy.model.Task;
import com.coffeearmy.model.User;


import android.app.AlertDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.MatrixCursor;
import android.graphics.Typeface;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnKeyListener;
import android.view.View.OnLayoutChangeListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

public class KarmalyActivity extends FragmentActivity {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	private  static Animation animTranslate;

	private static Animation animDelete;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Changing the title in the bar with the custom font.
		final int titleId = Resources.getSystem().getIdentifier(
				"action_bar_title", "id", "android");
		TextView title = (TextView) getWindow().findViewById(titleId);
		Typeface type = Typeface.createFromAsset(getAssets(), "VampiroOne.ttf");
		title.setTypeface(type);
		title.setTextSize(26);
		title.setTextColor(getResources().getColor(R.color.Tabs_color));

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		DatabaseManager.init(this);
		//Set animations
		animDelete = AnimationUtils.loadAnimation(this, R.animator.delete_anim);
		animTranslate = AnimationUtils.loadAnimation(this, R.animator.translate_anim);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		// Option: New Task
		case R.id.action_compose:
			mViewPager.setCurrentItem(0);
			TaskListFragment.showLayoutCompose();
			RewardListFragment.hideLayoutCompose();
			break;
		// New Reward
		case R.id.action_compose2:
			mViewPager.setCurrentItem(1);
			TaskListFragment.hideLayoutCompose();
			RewardListFragment.showLayoutCompose();
			break;
		// Delete all tasks
		case R.id.delete_all_tasks:
			new AlertDialog.Builder(this)
					.setTitle(android.R.string.dialog_alert_title)
					.setMessage(R.string.delete_tasks_pref)
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
									DatabaseManager.getInstance()
											.deleteAllTasks();
									TaskListFragment.adapterCustom
											.notifyCursorChanged();
									dialog.dismiss();
								}
							}).show();
			break;
		// Delete all rewards
		case R.id.delete_rewards:
			new AlertDialog.Builder(this)
					.setTitle(android.R.string.dialog_alert_title)
					.setMessage(R.string.delete_rewards_explain_pref)
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
									DatabaseManager.getInstance()
											.deleteAllRewards();
									RewardListFragment.adapterCustom
											.notifyCursorChanged();
									dialog.dismiss();
								}
							}).show();
			break;
		// Delete User data
		case R.id.delete_user_data:
			new AlertDialog.Builder(this)
					.setTitle(android.R.string.dialog_alert_title)
					.setMessage(R.string.delete_user_pref)
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
									DatabaseManager.getInstance().deleteUser();
									TaskListFragment.setUserInfo();
									TaskListFragment.adapterCustom.notifyCursorChanged();
									dialog.dismiss();
								}
							}).show();
			break;
		// Help option
		case R.id.help:
			new AlertDialog.Builder(this)
			.setTitle(R.string.help_title) 
			.setMessage(R.string.help_text)
			.show();
			break;
		// About
		case R.id.about:
			new AlertDialog.Builder(this)
			.setTitle(R.string.about_title) 
			.setMessage(R.string.about_text)
			.show();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		private static final int TOTAL_PAGES = 2;

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.

			switch (position) {
			case 0:
				Fragment fragment = new TaskListFragment();
				Bundle args = new Bundle();
				args.putInt(TaskListFragment.ARG_SECTION_NUMBER, position + 1);
				fragment.setArguments(args);
				return fragment;
			case 1:
				Fragment fragment2 = new RewardListFragment();
				Bundle args2 = new Bundle();
				args2.putInt(TaskListFragment.ARG_SECTION_NUMBER, position + 1);
				fragment2.setArguments(args2);
				return fragment2;
			}
			return null;
		}

		@Override
		public int getCount() {
			// Show 2 total pages.
			return TOTAL_PAGES;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.tabs_task).toUpperCase(l);
			case 1:
				return getString(R.string.tabs_rewards).toUpperCase(l);
			}
			return null;
		}
	}

	/**
	 * 
	 */
	public static class TaskListFragment extends ListFragment{
		// Cursor
		public static final String ARG_SECTION_NUMBER = "section_number";
		public static final String TEXT_TASK = "text_task";
		public static final String NOT_DONE = "not_done";
		public static final String DONE = "done";
		public static final String ID = "_id";

		private static EditText editTask;
		private Button btnSave;
		private static TaskListAdapter adapterCustom;
		private List<Task> taskLists;
	
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
			View rootView = inflater.inflate(R.layout.list_view_tasks,
					container, false);
			editTask = (EditText) rootView.findViewById(R.id.etxComp);
			btnSave = (Button) rootView.findViewById(R.id.btnCompSave);
			lytCompose = (LinearLayout) rootView.findViewById(R.id.lytCompose);
			lytUser = (LinearLayout) rootView.findViewById(R.id.lytUserData);
			txtNotDone = (TextView) rootView.findViewById(R.id.txtNotDoneTask);
			txtDone = (TextView) rootView.findViewById(R.id.txtDoneTask);
			pgbReward = (ProgressBar) rootView.findViewById(R.id.pgbTask);
			
			return rootView;
		}
		
		
		
		@Override
		public void onStart() {
			super.onStart();
			initButton(btnSave);
			initEditTask(editTask);
			setUserInfo();
			setupListView(getListView());
		}

		//Initiate the edittext. When the edittext loose the focus must disapear.
		private void initEditTask(EditText editTask2) {
			editTask2.setOnFocusChangeListener(new OnFocusChangeListener() {
				
				public void onFocusChange(View v, boolean hasFocus) {
					if(!hasFocus) hideLayoutCompose();
				}
			});
		}

		//Initiate the button from the create task form
		private void initButton(Button btnSave2) {
			btnSave2.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					String taskDesc = editTask.getText().toString();
					if (null != taskDesc && taskDesc.length() > 0) {
						createTask(taskDesc);
						hideLayoutCompose();

					} else {
						new AlertDialog.Builder(getActivity())
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
		
		//Initiate the user info
		public static void setUserInfo() {
			 u = DatabaseManager.getInstance().getUser();
			txtDone.setText(u.getmDonePoints() + "");
			txtNotDone.setText(u.getmNotDonePoints() + "");
			pgbReward.setProgress(u.getmRewardPoints());

		}
		
		//Initiate the listview
		private void setupListView(ListView lv) {
			//Retrieve from the database the task.
			//the bd result is an arraylist
			taskLists = DatabaseManager.getInstance().getAllTasks();
			String[] col = new String[] { ID, TEXT_TASK, NOT_DONE, DONE };
			MatrixCursor cursor = new MatrixCursor(col);
			//Pass the arraylist to a cursor for the custom adapter
			for (Task wl : taskLists) {

				cursor.addRow(new Object[] { wl.getmId(), wl.getmText(),
						wl.getmNumNotDone(), wl.getmNumDone() });
			}
			adapterCustom = new TaskListAdapter(this.getActivity()
					.getApplicationContext(), cursor, getActivity());
			
			lv.setAdapter(adapterCustom);
			//Add the listener: with a click its shows the tasks details
			// with a long click the list item can be delete.
			lv.setOnItemClickListener(new OnItemClickListener() {
				
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					
					//Start the task details activity
					Intent intent = new Intent(getActivity(), TaskDetails.class);
					intent.putExtra("taskdetailsid", taskLists.get(position)
							.getmId());
					startActivity(intent);
				}
			});
		

			lv.setOnItemLongClickListener(new OnItemLongClickListener() {
				public boolean onItemLongClick(AdapterView<?> arg0,
						final View v, int arg2, long arg3) {
					v.startAnimation(animDelete);
					new AlertDialog.Builder(getActivity())
							.setTitle(android.R.string.dialog_alert_title)
							.setMessage(R.string.delete_task) 
							.setNegativeButton(android.R.string.cancel,
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
											dialog.dismiss();
										}
									})
							.setPositiveButton(android.R.string.ok,
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
											deleteTask(((TaskListAdapter.ViewHolder) v
													.getTag()).idCode);
											v.startAnimation(animTranslate);
											dialog.dismiss();
										}
									}).show();
					return false;

				}
			});
	
			lv.setOnTouchListener(new OnTouchListener() {
				
				public boolean onTouch(View v, MotionEvent event) {
					boolean event_consumed=false;
					if(isLayoutVisible()){
					hideLayoutCompose();
					event_consumed=true;
					}
					return event_consumed;
				}
			});
		}
		
		//Create a new task and refresh the adapter
		protected void createTask(String taskDesc) {
			Task t = new Task();
			t.setmText(taskDesc);
			DatabaseManager.getInstance().addTask(t);
			taskLists = adapterCustom.notifyCursorChanged();
		}
		
		//Hide the newtask form and show the user data 
		public static void hideLayoutCompose() {
			if (isLayoutVisible()){
				lytCompose.setVisibility(View.GONE);
				editTask.setText("");
			}
			if (!isLayoutVisibleUser())
				lytUser.setVisibility(View.VISIBLE);
		}
		//Hide de User data and show the new task form
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
			adapterCustom.notifyCursorChanged();
		}

	

	}

	/**
	 * 
	 */
	public static class RewardListFragment extends ListFragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";
		public static final String ID = "_id";
		public static final String TEXT_REWARD = "text_reward";
		public static final String HIDDEN = "hidden";
		public static final String RATING = "rating";
		
		private static EditText editTask;
		private Button btnSave;
		private static RatingBar rtbValue;
		private static CheckBox isSurprise;
		private static RewardsListAdapter adapterCustom;
		private static List<Reward> rewardLists;
		private static LinearLayout lytCompose;

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.list_view_reward,
					container, false);
			editTask = (EditText) rootView.findViewById(R.id.etxCompReward);
			btnSave = (Button) rootView.findViewById(R.id.btnCompSaveReward);
			lytCompose = (LinearLayout) rootView
					.findViewById(R.id.lytRewardCompose);
			rtbValue = (RatingBar) rootView.findViewById(R.id.rtbReward);
			isSurprise = (CheckBox) rootView.findViewById(R.id.chbIsHidden);
			initButton(btnSave);
			return rootView;
		}

		@Override
		public void onStart() {
			super.onStart();
			setupListView(getListView());
		}
		
		private void initButton(Button btnSave2) {
			btnSave2.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					String taskDesc = editTask.getText().toString();
					int rating = ((Float) rtbValue.getRating()).intValue();
					Boolean isHidden = isSurprise.isChecked();
					if (null != taskDesc && taskDesc.length() > 0) {
						createReward(taskDesc, rating, isHidden);
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
		
		private void setupListView(ListView lv) {
			rewardLists = DatabaseManager.getInstance().getAllRewards();

			String[] col = new String[] { ID, TEXT_REWARD, HIDDEN, RATING };
			MatrixCursor cursor = new MatrixCursor(col);

			for (Reward rl : rewardLists) {

				cursor.addRow(new Object[] { rl.getmId(), rl.getmRewardText(),
						rl.ismIsHidden(), rl.getmValue() });
			}
			adapterCustom = new RewardsListAdapter(this.getActivity()
					.getApplicationContext(), cursor);

			lv.setOnItemLongClickListener(new OnItemLongClickListener() {

				public boolean onItemLongClick(AdapterView<?> arg0,
						final View v, int arg2, long arg3) {
					v.startAnimation(animDelete);
					new AlertDialog.Builder(getActivity())
							.setTitle(android.R.string.dialog_alert_title)
							.setMessage(R.string.delete_reward) 
							.setNegativeButton(android.R.string.cancel,
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
											dialog.dismiss();
										}
									})
							.setPositiveButton(android.R.string.ok,
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
											deleteReward(((RewardsListAdapter.ViewHolder) v
													.getTag()).id);
											v.startAnimation(animTranslate);
											dialog.dismiss();
										}
									}).show();
					return false;

				}
			});
			
			lv.setOnTouchListener(new OnTouchListener() {
				
				public boolean onTouch(View v, MotionEvent event) {
					boolean event_consumed=false;
					if(isLayoutVisible()){
					hideLayoutCompose();
					event_consumed=true;
					}
					return event_consumed;
				}
			});
			lv.setAdapter(adapterCustom);

		}

		protected void createReward(String taskDesc, int rating2,
				Boolean isHidden) {
			Reward r = new Reward();
			r.setmRewardText(taskDesc);
			r.setmValue(rating2);
			r.setmIsHidden(isHidden);
			DatabaseManager.getInstance().addReward(r);
			notifyCursorChanged();
		}

		public static void notifyCursorChanged() {
			rewardLists = adapterCustom.notifyCursorChanged();
		}

		public static void hideLayoutCompose() {
			if (isLayoutVisible()){
				lytCompose.setVisibility(View.GONE);
				//Clear the form
				editTask.setText("");
				rtbValue.setRating(0);
				isSurprise.setChecked(false);
			}
		}

		public static void showLayoutCompose() {
			if (!isLayoutVisible())
				lytCompose.setVisibility(View.VISIBLE);
		}

		public static Boolean isLayoutVisible() {
			return lytCompose.isShown();
		}
			
		private void deleteReward(Integer id) {
			DatabaseManager.getInstance().deleteReward(id);
			notifyCursorChanged();

		}
	}

}
