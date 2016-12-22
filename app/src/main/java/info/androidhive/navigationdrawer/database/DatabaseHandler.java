package info.androidhive.navigationdrawer.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import info.androidhive.navigationdrawer.model.User;

/**
 * Created by zeesh on 12/22/2016.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    private SQLiteDatabase database;
    private DatabaseHandler dbHelper;
    private static final int DATABASE_VERSION = 3;

    private static final String DATABASE_NAME = "usersManage",
            TABLE_USER = "users", KEY_ID = "id", KEY_NAME = "name",
            KEY_HOMETOWN = "hometown",
            KEY_USERNAME = "username",
            KEY_DATEOFBIRTH = "dateofbirth",
            KEY_AGE = "age",
            KEY_PHONE = "phone",
            KEY_IMAGE = "image",
            KEY_FPHONE = "fatherphone",
            KEY_TV= "television";

    private static final String DATABASE_ALTER = "ALTER TABLE "
            + TABLE_USER + " ADD COLUMN " + KEY_TV + " TEXT;";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = " CREATE TABLE " + TABLE_USER + "(" + KEY_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_NAME + " TEXT, "
                + KEY_HOMETOWN + " TEXT, " + KEY_USERNAME + " TEXT NOT NULL UNIQUE, " + KEY_DATEOFBIRTH + " TEXT, " + KEY_AGE + " TEXT, " + KEY_PHONE + " TEXT, " + KEY_FPHONE + " TEXT, " + KEY_TV + " TEXT, "+ KEY_IMAGE + " TEXT);";

        Log.e(getClass().getName(), "Query is: " + query);

        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion<3){
            db.execSQL(DATABASE_ALTER);
        }
    }

    public void createUser(User user) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_NAME, user.getName());
        values.put(KEY_HOMETOWN, user.getHometown());
        values.put(KEY_USERNAME, user.getUsername());
        values.put(KEY_DATEOFBIRTH, user.getDob());
        values.put(KEY_AGE, user.getAge());
        values.put(KEY_PHONE, user.getPhone());
        values.put(KEY_IMAGE, user.getImageUri());
        values.put(KEY_FPHONE, user.getFphone());
        values.put(KEY_TV, user.getTv());


        db.insert(TABLE_USER, null, values);

        //      String query = "SELECT * FROM " + TABLE_USER + " WHERE " + KEY_USERNAME + " = " + user.getUsername() ;

//        db.execSQL(query);

        // int inset = db.insert(1);
        //Log.e(getClass().getName(), ""+ db.insert() );
        Log.e(getClass().getName(), "User Name is = " + user.getName());
        Log.e(getClass().getName(), "User ID is = " + user.getId());
        Log.e(getClass().getName(), "User PATH is = " + user.getImageUri());


        db.close();

    }

    public void update(User user) {
        // TODO Auto-generated method stub
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_NAME, user.getName());
        values.put(KEY_HOMETOWN, user.getHometown());
        values.put(KEY_USERNAME, user.getUsername());
        values.put(KEY_DATEOFBIRTH, user.getDob());
        values.put(KEY_AGE, user.getAge());
        values.put(KEY_PHONE, user.getPhone());
        values.put(KEY_IMAGE, user.getImageUri());
        values.put(KEY_FPHONE, user.getFphone());
        values.put(KEY_TV, user.getTv());


        Log.e(getClass().getName(), "User NAME is = " + user.getName());
        Log.e(getClass().getName(), "User ID = " + user.getId());
        Log.e(getClass().getName(), "User PATH = " + user.getImageUri());

        String query = "SELECT * FROM " + TABLE_USER + " WHERE " + KEY_USERNAME + " = '" + user.getUsername()+"'" ;

        Cursor c = db.rawQuery(query, null);
        if (c != null && c.moveToFirst()) {
            // do your stuff
        }

        // updating row
        db.update(TABLE_USER, values, KEY_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }

    public ArrayList<User> getAllUsers() {

        ArrayList<User> users = new ArrayList<User>();
        SQLiteDatabase db = getWritableDatabase();


        String allusers = "SELECT * FROM " + TABLE_USER;
        Cursor cursor = db.rawQuery(allusers, null);
        Log.e(getClass().getName(), "Query is: " + allusers);
        if (cursor.moveToFirst()) {
            do {
                User user = new User(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9));


                users.add(user);
                // Log.e(getClass().getName(), "User Entered" + user);
            } while (cursor.moveToNext());

        }
        return users;
    }

    public User getUser(User user) {
        SQLiteDatabase db = getReadableDatabase();

//        User user = new User();
        String query = "SELECT * FROM " + TABLE_USER + " WHERE " + KEY_USERNAME + " = '" + user.getUsername()+"'" ;

        Cursor c = db.rawQuery(query, null);
        if (c != null && c.moveToFirst()) {
            // do your stuff
        }


        Cursor cursor = db.query(TABLE_USER, new String[]{KEY_ID, KEY_NAME, KEY_HOMETOWN, KEY_USERNAME, KEY_DATEOFBIRTH, KEY_AGE, KEY_PHONE, KEY_IMAGE, KEY_FPHONE, KEY_TV},
                KEY_ID + "=?", new String[]{String.valueOf(user.getId())}, null, null,
                null, null);

        if (cursor != null)
            cursor.moveToFirst();

        user = new User(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9));

        // Log.e(getClass().getName(), " " + user);

        db.close();
        cursor.close();

        return user;
    }


    public void delete(User user) {
        // TODO Auto-generated method stub
        //        boolean deleteSuccessful = false;

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USER, KEY_ID + " = ? ", new String[]{String.valueOf(user.getId())});
        db.close();


    }

    public boolean CheckIsDataAlreadyInDBorNot(String username) {

        SQLiteDatabase db = getWritableDatabase();

        String Query = "Select * from " + TABLE_USER + " where " + KEY_USERNAME + " = '" +username+"'";
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

}