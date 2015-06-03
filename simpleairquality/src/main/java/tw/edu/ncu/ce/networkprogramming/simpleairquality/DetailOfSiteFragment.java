package tw.edu.ncu.ce.networkprogramming.simpleairquality;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by mpclab on 2015/6/2.
 */
public class DetailOfSiteFragment extends Fragment {

    final static String ARG_POSITION = "position";
    int mCurrentPosition = -1;

    public static DetailOfSiteFragment newInstance(int position) {
        DetailOfSiteFragment f = new DetailOfSiteFragment();
        Bundle args = new Bundle();
        args.putInt(DetailOfSiteFragment.ARG_POSITION, position);
        f.setArguments(args);
        return f;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // If activity recreated (such as from screen rotate), restore
        // the previous article selection set by onSaveInstanceState().
        // This is primarily necessary when in the two-pane layout.
        if (savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getInt(ARG_POSITION);

        }

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.detail_view, container, false);

    }

    @Override
    public void onStart() {
        super.onStart();


        Bundle args = getArguments();

        if (args != null) {

            updateDetailsView(args.getInt(ARG_POSITION));
        } else if (mCurrentPosition != -1) {

            updateDetailsView(mCurrentPosition);
        }
    }

    public void updateDetailsView(int position) {

        AQXData data = AQXApp.getInstance(getActivity()).getAQXData().get(position);

        mCurrentPosition = position;

        ListView detailsView = (ListView)getView().findViewById(R.id.details);
        TextView sitetextView = (TextView)getView().findViewById(R.id.sitetextView);
        TextView statustextView = (TextView)getView().findViewById(R.id.statustextView);

        sitetextView.setText(data.getSiteName());
        statustextView.setText(data.getStatus());



        List<String> result = data.getDetails();


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, result);


        detailsView.setAdapter(adapter);



    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);


        outState.putInt(ARG_POSITION, mCurrentPosition);

    }


}