package com.example.project_weatherforecast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpRequest {
    public static String excuteGet(String targetURL){
        URL url;
        HttpURLConnection connection= null;
        try {
            url= new URL(targetURL);
            connection=(HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            InputStream inputStream;
            int status= connection.getResponseCode();
            if(status != HttpURLConnection.HTTP_OK)
                inputStream= connection.getErrorStream();
            else
                inputStream= connection.getInputStream();

            BufferedReader bufferedReader= new BufferedReader(new InputStreamReader(inputStream));
            String line;
            StringBuffer response= new StringBuffer();
            while((line = bufferedReader.readLine())!= null){
                response.append(line);
                response.append('\r');
            }
            bufferedReader.close();
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if(connection != null){
                connection.disconnect();
            }
        }
    }
}
