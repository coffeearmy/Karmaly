package com.coffeearmy.model;

import java.sql.Date;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Event {
	 @DatabaseField(columnName = "id", generatedId=true)
	    private int mId;
	    
	    @DatabaseField(columnName = "timestamp")
	    private Date mTimestamp;

	    @DatabaseField(columnName = "is_done")
	    private Boolean mIsDone;

	    @DatabaseField(foreign=true,foreignAutoRefresh=true)
	    private Task list;
	    
	    public Event() {
			// TODO Auto-generated constructor stub
		}

		public int getmId() {
			return mId;
		}

		public void setmId(int mId) {
			this.mId = mId;
		}

		public Date getmTimestamp() {
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
