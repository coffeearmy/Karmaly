package com.coffeearmy;

import com.coffeearmy.TaskListAdapter.ViewHolder;

import android.content.Context;
import android.database.Cursor;
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
	        // holder.done.setText(cursor.getString(DONE));
	         holder.date.setText(cursor.getString(TIMESTAMP));
	         
		}

		@Override
		public View newView(Context arg0, Cursor arg1, ViewGroup arg2) {
			View view = View.inflate(context, R.layout.row_tasks, null);

			ViewHolder holder = new ViewHolder();
			holder.date = (TextView) view.findViewById(R.id.txtTask);
	       // holder.done = (TextView) view.findViewById(R.id.txtDone);
	        

	        view.setTag(holder);

	        return view;
	    }

}
