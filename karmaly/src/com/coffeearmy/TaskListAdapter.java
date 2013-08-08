package com.coffeearmy;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class TaskListAdapter extends CursorAdapter {
	
	public static final int TEXT_INDEX=1;
	public static final int NOT_DONE_INDEX=2;
	public static final int DONE_INDEX=3;
	public static final int ID_INDEX=0;
	
	protected static final String TAG = null;

	protected ListView mListView;
	protected Context context;

    protected static class ViewHolder {
        public TextView mNotDone;
        public TextView mDone;
        public TextView mText;
        public TextView id;
		public LinearLayout mlytNotDone;
		public LinearLayout mlytDone;
    }
    public TaskListAdapter(Context context, Cursor c) {
  		super(context, c);
  		this.context=context;  		
  	}	

	@Override
	public void bindView(View arg0, Context arg1, Cursor cursor) {
		 ViewHolder holder  =   (ViewHolder)    arg0.getTag();
         holder.mNotDone.setText(cursor.getString(NOT_DONE_INDEX));
         holder.mDone.setText(cursor.getString(DONE_INDEX));
         holder.mText.setText(cursor.getString(TEXT_INDEX));
         holder.id.setText(cursor.getString(ID_INDEX));
	}

	@Override
	public View newView(Context arg0, Cursor arg1, ViewGroup arg2) {
		View view = View.inflate(context, R.layout.row_tasks, null);

		ViewHolder holder = new ViewHolder();
		holder.mNotDone = (TextView) view.findViewById(R.id.txtNotDone);
        holder.mDone = (TextView) view.findViewById(R.id.txtDone);
        holder.mText = (TextView) view.findViewById(R.id.txtTask);
        holder.id= (TextView) view.findViewById(R.id.txtid);
        holder.mlytNotDone = (LinearLayout) view.findViewById(R.id.lytNotDone);
        holder.mlytDone = (LinearLayout) view.findViewById(R.id.lytDone);
        holder.mlytNotDone.setOnClickListener(mOnNotDoneClickListener);
        holder.mlytDone.setOnClickListener(mOnDoneClickListener);

        view.setTag(holder);

        return view;
    }

    private OnClickListener mOnNotDoneClickListener = new OnClickListener() {
        public void onClick(View v) {
        	mListView = (ListView) v.getParent().getParent(); 
            final int position = mListView.getPositionForView((View) v.getParent());
            Log.v(TAG, "Title clicked, row %d");
        }
    };

    private OnClickListener mOnDoneClickListener = new OnClickListener() {
        public void onClick(View v) {
            final int position = mListView.getPositionForView((View) v.getParent());
            Log.v(TAG, "Text clicked, row %d");
        }
    
	};

	

}
