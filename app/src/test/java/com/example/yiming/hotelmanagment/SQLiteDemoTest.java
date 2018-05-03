package com.example.yiming.hotelmanagment;

import android.app.Activity;
import android.content.Context;
import android.test.InstrumentationTestCase;
import android.test.mock.MockContext;

import android.util.Log;

import com.example.yiming.hotelmanagment.common.Room;
import com.example.yiming.hotelmanagment.data.local.TasksLocalDataSource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertEquals;

public class SQLiteDemoTest extends InstrumentationTestCase {
    int roomNumber=1022;
    int roomPrice=1000;
    int bed=2;
    int newBed=3;
    Room roomGet;
    Context context;
    /**
     * 测试插入数据
     */
    @Before
    public void testinsert() {
        Room room=new Room();
        room.setRoomNumber(roomNumber);
        room.setPrice(roomPrice);
        room.setBeds(newBed);
        context = new Activity();
        TasksLocalDataSource.getInstance(context).addRoom(room);
        Log.i("context ",context.toString());
        System.out.println("add room "+room.toString());
    }

    /**
     * 测试查询数据
     */
    @Test
    public void testfetchAll() {
        roomGet=TasksLocalDataSource.getInstance(context).getRoomById(roomNumber);
        assertEquals(roomGet.getBeds(),bed);
        assertEquals(roomGet.getPrice(),roomPrice);
    }

    /**
     * 测试更新数据
     */
    @After
    public void testupdate()
    {
       roomGet.setBeds(newBed);
       TasksLocalDataSource.getInstance(context).updateRoomInfo(roomGet);
       roomGet=TasksLocalDataSource.getInstance(context).getRoomById(roomNumber);
       assertEquals(roomGet.getBeds(),newBed);
    }

    /**
     * 测试删除数据
     */
    @Test
    public void testdelete() {
        TasksLocalDataSource.getInstance(context).deleteRoom(roomNumber);
        System.out.println("delete room");
    }
}
