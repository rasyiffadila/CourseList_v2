package com.example.courselist.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.courselist.Util.Config;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static DatabaseHelper databaseHelper;

    // All Static variables
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = Config.DATABASE_NAME;

    // Constructor
    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    public static synchronized DatabaseHelper getInstance(Context context){
        if(databaseHelper==null){
            databaseHelper = new DatabaseHelper(context);
        }
        return databaseHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Create tables SQL execution
        String CREATE_STUDENT_TABLE = "CREATE TABLE " + Config.TABLE_STUDENT + "("
                + Config.COLUMN_STUDENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Config.COLUMN_STUDENT_NAME + " TEXT NOT NULL, "
                + Config.COLUMN_STUDENT_REGISTRATION + " TEXT(15) NOT NULL UNIQUE, "
                + Config.COLUMN_STUDENT_PHONE + " TEXT(15), " //nullable
                + Config.COLUMN_STUDENT_EMAIL + " TEXT " //nullable
                + ")";

        String CREATE_SUBJECT_TABLE = "CREATE TABLE " + Config.TABLE_SUBJECT + "("
                + Config.COLUMN_SUBJECT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Config.COLUMN_SUBJECT_NAME + " TEXT NOT NULL, "
                + Config.COLUMN_SUBJECT_CODE + " TEXT(15) NOT NULL UNIQUE, "
                + Config.COLUMN_SUBJECT_CREDIT + " INTEGER NOT NULL "
                + ")";

        String CREATE_TAKEN_SUBJECT_TABLE = "CREATE TABLE " + Config.TABLE_TAKEN_SUBJECT + "("
                + Config.COLUMN_TAKEN_SUBJECT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Config.COLUMN_STUDENT_ID_TAKEN + " INTEGER NOT NULL, "
                + Config.COLUMN_SUBJECT_ID_TAKEN + " INTEGER NOT NULL, "
                + "CONSTRAINT fk_column_1 "
                + "FOREIGN KEY ("+Config.COLUMN_STUDENT_ID_TAKEN+") REFERENCES "+Config.TABLE_STUDENT+"("+Config.COLUMN_STUDENT_ID+") ON DELETE CASCADE, "
                + "CONSTRAINT fk_column_2 "
                + "FOREIGN KEY ("+Config.COLUMN_SUBJECT_ID_TAKEN+") REFERENCES "+Config.TABLE_SUBJECT+"("+Config.COLUMN_SUBJECT_ID+") ON DELETE CASCADE "
                + ")";

        Logger.d("Table create SQL: " + CREATE_STUDENT_TABLE);
        Logger.d("Table create SQL: " + CREATE_SUBJECT_TABLE);
        Logger.d("Table create SQL: " + CREATE_TAKEN_SUBJECT_TABLE);

        db.execSQL(CREATE_STUDENT_TABLE);
        db.execSQL(CREATE_SUBJECT_TABLE);
        db.execSQL(CREATE_TAKEN_SUBJECT_TABLE);

        Logger.d("DB created!");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Config.TABLE_STUDENT);
        db.execSQL("DROP TABLE IF EXISTS " + Config.TABLE_SUBJECT);
        db.execSQL("DROP TABLE IF EXISTS " + Config.TABLE_TAKEN_SUBJECT);
        // Create tables again
        onCreate(db);
    }

}