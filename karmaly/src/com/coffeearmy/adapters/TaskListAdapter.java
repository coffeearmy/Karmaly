package com.coffeearmy.adapters;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.coffeearmy.bd.DatabaseManager;
import com.coffeearmy.karmaly.AwardPolicy;
import com.coffeearmy.karmaly.KarmalyActivity;
import com.coffeearmy.karmaly.R;
import com.coffeearmy.karmaly.KarmalyActivity.RewardListFragment;
import com.coffeearmy.karmaly.KarmalyActivity.TaskListFragment;
import com.coffeearmy.karmaly.R.id;
import com.coffeearmy.karmaly.R.layout;
import com.coffeearmy.model.Reward;
import com.coffeearmy.model.Task;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class TaskListAdapter extends CursorAdapter {

	public static final int TEXT_INDEX = 1;
	public static final int NOT_DONE_INDEX = 2;
	public static final int DONE_INDEX = 3;
	public static final int ID_INDEX = 0;

	protected static final String TAG = "TListAdapter";

	protected Context context;
	private AwardPolicy awardPolicy;
	private Animation animIn;
	

	public static class ViewHolder {
		public TextView mNotDone;
		public TextView mDone;
		public TextView mText;
		public TextView id;
		public LinearLayout mlytNotDone;
		public LinearLayout mlytDone;
		public Integer idCode;

	}

	public TaskListAdapter(Context context, Cursor c,
			FragmentActivity fragmentActivity) {
		super(context, c);
		this.context = context;
		this.mCursor = c;
		this.awardPolicy= new AwardPolicy(fragmentActivity);
		animIn = AnimationUtils.loadAnimation(context,
			    R.animator.rotate_anim);
	}

	@Override
	public void bindView(View arg0, Context arg1, Cursor cursor) {
		ViewHolder holder = (ViewHolder) arg0.getTag();
		holder.mNotDone.setText(cursor.getString(NOT_DONE_INDEX));
		holder.mDone.setText(cursor.getString(DONE_INDEX));
		holder.mText.setText(cursor.getString(TEXT_INDEX));
		holder.idCode = cursor.getInt(ID_INDEX);
		// holder.id.setText(holder.idCode+"");
	}

	@Override
	public View newView(Context arg0, Cursor arg1, ViewGroup arg2) {
		View view = View.inflate(context, R.layout.row_tasks, null);

		ViewHolder holder = new ViewHolder();
		holder.mNotDone = (TextView) view.findViewById(R.id.txtNotDone);
		holder.mDone = (TextView) view.findViewById(R.id.txtDone);
		holder.mText = (TextView) view.findViewById(R.id.txtTask);
		// holder.id= (TextView) view.findViewById(R.id.txtid);
		holder.mlytNotDone = (LinearLayout) view.findViewById(R.id.lytNotDone);
		holder.mlytDone = (LinearLayout) view.findViewById(R.id.lytDone);
		holder.mlytNotDone.setOnClickListener(mOnNotDoneClickListener);
		holder.mlytDone.setOnClickListener(mOnDoneClickListener);

		view.setTag(holder);

		return view;
	}

	public List<Task> notifyCursorChanged() {
		this.notifyDataSetChanged();
		final List<Task> taskLists = DatabaseManager.getInstance()
				.getAllTasks();
		String[] col = new String[] { TaskListFragment.ID,
				TaskListFragment.TEXT_TASK, TaskListFragment.NOT_DONE,
				TaskListFragment.DONE };
		MatrixCursor cursor = new MatrixCursor(col);
		List<String> titles = new ArrayList<String>();
		for (Task wl : taskLists) {
			titles.add(wl.getmText());
			cursor.addRow(new Object[] { wl.getmId(), wl.getmText(),
					wl.getmNumNotDone(), wl.getmNumDone() });
		}
		this.changeCursor(cursor);
		return taskLists;
	}

	private OnClickListener mOnNotDoneClickListener = new OnClickListener() {

		public void onClick(View v) {

			int taskId = ((ViewHolder) ((View) v.getParent().getParent()
					.getParent()).getTag()).idCode;
			v.findViewById(R.id.imgNotDone).startAnimation(animIn);
			DatabaseManager.getInstance().updatePoints(false, taskId);
			notifyCursorChanged();
			awardPolicy.giveOneNotDonePoint();
			TaskListFragment.setUserInfo(2);
		}
	};

	private OnClickListener mOnDoneClickListener = new OnClickListener() {
		public void onClick(View v) {

			int taskId = ((ViewHolder) ((View) v.getParent().getParent()
					.getParent()).getTag()).idCode;
			v.findViewById(R.id.imgDone).startAnimation(animIn);
			DatabaseManager.getInstance().updatePoints(true, taskId);
			notifyCursorChanged();
			awardPolicy.giveOneDonePoint();
			TaskListFragment.setUserInfo(1);

		}

	};

}
