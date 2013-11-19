package com.coffeearmy.karmaly;

import java.util.List;

import com.coffeearmy.adapters.RewardListAdapterArray;
import com.coffeearmy.bd.DatabaseManager;
import com.coffeearmy.model.Reward;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.AdapterView.OnItemLongClickListener;

public class RewardListFragment extends ListFragment {
	/**
	 * The fragment argument representing the section number for this fragment.
	 */
	// public static final String ARG_SECTION_NUMBER = "section_number";
	// public static final String ID = "_id";
	// public static final String TEXT_REWARD = "text_reward";
	// public static final String HIDDEN = "hidden";
	// public static final String RATING = "rating";

	private static EditText editTask;
	private Button btnSave;
	private static RatingBar rtbValue;
	private static CheckBox isSurprise;
	private static RewardListAdapterArray adapterCustom;
	private static List<Reward> rewardLists;
	private static LinearLayout lytCompose;
	private ImageView imgInfo;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.list_view_reward, container,
				false);
		editTask = (EditText) rootView.findViewById(R.id.etxCompReward);
		btnSave = (Button) rootView.findViewById(R.id.btnCompSaveReward);
		lytCompose = (LinearLayout) rootView
				.findViewById(R.id.lytRewardCompose);
		rtbValue = (RatingBar) rootView.findViewById(R.id.rtbReward);
		isSurprise = (CheckBox) rootView.findViewById(R.id.chbIsHidden);
		imgInfo = (ImageView) rootView.findViewById(R.id.imgInfo);
		initButton(btnSave);
		return rootView;
	}

	@Override
	public void onStart() {
		super.onStart();
		setupListView(getListView());
	}

	private void initButton(Button btnSave2) {
		imgInfo.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				new AlertDialog.Builder(getActivity())
						.setTitle(R.string.info_title)
						.setMessage(R.string.info_text).show();

			}
		});
		btnSave2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				String taskDesc = editTask.getText().toString();
				int rating = ((Float) rtbValue.getRating()).intValue();
				Boolean isHidden = isSurprise.isChecked();
				if (null != taskDesc && taskDesc.length() > 0) {
					createReward(taskDesc, rating, isHidden);
					hideLayoutCompose();
				} else {
					new AlertDialog.Builder(getActivity())
							.setTitle(android.R.string.dialog_alert_title)
							.setMessage(R.string.new_task_error)
							.setNegativeButton(android.R.string.ok,
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

	private void setupListView(ListView lv) {
		rewardLists = DatabaseManager.getInstance().getAllRewards();

		// String[] col = new String[] { ID, TEXT_REWARD, HIDDEN, RATING };
		// MatrixCursor cursor = new MatrixCursor(col);
		//
		// for (Reward rl : rewardLists) {
		//
		// cursor.addRow(new Object[] { rl.getmId(), rl.getmRewardText(),
		// rl.ismIsHidden(), rl.getmValue() });
		// }
		// setAdapterCustom(new RewardsListAdapter(this.getActivity()
		// .getApplicationContext(), cursor));
		setAdapterCustom(new RewardListAdapterArray(this.getActivity(),
				R.layout.row_rewards, rewardLists));

		lv.setOnItemLongClickListener(new OnItemLongClickListener() {

			public boolean onItemLongClick(AdapterView<?> arg0, final View v,
					int arg2, long arg3) {
				v.startAnimation(KarmalyActivity.animDelete);
				new AlertDialog.Builder(getActivity())
						.setTitle(android.R.string.dialog_alert_title)
						.setMessage(R.string.delete_reward)
						.setNegativeButton(android.R.string.cancel,
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
										dialog.dismiss();
									}
								})
						.setPositiveButton(android.R.string.ok,
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
										deleteReward(((RewardListAdapterArray.ViewHolder) v
												.getTag()).id);
										v.startAnimation(KarmalyActivity.animTranslate);
										dialog.dismiss();
									}
								}).show();
				return false;

			}
		});

		lv.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				boolean event_consumed = false;
				if (isLayoutVisible()) {
					hideLayoutCompose();
					event_consumed = true;
				}
				return event_consumed;
			}
		});
		lv.setAdapter(getAdapterCustom());

	}

	protected void createReward(String taskDesc, int rating2, Boolean isHidden) {
		Reward r = new Reward();
		r.setmRewardText(taskDesc);
		r.setmValue(rating2);
		r.setmIsHidden(isHidden);
		DatabaseManager.getInstance().addReward(r);
		notifyListChanged();
	}

	public static void notifyListChanged() {
		rewardLists = getAdapterCustom().notifyListChanged();
	}

	public static void hideLayoutCompose() {
		if (isLayoutVisible()) {
			lytCompose.setVisibility(View.GONE);
			// Clear the form
			editTask.setText("");
			rtbValue.setRating(0);
			isSurprise.setChecked(false);
		}
	}

	public static void showLayoutCompose() {
		if (!isLayoutVisible())
			lytCompose.setVisibility(View.VISIBLE);
	}

	public static Boolean isLayoutVisible() {
		return lytCompose.isShown();
	}

	private void deleteReward(Integer id) {
		DatabaseManager.getInstance().deleteReward(id);
		notifyListChanged();

	}

	public static RewardListAdapterArray getAdapterCustom() {
		return adapterCustom;
	}

	public static void setAdapterCustom(
			RewardListAdapterArray rewardListAdapterArray) {
		RewardListFragment.adapterCustom = rewardListAdapterArray;
	}
}
