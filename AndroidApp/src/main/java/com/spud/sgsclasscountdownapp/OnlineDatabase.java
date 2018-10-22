package com.spud.sgsclasscountdownapp;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HttpsURLConnection;

import static com.spud.sgsclasscountdownapp.WeekType.getWeekType;

/**
 * Created by Stephen Ogden on 10/11/18.
 * FTC 6128 | 7935
 * FRC 1595
 */

// TODO: Finish this
public class OnlineDatabase {

    private URL onlineDB = null;

    private void getFromDatabase() {

        try {
            onlineDB = new URL("https://raw.githubusercontent.com/jeffrypig23/SGSClassCountdown/database/database.json");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        URLConnection connection = null;
        try {
            connection = (HttpsURLConnection) onlineDB.openConnection();
            connection.setReadTimeout(5000);

            String data;

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while ((br.readLine()) != null) {
                data = br.readLine();
            }
            br.close();
            ((HttpsURLConnection) connection).disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parseJson(String data) {
        JSONObject object = null;
        try {
            object = new JSONObject(data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (object != null) {

        }
    }

}
