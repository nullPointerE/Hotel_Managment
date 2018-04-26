package com.example.yiming.hotelmanagment.data.local;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.yiming.hotelmanagment.common.Constants;
import com.example.yiming.hotelmanagment.common.Customer;
import com.example.yiming.hotelmanagment.common.Food;
import com.example.yiming.hotelmanagment.common.Room;
import com.example.yiming.hotelmanagment.data.TasksDataSource;
import com.example.yiming.hotelmanagment.util.CheckNotNull;

import java.util.Date;

import static com.example.yiming.hotelmanagment.data.local.TasksPersistenceContract.*;
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

    public void addRoom(Room room){
        CheckNotNull.checkNotNull(room);
        SQLiteDatabase db=mDbHelper.getWritableDatabase();
        db.execSQL("insert into "+ RoomTable.TABLE_NAME+"("+RoomTable.roomNumber+","+RoomTable.status+","+RoomTable.owedByCustomer+","+RoomTable.PRICE+"," +
                        RoomTable.BEDS+","+RoomTable.expectCheckInDate+","+RoomTable.expectCheckoOutDate+","+RoomTable.actualCheckInDate+"," +
                        RoomTable.actualCheckOutDate+","+RoomTable.autoCancelDate+
                        ") values(?,?,?,?,?,?,?,?,?,?)"
                ,new Object[]{room.getRoomNumber(),room.getStatus(),room.getOwedByCustomer(),room.getPrice(),room.getBeds(),
                        0,0,0,0,0});
        db.close();
    }
    public boolean deleteRoom(int roomNumber){
        SQLiteDatabase db=mDbHelper.getWritableDatabase();
        Cursor cursor = db.query (RoomTable.TABLE_NAME,new String[]{RoomTable.status},RoomTable.roomNumber+"=?",new String[]{String.valueOf(roomNumber)},null,null,null);
        cursor.moveToFirst();

        if(cursor.isAfterLast()){
            Log.e("deleteRoom ","room numbser not exist");
            return false;  // room numbser not exist
        }
        db.execSQL("delete from "+ RoomTable.TABLE_NAME+" where "+RoomTable.roomNumber+"="+roomNumber);
        return true;
    }
    public void updateRoomInfo(Room room){
        CheckNotNull.checkNotNull(room);
        SQLiteDatabase db=mDbHelper.getWritableDatabase();
        db.execSQL("update "+ RoomTable.TABLE_NAME+" set "+RoomTable.status+"=?,"+RoomTable.owedByCustomer+"=?,"+RoomTable.PRICE+"=?," +
                        RoomTable.BEDS+"=?,"+RoomTable.expectCheckInDate+"=?,"+RoomTable.expectCheckoOutDate+"=?,"+RoomTable.actualCheckInDate+"=?," +
                        RoomTable.actualCheckOutDate+"=?,"+RoomTable.autoCancelDate+"=?"+
                        " where "+RoomTable.roomNumber+"=?"
                ,new Object[]{room.getStatus(),room.getOwedByCustomer(),room.getPrice(),room.getBeds(),
                        room.getExpectCheckInDate().getTime(),room.getExpectCheckoOutDate().getTime(),
                        room.getActualCheckInDate().getTime(),room.getActualCheckOutDate().getTime(),
                        room.getAutoCancelDate().getTime(),room.getRoomNumber()});
        db.close();
    }

    public boolean bookRoom(int roomNumber, int owedByCustomer, Date in, Date out){
        SQLiteDatabase db=mDbHelper.getWritableDatabase();
        Cursor cursor = db.query (RoomTable.TABLE_NAME,new String[]{RoomTable.status},RoomTable.roomNumber+"=?",new String[]{String.valueOf(roomNumber)},null,null,null);
        cursor.moveToFirst();

        if(cursor.isAfterLast()){
            Log.e("bookRoom ","room numbser not exist");
            return false;  // room numbser not exist
        }
        int status=cursor.getInt(cursor.getColumnIndex(RoomTable.status));
        if(status!=Constants.isVacant){
            Log.e("bookRoom ","room is booked or occupied");
            return false; // room is booked or occupied
        }

        db.execSQL("update "+ RoomTable.TABLE_NAME+" set "+RoomTable.status+"=?,"+RoomTable.owedByCustomer+"=?,"+
                        RoomTable.expectCheckInDate+"=?,"+RoomTable.expectCheckoOutDate+"=?,"+RoomTable.actualCheckInDate+"=?," +
                        RoomTable.actualCheckOutDate+"=?,"+RoomTable.autoCancelDate+"=?"+
                        " where "+RoomTable.roomNumber+"=?"
                ,new Object[]{Constants.isBooked ,owedByCustomer,in.getTime(),out.getTime(),0,0,in.getTime()+ Constants.autoCancellTime,roomNumber});

        db.close();
        return true;
    }

    public boolean roomCheckIn(int roomNumber, int owedByCustomer, Date today){
        SQLiteDatabase db=mDbHelper.getWritableDatabase();
        Cursor cursor = db.query (RoomTable.TABLE_NAME,new String[]{RoomTable.status,RoomTable.expectCheckInDate,RoomTable.expectCheckoOutDate},RoomTable.roomNumber+"=?",new String[]{String.valueOf(roomNumber)},null,null,null);
        cursor.moveToFirst();
        if(cursor.isAfterLast()){
            Log.e("roomCheckIn ","room numbser not exist");
            return false;  // room numbser not exist
        }
        int status=cursor.getInt(cursor.getColumnIndex(RoomTable.status));
        if(status!=Constants.isBooked){
            Log.e("roomCheckIn ","room is not booked");
            return false; // room is not booked
        }
        long expectCheckInDate=cursor.getInt(cursor.getColumnIndex(RoomTable.expectCheckInDate));
        if(expectCheckInDate>today.getTime()){
            Log.e("roomCheckIn ","check in earlier than expectCheckInDate");
            return false; // check in earlier than expectCheckInDate
        }
        long expectCheckoOutDate=cursor.getInt(cursor.getColumnIndex(RoomTable.expectCheckInDate));
        if(expectCheckoOutDate<today.getTime()){
            Log.e("roomCheckIn ","check in  later thant expectCheckoOutDate");
            return false; //check in  later thant expectCheckoOutDate;
        }
        db.execSQL("update "+ RoomTable.TABLE_NAME+" set "+RoomTable.status+"=?,"+RoomTable.owedByCustomer+"=?,"+
                        RoomTable.actualCheckInDate+"=?"+
                        " where "+RoomTable.roomNumber+"=?"
                ,new Object[]{Constants.isCheckIn ,owedByCustomer,today.getTime()+ Constants.autoCancellTime,roomNumber});

        db.close();
        return true;
    }

    public boolean roomCheckOut(int roomNumber, int owedByCustomer, Date today){
        SQLiteDatabase db=mDbHelper.getWritableDatabase();
        Cursor cursor = db.query (RoomTable.TABLE_NAME,new String[]{RoomTable.status},RoomTable.roomNumber+"=?",new String[]{String.valueOf(roomNumber)},null,null,null);
        cursor.moveToFirst();
        if(cursor.isAfterLast()){
            Log.e("roomCheckOut ","room numbser not exist");
            return false;  // room numbser not exist
        }
        int status=cursor.getInt(cursor.getColumnIndex(RoomTable.status));
        if(status!=Constants.isCheckIn){
            Log.e("roomCheckOut ","not checkIn yet");
            return false; // not checkIn yet
        }
        db.execSQL("update "+ RoomTable.TABLE_NAME+" set "+RoomTable.status+"=?,"+RoomTable.owedByCustomer+"=?,"+
                        RoomTable.actualCheckOutDate+"=?"+
                        " where "+RoomTable.roomNumber+"=?"
                ,new Object[]{Constants.isVacant ,owedByCustomer,today.getTime()+ Constants.autoCancellTime,roomNumber});

        db.close();
        return true;
    }

    public boolean cancelRoom(int roomNumber, int owedByCustomer, Date today){
        SQLiteDatabase db=mDbHelper.getWritableDatabase();
        Cursor cursor = db.query (RoomTable.TABLE_NAME,new String[]{RoomTable.status},RoomTable.roomNumber+"=?",new String[]{String.valueOf(roomNumber)},null,null,null);
        cursor.moveToFirst();
        if(cursor.isAfterLast()){
            Log.e("cancelRoom ","room numbser not exist");
            return false;  // room numbser not exist
        }
        int status=cursor.getInt(cursor.getColumnIndex(RoomTable.status));
        if(status!=Constants.isCheckIn){
            Log.e("cancelRoom ","not checkIn yet");
            return false; // not checkIn yet
        }
        db.execSQL("update "+ RoomTable.TABLE_NAME+" set "+RoomTable.status+"=?,"+RoomTable.owedByCustomer+"=?,"+
                        RoomTable.actualCheckOutDate+"=?"+
                        " where "+RoomTable.roomNumber+"=?"
                ,new Object[]{Constants.isVacant ,owedByCustomer,today.getTime()+ Constants.autoCancellTime,roomNumber});

        db.close();
        return true;
    }



    public void addCustomer(Customer customer){
        CheckNotNull.checkNotNull(customer);
        SQLiteDatabase db=mDbHelper.getWritableDatabase();
        db.execSQL("insert into "+ CustomerTable.TABLE_NAME+"("+CustomerTable.assignedRoom+","+CustomerTable.creditCard+","
                        +CustomerTable.roomIsGuaranteed+","+CustomerTable.numberOfCustomers+","+CustomerTable.title +","
                        +CustomerTable.firstName+","+CustomerTable.middleName+","+CustomerTable.lastName +","
                        +CustomerTable.emailAddress+","+CustomerTable.mobilePhone+","+CustomerTable.comments +","
                        +CustomerTable.gender+","+CustomerTable.companyName+","+CustomerTable.address +","
                        +CustomerTable.city+","+CustomerTable.postalCode+","+CustomerTable.country +","
                        +CustomerTable.daytimePhone+","+CustomerTable.purposeOfVisit+
                        ") values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"
                ,new Object[]{customer.getAssignedRoom(),customer.getCreditCard(),
                        customer.isRoomIsGuaranteed(),customer.getNumberOfCustomers(),customer.getTitle(),
                        customer.getFirstName(),customer.getMiddleName(),customer.getLastName(),
                        customer.getEmailAddress(),customer.getMobilePhone(),customer.getComments(),
                        customer.getGender(),customer.getCompanyName(),customer.getAddress(),
                        customer.getCity(),customer.getPostalCode(),customer.getCountry(),
                        customer.getDaytimePhone(),customer.getPurposeOfVisit()});
        db.close();
    }


    public void updateCustomer(Customer customer){
        CheckNotNull.checkNotNull(customer);
        SQLiteDatabase db=mDbHelper.getWritableDatabase();
        db.execSQL("update "+ CustomerTable.TABLE_NAME+" set "+CustomerTable.assignedRoom+"=?,"+CustomerTable.creditCard+"=?,"
                        +CustomerTable.roomIsGuaranteed+"=?,"+CustomerTable.numberOfCustomers+"=?,"+CustomerTable.title +"=?,"
                        +CustomerTable.firstName+"=?,"+CustomerTable.middleName+"=?,"+CustomerTable.lastName +"=?,"
                        +CustomerTable.emailAddress+"=?,"+CustomerTable.mobilePhone+"=?,"+CustomerTable.comments +"=?,"
                        +CustomerTable.gender+"=?,"+CustomerTable.companyName+"=?,"+CustomerTable.address +"=?,"
                        +CustomerTable.city+"=?,"+CustomerTable.postalCode+"=?,"+CustomerTable.country +"=?,"
                        +CustomerTable.daytimePhone+"=?,"+CustomerTable.purposeOfVisit+"=?"+
                        " where "+CustomerTable.customerId+"=?"
                ,new Object[]{customer.getAssignedRoom(),customer.getCreditCard(),
                        customer.isRoomIsGuaranteed(),customer.getNumberOfCustomers(),customer.getTitle(),
                        customer.getFirstName(),customer.getMiddleName(),customer.getLastName(),
                        customer.getEmailAddress(),customer.getMobilePhone(),customer.getComments(),
                        customer.getGender(),customer.getCompanyName(),customer.getAddress(),
                        customer.getCity(),customer.getPostalCode(),customer.getCountry(),
                        customer.getDaytimePhone(),customer.getPurposeOfVisit(),customer.getCustomerId()});
        db.close();
    }

    public void grantCustomerRoom(int customerId, int grantStatus){
        SQLiteDatabase db=mDbHelper.getWritableDatabase();
        db.execSQL("update "+ CustomerTable.TABLE_NAME+" set "+CustomerTable.roomIsGuaranteed+"=? where "+CustomerTable.customerId+"=?"
                ,new Integer[]{grantStatus,customerId});
        db.close();
    }

    public void addFood(Food food){
        CheckNotNull.checkNotNull(food);
        SQLiteDatabase db=mDbHelper.getWritableDatabase();
        db.execSQL("insert into "+ FoodTable.TABLE_NAME+"("+FoodTable.PRICE+","+FoodTable.foodName+","+FoodTable.foodType+","+FoodTable.status+") values(?,?,?,?)"
                ,new Object[]{food.getPrice(),food.getFoodName(),food.getFoodType(),food.isPayed()});
        db.close();
    }

    public void updateFoodStatus(Food food, int status){
        CheckNotNull.checkNotNull(food);
        SQLiteDatabase db=mDbHelper.getWritableDatabase();
        db.execSQL("update "+ FoodTable.TABLE_NAME+" set "+FoodTable.PRICE+"=?,"+FoodTable.foodName+"=?,"+FoodTable.foodType+"=?,"+FoodTable.status+"=? where "+FoodTable.foodId+"=?"
                ,new Object[]{food.getPrice(),food.getFoodName(),food.getFoodType(),status,food.getFoodId()});
        db.close();
    }

    // if you wanto order food, order complete
    public void updateFoodStatus(int foodId, int status){
        SQLiteDatabase db=mDbHelper.getWritableDatabase();
        db.execSQL("update "+ FoodTable.TABLE_NAME+" set "+FoodTable.status+"=? where "+FoodTable.foodId+"=?"
                ,new Integer[]{status,foodId});
        db.close();
    }

    public void updateFoodInfo(Food food){
        CheckNotNull.checkNotNull(food);
        SQLiteDatabase db=mDbHelper.getWritableDatabase();
        db.execSQL("update "+ FoodTable.TABLE_NAME+" set "+FoodTable.status+"=? where "+FoodTable.foodId+"=?"
                ,new Integer[]{food.isPayed(),food.getFoodId()});
        db.close();
    }

    public boolean deleteFood(int foodId){
        SQLiteDatabase db=mDbHelper.getWritableDatabase();
        Cursor cursor = db.query (FoodTable.TABLE_NAME,new String[]{FoodTable.status},FoodTable.foodId+"=?",new String[]{String.valueOf(foodId)},null,null,null);
        cursor.moveToFirst();
        if(cursor.isAfterLast()){
            Log.e("deleteFood ","food numbser not exist");
            return false;  // room numbser not exist
        }
        db.execSQL("delete from "+ FoodTable.TABLE_NAME+" where "+FoodTable.foodId+"="+foodId);
        return true;
    }

    public Cursor getRoomById(int roomNumber){
        SQLiteDatabase db=mDbHelper.getReadableDatabase();
        return db.query(RoomTable.TABLE_NAME,null,RoomTable.roomNumber+"=?",new String[]{String.valueOf(roomNumber)},null,null,null);
    }

    public Cursor getFoodById(int foodId){
        SQLiteDatabase db=mDbHelper.getReadableDatabase();
        return db.query(FoodTable.TABLE_NAME,null,FoodTable.foodId+"=?",new String[]{String.valueOf(foodId)},null,null,null);
    }
    public Cursor getCustomerById(int customerId){
        SQLiteDatabase db=mDbHelper.getReadableDatabase();
        return db.query(CustomerTable.TABLE_NAME,null,CustomerTable.customerId+"=?",new String[]{String.valueOf(customerId)},null,null,null);
    }
    public void closeDB(){
        SQLiteDatabase db=mDbHelper.getWritableDatabase();
        db.close();
    }
}

