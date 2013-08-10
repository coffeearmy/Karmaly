package com.coffeearmy;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.coffeearmy.bd.DatabaseManager;
import com.coffeearmy.model.Reward;
import com.coffeearmy.model.Task;



import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.text.method.HideReturnsTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		DatabaseManager.init(this);

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
		case R.id.action_compose:
			mViewPager.setCurrentItem(0);			
			TaskListFragment.showLayoutCompose();
			RewardListFragment.hideLayoutCompose();
			break;
		case R.id.action_compose2:	
			mViewPager.setCurrentItem(1);
			TaskListFragment.hideLayoutCompose();
			RewardListFragment.showLayoutCompose();
			break;
		default:
			return super.onOptionsItemSelected(item);
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
			// Show 3 total pages.
			return TOTAL_PAGES;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.app_name).toUpperCase(l);
			case 1:
				return getString(R.string.app_name).toUpperCase(l);
			case 2:
				return getString(R.string.app_name).toUpperCase(l);
			}
			return null;
		}
	}

	/**
	 * 
	 */
	public static class TaskListFragment extends ListFragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";
		public static final String TEXT_TASK = "text_task";
		public static final String NOT_DONE = "not_done";
		public static final String DONE = "done";
		public static final String ID="_id";
		private EditText editTask;
		private Button btnSave;
		private TaskListAdapter adapterCustom;
		private static LinearLayout lytCompose;
		
		public TaskListFragment() {
		}
		

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.list_view_tasks,
					container, false);
			editTask=(EditText)rootView.findViewById(R.id.etxComp);
			btnSave=(Button)rootView.findViewById(R.id.btnCompSave);
			lytCompose=(LinearLayout) rootView.findViewById(R.id.lytCompose);
			initButton(btnSave);
			initEditTask(editTask);
			return rootView;
		}
		 private void initEditTask(EditText editTask2) {
			editTask2.setOnFocusChangeListener(new OnFocusChangeListener() {
				
				public void onFocusChange(View v, boolean hasFocus) {
					if(!hasFocus) hideLayoutCompose();					
				}
			});
			
		}


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

		protected void createTask(String taskDesc) {
			 Task t = new Task();
		        t.setmText(taskDesc);
		        DatabaseManager.getInstance().addTask(t);
		        adapterCustom.notifyCursorChanged();
		}
		public static void hideLayoutCompose(){
			if(isLayoutVisible())lytCompose.setVisibility(View.GONE);
		}
		public static void showLayoutCompose(){
			if(!isLayoutVisible())lytCompose.setVisibility(View.VISIBLE);
		}
		public static Boolean isLayoutVisible(){
			return lytCompose.isShown();
		}

		@Override
		public void onStart() {
		        super.onStart();
		        setupListView(getListView());
		    }

		    private void setupListView(ListView lv) {
		        final List<Task> taskLists = DatabaseManager.getInstance().getAllTasks();
		        String[] col = new String[] { ID, TEXT_TASK, NOT_DONE, DONE };
				MatrixCursor cursor = new MatrixCursor(col);
		        List<String> titles = new ArrayList<String>();
		        for (Task wl : taskLists) {
		            titles.add(wl.getmText());
		            cursor.addRow(new Object[] { wl.getmId(),wl.getmText(),wl.getmNumNotDone(),wl.getmNumDone()});
		        }
		         adapterCustom= new TaskListAdapter(this.getActivity().getApplicationContext(), cursor);	
		       // ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, titles);
		        lv.setAdapter(adapterCustom);

		        
		        lv.setOnItemClickListener(new OnItemClickListener() {
		        	//Details
		            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		                //Task wishList = wishLists.get(position);
		                Intent intent = new Intent(getActivity(), TaskDetails.class);		                
		                intent.putExtra("taskdetailsid", taskLists.get(position).getmId());
		                intent.putExtra("taskdetailnodone", taskLists.get(position).getmNumNotDone());
		                intent.putExtra("taskdetaildone", taskLists.get(position).getmNumDone());
		                startActivity(intent);
		            }				
		        });
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
		private EditText editTask;
		private Button btnSave;
		private RatingBar rtbValue;
		private CheckedTextView isSurprise;
		private static LinearLayout lytCompose;

		public RewardListFragment() {
			//setEmptyText("NO REWARDS?! Create a nice reward!");
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.list_view_reward,
					container, false);
			editTask=(EditText)rootView.findViewById(R.id.etxCompReward);
			btnSave=(Button)rootView.findViewById(R.id.btnCompSaveReward);
			lytCompose=(LinearLayout) rootView.findViewById(R.id.lytRewardCompose);
			rtbValue= (RatingBar) rootView.findViewById(R.id.rtbReward);
			isSurprise= (CheckedTextView) rootView.findViewById(R.id.cktSurprise);
			initButton(btnSave);
			return rootView;
		}
		private void initButton(Button btnSave2) {
			btnSave2.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					String taskDesc = editTask.getText().toString();
					if (null != taskDesc && taskDesc.length() > 0) {
						createReward(taskDesc);
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

		protected void createReward(String taskDesc) {
			 Reward r = new Reward();
		        r.setmRewardText(taskDesc);
		        DatabaseManager.getInstance().addReward(r);

		}
		
		public static void hideLayoutCompose(){
			if(isLayoutVisible())lytCompose.setVisibility(View.GONE);
		}
		public static void showLayoutCompose(){
			if(!isLayoutVisible())lytCompose.setVisibility(View.VISIBLE);
		}
		public static Boolean isLayoutVisible(){
			return lytCompose.isShown();
		}
		 @Override
		public void onStart() {
		        super.onStart();
		        setupListView(getListView());
		    }

		    private void setupListView(ListView lv) {
		        final List<Reward> rewardLists = DatabaseManager.getInstance().getAllRewards();
		        
		        List<String> titles = new ArrayList<String>();
		        for (Reward wl : rewardLists) {
		            titles.add(wl.getmRewardText());
		        }

		        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, titles);
		        lv.setAdapter(adapter);

		        
		        lv.setOnItemClickListener(new OnItemClickListener() {
		        	//Delete reward
		            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//		                Task wishList = wishLists.get(position);
//		                Intent intent = new Intent(activity, WishItemListActivity.class);
//		                intent.putExtra(Constants.keyWishListId, wishList.getId());
//		                startActivity(intent);
		            }				
		        });
		    }
		
	}

}

