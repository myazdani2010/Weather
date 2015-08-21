package example.com.exsqlite;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class WeatherDetailActivityFragment extends Fragment {
    private String icon;
    private String iconUrl = "http://openweathermap.org/img/w/";
    private Bitmap iconBitmap;
    private ImageView imageView;
    private TextView mainText = null;
    private TextView tempMaxText = null;
    private TextView tempMinText = null;
    private TextView desText = null;
    private TextView dayText = null;
    private int id;
    private String main;
    private String description;
    private float tempMin;
    private float tempMax;
    private String day;

    public WeatherDetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_weather_detail, container, false);
        imageView = (ImageView) view.findViewById(R.id.imageView);
        mainText = (TextView)view.findViewById(R.id.mainText);
        tempMaxText = (TextView)view.findViewById(R.id.tempMax);
        tempMinText = (TextView)view.findViewById(R.id.tempMin);
        desText = (TextView)view.findViewById(R.id.description);
        dayText = (TextView)view.findViewById(R.id.day);

        Bundle bundle = this.getArguments();
        icon = bundle.getString("icon");
        Log.i("icon: ", icon);
        AsyncTask<String, Void, Long> task = new GetPhoto();
        task.execute();

        id = bundle.getInt("id");
        Log.i("id: ", String.valueOf(id));
        main = bundle.getString("main");
        mainText.setText(main);
        description = bundle.getString("description");
        desText.setText(description.substring(0, 1).toUpperCase()+description.substring(1));
        tempMin = bundle.getFloat("tempMin");
        tempMinText.setText(String.valueOf(String.format("%.1f", tempMin))+"\u2109");
        tempMax = bundle.getFloat("tempMax");
        tempMaxText.setText(String.valueOf(String.format("%.1f", tempMax))+"\u2109");
        day = bundle.getString("day");
        dayText.setText(day);

        return view;
    }

    //GetPhoto connects to the URL link to get the related weather icon.
    // updates the imageView
    private class GetPhoto extends AsyncTask<String, Void, Long> {
        InputStream is = null;
        HttpURLConnection connection = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Long doInBackground(String... strings) {

            try {
                URL imageUrl = new URL(iconUrl + icon + ".png");
                Log.i("Icon URL: ", imageUrl.toString());
                connection = (HttpURLConnection) imageUrl.openConnection();
                connection.connect();
                is = connection.getInputStream();
                iconBitmap = BitmapFactory.decodeStream(is);
                return (0L);

            } catch (MalformedURLException e) {
                e.printStackTrace();
                return (1L);
            } catch (IOException e) {
                e.printStackTrace();
                return (1L);
            } finally {
                connection.disconnect();
            }
        }
        @Override
        protected void onPostExecute(Long aLong) {
            imageView.setImageBitmap(iconBitmap);
            super.onPostExecute(aLong);
        }
    }
}
