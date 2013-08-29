package com.coffeearmy.adapters;

import com.coffeearmy.R;
import com.coffeearmy.R.drawable;
import com.coffeearmy.R.id;
import com.coffeearmy.R.layout;

import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DetailListAdapter extends CursorAdapter {
	public static final int DONE=1;
	public static final int TIMESTAMP=2;
	protected Context context;
	private Drawable iconSad;
	private Drawable iconHappy;
	public DetailListAdapter(Context context,  Cursor c) {
		super(context, c);
		this.context=context;
	}
	 protected static class ViewHolder {
	        public TextView date;
	        public ImageView done;
	}
	 @Override
		public void bindView(View arg0, Context arg1, Cursor cursor) {
			 ViewHolder holder  =   (ViewHolder)    arg0.getTag();
			 //By default the icon has the happy face
			 if(cursor.getString(DONE)=="false"){
				 if(iconSad==null) iconSad = context.getResources().getDrawable(R.drawable.sadicon);
				 holder.done.setImageDrawable(iconSad);
			 }else{
				 if(iconHappy==null) iconHappy = context.getResources().getDrawable(R.drawable.smileyicon);
				 holder.done.setImageDrawable(iconHappy);
			 }
	         holder.date.setText(cursor.getString(TIMESTAMP));
	         
		}

		@Override
		public View newView(Context arg0, Cursor arg1, ViewGroup arg2) {
			View view = View.inflate(context, R.layout.row_detail, null);

			ViewHolder holder = new ViewHolder();
			holder.date = (TextView) view.findViewById(R.id.txtRewardsList);
	        holder.done = (ImageView) view.findViewById(R.id.imgDetail);
	        

	        view.setTag(holder);

	        return view;
	    }

}