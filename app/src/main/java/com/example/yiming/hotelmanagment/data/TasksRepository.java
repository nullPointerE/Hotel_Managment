package com.example.yiming.hotelmanagment.data;

import android.support.annotation.NonNull;

import java.util.Map;

import static com.example.yiming.hotelmanagment.util.CheckNotNull.checkNotNull;

public class TasksRepository implements TasksDataSource {
    private static TasksRepository INSTANCE = null;
    private TasksDataSource mTasksLocalDataSource;

    Map<String, String> mCachedTasks;

    private TasksRepository(@NonNull TasksDataSource tasksLocalDataSource) {
        mTasksLocalDataSource = checkNotNull(tasksLocalDataSource);

    }

    public static TasksRepository getInstance(TasksDataSource tasksLocalDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new TasksRepository(tasksLocalDataSource);
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
