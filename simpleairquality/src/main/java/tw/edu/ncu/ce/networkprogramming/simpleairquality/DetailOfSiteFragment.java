package tw.edu.ncu.ce.networkprogramming.simpleairquality;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by mpclab on 2015/6/2.
 */
public class DetailOfSiteFragment extends Fragment {

    final static String ARG_POSITION = "position";
    final static String ARG_JSON = "json";
    int mCurrentPosition = -1;
    private ListView detailsView;
    private TextView sitetextView;
    private TextView statustextView;
    private TextView pm25textView;
    private AQXData mData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // If activity recreated (such as from screen rotate), restore
        // the previous article selection set by onSaveInstanceState().
        // This is primarily necessary when in the two-pane layout.
        if (savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getInt(ARG_POSITION);
            mData = new Gson().fromJson(savedInstanceState.getString(ARG_JSON), AQXData.class);
        }

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.detail_view, container, false);

    }

    @Override
    public void onStart() {
        super.onStart();

        // During startup, check if there are arguments passed to the fragment.
        // onStart is a good place to do this because the layout has already been
        // applied to the fragment at this point so we can safely call the method
        // below that sets the article text.
        Bundle args = getArguments();

        if (args != null) {
            // Set article based on argument passed in
            AQXData d = new Gson().fromJson(args.getString(ARG_JSON),AQXData.class);
            updateArticleView(args.getInt(ARG_POSITION),d);
        } else if (mCurrentPosition != -1) {
            // Set article based on saved instance state defined during onCreateView
            updateArticleView(mCurrentPosition, mData);
        }
    }

    public void updateArticleView(int position, AQXData data) {
        this.mData = data;
        //TODO

        mCurrentPosition = position;

        detailsView = (ListView)getView().findViewById(R.id.details);
        sitetextView = (TextView)getView().findViewById(R.id.sitetextView);
        statustextView = (TextView)getView().findViewById(R.id.statustextView);
        pm25textView = (TextView)getView().findViewById(R.id.pm25textView);

        sitetextView.setText(data.getSiteName());
        statustextView.setText("空氣品質 : "+data.getStatus());
        pm25textView.setText("PM2.5 : "+data.getPM2_5());


        List<String> result = data.getDetails();


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, result);


        detailsView.setAdapter(adapter);



    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the current article selection in case we need to recreate the fragment
        outState.putInt(ARG_POSITION, mCurrentPosition);
        outState.putString(ARG_JSON, new Gson().toJson(mData));
    }


}