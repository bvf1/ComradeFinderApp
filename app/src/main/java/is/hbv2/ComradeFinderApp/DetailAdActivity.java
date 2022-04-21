package is.hbv2.ComradeFinderApp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import is.hbv2.ComradeFinderApp.Entities.Ad;

public class DetailAdActivity extends AppCompatActivity implements LoginStatusFragment.Callbacks {
    private final String TAG = "DetailAdActivity";

    Ad selectedAd;
    private Button mApplicationButton;
    private ListView listView;
    private String mUser = "";
    private boolean isCompany = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_ad);

        Intent previousIntent = getIntent();
        mUser = previousIntent.getStringExtra("user");

        Long parsedStringID = previousIntent.getLongExtra("id", 0);
        String u = previousIntent.getStringExtra("username");
       // if (u != null) mUser = u;
        Log.d("username", ":"+ mUser);
        Log.d("long", ":"+ parsedStringID);

        createLoginFragment();

        getSlecetedAd(parsedStringID);
        setValues();

        mApplicationButton = findViewById(R.id.makeApplicationButton);
        mApplicationButton.setOnClickListener(view -> {
            Intent i = new Intent(DetailAdActivity.this, MakeApplicationActivity.class);
            startActivity(i);
        });
    }

    private void getSlecetedAd(Long parsedStringID) {
        Log.d(TAG,"Selected ad ID: "+parsedStringID.toString());
        if (HomeActivity.ads.size() == 0) {
            HomeActivity.ads.addAll(dummyAds());
        }
        selectedAd = HomeActivity.ads.get(Math.toIntExact((parsedStringID)));
    }

    private void setValues() {
        //TODO: taka test og setja alvöru questions
        List<String> test = new ArrayList<>();
        test.add("banana?");
        test.add("is jon?");

        TextView adName = (TextView) findViewById(R.id.adName);
        TextView adSalary = (TextView) findViewById(R.id.adSalary);
        TextView adDesc = (TextView) findViewById(R.id.adDescriptionHome);
        TextView adComp = (TextView) findViewById(R.id.adCompany);

        listView = (ListView) findViewById(R.id.adsDetailListView);
        //TODO: Breyta test í eitthvað almennilegt
        AdsDetailAdapter adsDetailAdapter = new AdsDetailAdapter(getApplicationContext(), 0, test);
        listView.setAdapter(adsDetailAdapter);

        adName.setText(selectedAd.getTitle());
        adSalary.setText(selectedAd.getSalaryRange());
        adDesc.setText(selectedAd.getDescription());
        adComp.setText(selectedAd.getCompanyUsername());


    }

    private List<Ad> dummyAds() {
        List<Ad> ads = new ArrayList<>();

        Ad testAd1 = new Ad("Title1", "Description1", "1-1", new ArrayList<>(), "Comapny1", "LinkToImage1", new ArrayList<>());
        Ad testAd2 = new Ad("Title2", "Description2", "2-2", new ArrayList<>(), "Comapny2", "LinkToImage2", new ArrayList<>());
        Ad testAd3 = new Ad("Title3", "Description3", "3-3", new ArrayList<>(), "Comapny3", "LinkToImage3", new ArrayList<>());

        testAd1.setID(0);
        testAd2.setID(1);
        testAd3.setID(2);

        ads.add(testAd1);
        ads.add(testAd2);
        ads.add(testAd3);
        return ads;
    }

    // Puts LoginStatus fragment in login_fragment_container
    private void createLoginFragment() {
        Log.d("muser", ""+mUser);
        LoginStatusFragment login = new LoginStatusFragment().newInstance("mUser");
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .add(R.id.login_fragment_container, login)
                .commit();
    }

    public void login() {
        Log.d("login", "Should not be here");
    }

    public void register() {
        Log.d("register", "Should not be here");
    }

    public void logout() {
        Intent i = new Intent(DetailAdActivity.this, HomeActivity.class);
        startActivity(i);
    }
}