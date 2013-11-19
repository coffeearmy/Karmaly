package com.coffeearmy.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Reward {

	@DatabaseField(columnName = "id", generatedId = true)
	private int mId;

	@DatabaseField(columnName = "reward_text")
	private String mRewardText;

	@DatabaseField(columnName = "value")
	private int mValue;

	@DatabaseField(columnName = "is_hidden")
	private boolean mIsHidden;

	public int getmId() {
		return mId;
	}

	public void setmId(int mId) {
		this.mId = mId;
	}

	public String getmRewardText() {
		return mRewardText;
	}

	public void setmRewardText(String mRewardText) {
		this.mRewardText = mRewardText;
	}

	public int getmValue() {
		return mValue;
	}

	public void setmValue(int mValue) {
		this.mValue = mValue;
	}

	public boolean ismIsHidden() {
		return mIsHidden;
	}

	public void setmIsHidden(boolean mIsHidden) {
		this.mIsHidden = mIsHidden;
	}

}
