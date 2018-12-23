package com.hska.simon.findit.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;

import static com.hska.simon.findit.database.DataAccessHelper.JobEntry.*;

public class DataAccessHelper extends SQLiteOpenHelper{

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "jobs.db";

    public DataAccessHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static final String CREATE_TABLE_JOB = "CREATE TABLE "
            +TABLE_NAME
            +" ("
            +COLUMN_NAME_ID
            +" INTEGER PRIMARY KEY, "
            +COLUMN_NAME_TYPE
            +" INTEGER, "
            +COLUMN_NAME_COMPANY
            +" TEXT, "
            +COLUMN_NAME_POSITION
            +" TEXT, "
            +COLUMN_NAME_DESCRIPTION
            +" TEXT"
            +");";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_JOB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    public static class JobEntry implements BaseColumns{
        public static final String TABLE_NAME = "job";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_TYPE = "type";
        public static final String COLUMN_NAME_COMPANY = "company";
        public static final String COLUMN_NAME_POSITION = "position";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
    }

    public void addJob (Job job){
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_ID, job.getID());
        values.put(COLUMN_NAME_TYPE, job.getType());
        values.put(COLUMN_NAME_COMPANY, job.getCompany());
        values.put(COLUMN_NAME_POSITION, job.getPosition());
        values.put(COLUMN_NAME_DESCRIPTION, job.getDescription());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public List<Job> getAllJobs(){
        List<Job> allJobs = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.DrawQuery("select * from " + TABLE_NAME, null);

        if (cursor != null){
            if (cursor.moveToFirst()){
                do {
                    int id = cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_ID));
                    String type = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_TYPE));
                    String company = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_COMPANY));
                    String position = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_POSITION));
                    String description = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_DESCRIPTION));
                    allJobs.add(new Job(id, type, company, position, description));
                } while (cursor.moveToNext());
            }
        }
        return allJobs;
    }
}
