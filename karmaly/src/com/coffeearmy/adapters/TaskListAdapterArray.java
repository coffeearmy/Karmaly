package com.coffeearmy.adapters;

import java.util.ArrayList;
import java.util.List;

import com.coffeearmy.bd.DatabaseManager;
import com.coffeearmy.karmaly.AwardPolicy;
import com.coffeearmy.karmaly.KarmalyActivity;
import com.coffeearmy.karmaly.R;
import com.coffeearmy.karmaly.TaskListFragment;
import com.coffeearmy.model.Task;

import android.app.Activity;
import android.content.Context;
import android.database.MatrixCursor;

import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TaskListAdapterArray extends ArrayAdapter<Task> {

	private Context mContext;
	private int layoutResourceId;
	private List<Task> objects;
	private AwardPolicy awardPolicy;

	public static class ViewHolder {
		public TextView mNotDone;
		public TextView mDone;
		public TextView mText;
		public TextView id;
		public ImageView mDoneImg;
		public ImageView mNotDoneImg;
		public LinearLayout mlytNotDone;
		public LinearLayout mlytDone;
		public Integer idCode;

	}

	public TaskListAdapterArray(Context context, int layoutId, List<Task> obj,
			FragmentActivity fragmentActivity) {
		super(context, layoutId, obj);
		mContext = context;
		layoutResourceId = layoutId;
		objects = obj;
		awardPolicy = this.awardPolicy = new AwardPolicy(fragmentActivity);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		/*
		 * The convertView argument is essentially a "ScrapView" as described is
		 * Lucas post
		 * http://lucasr.org/2012/04/05/performance-tips-for-androids-listview/
		 * It will have a non-null value when ListView is asking you recycle the
		 * row layout. So, when convertView is not null, you should simply
		 * update its contents instead of inflating a new row layout.
		 */
		if (convertView == null) {
			// inflate the layout
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			convertView = inflater.inflate(layoutResourceId, parent, false);
			holder = new ViewHolder();
			holder.mNotDone = (TextView) convertView
					.findViewById(R.id.txtNotDone);
			holder.mDone = (TextView) convertView.findViewById(R.id.txtDone);
			holder.mText = (TextView) convertView.findViewById(R.id.txtTask);
			holder.mlytNotDone = (LinearLayout) convertView
					.findViewById(R.id.lytNotDone);
			holder.mlytDone = (LinearLayout) convertView
					.findViewById(R.id.lytDone);
			holder.mNotDoneImg = (ImageView) convertView
					.findViewById(R.id.imgNotDone);
			holder.mDoneImg = (ImageView) convertView
					.findViewById(R.id.imgDone);
			holder.mlytDone = (LinearLayout) convertView
					.findViewById(R.id.lytDone);
			holder.mlytNotDone.setOnClickListener(mOnNotDoneClickListener);
			holder.mlytDone.setOnClickListener(mOnDoneClickListener);

			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.mNotDone.setText(objects.get(position).getmNumNotDone() + "");
		holder.mDone.setText(objects.get(position).getmNumDone() + "");
		holder.mText.setText(objects.get(position).getmText());
		holder.idCode = objects.get(position).getmId();

		return convertView;
	}

	public List<Task> notifyListChanged() {

		final List<Task> taskLists = DatabaseManager.getInstance()
				.getAllTasks();

		this.objects.clear();
		this.objects.addAll(taskLists);
		this.notifyDataSetChanged();
		return taskLists;
	}

	private OnClickListener mOnNotDoneClickListener = new OnClickListener() {
		public void onClick(View v) {

			int taskId = ((ViewHolder) ((View) v.getParent().getParent()
					.getParent()).getTag()).idCode;
			// v.findViewById(R.id.imgNotDone).startAnimation(KarmalyActivity.animIn);
			DatabaseManager.getInstance().updatePoints(false, taskId);
			notifyListChanged();
			awardPolicy.giveOneNotDonePoint();
			TaskListFragment.setUserInfo(2);
		}
	};

	private OnClickListener mOnDoneClickListener = new OnClickListener() {
		public void onClick(View v) {

			int taskId = ((ViewHolder) ((View) v.getParent().getParent()
					.getParent()).getTag()).idCode;

			// ((ViewHolder) ((View) v.getParent().getParent()
			// .getParent()).getTag()).mDoneImg.startAnimation(KarmalyActivity.animIn);
			DatabaseManager.getInstance().updatePoints(true, taskId);
			notifyListChanged();
			awardPolicy.giveOneDonePoint();
			TaskListFragment.setUserInfo(1);

		}

	};
}
