package com.example.libraryprebid;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

class GetID {
    private static String url = "https://api.github.com/";
    private static String id1 ,id2;
    private static Context context;

    public GetID(Context context) {
        GetID.context = context;

        URL url1 = null;
        try {
            url1 = new URL(url);
            if (isConnected(url1)){
                new yourDataTask().execute(url);
            } else {
                new java.util.Timer().schedule(
                        new java.util.TimerTask() {
                            @Override
                            public void run() {
                                new yourDataTask().execute(url);
                            }
                        },
                        5000
                );
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    public String getId1() {
        return id1;
    }

    public String getId2() {
        return id2;
    }

    private static class yourDataTask extends AsyncTask<String, Void, String> {

        yourDataTask(){
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {

            final StringBuilder content = new StringBuilder();


            try {
                URL url1 = new URL(strings[0]);
                readContent(url1, content);
                Log.d("Test1", content.toString());

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return content.toString();
        }

        @Override
        protected void onPostExecute(final String response) {
            super.onPostExecute(response);
            getJson(response);
            Log.d("Test2",response);

        }

        @Override
        protected void onCancelled() {
            super.onCancelled();

        }

    }

    public static void setId(String id1, String id2) {
        GetID.id1 = id1;
        GetID.id2 = id2;
    }

    public static void readContent(URL url, StringBuilder content){
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(url.openConnection().getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = "";

            while ((line = bufferedReader.readLine()) != null){
                content.append(line);
            }

            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void getJson(String response){
        try {
            JSONObject jsonObject = new JSONObject(response);
            setId(jsonObject.getString("current_user_url"),
                    jsonObject.getString("current_user_authorizations_html_url"));
            Log.d("Test",response);

        } catch (JSONException e) {
            Log.e("Err",e+"");
        }
    }

    public static boolean isConnected(URL url) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();

            if (netInfo != null && netInfo.isConnected()) {
                // Network is available but check if we can get access from the network
                HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
                urlc.setRequestProperty("Connection", "close");
                urlc.setConnectTimeout(3000); // Timeout 2 seconds.
                urlc.connect();

                if (urlc.getResponseCode() == 200) // Successful response.
                {
                    return true;
                } else {
                    Log.d("NO INTERNET", "NO INTERNET");
                    return false;
                }
            } else {
                Log.d("NO INTERNET Connection", "NO INTERNET Connection");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


}
