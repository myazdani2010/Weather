package example.com.exsqlite;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class Weather {
    static String city;
    static String country;
    private List<WeatherDetail> weatherDetails = new ArrayList<>();

    //thakes the JSON String and converts
    public static ArrayList<WeatherDetail> getForcastList (String photoData) throws JSONException
    {
        int id;
        String main;
        String description;
        String icon;
        float tempMin;
        float tempMax;
        long  dt;
        String day;
        ArrayList<WeatherDetail> wdl = new ArrayList<>();

        JSONObject data = new JSONObject(photoData);
        JSONArray listJson = data.optJSONArray("list");

        //System.out.println(city +"  "+county);
        Log.i("Data:", data.toString());

        Date date;
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM d");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT-4"));

        WeatherDetail wd;
        for (int i = 0; listJson.length()>i; i++)
        {
            id = listJson.optJSONObject(i).getJSONArray("weather").getJSONObject(0).getInt("id");
            main = listJson.optJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("main");
            description = listJson.optJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("description");
            icon = listJson.optJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("icon");
            tempMin = (float)listJson.optJSONObject(i).optJSONObject("temp").getDouble("min");
            tempMax = (float)listJson.optJSONObject(i).optJSONObject("temp").getDouble("max");
            dt = listJson.optJSONObject(i).getInt("dt");
            date = new Date(dt*1000L);
            day = sdf.format(date);
            System.out.println(day);
            wd = new WeatherDetail();
            wd.setDay(day);
            wd.setDescription(description);
            wd.setIcon(icon);
            wd.setId(id);
            wd.setMain(main);
            wd.setTempMax(tempMax);
            wd.setTempMin(tempMin);
            wdl.add(wd);
        }
        return wdl;
    }

    public static String getCityName(String str) throws JSONException
    {
        JSONObject data = new JSONObject(str);
        JSONObject cityJson = data.optJSONObject("city");
        city = cityJson.getString("name");
        return cityJson.getString("name");
    }

    public static String getCountryName(String str) throws JSONException
    {
        JSONObject data = new JSONObject(str);
        JSONObject cityJson = data.optJSONObject("city");
        country = cityJson.getString("country");
        return cityJson.getString("country");
    }

}
