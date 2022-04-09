package is.hbv2.ComradeFinderApp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

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
    private LinearLayout listViewAd;
    private ArrayList<Ad> ads = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_home);

        createLoginFragment();
        populateListView();
    }

    public void populateListView() {
        listViewAd = (LinearLayout) findViewById(R.id.listViewAd);
        ads.add(new Ad("Title1", "Description1", new ArrayList<>(), new ArrayList<>(), "Comapny1", "LinkToImage1", new ArrayList<>()));
        ads.add(new Ad("Title2", "Description2", new ArrayList<>(), new ArrayList<>(), "Comapny2", "LinkToImage2", new ArrayList<>()));
        ads.add(new Ad("Title3", "Description3", new ArrayList<>(), new ArrayList<>(), "Comapny3", "LinkToImage3", new ArrayList<>()));

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
    }

    // Puts LoginStatus fragment in login_fragment_container
    private void createLoginFragment() {
        // user is logged in
        //Fragment login = new LoginStatusFragment().newInstance("");
        Fragment login = new LoginStatusFragment().newInstance("Paul");
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .add(R.id.login_fragment_container, login)
                .commit();

    }

    //
    public void login() {
        Log.d("login", "login in homepage");
        Intent i = new Intent(HomeActivity.this, LoginActivity.class);
        startActivity(i);
    }
}