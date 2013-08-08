package com.coffeearmy.model;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable
public class Task {

	@DatabaseField(columnName = "id", generatedId=true)
    private int mId;
    
    @DatabaseField(columnName = "text")
    private String mText;
    
    @DatabaseField(columnName = "value")
    private int mValue;

    @DatabaseField(columnName = "num_done")
    private int mNumDone;
    
    @DatabaseField(columnName = "num_not_done")
    private int mNumNotDone;
    
    @ForeignCollectionField
    private ForeignCollection<Event> Event;
   
    public Task() {
		// TODO Auto-generated constructor stub
	}
    
    public void setmId(int mId) {
		this.mId = mId;
	}
    public int getmId() {
		return mId;
	}
    public void setmText(String mText) {
		this.mText = mText;
	}
    public String getmText() {
		return mText;
	}
    public void setmValue(int mValue) {
		this.mValue = mValue;
	}
    public int getmValue() {
		return mValue;
	}
    public void setmNumDone(int mNumDone) {
		this.mNumDone = mNumDone;
	}
    public int getmNumDone() {
		return mNumDone;
	}
    public void setmNumNotDone(int mNumNotDone) {
		this.mNumNotDone = mNumNotDone;
	}
    public int getmNumNotDone() {
		return mNumNotDone;
	}
    public void setEvent(ForeignCollection<Event> event) {
		Event = event;
	}
    public ForeignCollection<Event> getEvent() {
		return Event;
	}
    

}
