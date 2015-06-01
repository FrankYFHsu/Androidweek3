package tw.edu.ncu.ce.networkprogramming.xmlexample;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class HttpXMLActivity extends ActionBarActivity {

    private String xmlAPI = "http://opendata.epa.gov.tw/ws/Data/AQX/?$format=xml";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_xml);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

        new HttpGetTask().execute(xmlAPI);
    }



    private class HttpGetTask extends AsyncTask<String, Void, List<String>> {


        @Override
        protected List<String> doInBackground(String... urls) {
            List<String> result = new ArrayList<String>();
            String apiurl = urls[0];
            try {


                URL url = new URL(apiurl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(5000);

                if (conn.getResponseCode() == 200) {


                    //AQXXmlParser xmlparser = new AQXXmlParser();
                    AQXXmlParser2 xmlparser = new AQXXmlParser2();

                    return xmlparser.parse(conn.getInputStream());


                }

                conn.disconnect();


            } catch (Exception e) {
                Log.e(this.getClass().getName(), "Exception for url:" + apiurl);
                Log.e(this.getClass().getName(), "Exception :" + e.getMessage());
            }


            return result;
        }



        @Override
        protected void onPostExecute(List<String> result) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(HttpXMLActivity.this,
                    android.R.layout.simple_list_item_1, result);

            ListView listView = (ListView) findViewById(R.id.listView);
            listView.setAdapter(adapter);
        }
    }


}
