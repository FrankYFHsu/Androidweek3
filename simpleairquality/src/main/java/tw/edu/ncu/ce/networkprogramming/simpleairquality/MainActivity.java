package tw.edu.ncu.ce.networkprogramming.simpleairquality;

import android.os.AsyncTask;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends ActionBarActivity implements AQXListFragment.OnFragmentInteractionListener, AQXApp.AQXResponseCallback {

    private static final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            return;
        }

        AQXApp.getInstance(this).requestNewAQXData(this);



    }

    private boolean isInTwoPaneMode() {

        return findViewById(R.id.fragment_container) == null;

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


    @Override
    public void onFragmentInteraction(int position, AQXData data) {
        DetailOfSiteFragment articleFrag = (DetailOfSiteFragment)
                getSupportFragmentManager().findFragmentById(R.id.article_fragment);

        if (articleFrag != null) {
            // If article frag is available, we're in two-pane layout…
            // Call a method in the ArticleFragment to update its content
            articleFrag.updateArticleView(position, data);
        } else {
            // Otherwise, we're in the one-pane layout and must swap frags…
            // Create fragment and give it an argument for the selected article
            DetailOfSiteFragment newFragment = new DetailOfSiteFragment();
            Bundle args = new Bundle();
            args.putInt(DetailOfSiteFragment.ARG_POSITION, position);
            args.putString(DetailOfSiteFragment.ARG_JSON, new Gson().toJson(data));
            newFragment.setArguments(args);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack so the user can navigate back
            transaction.replace(R.id.fragment_container, newFragment);
            transaction.addToBackStack(null);

            // Commit the transaction
            transaction.commit();
        }
    }

    @Override
    public void onSuccess(List<AQXData> result) {
        if (!isInTwoPaneMode()) {


            Log.d(TAG, "in single mode");
            // Create a new Fragment to be placed in the activity layout
            AQXListFragment firstFragment = new AQXListFragment();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            firstFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, firstFragment).commit();
        }else{
            AQXListFragment firstFragment = (AQXListFragment) getSupportFragmentManager().findFragmentById(R.id.recipient_fragment);
            firstFragment.updateList();
        }


    }

    @Override
    public void onFailure(String responseMessage) {

        Toast.makeText(this, "ResponseMessage", Toast.LENGTH_LONG).show();


    }
}
