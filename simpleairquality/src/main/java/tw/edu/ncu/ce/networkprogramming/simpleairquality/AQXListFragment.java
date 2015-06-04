package tw.edu.ncu.ce.networkprogramming.simpleairquality;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;


public class AQXListFragment extends Fragment{
    private static final String TAG = AQXListFragment.class.getName();
    private OnFragmentInteractionListener mListener;
    private ListView mListView;
    private FloatingActionButton mFabButton;
    private AQXDataAdapter mAdapter;

    public AQXListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mFabButton = (FloatingActionButton)getView().findViewById(R.id.fabButton);
        if(AQXApp.getInstance(getActivity()).hasAQXData()){
            mFabButton.setVisibility(View.VISIBLE);
        }

        mListView = (ListView) getView().findViewById(R.id.listView);
        mAdapter = new AQXDataAdapter(getActivity(), AQXApp.getInstance(getActivity()).getAQXData());
        mListView.setAdapter(mAdapter);


        mListView.setOnItemClickListener(new ListView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                onDataItemClick(position);
                mListView.setItemChecked(position, true);

            }

        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_aqxlist, container, false);
    }


    public void onDataItemClick(int position) {
        if (mListener != null) {
            mListener.onFragmentInteraction(position);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onStart() {
        super.onStart();

        // When in two-pane layout, set the listview to highlight the selected list item
        // (We do this during onStart because at the point the listview is available.)
        if (isInTwoPaneMode()) {
            mListView = (ListView) getView().findViewById(R.id.listView);
            mListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        }
    }



    public void updateAQXDataAdapter() {

        mFabButton.setVisibility(View.VISIBLE);
        mAdapter.updateAQXData(AQXApp.getInstance(getActivity()).getAQXData());
    }

    private boolean isInTwoPaneMode() {

        return getFragmentManager().findFragmentById(R.id.details_fragment) != null;

    }




    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(int position);
    }



}
