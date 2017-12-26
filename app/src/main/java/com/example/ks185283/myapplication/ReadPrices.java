package com.example.ks185283.myapplication;

/**
 * Created by ks185283 on 12/23/2017.
 */

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ReadPrices extends AsyncTask<Void,Void,Void> {
    String data ="";
    String goldPrices = "";

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            URL urll = new URL("https://cws.hellogold.com/api/v2/spot_price.json");
            HttpURLConnection httpURLConnection = (HttpURLConnection) urll.openConnection();
            httpURLConnection.setRequestProperty("User-Agent", "Chrome");
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String line = "";
            while(line != null){
                line = bufferedReader.readLine();
                if(line!=null){
                    data = data + line;
                }
            }

            JSONObject json = new JSONObject(data);
            String buy=json.getJSONObject("data").get("buy").toString();
            String sell=json.getJSONObject("data").get("sell").toString();
            String timestamp=json.getJSONObject("data").get("timestamp").toString();

            goldPrices = " Buy:"+buy +"\n Sell:"+sell+ "\n Time:-"+timestamp;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        MainActivity.data.setText(this.goldPrices);

    }
}