package tw.edu.ncu.ce.networkprogramming.socketexample;

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
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class HttpSocketActivity extends ActionBarActivity {

    private String url = "http://opendata.epa.gov.tw/ws/Data/AQX/?$orderby=SiteName&$skip=0&$top=1000&format=xml";
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_socket);
        mTextView = (TextView) findViewById(R.id.textView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_http_socket, menu);
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

        new HttpGetTask().execute();
    }

    private class HttpGetTask extends AsyncTask<Void, Void, String> {


        @Override
        protected String doInBackground(Void...para) {

            String host = "opendata.epa.gov.tw";

            int port = 80;
            String command = "GET /ws/Data/AQX/?$format=json";

            try {

                Socket socket = new Socket(host, port);

                PrintWriter pw = new PrintWriter(new OutputStreamWriter(
                        socket.getOutputStream()), true);
                Scanner sc = new Scanner(socket.getInputStream());

                pw.printf(command+"\n");
                pw.printf("HOST: " + host + "\n");
                pw.printf("Connection: close\r\n\r\n");
                StringBuffer sb = new StringBuffer();
                while (sc.hasNextLine()) {
                    sb.append(sc.nextLine());
                }

                return sb.toString();

            } catch (UnknownHostException e) {

                e.printStackTrace();
            } catch (IOException e) {

                e.printStackTrace();
            }


            return "";


        }


        @Override
        protected void onPostExecute(String result) {
            mTextView.setText(result);

        }
    }
}
