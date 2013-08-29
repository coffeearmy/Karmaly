package com.coffeearmy.bd;
import java.sql.SQLException;
import java.util.List;

import com.coffeearmy.model.Event;
import com.coffeearmy.model.Reward;
import com.coffeearmy.model.Task;
import com.coffeearmy.model.User;

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
        	tasks = getHelper().getTaskListDao().queryBuilder().orderBy("id", false).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }
    
    public Task getTask(Integer mID) {
    	try{
    		List<Task> results = getHelper().getTaskListDao().queryBuilder().where().eq("id",mID).query();
    		if(results.size()>0){
    			///TODO add events here
    			Task task = results.get(0);
    			return task;
    		}
    	}catch(SQLException e){
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
    public void updateTask(Task t){
    	try{
    		getHelper().getTaskListDao().update(t);
    	}catch(SQLException e){
    		 e.printStackTrace();
    	}
    }
    public void updatePoints(Boolean done,int t){
    	try{
    		List<Task> results = getHelper().getTaskListDao().queryBuilder().where().eq("id",t).query();
    		if(results.size()>0){
    			///TODO add events here
    			Task task = results.get(0);
    			Event event = new Event();
    			event.setmIsDone(done);
    			event.setList(task);
    			if(done){    				
    				task.updateDone();
    			}else{    				
    				task.updateNotDone();
    			}
    			addEvent(event);
    			updateTask(task);
    		}
    	}catch(SQLException e){
    		 e.printStackTrace();
    	}
    }
    public void deleteTask(int t){
    	try{
    		List<Task> results = getHelper().getTaskListDao().queryBuilder().where().eq("id",t).query();
    		if(results.size()>0) getHelper().getTaskListDao().delete(results.get(0));
    		///TODO All the events with task=task have to be delete as well.
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
	        	reward = getHelper().getRewardListDao().queryBuilder().orderBy("id", false).query();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return reward;
	    }
	  public List<Reward> getAllRewardsRated(boolean asc) {
	        List<Reward> reward = null;
	        try {
	        	reward = getHelper().getRewardListDao().queryBuilder().orderBy("value", asc).query();
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
	  public void deleteReward(int t){
	    	try{
	    		List<Reward> results = getHelper().getRewardListDao().queryBuilder().where().eq("id",t).query();
	    		if(results.size()>0) getHelper().getRewardListDao().delete(results.get(0));
	    		///TODO All the events with task=task have to be delete as well.
	    	}catch(SQLException e){
	    		 e.printStackTrace();
	    	}
	    }
	  /**
	     * Events Operation, GetALL, ADD, Delete, Update 
	 * @param mID 
	     */
	  public List<Event> getAllEvents(Integer mID) {
	        List<Event> events = null;
	        try {
	        	List<Task> results = getHelper().getTaskListDao().queryBuilder().where().eq("id",mID).query();
	        	if(results.size()>0){
	    			
	    			Task task = results.get(0);
	    			events = getHelper().getEventListDao().queryBuilder().where().eq("list_id",mID).query();
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
	  
	  /**
	   * User class operation
	   */
	  public User getUser() {
	        List<User> users = null;
	        try {
	        	users = getHelper().getUserDao().queryForAll();
	        	if(users.size()>0){
	    			
	    			User user = users.get(0);
	    			return user;
	        	}else{
	        		User u = new User();
	        		addUser(u);
	        		return u;
	        	}
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return null;
	    }
	  
	  public void addUser(User u){
		  try {
	        	getHelper().getUserDao().create(u);
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	  }
	  
	  public void updateUser(User u){
		  try {
	        	getHelper().getUserDao().update(u);
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	  }

	
	  
	
}
