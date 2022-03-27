package is.hbv2.ComradeFinderApp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


import is.hbv2.ComradeFinderApp.Entities.Ad;

public class HomeActivity extends AppCompatActivity {
    private ListView listViewAd;
    private ArrayList<Ad> ads = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_home);

        createLoginFragment();
        populateListView();
    }

    public void populateListView() {
        listViewAd = (ListView) findViewById(R.id.listViewAd);
        ads.add(new Ad("Title1", "Description1", new ArrayList<>(), new ArrayList<>(), "Comapny1", "LinkToImage1", new ArrayList<>()));
        ads.add(new Ad("Title2", "Description2", new ArrayList<>(), new ArrayList<>(), "Comapny2", "LinkToImage2", new ArrayList<>()));
        ads.add(new Ad("Title3", "Description3", new ArrayList<>(), new ArrayList<>(), "Comapny3", "LinkToImage3", new ArrayList<>()));

        Ad adObject1 = ads.get(0);
        Ad adObject2 = ads.get(1);
        Ad adObject3 = ads.get(2);
        Fragment ad = new AdsHome().newInstance(adObject1.getTitle(), adObject1.getSalaryRange());
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.listViewAd, ad)
                .commit();
        //TODO: Bæta við að sýna þessa items í home
    }

    // Puts LoginStatus fragment in login_fragment_container
    private void createLoginFragment() {
        Fragment login = new LoginStatusFragment().newInstance("Paul");
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .add(R.id.login_fragment_container, login)
                .commit();
    }
}