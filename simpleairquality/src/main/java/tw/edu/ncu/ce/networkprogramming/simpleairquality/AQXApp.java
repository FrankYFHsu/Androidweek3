package tw.edu.ncu.ce.networkprogramming.simpleairquality;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mpclab on 2015/6/3.
 */
public class AQXApp {

    private List<AQXData> mAqxData;
    private static final String TAG = AQXApp.class.getName();
    private final String jsonAPI =
            "http://opendata.epa.gov.tw/ws/Data/AQX/?$format=json";

    private static AQXApp airQualityAppInstance;
    private Context mAppContext;

    private Gson gson;

    private AQXApp(Context appContext) {
        mAppContext = appContext;
        gson = new Gson();
        mAqxData = new ArrayList<>();
    }

    public Gson getGson(){
        return gson;
    }


    public static synchronized AQXApp getInstance(Context c) {
        if (airQualityAppInstance == null) {
            airQualityAppInstance = new AQXApp(c.getApplicationContext());
        }
        return airQualityAppInstance;
    }

    public List<AQXData> getAQXData(){

        return mAqxData;
    }

    public void setAQXData(List<AQXData> newAQXData){
        mAqxData = newAQXData;
    }

    public void requestNewAQXData(AQXResponseCallback callback){

        new GetAQXTask(callback).execute(jsonAPI);

    }


    private class GetAQXTask extends AsyncTask<String, Integer, List<AQXData>> {

        int responseCode;
        AQXResponseCallback callback;
        String responseMessage;

        public GetAQXTask(AQXResponseCallback callback){
            this.callback = callback;
        }


        @Override
        protected List<AQXData> doInBackground(String... urls) {

            List<AQXData> result = new ArrayList<AQXData>();
            String apiurl = urls[0];
            try {

                URL url = new URL(apiurl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(5000);

                responseCode = conn.getResponseCode();
                responseMessage = conn.getResponseMessage();

                if (responseCode == 200) {

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


            if (responseCode== 200) {
                setAQXData(result);
                callback.onSuccess(result);

            }else{
                callback.onFailure(responseMessage);

            }



        }

    }

    public interface AQXResponseCallback {
        void onSuccess(List<AQXData> result);
        void onFailure(String responseMessage);
    }




}
