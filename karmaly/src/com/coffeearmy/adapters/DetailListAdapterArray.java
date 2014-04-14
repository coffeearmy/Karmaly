package com.coffeearmy.adapters;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import com.coffeearmy.adapters.RewardListAdapterArray.ViewHolder;
import com.coffeearmy.bd.DatabaseManager;
import com.coffeearmy.karmaly.R;
import com.coffeearmy.model.Event;
import com.coffeearmy.model.Reward;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class DetailListAdapterArray extends ArrayAdapter<Event> {
	private Context mContext;
	private int layoutResourceId;
	private List<Event> objects;
	private Drawable iconSad;
	private Drawable iconHappy;
	public static DateFormat dateFormatH = SimpleDateFormat
			.getTimeInstance(DateFormat.MEDIUM, Locale.getDefault());
	public static DateFormat dateFormatDate = SimpleDateFormat
		.getDateInstance(DateFormat.LONG, Locale.getDefault());

	protected static class ViewHolder {
		public TextView date;
		public ImageView done;
	}

	public DetailListAdapterArray(Context context, int layoutid, List<Event> obj) {
		super(context, layoutid, obj);

		mContext = context;
		layoutResourceId = layoutid;
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

			holder.date = (TextView) convertView
					.findViewById(R.id.txtRewardsList);
			holder.done = (ImageView) convertView.findViewById(R.id.imgDetail);

			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		// By default the icon has the happy face
		if (!objects.get(position).getmIsDone()) {
			if (iconSad == null)
				iconSad = mContext.getResources().getDrawable(
						R.drawable.ic__redface);
			holder.done.setImageDrawable(iconSad);
		} else {
			if (iconHappy == null)
				iconHappy = mContext.getResources().getDrawable(
						R.drawable.ic__greenface);
			holder.done.setImageDrawable(iconHappy);
		}
		String date = dateFormatDate.format(objects.get(position)
				.getmTimestamp());
		String hour = dateFormatH.format(objects.get(position)
				.getmTimestamp());
		holder.date.setText(date+" "+hour);

		return convertView;
	}

	public void notifyListChanged(List<Event> ev) {

		this.objects.clear();
		this.objects.addAll(ev);
		this.notifyDataSetChanged();

	}
}
