package tw.edu.ncu.ce.networkprogramming.jsonexample
        ;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class HttpJSONActivity extends ActionBarActivity {

    private final String jsonAPI = "http://opendata.epa.gov.tw/ws/Data/AQX/?$format=json";
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_json);

        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mProgressBar.setProgress(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_http_json, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void downloadInformation(View view) {

        new HttpGetTask().execute(jsonAPI);
    }

    public void downloadInformation2(View view) {

        new HttpGetTaskWithGson().execute(jsonAPI);
    }

    public void setProgressPercent(Integer progress) {
        mProgressBar.setProgress(progress);
    }


    private class HttpGetTask extends AsyncTask<String, Integer, List<String>> {

        private static final String SITENAMETAG = "SiteName";
        private static final String STATUSTAG = "Status";
        private static final String PM25TAG = "PM2.5";


        @Override
        protected List<String> doInBackground(String... urls) {
            Log.d("TAG", "doinbackg");
            List<String> result = new ArrayList<String>();
            String apiurl = urls[0];
            try {

                URL url = new URL(apiurl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(5000);


                if (conn.getResponseCode() == 200) {
                    Log.d("TAG", "connect successful");
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    reader.close();

                    String jsonString = sb.toString();


                    JSONArray jsonArray = new JSONArray(jsonString);
                    int count = jsonArray.length();
                    for (int i = 0; i < count; i++) {
                        publishProgress((int) (((i + 1) / (float) count) * 100));
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        result.add("地點" + ":"
                                + jsonObject.get(SITENAMETAG) + ","
                                + "空氣品質" + ":"
                                + jsonObject.getString(STATUSTAG) + ","
                                + "PM2.5" + ":"
                                + jsonObject.get(PM25TAG));
                    }


                }

                conn.disconnect();


            } catch (Exception e) {
                Log.e(this.getClass().getName(), "Exception for url:" + apiurl);
                Log.e(this.getClass().getName(), "Exception :" + e.getMessage());
            }


            return result;
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            setProgressPercent(progress[0]);
        }


        @Override
        protected void onPostExecute(List<String> result) {

            if (result.size() == 0) {
                Toast.makeText(HttpJSONActivity.this, "Service Unavailable", Toast.LENGTH_LONG).show();

            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(HttpJSONActivity.this,
                    android.R.layout.simple_list_item_1, result);

            ListView listView = (ListView) findViewById(R.id.listView);
            listView.setAdapter(adapter);
        }
    }


    private class HttpGetTaskWithGson extends HttpGetTask {
        @Override
        protected List<String> doInBackground(String... urls) {
            Log.d("TAG", "doinbackg");
            List<String> result = new ArrayList<String>();
            String apiurl = urls[0];
            try {

                URL url = new URL(apiurl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(5000);


                if (conn.getResponseCode() == 200) {
                    Log.d("TAG", "connect successful");
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
                        result.add(data[i].toString());
                    }


                }

                conn.disconnect();


            } catch (Exception e) {
                Log.e(this.getClass().getName(), "Exception for url:" + apiurl);
                Log.e(this.getClass().getName(), "Exception :" + e.getMessage());
            }


            return result;
        }

    }


}





