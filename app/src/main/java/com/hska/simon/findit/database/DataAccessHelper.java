package com.hska.simon.findit.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;
import com.hska.simon.findit.model.Job;

import java.util.ArrayList;
import java.util.List;

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
            +" TEXT, "
            +COLUMN_NAME_ISFAVORITE
            +" INTEGER "
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
        public static final String COLUMN_NAME_ISFAVORITE = "isfavorite";
    }

    public void changeIsfavorite(Job job, int newValue){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_ISFAVORITE, newValue);
        String selection = COLUMN_NAME_ID + " = ?";
        String[] selectionArgs = { job.getId() + "" };

        db.update(TABLE_NAME, values, selection, selectionArgs);

        //"UPDATE " + TABLE_NAME + " SET " + COLUMN_NAME_ISFAVORITE + " = " + newValue + " WHERE " + COLUMN_NAME_ID + " = " + job.getId();
    }

    public void addJob (Job job){
        ContentValues values = new ContentValues();
        //values.put(COLUMN_NAME_ID, job.getId());
        values.put(COLUMN_NAME_TYPE, job.getType());
        values.put(COLUMN_NAME_COMPANY, job.getCompany());
        values.put(COLUMN_NAME_POSITION, job.getPosition());
        values.put(COLUMN_NAME_DESCRIPTION, job.getDescription());
        values.put(COLUMN_NAME_ISFAVORITE, job.getIsfavorite());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public List<Job> getAllJobs(){
        List<Job> allJobs = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME, null);

        if (cursor != null){
            if (cursor.moveToFirst()){
                do {
                    int id = cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_ID));
                    int type = cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_TYPE));
                    String company = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_COMPANY));
                    String position = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_POSITION));
                    String description = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_DESCRIPTION));
                    int isfavorite = cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_ISFAVORITE));
                    Log.i("---Datenbankeinträge---", id+type+company+position+description);
                    allJobs.add(new Job(id, type, company, position, description, isfavorite));
                } while (cursor.moveToNext());
            }
        }
        return allJobs;
    }

    public List<Job> getAllWorkingStudent(){
        List<Job> wsJobs = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME, null);

        if (cursor != null){
            if (cursor.moveToFirst()){
                do {
                    int type = cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_TYPE));
                    if(type == 0) {
                        int id = cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_ID));
                        String company = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_COMPANY));
                        String position = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_POSITION));
                        String description = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_DESCRIPTION));
                        int isfavorite = cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_ISFAVORITE));
                        Log.i("---Datenbankeinträge---", id + type + company + position + description);
                        wsJobs.add(new Job(id, type, company, position, description, isfavorite));
                    }
                } while (cursor.moveToNext());
            }
        }
        return wsJobs;
    }

    public List<Job> getAllInternship(){
        List<Job> internJobs = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME, null);

        if (cursor != null){
            if (cursor.moveToFirst()){
                do {
                    int type = cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_TYPE));
                    if(type == 1) {
                        int id = cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_ID));
                        String company = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_COMPANY));
                        String position = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_POSITION));
                        String description = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_DESCRIPTION));
                        int isfavorite = cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_ISFAVORITE));
                        Log.i("---Datenbankeinträge---", id + type + company + position + description);
                        internJobs.add(new Job(id, type, company, position, description, isfavorite));
                    }
                } while (cursor.moveToNext());
            }
        }
        return internJobs;
    }

    public List<Job> getAllThesis(){
        List<Job> thesisJobs = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME, null);

        if (cursor != null){
            if (cursor.moveToFirst()){
                do {
                    int type = cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_TYPE));
                    if(type == 2) {
                        int id = cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_ID));
                        String company = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_COMPANY));
                        String position = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_POSITION));
                        String description = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_DESCRIPTION));
                        int isfavorite = cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_ISFAVORITE));
                        Log.i("---Datenbankeinträge---", id + type + company + position + description);
                        thesisJobs.add(new Job(id, type, company, position, description, isfavorite));
                    }
                } while (cursor.moveToNext());
            }
        }
        return thesisJobs;
    }
}
