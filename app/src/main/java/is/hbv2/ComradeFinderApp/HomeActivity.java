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
import java.util.List;


import is.hbv2.ComradeFinderApp.Entities.Ad;
import is.hbv2.ComradeFinderApp.Entities.Company;
import is.hbv2.ComradeFinderApp.Entities.User;
import is.hbv2.ComradeFinderApp.Network.NetworkCallback;
import is.hbv2.ComradeFinderApp.Network.NetworkManager;

public class HomeActivity extends AppCompatActivity implements LoginStatusFragment.Callbacks {

    private static final String TAG = "HomeActivity";
    private NetworkManager mNetworkManager;

    private String mUsername = "";
    private boolean mIsCompany = false;
    private LinearLayout listViewAd;
    private ListView listView;
    private Button mMakeAd;
    public static ArrayList<Ad> ads = new ArrayList<>();
    ActivityResultLauncher<Intent> resultLauncher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mNetworkManager = NetworkManager.getInstance(this);

        // Gets username from loginfragment
        resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        Intent intent = result.getData();

                        if (intent != null) {
                            Intent data = result.getData();

                            mUsername = data.getStringExtra("user");
                            mIsCompany = data.getBooleanExtra("isCompany", false);
                            updateLoginFragment();
                            Log.d("user", mUsername);
                            Log.d("isCompany", ""+ mIsCompany);
                            if (mIsCompany) {
                                updateAdCreateButton(mIsCompany);
                            }
                        }
                    }
                });
        Log.d("here","here");

        mMakeAd = findViewById(R.id.makeAdButton);
        Log.d("here","here");
        if (mIsCompany) {
            updateAdCreateButton(mIsCompany);
        }



        setContentView(R.layout.activity_home);


        createLoginFragment();
        //populateListView();

        startFetchAllAdsAndDisplay();

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
        if (login != null) Log.d("update", login.toString());
        if (login != null) {
            login.setLoggedUser(mUsername);
        }
    }

    private void updateAdCreateButton(boolean isCompany) {
        mMakeAd = findViewById(R.id.makeAdButton);
        if (isCompany) {
            mMakeAd.setVisibility(View.VISIBLE);
            LinearLayout.LayoutParams childParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            mMakeAd.setLayoutParams(childParams);
            mMakeAd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("click","yes");

                    Intent i = new Intent(HomeActivity.this, MakeAdvertisementActivity.class);
                    i.putExtra("username", mUsername);
                    startActivity(i);
                }
            });
            return;
        }
        mMakeAd.setVisibility(View.INVISIBLE);
        mMakeAd.setLayoutParams(new LinearLayout.LayoutParams(0, 0));
    }

    //
    public void login() {
        Log.d("login", "login in homepage");

        Intent i = new Intent(HomeActivity.this, LoginActivity.class);
        //startActivity(i);
        i.setType("String");
         resultLauncher.launch(i);

    }

    public void register() {
        login();
        Intent i = new Intent(HomeActivity.this, RegisterActivity.class);
        startActivity(i);
    }

    public void logout() {
        Log.d("logut", "is in home");
        updateAdCreateButton(false);
    }

    //==============================================================================================
    //                         THREADS FOR THE NETWORK MANAGER
    //==============================================================================================
    private void startFetchAllAdsAndDisplay() {
        HomeActivity.FetchAllAds fetchAllAds = new HomeActivity.FetchAllAds();
        Thread t = new Thread(new ThreadGroup("fetchAllAds"), fetchAllAds);
        t.start();
    }

    class FetchAllAds implements Runnable {
        FetchAllAds() {
        }

        @Override
        public void run() {

            mNetworkManager.getAllAds(new NetworkCallback<List<Ad>>() {
                @Override
                public void onSuccess(List<Ad> result) {
                    if (result == null) {
                        Log.d(TAG, "Fetching ads returned null");
                        return;
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ads.addAll(result);
                            setUpList();
                            setUpOnClickListener();
                        }
                    });
                }

                @Override
                public void onFailure(String errorString) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d(TAG, "Ads Fetched Failure: " + errorString);
                        }
                    });
                }
            });
            Log.d(TAG, "Login thread finished");
        }
    }

    private void startFetchAllAdsForCompanyAndDisplay(String username) {
        HomeActivity.FetchAllAdsForCompany fetchAllAdsForCompany = new HomeActivity.FetchAllAdsForCompany(username);
        Thread t = new Thread(new ThreadGroup("fetchAllAds"), fetchAllAdsForCompany);
        t.start();
    }

    class FetchAllAdsForCompany implements Runnable {
        String companyUsername;
        FetchAllAdsForCompany(String username) {
            this.companyUsername = username;
        }

        @Override
        public void run() {

            mNetworkManager.getAllAdsByCompany(this.companyUsername, new NetworkCallback<List<Ad>>() {
                @Override
                public void onSuccess(List<Ad> result) {
                    if (result == null) {
                        Log.d(TAG, "Fetching ads returned null");
                        return;
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ads.addAll(result);
                            setUpList();
                            setUpOnClickListener();
                        }
                    });
                }

                @Override
                public void onFailure(String errorString) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d(TAG, "Ads Fetched Failure: " + errorString);
                        }
                    });
                }
            });
            Log.d(TAG, "Login thread finished");
        }
    }
}