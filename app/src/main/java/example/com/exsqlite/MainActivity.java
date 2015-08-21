package example.com.exsqlite;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends ActionBarActivity {
    private ProgressBar progress;
    private ArrayList<Weather> weatherList = new ArrayList<>();
    private ArrayList<WeatherDetail> weatherDetailList = new ArrayList<>();
    private String city="";
    private String country="";
    private static double latitude=56.479261;
    private static double longitude=-115.006348;

    private LocationManager locationManager;
    private LocationListener locationListener;
    private static TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getLocation();
        progress = (ProgressBar) findViewById(R.id.progressBar);
        textView = (TextView) findViewById(R.id.textView);
        if (isOnline()) {
            LoadPhotos task = new LoadPhotos();
            task.execute();
        } else {
            progress.setVisibility(View.GONE);
            Toast.makeText(this, "Not online", Toast.LENGTH_LONG).show();
        }
    }

    public ProgressBar getProgress() {
        return progress;
    }

    public void setProgress(ProgressBar progress) {
        this.progress = progress;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public ArrayList<WeatherDetail> getWeatherDetailList() {
        return weatherDetailList;
    }

    public void setWeatherDetailList(ArrayList<WeatherDetail> weatherDetailList) {
        this.weatherDetailList = weatherDetailList;
    }

    public void setWeatherList(ArrayList<Weather> list) {
        this.weatherList = list;
    }

    public ArrayList<Weather> getWeatherList() {
        return weatherList;
    }


    public boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }


    public void showList() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.container, new WeatherFragment());
        ft.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class LoadPhotos extends AsyncTask<String, Long, Long> {
        HttpURLConnection connection = null;
        ArrayList<Weather> weatherList;

        @Override
        protected void onPreExecute() {
            progress.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Long... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected Long doInBackground(String... strings) {
            //url = http://api.openweathermap.org/data/2.5/forecast/daily?lat=56.479261&lon=-115.006348&cnt=10&mode=json&units=imperial
            String urlString = "http://api.openweathermap.org/data/2.5/forecast/daily?" +
                    "lat="+latitude+
                    "&lon="+longitude+
                    "&cnt=10&mode=json&units=imperial";

            Log.i("URL", urlString);
            try {
                URL dataUrl = new URL(urlString);
                connection = (HttpURLConnection) dataUrl.openConnection();
                connection.connect();
                int status = connection.getResponseCode();
                Log.d("TAG", "status " + status);

                if (status == 200) {
                    InputStream is = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                    String responseString;
                    StringBuilder sb = new StringBuilder();

                    while ((responseString = reader.readLine()) != null) {
                        sb = sb.append(responseString);
                        Log.i("responseString value= " , responseString);
                    }

                    String url = sb.toString();
                    city = Weather.getCityName(url);
                    country = Weather.getCountryName(url);
                    weatherDetailList = Weather.getForcastList(url);

                    return 0l;
                } else {
                    return 1l;
                }

            } catch (MalformedURLException e) {
                Log.i(Constants.TAG, "Malformed Url");
                e.printStackTrace();
                return 1l;
            } catch (IOException e) {
                e.printStackTrace();
                return 1l;
            } catch (JSONException e) {
                e.printStackTrace();
                return 1l;
            } finally {
                if (connection != null)
                    connection.disconnect();
            }
        }

        @Override
        protected void onPostExecute(Long result) {
            if (result != 1l) {
                textView.setText(Weather.city+", "+Weather.country);
                setWeatherList(weatherList);
                showList();

            } else {
                Toast.makeText(getApplicationContext(),
                        "AsyncTask didn't complete",
                        Toast.LENGTH_LONG).show();
            }
            progress.setVisibility(View.GONE);
        }
    }

    public void getLocation(){
        locationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
        boolean isGPRProvider=locationManager.isProviderEnabled(locationManager.GPS_PROVIDER);
        boolean isNetworkProvider=locationManager.isProviderEnabled(locationManager.NETWORK_PROVIDER);

        Log.i("Location", "getLocation called");
        locationListener = new LocationListener() {

            @Override
            public void onLocationChanged(Location location) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {}

            @Override
            public void onProviderEnabled(String s) {}

            @Override
            public void onProviderDisabled(String s) {}
        };

            Location location=null;
            if(isNetworkProvider)
            {
                Log.i("location Provider: ", "NETWORK_PROVIDER");
                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }
            if(isGPRProvider) {
                Log.i("location Provider: ", "GPS_PROVIDER");
                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }
            if(location!=null)
            {
                Log.d("location latitude",+location.getLatitude()+"");
                Log.d("location longitude",+location.getLongitude()+"");
                latitude=location.getLatitude();
                longitude=location.getLongitude();
            }

        Log.i("My Location", latitude + " : " + longitude);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, locationListener);
    }
}