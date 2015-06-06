package example.com.exsqlite;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by mark on 4/30/15.
 */
public class Weather {
    //String id, owner, secret, server, farm, title;
    //Boolean isPublic, isFriend, isFamily;

    static String city;
    static String country;
    private List<WeatherDetail> weatherDetails = new ArrayList<>();

    public Weather(JSONObject jsonPhoto) throws JSONException {
//        this.id = (String) jsonPhoto.optString("id");
//        this.secret = (String) jsonPhoto.optString("secret");
//        this.owner = (String) jsonPhoto.optString("owner");
//        this.server = (String) jsonPhoto.optString("server");
//        this.farm = (String) jsonPhoto.optString("farm");
//        this.title = (String)jsonPhoto.optString("title");
//        this.isPublic = (Boolean) jsonPhoto.optBoolean("ispublic");
//        this.isFriend = (Boolean) jsonPhoto.optBoolean("isfriend");
//        this.isFamily = (Boolean) jsonPhoto.optBoolean("isfamily");
    }

    //thakes the JSON String and converts
    public static ArrayList<WeatherDetail> getForcastList (String photoData) throws JSONException
    {
         int id;
         String main;
         String description;
         String icon;
         int tempMin;
        int tempMax;
        long  dt;
        String day;
        ArrayList<WeatherDetail> wdl = new ArrayList<>();

        ArrayList<Weather> weathers = new ArrayList<>();
        JSONObject data = new JSONObject(photoData);
        JSONArray listJson = data.optJSONArray("list");
       // JSONObject listJson = data.optJSONObject("list");
        //String city = listJson.getString("list");
        //String county = listJson.getString("country");

        //System.out.println(city +"  "+county);
        Log.i("Data:", data.toString());
//        System.out.println("***keys");
//
//        Iterator<String> keys = data.keys();
//        while (keys.hasNext())
//        {
//            System.out.println(keys.next());
//        }



        //JSONObject city = data.optJSONObject("city");

//        System.out.println("***keys");
//       // keys = listJson.keys();
//
//        keys = listJson.optJSONObject(0).keys();
//        while (keys.hasNext())
//        {
//            System.out.println(keys.next());
//        }

        Date date;
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM d"); // the format of your date
        sdf.setTimeZone(TimeZone.getTimeZone("GMT-4")); // give a timezone reference for formating (see comment at the bottom

        WeatherDetail wd;
        for (int i = 0; listJson.length()>i; i++)
        {
            id = listJson.optJSONObject(i).getJSONArray("weather").getJSONObject(0).getInt("id");
            main = listJson.optJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("main");
            description = listJson.optJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("description");
            icon = listJson.optJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("icon");
            tempMin = listJson.optJSONObject(i).optJSONObject("temp").getInt("min");
            tempMax = listJson.optJSONObject(i).optJSONObject("temp").getInt("max");
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

    public static String getCityName(String photoData) throws JSONException
    {
        ArrayList<Weather> flickrPhotos = new ArrayList<>();
        JSONObject data = new JSONObject(photoData);
        JSONObject cityJson = data.optJSONObject("city");
        city = cityJson.getString("name");
        return cityJson.getString("name");
    }

    public static String getCountryName(String photoData) throws JSONException
    {
        ArrayList<Weather> flickrPhotos = new ArrayList<>();
        JSONObject data = new JSONObject(photoData);
        JSONObject cityJson = data.optJSONObject("city");
        country = cityJson.getString("country");
        return cityJson.getString("country");
    }

}
