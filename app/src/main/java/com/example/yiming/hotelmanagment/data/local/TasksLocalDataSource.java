package com.example.yiming.hotelmanagment.data.local;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.yiming.hotelmanagment.data.TasksDataSource;

import static com.example.yiming.hotelmanagment.util.CheckNotNull.checkNotNull;

public class TasksLocalDataSource implements TasksDataSource {

    private static TasksLocalDataSource INSTANCE;

    private TasksDbHelper mDbHelper;

    // Prevent direct instantiation.
    private TasksLocalDataSource(@NonNull Context context) {
        checkNotNull(context);
        mDbHelper = new TasksDbHelper(context);
    }

    public static TasksLocalDataSource getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            INSTANCE = new TasksLocalDataSource(context);
        }
        return INSTANCE;
    }
}
