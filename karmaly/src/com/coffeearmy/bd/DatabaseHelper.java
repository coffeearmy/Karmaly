package com.coffeearmy.bd;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.coffeearmy.model.Event;
import com.coffeearmy.model.Reward;
import com.coffeearmy.model.Task;
import com.coffeearmy.model.User;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
	// name of the database file for your application -- change to something
	// appropriate for your app
	private static final String DATABASE_NAME = "KarmalyDBvdev04.sqlite";

	// any time you make changes to your database objects, you may have to
	// increase the database version
	private static final int DATABASE_VERSION = 3;

	// the DAO object we use to access the SimpleData table
	private Dao<Event, Integer> mEventDao = null;
	private Dao<Task, Integer> mTaskDao = null;
	private Dao<Reward, Integer> mRewardDao = null;
	private Dao<User, Integer> mUserDao = null;
	private Context c;

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);// chngeitfor
																// database
																// version
		c = context;
	}

	@Override
	public void onCreate(SQLiteDatabase database,
			ConnectionSource connectionSource) {
		try {
			TableUtils.createTable(connectionSource, Task.class);
			TableUtils.createTable(connectionSource, Event.class);
			TableUtils.createTable(connectionSource, Reward.class);
			TableUtils.createTable(connectionSource, User.class);
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
			throw new RuntimeException(e);
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource,
			int oldVersion, int newVersion) {
		try {

			if (oldVersion < DATABASE_VERSION) {
				// 1. Retrieve the timestamp column
				GenericRawResults<String[]> rawResults = getEventListDao()
						.queryRaw("select * from event");
				List<String[]> results = rawResults.getResults();

				SimpleDateFormat oldFormat = SelectRightDateFormatFromOldDatabase(results
						.get(0)[1]);

				// Delete table and re-create table
				TableUtils.dropTable(connectionSource, Event.class, false);
				TableUtils.createTable(connectionSource, Event.class);
				for (String[] timeStamp : results) {
//					Toast toast = Toast.makeText(c, timeStamp[1],
//							Toast.LENGTH_SHORT);
//					toast.show();

					Date date = oldFormat.parse(timeStamp[1]);

					// 2. Modify the time format (UNIX TIME FORMAT)
					getEventListDao().updateRaw(
							"INSERT INTO event (list_id, timestamp,is_done,id) "
									+ "VALUES (" + timeStamp[0] + ","
									+ date.getTime() + "," + timeStamp[2] + ","
									+ timeStamp[3] + ")");

					// Enjoy version 3.
				}
			}

		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "exception during onUpgrade",
					e);
			throw new RuntimeException(e);
		} catch (java.sql.SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public Dao<Task, Integer> getTaskListDao() {
		if (null == mTaskDao) {
			try {
				mTaskDao = getDao(Task.class);
			} catch (java.sql.SQLException e) {
				e.printStackTrace();
			}
		}
		return mTaskDao;
	}

	public Dao<Event, Integer> getEventListDao() {
		if (null == mEventDao) {
			try {
				mEventDao = getDao(Event.class);
			} catch (java.sql.SQLException e) {
				e.printStackTrace();
			}
		}
		return mEventDao;
	}

	public Dao<Reward, Integer> getRewardListDao() {
		if (null == mRewardDao) {
			try {
				mRewardDao = getDao(Reward.class);
			} catch (java.sql.SQLException e) {
				e.printStackTrace();
			}
		}
		return mRewardDao;
	}

	public Dao<User, Integer> getUserDao() {
		if (null == mUserDao) {
			try {
				mUserDao = getDao(User.class);
			} catch (java.sql.SQLException e) {
				e.printStackTrace();
			}
		}
		return mUserDao;
	}

	/***
	 * Changes from Database v2 to v3
	 */
	public SimpleDateFormat SelectRightDateFormatFromOldDatabase(String oldDate) {
		SimpleDateFormat oldFormat = null;
		// Choose the correct format
		if (oldDate
				.matches("[0-9]{2}/[0-9]{2}/[0-9]{4} [0-9]{2}:[0-9]{2}:[0-9]{2}"))
			oldFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		else if (oldDate
				.matches("[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}.[0-9]*"))
			oldFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
		else if (oldDate
				.matches("[0-9]{2} [a-zA-Z]{3} [0-9]{4} [0-9]{2}:[0-9]{2}:[0-9]{2}.[0-9]*"))
			oldFormat = new SimpleDateFormat("dd MMM yyyy HH:mm:ss.S");
		else if (oldDate
				.matches("[0-9]{2} [a-zA-Z]{3} [0-9]{4} [0-9]{2}:[0-9]{2}:[0-9]{2}"))
			oldFormat = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
		else{
			//Try the locale 			
			oldFormat=new SimpleDateFormat();
		}
		
		oldFormat.setLenient(false);
		return oldFormat;
	}

}