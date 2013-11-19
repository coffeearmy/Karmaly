package com.coffeearmy.model;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Task {

	@DatabaseField(columnName = "id", generatedId = true)
	private int mId;

	@DatabaseField(columnName = "text")
	private String mText;

	@DatabaseField(columnName = "value")
	private int mValue;

	@DatabaseField(columnName = "num_done")
	private int mNumDone;

	@DatabaseField(columnName = "num_not_done")
	private int mNumNotDone;

	@DatabaseField(columnName = "daily")
	private boolean mIsDaily;

	@ForeignCollectionField
	private ForeignCollection<Event> Event;

	public Task() {
		this.mValue = 0;
		this.mNumDone = 0;
		this.mNumNotDone = 0;
		this.mIsDaily = false;
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

	public void setmIsDaily(boolean mIsDaily) {
		this.mIsDaily = mIsDaily;
	}

	public boolean ismIsDaily() {
		return mIsDaily;
	}

	public void updateDone() {
		this.mNumDone = this.mNumDone + 1;
	}

	public void updateNotDone() {
		this.mNumNotDone = this.mNumNotDone + 1;
	}

	public void setEvent(ForeignCollection<Event> event) {
		Event = event;
	}

	public ForeignCollection<Event> getEvent() {
		return Event;
	}

}
