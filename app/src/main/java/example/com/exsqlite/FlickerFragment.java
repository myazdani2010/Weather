package example.com.exsqlite;

import android.app.ListFragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import java.util.ArrayList;

/**
 * Created by mark on 4/29/15.
 */
public class FlickerFragment extends Fragment implements AdapterView.OnItemClickListener{
    String[] mTitles;
    ArrayList<WeatherDetail> weatherDetailList;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layout, container, false);
        MainActivity activity = (MainActivity)this.getActivity();
        weatherDetailList = activity.getWeatherDetailList();
        mTitles = new String[weatherDetailList.size()];

        System.out.println("size: "+weatherDetailList.size());
        for(int i = 0; i< weatherDetailList.size(); i++){
            mTitles[i] = weatherDetailList.get(i).toString();
        }

        ListView lv =(ListView)view.findViewById(R.id.listView);
        lv.setAdapter(new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, mTitles));
        lv.setOnItemClickListener(this);

        return view;
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //Toast.makeText(getActivity(), mTitles[i], Toast.LENGTH_LONG).show();
//        String photoURL = weatherList.get(i).getPhotoURL(true);
//        PhotoFragment pf = new PhotoFragment();
//        Bundle args = new Bundle();
//        args.putString("URL", photoURL);
//        pf.setArguments(args);
//        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
//        ft.replace(R.id.container, pf);
//        ft.addToBackStack("Image");
//        ft.commit();
    }
}
