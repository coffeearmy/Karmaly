package com.coffeearmy.model;

import java.util.Calendar;

import android.util.Log;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
@DatabaseTable
public class User {
	 @DatabaseField(columnName = "id", generatedId=true)
	    private int mId;
	    
	    @DatabaseField(columnName = "timestamp")
	    private java.util.Date mTimestamp;

	    @DatabaseField(columnName = "done_point")
	    private int mDonePoints;
	    
	    @DatabaseField(columnName = "not_done_point")
	    private int mNotDonePoints;
	    
	    @DatabaseField(columnName = "reward_point")
	    private int mRewardPoints;
	    
	    @DatabaseField(columnName = "level")
	    private int mlvl;
	    
	    @DatabaseField(columnName = "in_row")
	    private int minRow;

	   	    
	    public User() {
			// TODO Auto-generated constructor stub
	    	this.mTimestamp=Calendar.getInstance().getTime();
		}


		public int getmId() {
			return mId;
		}


		public void setmId(int mId) {
			this.mId = mId;
		}


		public java.util.Date getmTimestamp() {
			return mTimestamp;
		}


		public void setmTimestamp(java.util.Date mTimestamp) {
			this.mTimestamp = mTimestamp;
		}


		public int getmDonePoints() {
			return mDonePoints;
		}


		public void setmDonePoints(int mDonePoints) {
			this.mDonePoints = mDonePoints;
		}


		public int getmNotDonePoints() {
			return mNotDonePoints;
		}


		public void setmNotDonePoints(int mNotDonePoints) {
			this.mNotDonePoints = mNotDonePoints;
		}


		public int getmRewardPoints() {
			return mRewardPoints;
		}


		public void setmRewardPoints(int mRewardPoints) {
			this.mRewardPoints = mRewardPoints;
		}


		public int getMlvl() {
			return mlvl;
		}


		public void setMlvl(int mlvl) {
			this.mlvl = mlvl;
		}


		public int getMinRow() {
			return minRow;
		}


		public void setMinRow(int minRow) {
			this.minRow = minRow;
		}
		
		public void userPrint(){
			Log.d("USER","DonePoints:"+this.mDonePoints+" NotDonePoints"+this.mNotDonePoints+" RewardPoints:"+this.mRewardPoints+" inrow:"+ this.minRow);
		} 
	    
	    
}
