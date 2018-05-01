package com.example.yiming.hotelmanagment.data.livedata.RoomDatabase;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.example.yiming.hotelmanagment.data.livedata.module.RoomDao;
import com.example.yiming.hotelmanagment.data.livedata.module.RoomTrans;

import java.util.List;

import io.reactivex.Flowable;

public class RoomViewModel extends AndroidViewModel {
    private LiveData<List<RoomTrans>> allRoomTrans;
    private RoomDao roomDao;

    public RoomViewModel(@NonNull Application application) {
        super(application);
        roomDao = MyRoomDataBase.getDataBase(application).roomDao();
        allRoomTrans = roomDao.getAllRoomTrans();
    }
    public LiveData<List<RoomTrans>> getAllRoomTrans(){
        return allRoomTrans;
    }

    public List<RoomTrans> getRoomTransByRoomNumber(int roomNumber){
        return roomDao.getRoomTransByRoomNumber(roomNumber);
    }

    public void bookRoom(RoomTrans roomTrans) {
        new insertAsyncTask(roomDao).execute(roomTrans);
    }

    public void checkIn(int transactionId, long today) {
        new checkInAsyncTask(roomDao).execute(transactionId, today);
    }

    public void checkOut(int transactionId) {
        new checkOutAsynctask(roomDao).execute(transactionId);
    }

    public void update(RoomTrans roomTrans) {
        new updateAsynctask(roomDao).equals(roomTrans);
    }



    private static class insertAsyncTask extends AsyncTask<RoomTrans, Void, Void> {
        private RoomDao mAsyncTaskDao;

        insertAsyncTask(RoomDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final RoomTrans... params) {
            mAsyncTaskDao.bookRoom(params[0]);
            return null;
        }
    }

    private static class checkInAsyncTask extends AsyncTask {
        private RoomDao mAsyncTaskDao;

        checkInAsyncTask(RoomDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            mAsyncTaskDao.checkIn((int) objects[0], (long) objects[1]);
            return null;
        }
    }

    private static class checkOutAsynctask extends AsyncTask<Integer, Void, Void> {
        private RoomDao mAsyncTaskDao;

        checkOutAsynctask(RoomDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            mAsyncTaskDao.checkOut(integers[0]);
            return null;
        }
    }

    private static class updateAsynctask extends AsyncTask<RoomTrans, Void, Void> {
        private RoomDao mAsyncTaskDao;

        updateAsynctask(RoomDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(RoomTrans... params) {
            mAsyncTaskDao.update(params[0]);
            return null;
        }
    }
}
