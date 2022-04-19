package is.hbv2.ComradeFinderApp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;


import is.hbv2.ComradeFinderApp.Entities.Ad;

public class HomeActivity extends AppCompatActivity implements LoginStatusFragment.Callbacks {

    private String mUsername = "";
    private boolean mIsCompany = false;
    private LinearLayout listViewAd;
    private ListView listView;
    public static ArrayList<Ad> ads = new ArrayList<>();
    ActivityResultLauncher<Intent> resultLauncher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Gets username from loginfragment
        resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        Intent intent = result.getData();

                        if (intent != null) {
                            Intent data = result.getData();

                            String user = data.getStringExtra("user");
                            mIsCompany = data.getBooleanExtra("isCompany", false);
                            mUsername = user;
                            updateLoginFragment();
                            Log.d("user", mUsername);
                            Log.d("isCompany", ""+ mIsCompany);
                            if (mIsCompany) ((Button) findViewById(R.id.makeAdButton)).setVisibility(View.VISIBLE);
                        }
                    }
                });


        setContentView(R.layout.activity_home);


        createLoginFragment();
        //populateListView();

        setupData();
        setUpList();
        setUpOnClickListener();
    }

    private void setupData() {
        Ad testAd1 = new Ad("Title1", "Description1", "1-1", new ArrayList<>(), "Comapny1", "LinkToImage1", new ArrayList<>());
        Ad testAd2 = new Ad("Title2", "Description2", "2-2", new ArrayList<>(), "Comapny2", "LinkToImage2", new ArrayList<>());
        Ad testAd3 = new Ad("Title3", "Description3", "3-3", new ArrayList<>(), "Comapny3", "LinkToImage3", new ArrayList<>());

        testAd1.setID(0);
        testAd2.setID(1);
        testAd3.setID(2);

        ads.add(testAd1);
        ads.add(testAd2);
        ads.add(testAd3);
    }

    private void setUpList() {
        listView = (ListView) findViewById(R.id.adsListView);

        AdsAdapter adsAdapter = new AdsAdapter(getApplicationContext(), 0, ads);
        listView.setAdapter(adsAdapter);
    }

    private void setUpOnClickListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Ad selectedAd = (Ad) (listView.getItemAtPosition(position));
                Intent showDetail = new Intent(getApplicationContext(), DetailAdActivity.class);
                showDetail.putExtra("id",selectedAd.getID());
                startActivity(showDetail);
            }
        });
    }

    // Puts LoginStatus fragment in login_fragment_container
    private void createLoginFragment() {
        // user is logged in
        LoginStatusFragment login = new LoginStatusFragment().newInstance(mUsername);
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .add(R.id.login_fragment_container, login)
                .commit();
    }

    private void updateLoginFragment() {
        FragmentManager fm = getSupportFragmentManager();
        LoginStatusFragment login = (LoginStatusFragment) fm.findFragmentById(R.id.login_fragment_container);

        Log.d("update", login.toString());
        if (login != null) {
            login.setLoggedUser(mUsername);
        }
    }

    //
    public void login() {
        Log.d("login", "login in homepage");

        Intent i = new Intent(HomeActivity.this, LoginActivity.class);
        //startActivity(i);
        i.setType("String");
         resultLauncher.launch(i);
      //  someActivityResultLauncher.launch(i);

    }

    public void register() {
        Intent i = new Intent(HomeActivity.this, RegisterActivity.class);
        startActivity(i);
    }
}