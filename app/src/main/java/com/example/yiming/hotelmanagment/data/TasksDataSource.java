package com.example.yiming.hotelmanagment.data;

public interface TasksDataSource {
    interface LoadTasksCallback {

        void onTasksLoaded();

        void onDataNotAvailable();
    }

    interface GetTaskCallback {

        void onTaskLoaded();

        void onDataNotAvailable();
    }


}
