package com.coffeearmy.model;

import java.sql.Date;
import java.util.Calendar;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Event {
	 @DatabaseField(columnName = "id", generatedId=true)
	    private int mId;
	    
	    @DatabaseField(columnName = "timestamp")
	    private java.util.Date mTimestamp;

	    @DatabaseField(columnName = "is_done")
	    private Boolean mIsDone;

	    @DatabaseField(foreign=true,foreignAutoRefresh=true)
	    private Task list;
	    
	    public Event() {
			
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

		public void setmTimestamp(Date mTimestamp) {
			this.mTimestamp = mTimestamp;
		}

		public Boolean getmIsDone() {
			return mIsDone;
		}

		public void setmIsDone(Boolean mIsDone) {
			this.mIsDone = mIsDone;
		}

		public Task getList() {
			return list;
		}

		public void setList(Task list) {
			this.list = list;
		}
	    
	    
	
}
