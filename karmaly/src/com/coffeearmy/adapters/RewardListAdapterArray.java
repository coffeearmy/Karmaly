package com.coffeearmy.adapters;

import java.util.List;

import com.coffeearmy.adapters.TaskListAdapterArray.ViewHolder;
import com.coffeearmy.bd.DatabaseManager;
import com.coffeearmy.karmaly.R;
import com.coffeearmy.model.Reward;
import com.coffeearmy.model.Task;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

public class RewardListAdapterArray extends ArrayAdapter<Reward> {
	private Context mContext;
	private int layoutResourceId;
	private List<Reward> objects;

	public static class ViewHolder {
		public TextView txtRewards;
		public RatingBar rtgRewards;
		public Integer id;
	}

	public RewardListAdapterArray(Context context, int layoutId,
			List<Reward> obj) {
		super(context, layoutId, obj);
		mContext = context;
		layoutResourceId = layoutId;
		objects = obj;
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

			holder.txtRewards = (TextView) convertView
					.findViewById(R.id.txtRewardsList);
			holder.rtgRewards = (RatingBar) convertView
					.findViewById(R.id.rtbRewardsList);
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		if (!objects.get(position).ismIsHidden()) {
			holder.txtRewards.setText(objects.get(position).getmRewardText());
		} else {
			holder.txtRewards.setText("*************");
		}
		holder.rtgRewards.setRating(objects.get(position).getmValue());
		holder.id = objects.get(position).getmId();

		return convertView;
	}

	public List<Reward> notifyListChanged() {

		final List<Reward> rewardLists = DatabaseManager.getInstance()
				.getAllRewards();

		this.objects.clear();
		this.objects.addAll(rewardLists);
		this.notifyDataSetChanged();
		return rewardLists;
	}

}
