package com.coffeearmy.karmaly;

import hirondelle.date4j.DateTime;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import com.actionbarsherlock.ActionBarSherlock;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.coffeearmy.adapters.DetailListAdapter;
import com.coffeearmy.bd.DatabaseManager;
import com.coffeearmy.model.Event;
import com.coffeearmy.model.Reward;
import com.coffeearmy.model.Task;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
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
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class TaskDetailsFragment extends SherlockFragmentActivity {

	private static final String ID = "_id";
	private static final String DONE = "mDone";
	private static final String TIMESTAMP = "mTimestamp";
	private Integer mID;
	private ListView mListView;
	private CaldroidFragment caldroidFragment;

	private HashMap<String, List<Event>> hmapStore;
	private DateFormat dateFormat;
	private SimpleDateFormat formatter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.task_detail_view);
		// Set the option in the actionbar to go back to the previous activity
		getSherlock().getActionBar().setDisplayHomeAsUpEnabled(true);
		mID = getIntent().getIntExtra("taskdetailsid", 0);
		TextView edtDone = (TextView) findViewById(R.id.txtDoneDetail);
		TextView edtNoDone = (TextView) findViewById(R.id.txtNotDoneDetail);
		Task task = DatabaseManager.getInstance().getTask(mID);
		if (task != null) { // It's Impossible
			edtDone.setText(task.getmNumDone() + "");
			edtNoDone.setText(task.getmNumNotDone() + "");
		}
		// // Create the calendar view
		// View header = getLayoutInflater().inflate(
		// R.layout.calendar_header_view, null);
		//
		// mListView = (ListView) findViewById(R.id.ltvEvents);
		// mListView.setEmptyView((ViewStub) findViewById(android.R.id.empty));
		// // Ad the calendar as the the header of the listview
		// mListView.addHeaderView(header);
		formatter = new SimpleDateFormat("dd MMM yyyy");
		// setupListView(mListView);

		// Setup caldroid fragment
		// **** If you want normal CaldroidFragment, use below line ****
		caldroidFragment = new CaldroidFragment();

		// //////////////////////////////////////////////////////////////////////
		// **** This is to show customized fragment. If you want customized
		// version, uncomment below line ****
		// caldroidFragment = new CaldroidSampleCustomFragment();

		// Setup arguments

		// If Activity is created after rotation
		if (savedInstanceState != null) {
			caldroidFragment.restoreStatesFromKey(savedInstanceState,
					"CALDROID_SAVED_STATE");
		}
		// If activity is created from fresh
		else {
			Bundle args = new Bundle();
			Calendar cal = Calendar.getInstance();
			args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
			args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
			args.putBoolean(CaldroidFragment.ENABLE_SWIPE, true);
			args.putBoolean(CaldroidFragment.SIX_WEEKS_IN_CALENDAR, true);

			// Uncomment this to customize startDayOfWeek
			// args.putInt(CaldroidFragment.START_DAY_OF_WEEK,
			// CaldroidFragment.TUESDAY); // Tuesday
			caldroidFragment.setArguments(args);
		}

		setCustomResourceForDates();

		// Attach to the activity
		FragmentTransaction t = getSupportFragmentManager().beginTransaction();
		t.replace(R.id.calendarView, caldroidFragment);
		t.commit();
		// Setup listener
		final CaldroidListener listener = new CaldroidListener() {

			@Override
			public void onSelectDate(Date date, View view) {
				Toast.makeText(getApplicationContext(), formatter.format(date),
						Toast.LENGTH_SHORT).show();
				Toast.makeText(getApplicationContext(),
						hmapStore.containsKey(formatter.format(date)) + "",
						Toast.LENGTH_SHORT).show();
				Toast.makeText(getApplicationContext(),
						dateFormat.format(date) + "", Toast.LENGTH_SHORT)
						.show();

			}
		};

		// Setup Caldroid
		caldroidFragment.setCaldroidListener(listener);
	}

	private void setCustomResourceForDates() {
		// Get the Events
		final List<Event> eventLists = DatabaseManager.getInstance()
				.getAllEvents(mID);
		// Hashmap with the dates for the paint it in the widget
		HashMap<Date, Integer> hmap = new HashMap<Date, Integer>();
		// Hashmap for store the events
		hmapStore = new HashMap<String, List<Event>>();
		// DateFormat from the BD, is needed for read the bd format
		dateFormat = DateFormat.getDateTimeInstance();

		for (Event eventElemen : eventLists) {

			hmap.put(eventElemen.getmTimestamp(), R.color.Green_light);

			// Second formating

			// Calendar cal = new GregorianCalendar();
			// cal.setTime(eventElemen.getmTimestamp());
			// String dateAux = formatter.format(cal.getTime());
			String dataFormated = formatter.format(eventElemen.getmTimestamp());
			if (hmapStore.containsValue(dataFormated)) { // the hashmap has a
				// item in this date
				List<Event> aux = hmapStore.get(dataFormated);
				aux.add(eventElemen);
			} else {
				List<Event> newlist = new ArrayList<Event>();
				newlist.add(eventElemen);
				hmapStore.put(dataFormated, newlist);
			}
		}
		caldroidFragment.setBackgroundResourceForDates(hmap);
	}

	private void deleteTask(Integer id) {
		DatabaseManager.getInstance().deleteTask(id);

	}

	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getSupportMenuInflater().inflate(R.menu.task_detail_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(
			com.actionbarsherlock.view.MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			return true;
		default:
			new AlertDialog.Builder(this)
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
									deleteTask(mID);
									dialog.dismiss();
									finish();

								}
							}).show();
		}
		return super.onOptionsItemSelected(item);
	}

}
