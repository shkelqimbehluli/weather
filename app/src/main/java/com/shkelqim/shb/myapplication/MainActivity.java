package com.shkelqim.shb.myapplication;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private static DecimalFormat df2 = new DecimalFormat(".##");
    EditText cityname ;
    String location = "";
    double temperatura ;
    String temperaturadeskrip = "";
    String maintemptekst = "";
    //London
    //api.openweathermap.org/data/2.5/weather?q=London
    final String APIKey = "http://api.openweathermap.org/data/2.5/weather?q=";
    final String APIID = "&APPID=e7c75ebafb8910969cadcaaa39517929";
    String finale = "";
    DatabaseOperations mydb ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {






        //ApiRequestt(finale);
        mydb = new DatabaseOperations(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadsqllite();
        Button searchi = (Button) findViewById(R.id.button2);


        searchi.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                new Thread(new Runnable() {
                    public void run() {

                        try {
                            double kelvin = 273.15;
                            location = ApiRequestt(APIKey).getString("name").toString() ;
                            temperatura= ApiRequestt(APIKey).getJSONObject("main").getDouble("temp") -  kelvin;

                            temperaturadeskrip = ApiRequestt(APIKey).getJSONArray("weather").getJSONObject(0).getString("description") ;
                            maintemptekst = ApiRequestt(APIKey).getJSONArray("weather").getJSONObject(0).getString("main");
                            //Log.d(ApiRequestt(APIKey).getString("name").toString(),"") ;



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ndrroemrat();
                mydb.insertData(location,temperaturadeskrip,temperatura,maintemptekst);

            }
        });
    }

    public void ndrroemrat(){

        TextView mTextView = (TextView) findViewById(R.id.textView);
        TextView mTextView1 = (TextView) findViewById(R.id.textView4);
        TextView mTextView2 = (TextView) findViewById(R.id.textView5);
        TextView mTextView3 = (TextView) findViewById(R.id.textView6);

        mTextView.setText("City Name :  " + location);
        mTextView1.setText("Temperatura :  " + temperatura);
        mTextView2.setText("Pershkrimi :  " + temperaturadeskrip);
        mTextView3.setText("Pershkrimi2 :  " + maintemptekst);


    }
    public JSONObject ApiRequestt(String APIURL){

        cityname = (EditText)findViewById(R.id.editText);
        finale = APIURL + cityname.getText() + APIID;
        System.out.println(finale);
        URLConnection urlConn = null;
        BufferedReader bufferedReader = null;
        try {
            URL url = new URL(finale);
            urlConn = url.openConnection();
            bufferedReader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
            StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line);
            }
            Log.d("JSON FORMAT :   ", stringBuffer.toString());

            return new JSONObject(stringBuffer.toString());

        }catch (Exception ex){
            Log.d("App", "yourDataTask", ex);
            return null;
        }
        finally {
            if(bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }



    }


    protected void onPostExecute(JSONObject response) {

        if(response != null) {
            try {
                Log.e("App", "Success: " + response.getString("yourJsonElement") );
            } catch (JSONException ex) {
                Log.e("App", "Failure", ex);
            }
        }
    }

    public void loadsqllite(){
        TextView m1TextView = (TextView) findViewById(R.id.textView);
        TextView m1TextView1 = (TextView) findViewById(R.id.textView4);
        TextView m1TextView2 = (TextView) findViewById(R.id.textView5);
        TextView m1TextView3 = (TextView) findViewById(R.id.textView6);

        String location1 = "";
        String descript = "";
        String mdescript = "";
        String tempi = "";
        Cursor res = mydb.merri();
                    while (res.moveToNext()){
                        location1 = res.getString(1) ;
                        tempi = res.getString(2);
                        descript = res.getString(3);
                        mdescript = res.getString(4);
                    }

        m1TextView.setText("City Name :  " + location1);
        m1TextView1.setText("Temperatura :  " + tempi);
        m1TextView2.setText("Pershkrimi :  " + descript);
        m1TextView3.setText("Pershkrimi2 :  " + mdescript);


    }
}
