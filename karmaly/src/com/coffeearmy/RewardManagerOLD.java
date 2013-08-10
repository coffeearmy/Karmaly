package com.coffeearmy;

import com.coffeearmy.bd.DatabaseManager;
import com.coffeearmy.model.Reward;
import com.coffeearmy.model.Task;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.RatingBar;

public class RewardManagerOLD extends FragmentActivity{

	private EditText edtRewardText;
	private RatingBar rtgComplex;
	private CheckedTextView cktHidden;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reward_manager_view);
		Button btn = (Button) findViewById(R.id.btnEdit);
		edtRewardText = (EditText) findViewById(R.id.editReward);
		rtgComplex =(RatingBar) findViewById(R.id.rtbReward);
		cktHidden = (CheckedTextView) findViewById(R.id.cktHidden);
		setupButton(btn);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void setupButton(Button btn) {
		final Context activity = this;
		btn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				String rewardDesc = edtRewardText.getText().toString();
				// Boolean daily =...
				int value = rtgComplex.getNumStars();
				boolean hidden = cktHidden.isChecked();
				if (null != rewardDesc && rewardDesc.length() > 0) {
					createReward(rewardDesc, value,hidden);

				} else {
					new AlertDialog.Builder(activity)
							.setTitle("Error")
							.setMessage("Write something!")
							.setNegativeButton("OK",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
											dialog.dismiss();
										}
									}).show();
				}
			}
		}

		);
	}

	protected void createReward(String rewardDesc, int value,boolean hidden) {
		 Reward r = new Reward();
	        r.setmRewardText(rewardDesc);
	        r.setmValue(value);
	        r.setmIsHidden(hidden);
	        DatabaseManager.getInstance().addReward(r);

	}
}
