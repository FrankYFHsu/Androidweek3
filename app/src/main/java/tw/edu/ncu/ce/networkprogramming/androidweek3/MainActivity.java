package tw.edu.ncu.ce.networkprogramming.androidweek3;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class MainActivity extends AppCompatActivity {

    private ImageView mImageView;
    private String imageURL = "http://www.ce.ncu.edu.tw/ce/images/logo/logo_blue.jpg";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageView = (ImageView) this.findViewById(R.id.imageView);
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

    public void clearImage(View v){

        mImageView.setImageBitmap(null);

    }

    public void onClickOnNewThreadWithViewPost(View v) {
        new Thread(new Runnable() {
            public void run() {
                final Bitmap b = loadImageFromNetwork(imageURL);
                mImageView.post(new Runnable() {
                    public void run() {
                        mImageView.setImageBitmap(b);
                    }
                });
            }
        }).start();
    }

    public void onClickOnNewThread(View v) {
        new Thread(new Runnable() {
            public void run() {
                final Bitmap b = loadImageFromNetwork(imageURL);

                mImageView.setImageBitmap(b);

            }
        }).start();
    }

    public void onClickOnNewThreadWithRunOnUIThread(View v) {
        new Thread(new Runnable() {
            public void run() {
                final Bitmap b = loadImageFromNetwork(imageURL);
                runOnUiThread(new Runnable() {
                    public void run() {
                        mImageView.setImageBitmap(b);
                    }
                });
            }
        }).start();
    }

    public void onClickWithAsyncTask(View v) {
        new DownloadImageTask().execute(imageURL);

    }


    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        protected Bitmap doInBackground(String... urls) {
            return loadImageFromNetwork(urls[0]);
        }

        protected void onPostExecute(Bitmap result) {
            mImageView.setImageBitmap(result);
        }
    }


    public Bitmap loadImageFromNetwork(String urls) {
        try {
            URL url = new URL(urls);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;

        } catch (Exception e) {
            return null;
        }


    }
}
