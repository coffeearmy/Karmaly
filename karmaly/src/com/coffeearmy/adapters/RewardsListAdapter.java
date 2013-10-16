package com.coffeearmy.adapters;

import java.util.ArrayList;
import java.util.List;

import com.coffeearmy.R;
import com.coffeearmy.KarmalyActivity.RewardListFragment;
import com.coffeearmy.KarmalyActivity.TaskListFragment;
import com.coffeearmy.R.id;
import com.coffeearmy.R.layout;
import com.coffeearmy.adapters.TaskListAdapter.ViewHolder;
import com.coffeearmy.bd.DatabaseManager;
import com.coffeearmy.model.Reward;
import com.coffeearmy.model.Task;

import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.support.v4.widget.CursorAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

public class RewardsListAdapter extends CursorAdapter {
	
	public static final int TXT_REWARD_LIST=1;
	public static final int IS_HIDDEN=2;
	public static final int RATING=3;
	public static final int ID_INDEX=0;
	
	protected static final String TAG = "TListAdapter";

	
	protected Context context;
	  public static class ViewHolder {
	        public TextView txtRewards;
	        public RatingBar rtgRewards;
			public Integer id;
	    }

	public RewardsListAdapter(Context context, Cursor c) {
		super(context, c);
		this.context=context;  
	}

	@Override
	public void bindView(View arg0, Context arg1, Cursor cursor) {
		 ViewHolder holder  =   (ViewHolder)    arg0.getTag();
		 if(cursor.getString(IS_HIDDEN)=="false"){
         holder.txtRewards.setText(cursor.getString(TXT_REWARD_LIST));    
		 }else{
			 holder.txtRewards.setText("**********");
		 }
         holder.rtgRewards.setRating(cursor.getInt(RATING));
        holder.id=cursor.getInt(ID_INDEX);

	}

	@Override
	public View newView(Context arg0, Cursor arg1, ViewGroup arg2) {
		View view = View.inflate(context, R.layout.row_rewards, null);

		ViewHolder holder = new ViewHolder();
		holder.txtRewards = (TextView) view.findViewById(R.id.txtRewardsList);
        holder.rtgRewards = (RatingBar) view.findViewById(R.id.rtbRewardsList);
        view.setTag(holder);
		return view;
	}

	public List<Reward> notifyCursorChanged() {
		  this.notifyDataSetChanged();
		  final List<Reward> rewardLists = DatabaseManager.getInstance().getAllRewards();
		  String[] col = new String[] { RewardListFragment.ID, RewardListFragment.TEXT_REWARD,RewardListFragment.HIDDEN, RewardListFragment.RATING };
			MatrixCursor cursor = new MatrixCursor(col);
	        
	        for (Reward rl : rewardLists) {
	            
	            cursor.addRow(new Object[] {rl.getmId(),rl.getmRewardText(),rl.ismIsHidden(),rl.getmValue()});
	        }
	        this.changeCursor(cursor);
		return rewardLists;
	}

}
