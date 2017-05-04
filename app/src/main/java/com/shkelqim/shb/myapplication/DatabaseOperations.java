package com.shkelqim.shb.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by SHB on 5/4/2017.
 */

public class DatabaseOperations extends SQLiteOpenHelper {
    public static final int database_version = 1;
    public String CREATE_QUERY = "CREATE TABLE "+ TableData.TableInfo.TABLE_NAME + "(" + TableData.TableInfo.ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + TableData.TableInfo.LOCATION + " TEXT," + TableData.TableInfo.TEMPERATURE + " DOUBLE," + TableData.TableInfo.DESCRIPTION + " TEXT," + TableData.TableInfo.MAINDESCRIPT + " TEXT );" ;


    public DatabaseOperations(Context context) {
        super(context, TableData.TableInfo.DATABASE_NAME, null, database_version);
        SQLiteDatabase db = this.getWritableDatabase();



    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Log.d("Databaza ","Tabelen e krijoi");
        db.execSQL(CREATE_QUERY);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("Databaza ","Tabelen e krijuar edito");
    }

    public boolean insertData(String location,String descript, double temp, String mdescr){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contenti = new ContentValues();

        contenti.put(TableData.TableInfo.LOCATION,location);
        contenti.put(TableData.TableInfo.DESCRIPTION,descript);
        contenti.put(TableData.TableInfo.TEMPERATURE,temp);
        contenti.put(TableData.TableInfo.MAINDESCRIPT,mdescr);

        long rezultati = db.insert(TableData.TableInfo.TABLE_NAME,null,contenti);

        if(rezultati == -1) {
            // System.out.println("-1");
            return false;
        }else{
           // System.out.println("00");
            return true;
        }

    }

    public Cursor merri(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM "+ TableData.TableInfo.TABLE_NAME + "  WHERE   ID = (SELECT MAX(ID)  FROM "+ TableData.TableInfo.TABLE_NAME + ");", null);

        return result;
    }

}
