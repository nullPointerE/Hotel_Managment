package com.example.yiming.hotelmanagment.data.local;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.yiming.hotelmanagment.common.Constants;
import com.example.yiming.hotelmanagment.common.Customer;
import com.example.yiming.hotelmanagment.common.Food;
import com.example.yiming.hotelmanagment.common.Room;
import com.example.yiming.hotelmanagment.data.TasksDataSource;
import com.example.yiming.hotelmanagment.util.CheckNotNull;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        db.execSQL("update "+ CustomerTable.TABLE_NAME+" set "+CustomerTable.assignedRoom+"=?,"+CustomerTable.roomIsGuaranteed+"=?"+
                        " where "+CustomerTable.customerId+"=?"
                ,new Object[]{Constants.roomIsGuaranteed ,Constants.roomNotGuaranteed,owedByCustomer});

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

        db.execSQL("update "+ CustomerTable.TABLE_NAME+" set "+CustomerTable.assignedRoom+"=?,"+CustomerTable.roomIsGuaranteed+"=?"+
                        " where "+CustomerTable.customerId+"=?"
                ,new Object[]{Constants.roomIsGuaranteed ,Constants.roomIsGuaranteed,owedByCustomer});

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
        db.execSQL("update "+ CustomerTable.TABLE_NAME+" set "+CustomerTable.assignedRoom+"=?,"+CustomerTable.roomIsGuaranteed+"=?"+
                        " where "+CustomerTable.customerId+"=?"
                ,new Object[]{Constants.roomNotGuaranteed ,Constants.roomNotGuaranteed,owedByCustomer});

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
        db.execSQL("update "+ CustomerTable.TABLE_NAME+" set "+CustomerTable.assignedRoom+"=?,"+CustomerTable.roomIsGuaranteed+"=?"+
                        " where "+CustomerTable.customerId+"=?"
                ,new Object[]{Constants.roomNotGuaranteed ,Constants.roomNotGuaranteed,owedByCustomer});

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

    public Room getRoomById(int roomNumber){
        Room room=new Room();
        SQLiteDatabase db=mDbHelper.getReadableDatabase();
        Cursor cursor=db.query(RoomTable.TABLE_NAME,null,RoomTable.roomNumber+"= ? ",new String[]{String.valueOf(roomNumber)},null,null,null);
        if(cursor.moveToFirst() && !cursor.isAfterLast()){
            room.setRoomNumber(cursor.getInt(cursor.getColumnIndex(RoomTable.roomNumber)));
            room.setStatus(cursor.getInt(cursor.getColumnIndex(RoomTable.status)));
            room.setOwedByCustomer(cursor.getInt(cursor.getColumnIndex(RoomTable.owedByCustomer)));
            room.setPrice(cursor.getDouble(cursor.getColumnIndex(RoomTable.PRICE)));
            room.setBeds(cursor.getInt(cursor.getColumnIndex(RoomTable.BEDS)));

            room.setAutoCancelDate(new Date(cursor.getLong(cursor.getColumnIndex(RoomTable.autoCancelDate))));
            room.setExpectCheckInDate(new Date(cursor.getLong(cursor.getColumnIndex(RoomTable.expectCheckInDate))));
            room.setExpectCheckoOutDate(new Date(cursor.getLong(cursor.getColumnIndex(RoomTable.expectCheckoOutDate))));
            room.setActualCheckInDate(new Date(cursor.getLong(cursor.getColumnIndex(RoomTable.actualCheckInDate))));
            room.setActualCheckOutDate(new Date(cursor.getLong(cursor.getColumnIndex(RoomTable.actualCheckOutDate))));
        }
        db.close();
        return room;
    }

    public List<Room> getRoomListByBeds(int beds){
        List<Room> roomList=new ArrayList<>();
        SQLiteDatabase db=mDbHelper.getReadableDatabase();
        Cursor cursor=db.query(RoomTable.TABLE_NAME,null,RoomTable.BEDS+"= ? ",new String[]{String.valueOf(beds)},null,null,null);
        for(cursor.moveToFirst(); !cursor.isAfterLast();cursor.moveToNext()){
            Room room=new Room();
            room.setRoomNumber(cursor.getInt(cursor.getColumnIndex(RoomTable.roomNumber)));
            room.setStatus(cursor.getInt(cursor.getColumnIndex(RoomTable.status)));
            room.setOwedByCustomer(cursor.getInt(cursor.getColumnIndex(RoomTable.owedByCustomer)));
            room.setPrice(cursor.getDouble(cursor.getColumnIndex(RoomTable.PRICE)));
            room.setBeds(cursor.getInt(cursor.getColumnIndex(RoomTable.BEDS)));

            room.setAutoCancelDate(new Date(cursor.getLong(cursor.getColumnIndex(RoomTable.autoCancelDate))));
            room.setExpectCheckInDate(new Date(cursor.getLong(cursor.getColumnIndex(RoomTable.expectCheckInDate))));
            room.setExpectCheckoOutDate(new Date(cursor.getLong(cursor.getColumnIndex(RoomTable.expectCheckoOutDate))));
            room.setActualCheckInDate(new Date(cursor.getLong(cursor.getColumnIndex(RoomTable.actualCheckInDate))));
            room.setActualCheckOutDate(new Date(cursor.getLong(cursor.getColumnIndex(RoomTable.actualCheckOutDate))));
            roomList.add(room);
        }
        db.close();

        return roomList;
    }
    public Food getFoodById(int foodId){
        Food food=new Food();
        SQLiteDatabase db=mDbHelper.getReadableDatabase();
        Cursor cursor= db.query(FoodTable.TABLE_NAME,null,FoodTable.foodId+"=?",new String[]{String.valueOf(foodId)},null,null,null);
        if(cursor.moveToFirst() && !cursor.isAfterLast()){
            food.setFoodId(cursor.getInt(cursor.getColumnIndex(FoodTable.foodId)));
            food.setFoodType(cursor.getString(cursor.getColumnIndex(FoodTable.foodType)));
            food.setFoodName(cursor.getString(cursor.getColumnIndex(FoodTable.foodName)));
            food.setPrice(cursor.getDouble(cursor.getColumnIndex(FoodTable.PRICE)));
            food.setPayed(cursor.getInt(cursor.getColumnIndex(FoodTable.status)));
        }
        db.close();
        return food;
    }
    public Customer getCustomerById(int customerId){
        Customer customer=new Customer();
        SQLiteDatabase db=mDbHelper.getReadableDatabase();
        Cursor cursor= db.query(CustomerTable.TABLE_NAME,null,CustomerTable.customerId+"=?",new String[]{String.valueOf(customerId)},null,null,null);
        if(cursor.moveToFirst() && !cursor.isAfterLast()){
            customer.setTitle(cursor.getString(cursor.getColumnIndex(CustomerTable.title)));
            customer.setFirstName(cursor.getString(cursor.getColumnIndex(CustomerTable.firstName)));
            customer.setMiddleName(cursor.getString(cursor.getColumnIndex(CustomerTable.middleName)));
            customer.setLastName(cursor.getString(cursor.getColumnIndex(CustomerTable.lastName)));
            customer.setEmailAddress(cursor.getString(cursor.getColumnIndex(CustomerTable.emailAddress)));
            customer.setGender(cursor.getString(cursor.getColumnIndex(CustomerTable.gender)));
            customer.setCompanyName(cursor.getString(cursor.getColumnIndex(CustomerTable.companyName)));
            customer.setAddress(cursor.getString(cursor.getColumnIndex(CustomerTable.address)));
            customer.setCreditCard(cursor.getString(cursor.getColumnIndex(CustomerTable.creditCard)));

            customer.setCity(cursor.getString(cursor.getColumnIndex(CustomerTable.city)));
            customer.setPostalCode(cursor.getString(cursor.getColumnIndex(CustomerTable.postalCode)));
            customer.setCountry(cursor.getString(cursor.getColumnIndex(CustomerTable.country)));
            customer.setDaytimePhone(cursor.getString(cursor.getColumnIndex(CustomerTable.daytimePhone)));
            customer.setMobilePhone(cursor.getString(cursor.getColumnIndex(CustomerTable.mobilePhone)));
            customer.setComments(cursor.getString(cursor.getColumnIndex(CustomerTable.comments)));
            customer.setPurposeOfVisit(cursor.getString(cursor.getColumnIndex(CustomerTable.purposeOfVisit)));

            customer.setCustomerId(cursor.getInt(cursor.getColumnIndex(CustomerTable.customerId)));
            customer.setNumberOfCustomers(cursor.getInt(cursor.getColumnIndex(CustomerTable.numberOfCustomers)));
            customer.setAssignedRoom(cursor.getInt(cursor.getColumnIndex(CustomerTable.assignedRoom)));
            customer.setRoomIsGuaranteed(cursor.getInt(cursor.getColumnIndex(CustomerTable.roomIsGuaranteed)));
        }
        db.close();
        return customer;
    }

    public List<Customer> getCustomerList(){
        List<Customer> customerList=new ArrayList<>();
        SQLiteDatabase db=mDbHelper.getReadableDatabase();
        Cursor cursor= db.query(CustomerTable.TABLE_NAME,null,null,null,null,null,null);
        for(cursor.moveToFirst() ;!cursor.isAfterLast();cursor.moveToNext()){
            Customer customer=new Customer();
            customer.setTitle(cursor.getString(cursor.getColumnIndex(CustomerTable.title)));
            customer.setFirstName(cursor.getString(cursor.getColumnIndex(CustomerTable.firstName)));
            customer.setMiddleName(cursor.getString(cursor.getColumnIndex(CustomerTable.middleName)));
            customer.setLastName(cursor.getString(cursor.getColumnIndex(CustomerTable.lastName)));
            customer.setEmailAddress(cursor.getString(cursor.getColumnIndex(CustomerTable.emailAddress)));
            customer.setGender(cursor.getString(cursor.getColumnIndex(CustomerTable.gender)));
            customer.setCompanyName(cursor.getString(cursor.getColumnIndex(CustomerTable.companyName)));
            customer.setAddress(cursor.getString(cursor.getColumnIndex(CustomerTable.address)));
            customer.setCreditCard(cursor.getString(cursor.getColumnIndex(CustomerTable.creditCard)));

            customer.setCity(cursor.getString(cursor.getColumnIndex(CustomerTable.city)));
            customer.setPostalCode(cursor.getString(cursor.getColumnIndex(CustomerTable.postalCode)));
            customer.setCountry(cursor.getString(cursor.getColumnIndex(CustomerTable.country)));
            customer.setDaytimePhone(cursor.getString(cursor.getColumnIndex(CustomerTable.daytimePhone)));
            customer.setMobilePhone(cursor.getString(cursor.getColumnIndex(CustomerTable.mobilePhone)));
            customer.setComments(cursor.getString(cursor.getColumnIndex(CustomerTable.comments)));
            customer.setPurposeOfVisit(cursor.getString(cursor.getColumnIndex(CustomerTable.purposeOfVisit)));

            customer.setCustomerId(cursor.getInt(cursor.getColumnIndex(CustomerTable.customerId)));
            customer.setNumberOfCustomers(cursor.getInt(cursor.getColumnIndex(CustomerTable.numberOfCustomers)));
            customer.setAssignedRoom(cursor.getInt(cursor.getColumnIndex(CustomerTable.assignedRoom)));
            customer.setRoomIsGuaranteed(cursor.getInt(cursor.getColumnIndex(CustomerTable.roomIsGuaranteed)));
            customerList.add(customer);
        }
        db.close();
        return customerList;
    }

}

