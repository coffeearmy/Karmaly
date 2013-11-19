package com.coffeearmy.karmaly;

import java.util.List;
import java.util.Locale;

import com.actionbarsherlock.app.SherlockFragmentActivity;

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

import android.view.ContextThemeWrapper;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

public class KarmalyActivity extends SherlockFragmentActivity {

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

	public static Animation animTranslate;
	public static Animation animIn;
	public static Animation animDelete;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Changing the title in the bar with the custom font.
		if (android.os.Build.VERSION.SDK_INT >= 11) {
			final int titleId = Resources.getSystem().getIdentifier(
					"action_bar_title", "id", "android");

			TextView title = (TextView) getWindow().findViewById(titleId);
			Typeface type = Typeface.createFromAsset(getAssets(),
					"VampiroOne.ttf");
			title.setTypeface(type);
			title.setTextSize(26);
			title.setTextColor(getResources().getColor(R.color.Tabs_color));
		}
		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		DatabaseManager.init(this);
		// Set animations
		animDelete = AnimationUtils.loadAnimation(this, R.animator.delete_anim);
		animTranslate = AnimationUtils.loadAnimation(this,
				R.animator.translate_anim);
		animIn = AnimationUtils.loadAnimation(this, R.animator.rotate_anim);
	}

	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getSupportMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(
			com.actionbarsherlock.view.MenuItem item) {
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
									TaskListFragment.notifyListChanged();
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
									RewardListFragment.notifyListChanged();
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
									TaskListFragment.setUserInfo(0);
									TaskListFragment.getAdapterCustom()
											.notifyListChanged();
									dialog.dismiss();
								}
							}).show();
			break;
		// Help option
		case R.id.help:

			startActivity(new Intent(this, HelpWebview.class));

			break;
		// About
		/*
		 * case R.id.about: new AlertDialog.Builder(this)
		 * .setTitle(R.string.about_title) .setMessage(R.string.about_text)
		 * .show(); break;
		 */
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

			switch (position) {
			case 0:
				Fragment fragment = new TaskListFragment();

				return fragment;
			case 1:
				Fragment fragment2 = new RewardListFragment();

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

}
