package com.coffeearmy;

import java.util.ArrayList;
import java.util.List;

import com.coffeearmy.KarmalyActivity.TaskListFragment;
import com.coffeearmy.bd.DatabaseManager;
import com.coffeearmy.model.Task;

import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
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

	
	protected Context context;
	private Cursor mCursor;

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
  		this.mCursor=c;
  		
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
	
	
	public void notifyCursorChanged() {
		  this.notifyDataSetChanged();
		  final List<Task> taskLists = DatabaseManager.getInstance().getAllTasks();
	        String[] col = new String[] { TaskListFragment.ID, TaskListFragment.TEXT_TASK, TaskListFragment.NOT_DONE, TaskListFragment.DONE };
			MatrixCursor cursor = new MatrixCursor(col);
	        List<String> titles = new ArrayList<String>();
	        for (Task wl : taskLists) {
	            titles.add(wl.getmText());
	            cursor.addRow(new Object[] { wl.getmId(),wl.getmText(),wl.getmNumNotDone(),wl.getmNumDone()});
	        }
	        this.changeCursor(cursor);
		
	}

    private OnClickListener mOnNotDoneClickListener = new OnClickListener() {
        

		public void onClick(View v) {
        	 
           int taskId =Integer.parseInt(((ViewHolder)((View) v.getParent()).getTag()).id.getText().toString());
           DatabaseManager.getInstance().updatePoints(false, taskId);
           notifyCursorChanged();
        }
    };

    private OnClickListener mOnDoneClickListener = new OnClickListener() {
        public void onClick(View v) {
        	int taskId =Integer.parseInt(((ViewHolder)((View) v.getParent()).getTag()).id.getText().toString());
        	 DatabaseManager.getInstance().updatePoints(true, taskId);
        	 notifyCursorChanged();
             
             Log.v("msg", "OK");
        }
    
	};

	

}
