package com.shkelqim.shb.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {

    EditText cityname ;
    //London
    //api.openweathermap.org/data/2.5/weather?q=London
    final String APIKey = "http://api.openweathermap.org/data/2.5/weather?q=";
    final String APIID = "&APPID=e7c75ebafb8910969cadcaaa39517929";
    String finale = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        //ApiRequestt(finale);
        loadsqllite();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void klikimi (View view){
        new Thread(new Runnable() {
            public void run() {
                ApiRequestt(APIKey);
            }
        }).start();

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
           // Log.d("JSON FORMAT :   ", stringBuffer.toString());

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

    }
}