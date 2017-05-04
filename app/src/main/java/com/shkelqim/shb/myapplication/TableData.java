package com.shkelqim.shb.myapplication;

import android.provider.BaseColumns;

/**
 * Created by SHB on 5/4/2017.
 */

public class TableData {
    public TableData(){

    }

    public static abstract class TableInfo implements BaseColumns {

        public static final String ID = "id" ;
        public static final String LOCATION = "location" ;
        public static final String TEMPERATURE = "temperature" ;
        public static final String DESCRIPTION = "description" ;
        public static final String MAINDESCRIPT = "maindescript" ;

        public static final String DATABASE_NAME = "weather_info" ;
        public static final String TABLE_NAME = "info" ;

    }
}
