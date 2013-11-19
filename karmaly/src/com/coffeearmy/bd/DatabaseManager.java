package com.coffeearmy.bd;

import java.sql.SQLException;
import java.util.List;

import com.coffeearmy.model.Event;
import com.coffeearmy.model.Reward;
import com.coffeearmy.model.Task;
import com.coffeearmy.model.User;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;

import android.content.Context;

public class DatabaseManager {

	static private DatabaseManager sInstance;

	static public void init(Context ctx) {
		if (null == sInstance) {
			sInstance = new DatabaseManager(ctx);
		}
	}

	static public DatabaseManager getInstance() {
		return sInstance;
	}

	private DatabaseHelper helper;
	private User user;

	private DatabaseManager(Context ctx) {
		helper = new DatabaseHelper(ctx);
	}

	private DatabaseHelper getHelper() {
		return helper;
	}

	/**
	 * Task Operation, GetALL, ADD, Delete, Update
	 */
	public List<Task> getAllTasks() {
		List<Task> tasks = null;
		try {
			tasks = getHelper().getTaskListDao().queryBuilder()
					.orderBy("id", false).query();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tasks;
	}

	public Task getTask(Integer mID) {
		try {
			List<Task> results = getHelper().getTaskListDao().queryBuilder()
					.where().eq("id", mID).query();
			if (results.size() > 0) {

				Task task = results.get(0);
				return task;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void addTask(Task t) {
		try {
			getHelper().getTaskListDao().create(t);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void updateTask(Task t) {
		try {
			getHelper().getTaskListDao().update(t);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void updatePoints(Boolean done, int t) {
		try {
			List<Task> results = getHelper().getTaskListDao().queryBuilder()
					.where().eq("id", t).query();
			if (results.size() > 0) {

				Task task = results.get(0);
				Event event = new Event();
				event.setmIsDone(done);
				event.setList(task);
				if (done) {
					task.updateDone();
				} else {
					task.updateNotDone();
				}
				addEvent(event);
				updateTask(task);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteTask(int t) {
		try {
			// Delete first the events
			deleteEvents(t);
			List<Task> results = getHelper().getTaskListDao().queryBuilder()
					.where().eq("id", t).query();
			if (results.size() > 0)
				getHelper().getTaskListDao().delete(results.get(0));

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteAllTasks() {
		try {
			// Delete all events
			deleteAllEvents();
			getHelper().getTaskListDao().deleteBuilder().delete();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void resetTasks() {
		// Set tasks done or not done a 0
		try {
			UpdateBuilder<Task, Integer> updateBuilder = getHelper()
					.getTaskListDao().updateBuilder();
			// set the criteria like you would a QueryBuilder
			// No- criteria// save for future reference:
			// updateBuilder.where().eq("categoryId", 5);
			// update the value of your field(s)
			updateBuilder.updateColumnValue("num_done", 0);
			updateBuilder.update();
			updateBuilder.updateColumnValue("num_not_done", 0);
			updateBuilder.update();
			// delete the events
			deleteAllEvents();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Reward Operation, GetALL, ADD, Delete, Update
	 */
	public List<Reward> getAllRewards() {
		List<Reward> reward = null;
		try {
			reward = getHelper().getRewardListDao().queryBuilder()
					.orderBy("id", false).query();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return reward;
	}

	public List<Reward> getAllRewardsRated(boolean asc) {
		List<Reward> reward = null;
		try {
			reward = getHelper().getRewardListDao().queryBuilder()
					.orderBy("value", asc).query();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return reward;
	}

	public void updateReward(Reward r) {
		try {
			getHelper().getRewardListDao().update(r);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void addReward(Reward r) {
		try {
			getHelper().getRewardListDao().create(r);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void deleteReward(int t) {
		try {
			List<Reward> results = getHelper().getRewardListDao()
					.queryBuilder().where().eq("id", t).query();
			if (results.size() > 0)
				getHelper().getRewardListDao().delete(results.get(0));

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteAllRewards() {
		try {
			getHelper().getRewardListDao().deleteBuilder().delete();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Events Operation, GetALL, ADD, Delete, Update
	 * 
	 * @param mID
	 */
	public List<Event> getAllEvents(Integer mID) {
		List<Event> events = null;
		try {
			List<Task> results = getHelper().getTaskListDao().queryBuilder()
					.where().eq("id", mID).query();
			if (results.size() > 0) {

				Task task = results.get(0);
				events = getHelper().getEventListDao().queryBuilder().where()
						.eq("list_id", mID).query();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return events;
	}

	public void addEvent(Event ev) {
		try {
			getHelper().getEventListDao().create(ev);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void deleteEvents(int t) {
		try {
			// /TODO comprobar que exsite

			if (getHelper().getEventListDao().queryBuilder().where()
					.eq("list_id", t).query().size() > 0) {

				DeleteBuilder<Event, Integer> deleteBuilder = getHelper()
						.getEventListDao().deleteBuilder();
				deleteBuilder.where().eq("list_id", t);
				deleteBuilder.delete();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void deleteAllEvents() {
		try {
			getHelper().getEventListDao().deleteBuilder().delete();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * User class operation
	 */
	public User getUser() {
		if (user == null) {
			List<User> users = null;
			try {
				users = getHelper().getUserDao().queryForAll();
				if (users.size() > 0) {
					user = users.get(0);
					return user;
				} else {
					User u = new User();
					addUser(u);
					return u;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			return user;
		}
		return null;
	}

	public void addUser(User u) {
		try {
			getHelper().getUserDao().create(u);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void updateUser(User u) {
		try {
			getHelper().getUserDao().update(u);
			getHelper().getUserDao().refresh(u);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteUser() {
		// Set user data a 0;
		User u = getUser();
		u.deleteUser();
		updateUser(u);
		// Set tasks done or not done a 0
		resetTasks();
	}

}
