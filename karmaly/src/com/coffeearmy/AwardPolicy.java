package com.coffeearmy;

import java.util.Date;
import java.util.List;
import java.util.Random;

import android.app.Dialog;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.coffeearmy.KarmalyActivity.RewardListFragment;
import com.coffeearmy.bd.DatabaseManager;
import com.coffeearmy.model.Reward;
import com.coffeearmy.model.User;

public class AwardPolicy {

	private User u;
	private Context mfragment;
	private Random random;

	public AwardPolicy(FragmentActivity fragmentActivity) {
		this.mfragment = fragmentActivity;
		u= DatabaseManager.getInstance().getUser();
		random = new Random();
	}

	public void getAward() {
		try {
			int num_in_row = u.getMinRow();
			List<Reward> r = DatabaseManager.getInstance().getAllRewardsRated(true);
			int size = r.size();
			int max = size - 1;
			int min = 0;
			int randomReward = 0;
			if (size > 0) {
				if (num_in_row < 5) {
					// Low rate
					max = (int) (max * 0.3);
					min = 0;
					randomReward = random.nextInt(max - min + 1) + min;
				} else {
					if (num_in_row < 10) {
						max = (int) (max * 0.7);
						min = (int) (max * 0.3);
						randomReward = random.nextInt(max - min + 1) + min;
					} else {
						// highrate
						max = size - 1;
						min = (int) (max * 0.7);
						randomReward = random.nextInt(max - min + 1) + min;
					}
				}
				if (randomReward <= size) {
					showModalReward(r.get(randomReward));
				}
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}

	}

	/**
	 * When is a Award given ========================= + Task level - When the
	 * done points are 1/3 more than not done points = 1 reward point - When you
	 * make a daily done =+5 reward points - The first not done automatically 1
	 * minus reward - The first done high reward - Depending the in row
	 * parameter the user will be awarded with a better reward
	 */
	public void givePoints() {
		int points = 0;
		if (u.getmDonePoints() > (u.getmNotDonePoints() * 1.33)) {
			points = +1;
		}
		//Long aux = u.getmTimestamp().getTime();
	//	Date newDate = new Date(aux);
		// if() Compare date if was 1 day ago the last time +5 if its in the
		// same hour no points will be given,
		// I'ts time for a reward
		if (u.getmRewardPoints() + points >= 10) { // 10 for now, later with
													// levels
			getAward();
			u.setmRewardPoints((u.getmRewardPoints() + points) - 10);
		
		} else {
			if (points > 0) {
				u.setmRewardPoints(u.getmRewardPoints() + points);
				
			}
		}

	}

	public void giveOneDonePoint() {
		
		u.setmDonePoints(u.getmDonePoints() + 1);
		u.setMinRow(u.getMinRow()+1);
		givePoints();
		u.userPrint();
		DatabaseManager.getInstance().updateUser(u);
	}

	public void giveOneNotDonePoint() {
	
		u.setmNotDonePoints(u.getmNotDonePoints() + 1);
		u.setMinRow(0);
		
		DatabaseManager.getInstance().updateUser(u);
	}

	public void showModalReward(Reward r) {
		final Dialog dialog = new Dialog(mfragment);
		dialog.setContentView(R.layout.dialog_reward);
		TextView textSmall = (TextView) dialog.findViewById(R.id.txtDescDialog);
		
		if (r.ismIsHidden()) {
			r.setmIsHidden(false);
			DatabaseManager.getInstance().updateReward(r);
			textSmall.setText(R.string.hiddenreward);
			RewardListFragment.notifyCursorChanged();
		} else {
			textSmall.setText(R.string.reward);
		}
		dialog.setTitle(R.string.congratulations);
		// set the custom dialog components - text, image and button
		TextView text = (TextView) dialog.findViewById(R.id.txtDialog);
		RatingBar ratingBar= (RatingBar) dialog.findViewById(R.id.rtbDialog);
		
		text.setText(r.getmRewardText());
		ratingBar.setRating(r.getmValue());
		Button dialogButton = (Button) dialog.findViewById(R.id.btnOkDialog);
		// if button is clicked, close the custom dialog
		dialogButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

		dialog.show();
	}
}
