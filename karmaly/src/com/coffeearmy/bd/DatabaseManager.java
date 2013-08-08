package com.coffeearmy.bd;
import java.sql.SQLException;
import java.util.List;

import com.coffeearmy.model.Reward;
import com.coffeearmy.model.Task;

import android.content.Context;


public class DatabaseManager {

    static private DatabaseManager sInstance;

    static public void init(Context ctx) {
        if (null==sInstance) {
        	sInstance = new DatabaseManager(ctx);
        }
    }

    static public DatabaseManager getInstance() {
        return sInstance;
    }

    private DatabaseHelper helper;
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
        	tasks = getHelper().getTaskListDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }
    public void addTask(Task t) {
		 try {
	        	getHelper().getTaskListDao().create(t);
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }		
	}
    public void updateTask(Task t){
    	try{
    		getHelper().getTaskListDao().update(t);
    	}catch(SQLException e){
    		 e.printStackTrace();
    	}
    }
    public void deleteTask(int t){
    	try{
    		List<Task> results = getHelper().getTaskListDao().queryBuilder().where().eq("id",t).query();
    		if(results.size()>0) getHelper().getTaskListDao().delete(results.get(0));
    	}catch(SQLException e){
    		 e.printStackTrace();
    	}
    }
    /**
     * Reward Operation, GetALL, ADD, Delete, Update 
     */
	  public List<Reward> getAllRewards() {
	        List<Reward> reward = null;
	        try {
	        	reward = getHelper().getRewardListDao().queryForAll();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return reward;
	    }
	  public void addReward(Reward r) {
			 try {
		        	getHelper().getRewardListDao().create(r);
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
			
		}
}
