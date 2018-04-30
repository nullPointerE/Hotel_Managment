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
import com.example.yiming.hotelmanagment.common.RoomHist;
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
        db.execSQL("insert into "+ RoomTable.TABLE_NAME+"("+RoomTable.roomNumber+","+RoomTable.PRICE+"," +
                        RoomTable.BEDS+ ") values(?,?,?)"
                ,new Object[]{room.getRoomNumber(),room.getPrice(),room.getBeds()});
        db.close();
    }
    public boolean deleteRoom(int roomNumber){
        SQLiteDatabase db=mDbHelper.getWritableDatabase();
        db.execSQL("delete from "+ RoomTable.TABLE_NAME+" where "+RoomTable.roomNumber+"="+roomNumber);
        return true;
    }
    public void updateRoomInfo(Room room){
        CheckNotNull.checkNotNull(room);
        SQLiteDatabase db=mDbHelper.getWritableDatabase();
        db.execSQL("update "+ RoomTable.TABLE_NAME+" set "+RoomTable.PRICE+"=?," +
                        RoomTable.BEDS+"=? where "+RoomTable.roomNumber+"=?"
                ,new Object[]{room.getPrice(),room.getBeds(), room.getRoomNumber()});
        db.close();
    }

    public boolean bookRoom(int roomNumber, int owedByCustomer, Date in, Date out, double totalPrice){
        SQLiteDatabase db=mDbHelper.getWritableDatabase();
        Cursor cursor = db.query (RoomTable.TABLE_NAME,new String[]{RoomTable.roomNumber},RoomTable.roomNumber+"=?",new String[]{String.valueOf(roomNumber)},null,null,null);
        cursor.moveToFirst();

        if(cursor.isAfterLast()){
            Log.e("bookRoom ","room numbser not exist");
            return false;  // room numbser not exist
        }

        db.execSQL("update "+ CustomerTable.TABLE_NAME+" set "+CustomerTable.assignedRoom+"=?,"+CustomerTable.roomIsGuaranteed+"=?"+
                        " where "+CustomerTable.customerId+"=?"
                ,new Object[]{Constants.roomIsGuaranteed ,Constants.roomNotGuaranteed,owedByCustomer});

        db.execSQL("insert into "+ RoomTransaction.TABLE_NAME+" ("+RoomTransaction.status+","+RoomTransaction.owedByCustomer+","+RoomTransaction.roomNumber+","+
                        RoomTransaction.expectCheckInDate+","+RoomTransaction.expectCheckoOutDate+","+RoomTransaction.actualCheckInDate+"," +
                        RoomTransaction.actualCheckOutDate+","+RoomTransaction.autoCancelDate+","+RoomTransaction.totalPrice+")"+
                        " values (?,?,?,?,?,?,?,?,?)"
                ,new Object[]{Constants.isBooked ,owedByCustomer,roomNumber,in.getTime(),out.getTime(),0,0,
                        in.getTime()+ Constants.autoCancellTime,totalPrice});

        db.close();
        return true;
    }

    public boolean roomCheckIn(int transactionId , Date today){
        SQLiteDatabase db=mDbHelper.getWritableDatabase();
        Cursor cursor = db.query (RoomTransaction.TABLE_NAME,new String[]{RoomTransaction.status,RoomTransaction.owedByCustomer,RoomTransaction.expectCheckInDate,RoomTransaction.expectCheckoOutDate,RoomTransaction.roomNumber},RoomTransaction.transactionId+"=?",new String[]{String.valueOf(transactionId)},null,null,null);
        cursor.moveToFirst();
        if(cursor.isAfterLast()){
            Log.e("roomCheckIn ","customer didn't book this room yet");
            return false;  // room numbser not exist
        }
        int status=cursor.getInt(cursor.getColumnIndex(RoomTransaction.status));
        if(status!=Constants.isBooked){
            Log.e("roomCheckIn ","customer didn't book this room yet");
            return false; // room is not booked
        }
//        long expectCheckInDate=cursor.getInt(cursor.getColumnIndex(RoomTransaction.expectCheckInDate));
//        if(expectCheckInDate>today.getTime()){
//            Log.e("roomCheckIn ","check in earlier than expectCheckInDate");
//            return false; // check in earlier than expectCheckInDate
//        }
//        long expectCheckoOutDate=cursor.getInt(cursor.getColumnIndex(RoomTransaction.expectCheckInDate));
//        if(expectCheckoOutDate<today.getTime()){
//            Log.e("roomCheckIn ","check in  later thant expectCheckoOutDate");
//            return false; //check in  later than expectCheckoOutDate;
//        }
        String owedByCustomer=cursor.getString(cursor.getColumnIndex(RoomTransaction.owedByCustomer));
        String roomNumber=cursor.getString(cursor.getColumnIndex(RoomTransaction.roomNumber));
        db.execSQL("update "+ CustomerTable.TABLE_NAME+" set "+CustomerTable.assignedRoom+"=?,"+CustomerTable.roomIsGuaranteed+"=?"+
                        " where "+CustomerTable.customerId+"=?"
                ,new Object[]{roomNumber ,Constants.roomIsGuaranteed,owedByCustomer});

        db.execSQL("update "+ RoomTransaction.TABLE_NAME+" set "+RoomTransaction.status+"=?,"+ RoomTransaction.actualCheckInDate+"=?"+
                        " where "+RoomTransaction.transactionId+"=?"
                ,new Object[]{Constants.isCheckIn ,today.getTime(),transactionId});

        db.close();
        return true;
    }

    public boolean roomCheckOut(int transactionId, Date today){
        SQLiteDatabase db=mDbHelper.getWritableDatabase();
        Cursor cursor = db.query (RoomTransaction.TABLE_NAME,new String[]{RoomTransaction.owedByCustomer},RoomTransaction.transactionId+"=?",new String[]{String.valueOf(transactionId)},null,null,null);
        cursor.moveToFirst();
        if(cursor.isAfterLast()){
            Log.e("roomCheckOut ","customer didn't book this room yet");
            return false;  // room numbser not exist
        }
        int status=cursor.getInt(cursor.getColumnIndex(RoomTransaction.status));
        if(status!=Constants.isCheckIn){
            Log.e("roomCheckOut ","not checkIn yet");
            return false; // not checkIn yet
        }
        String owedByCustomer=cursor.getString(cursor.getColumnIndex(RoomTransaction.owedByCustomer));
        db.execSQL("update "+ CustomerTable.TABLE_NAME+" set "+CustomerTable.assignedRoom+"=?,"+CustomerTable.roomIsGuaranteed+"=?"+
                        " where "+CustomerTable.customerId+"=?"
                ,new Object[]{Constants.roomNotGuaranteed ,Constants.roomNotGuaranteed,owedByCustomer});

        db.execSQL("delete from "+ RoomTransaction.TABLE_NAME+
                        " where "+RoomTransaction.transactionId+"=?"
                ,new Object[]{transactionId});
        db.close();
        return true;
    }

    public boolean cancelRoom(int transactionId){
        SQLiteDatabase db=mDbHelper.getWritableDatabase();
        Cursor cursor = db.query (RoomTransaction.TABLE_NAME,new String[]{RoomTransaction.owedByCustomer},RoomTransaction.transactionId+"=?",new String[]{String.valueOf(transactionId)},null,null,null);
        cursor.moveToFirst();
        if(cursor.isAfterLast()){
            Log.e("cancelRoom ","customer didn't book this room yet");
            return false;  // room numbser not exist
        }
        String owedByCustomer=cursor.getString(cursor.getColumnIndex(RoomTransaction.owedByCustomer));
        db.execSQL("update "+ CustomerTable.TABLE_NAME+" set "+CustomerTable.assignedRoom+"=?,"+CustomerTable.roomIsGuaranteed+"=?"+
                        " where "+CustomerTable.customerId+"=?"
                ,new Object[]{Constants.roomNotGuaranteed ,Constants.roomNotGuaranteed,owedByCustomer});

        db.execSQL("delete from "+ RoomTransaction.TABLE_NAME+
                        " where "+RoomTransaction.transactionId+"=?"
                ,new Object[]{transactionId});
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
            room.setPrice(cursor.getDouble(cursor.getColumnIndex(RoomTable.PRICE)));
            room.setBeds(cursor.getInt(cursor.getColumnIndex(RoomTable.BEDS)));
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
            room.setPrice(cursor.getDouble(cursor.getColumnIndex(RoomTable.PRICE)));
            room.setBeds(cursor.getInt(cursor.getColumnIndex(RoomTable.BEDS)));
            roomList.add(room);
        }
        db.close();

        return roomList;
    }

    public List<RoomHist> getRoomTrasactionsList(){
        List<RoomHist> roomList=new ArrayList<>();
        SQLiteDatabase db=mDbHelper.getReadableDatabase();
        Cursor cursor=db.query(RoomTransaction.TABLE_NAME,null,null,null,null,null,null);
        for(cursor.moveToFirst(); !cursor.isAfterLast();cursor.moveToNext()){
            RoomHist roomHist=new RoomHist();
            roomHist.setTransactionId(cursor.getInt(cursor.getColumnIndex(RoomTransaction.transactionId)));
            roomHist.setRoomNumber(cursor.getInt(cursor.getColumnIndex(RoomTransaction.roomNumber)));
            roomHist.setStatus(cursor.getInt(cursor.getColumnIndex(RoomTransaction.status)));
            roomHist.setOwedByCustomer(cursor.getInt(cursor.getColumnIndex(RoomTransaction.owedByCustomer)));
            roomHist.setTotalPrice(cursor.getDouble(cursor.getColumnIndex(RoomTransaction.totalPrice)));

            roomHist.setAutoCancelDate(new Date(cursor.getLong(cursor.getColumnIndex(RoomTransaction.autoCancelDate))));
            roomHist.setExpectCheckInDate(new Date(cursor.getLong(cursor.getColumnIndex(RoomTransaction.expectCheckInDate))));
            roomHist.setExpectCheckoOutDate(new Date(cursor.getLong(cursor.getColumnIndex(RoomTransaction.expectCheckoOutDate))));
            roomHist.setActualCheckInDate(new Date(cursor.getLong(cursor.getColumnIndex(RoomTransaction.actualCheckInDate))));
            roomHist.setActualCheckOutDate(new Date(cursor.getLong(cursor.getColumnIndex(RoomTransaction.actualCheckOutDate))));
            roomList.add(roomHist);
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
    public List<RoomHist> getTransactionByRoomNumber(int roomNumber){
        SQLiteDatabase db=mDbHelper.getWritableDatabase();
        Cursor cursor = db.query (RoomTransaction.TABLE_NAME,null,RoomTransaction.roomNumber+"=?",new String[]{String.valueOf(roomNumber)},null,null,null);
        List<RoomHist> roomList=new ArrayList<>();

        for(cursor.moveToFirst(); !cursor.isAfterLast();cursor.moveToNext()){
            RoomHist roomHist=new RoomHist();
            roomHist.setTransactionId(cursor.getInt(cursor.getColumnIndex(RoomTransaction.transactionId)));
            roomHist.setRoomNumber(cursor.getInt(cursor.getColumnIndex(RoomTransaction.roomNumber)));
            roomHist.setStatus(cursor.getInt(cursor.getColumnIndex(RoomTransaction.status)));
            roomHist.setOwedByCustomer(cursor.getInt(cursor.getColumnIndex(RoomTransaction.owedByCustomer)));
            roomHist.setTotalPrice(cursor.getDouble(cursor.getColumnIndex(RoomTransaction.totalPrice)));

            roomHist.setAutoCancelDate(new Date(cursor.getLong(cursor.getColumnIndex(RoomTransaction.autoCancelDate))));
            roomHist.setExpectCheckInDate(new Date(cursor.getLong(cursor.getColumnIndex(RoomTransaction.expectCheckInDate))));
            roomHist.setExpectCheckoOutDate(new Date(cursor.getLong(cursor.getColumnIndex(RoomTransaction.expectCheckoOutDate))));
            roomHist.setActualCheckInDate(new Date(cursor.getLong(cursor.getColumnIndex(RoomTransaction.actualCheckInDate))));
            roomHist.setActualCheckOutDate(new Date(cursor.getLong(cursor.getColumnIndex(RoomTransaction.actualCheckOutDate))));
            roomList.add(roomHist);
        }
        db.close();
        return roomList;
    }

}

