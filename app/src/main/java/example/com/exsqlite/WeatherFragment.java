package example.com.exsqlite;

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
import java.util.ArrayList;

public class WeatherFragment extends Fragment implements AdapterView.OnItemClickListener{
    String[] mTitles;
    ArrayList<WeatherDetail> weatherDetailList;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layout, container, false);
        MainActivity activity = (MainActivity)this.getActivity();
        weatherDetailList = activity.getWeatherDetailList();
        mTitles = new String[weatherDetailList.size()];

        System.out.println("weatherDetailList size: "+weatherDetailList.size());
        for(int i = 0; i< weatherDetailList.size(); i++){
            mTitles[i] = weatherDetailList.get(i).toString();
        }

        ListView lv =(ListView)view.findViewById(R.id.listView);
        lv.setAdapter(new ArrayAdapter<String>(
                this.getActivity(),
                android.R.layout.simple_list_item_1,
                mTitles));
        lv.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


        // fetching weatherDetail object that has been clicked on
        WeatherDetail wd = weatherDetailList.get(i);

       // Toast.makeText(getActivity(), mTitles[i]+" Icon = "+wd.getIcon(), Toast.LENGTH_LONG).show();

        WeatherDetailActivityFragment pf = new WeatherDetailActivityFragment();
        Bundle args = new Bundle();

        args.putInt("id", wd.getId());
        args.putString("main", wd.getMain());
        args.putString("description", wd.getDescription());
        args.putString("icon", wd.getIcon());
        args.putFloat("tempMin", wd.getTempMin());
        args.putFloat("tempMax", wd.getTempMax());
        args.putString("day", wd.getDay());

        pf.setArguments(args);
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container, pf);
        ft.addToBackStack("Image");
        ft.commit();

    }
}