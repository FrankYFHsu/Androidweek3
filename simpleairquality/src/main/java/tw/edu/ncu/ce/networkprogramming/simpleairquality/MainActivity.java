package tw.edu.ncu.ce.networkprogramming.simpleairquality;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.List;


public class MainActivity extends ActionBarActivity implements AQXListFragment.OnFragmentInteractionListener, AQXApp.AQXResponseCallback {

    private static final String TAG = MainActivity.class.getName();
    private static final String AQXListFragment_TAG = AQXListFragment.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            return;
        }

        AQXApp.getInstance(this).asyncRequestNewAQXData(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
        setSupportActionBar(toolbar);



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
    public void onFragmentInteraction(int position) {

        DetailOfSiteFragment newFragment = (DetailOfSiteFragment)
                getSupportFragmentManager().findFragmentById(R.id.details_fragment);

        if (newFragment != null) {
            // we're in two-pane layout…
            newFragment.updateDetailsView(position);
        } else {
            // we're in the one-pane layout and must swap frags…
            newFragment = DetailOfSiteFragment.newInstance(position);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    public void refresh(View view){
        AQXApp.getInstance(this).asyncRequestNewAQXData(this);
    }

    @Override
    public void onSuccess(List<AQXData> result) {

        if (isInTwoPaneMode()) {

            AQXListFragment firstFragment = (AQXListFragment) getSupportFragmentManager().findFragmentById(R.id.aqxlist_fragment);
            firstFragment.updateAQXDataAdapter();
        } else {

            AQXListFragment firstFragment = (AQXListFragment)getSupportFragmentManager().findFragmentByTag(AQXListFragment_TAG);

            if(firstFragment==null){
                firstFragment = new AQXListFragment();
                firstFragment.setArguments(getIntent().getExtras());

                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragment_container, firstFragment,AQXListFragment_TAG).commit();
            }else{
                firstFragment.updateAQXDataAdapter();
            }


        }


    }

    @Override
    public void onFailure(String responseMessage) {

        Toast.makeText(this, responseMessage, Toast.LENGTH_LONG).show();


    }
}
