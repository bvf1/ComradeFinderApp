package is.hbv2.ComradeFinderApp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


import is.hbv2.ComradeFinderApp.Entities.Ad;

public class HomeActivity extends AppCompatActivity implements LoginStatusFragment.Callbacks {

    private String username = "";
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
                            username = user;
                            updateLoginFragment();
                            Log.d("user", username);
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


    /*public void populateListView() {
        listViewAd = (LinearLayout) findViewById(R.id.listViewAd);
        ads.add(new Ad("Title1", "Description1", "", new ArrayList<>(), "Comapny1", "LinkToImage1", new ArrayList<>()));
        ads.add(new Ad("Title2", "Description2", "", new ArrayList<>(), "Comapny2", "LinkToImage2", new ArrayList<>()));
        ads.add(new Ad("Title3", "Description3", "", new ArrayList<>(), "Comapny3", "LinkToImage3", new ArrayList<>()));

        Ad adObject1 = ads.get(0);
        Ad adObject2 = ads.get(1);
        Ad adObject3 = ads.get(2);
        Fragment ad = new AdsHome().newInstance(adObject1.getTitle(), adObject1.getSalaryRange());
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .add(R.id.listViewAd, ad)
                .commit();
        ad = new AdsHome().newInstance(adObject2.getTitle(), adObject2.getSalaryRange());
        fm.beginTransaction()
                .add(R.id.listViewAd, ad)
                .commit();
        //TODO: Bæta við að sýna þessa items í home
    }*/

    // Puts LoginStatus fragment in login_fragment_container
    private void createLoginFragment() {
        // user is logged in
        LoginStatusFragment login = new LoginStatusFragment().newInstance(username);
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
            login.setLoggedUser(username);
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