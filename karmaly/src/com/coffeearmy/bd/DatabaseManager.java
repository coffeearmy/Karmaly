package com.coffeearmy.bd;
import java.sql.SQLException;
import java.util.List;

import com.coffeearmy.model.Task;

import android.content.Context;


public class DatabaseManager {

    static private DatabaseManager sInstance;

    static public void init(Context ctx) {
        if (null==sInstance) {
        	sInstance = new DatabaseManager(ctx);
        }
    }

    static public DatabaseManager getInstance() {
        return sInstance;
    }

    private DatabaseHelper helper;
    private DatabaseManager(Context ctx) {
        helper = new DatabaseHelper(ctx);
    }

    private DatabaseHelper getHelper() {
        return helper;
    }

    public List<Task> getAllWishLists() {
        List<Task> tasks = null;
        try {
        	tasks = getHelper().getWishListDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }
}
