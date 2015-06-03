package tw.edu.ncu.ce.networkprogramming.simpleairquality;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class AQXListFragment extends Fragment {
    private static final String TAG = AQXListFragment.class.getName();
    private OnFragmentInteractionListener mListener;
    private ListView mListView;
    private final String jsonAPI =
            "http://opendata.epa.gov.tw/ws/Data/AQX/?$format=json";
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
        Log.d(TAG,"do HttpGetTaskWithGson");
        new HttpGetTaskWithGson().execute(jsonAPI);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_aqxlist, container, false);
    }


    public void onDataItemClick(int position, AQXData data) {
        if (mListener != null) {
            mListener.onFragmentInteraction(position,data);
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

    private boolean isInTwoPaneMode() {

        return getFragmentManager().findFragmentById(R.id.article_fragment) != null;

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(int position, AQXData data);
    }

    private class HttpGetTaskWithGson extends AsyncTask<String, Integer, List<AQXData>> {
        @Override
        protected List<AQXData> doInBackground(String... urls) {
            Log.d(TAG, "doInBackground");
            List<AQXData> result = new ArrayList<AQXData>();
            String apiurl = urls[0];
            try {

                URL url = new URL(apiurl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(5000);

                if (conn.getResponseCode() == 200) {
                    Log.d(TAG, "connect successful");
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    reader.close();

                    String jsonString = sb.toString();


                    Gson gson = new Gson();
                    AQXData[] data = gson.fromJson(jsonString, AQXData[].class);

                    for (int i = 0; i < data.length; i++) {
                        publishProgress((int) (((i + 1) / (float) data.length) * 100));
                        result.add(data[i]);
                    }


                }

                conn.disconnect();


            } catch (Exception e) {
                Log.e(TAG, "Exception for url:" + apiurl);
                Log.e(TAG, "Exception :" + e.getMessage());
            }


            return result;
        }

        @Override
        protected void onPostExecute(List<AQXData> result) {


            if (result.size() == 0) {
                Toast.makeText(getActivity(), "Service Unavailable", Toast.LENGTH_LONG).show();

            }

            mListView = (ListView) getView().findViewById(R.id.listView);
            mAdapter = new AQXDataAdapter(getActivity(), result);
            mListView.setAdapter(mAdapter);


            mListView.setOnItemClickListener(new ListView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    onDataItemClick(position,mAdapter.getItem(position));
                    mListView.setItemChecked(position, true);

                }

            });


        }

    }

}
