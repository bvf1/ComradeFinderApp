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

import is.hbv2.ComradeFinderApp.Entities.Ad;

public class HomeActivity extends AppCompatActivity {
    private ListView listViewAd;
    private ArrayList<Ad> ads = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_home);

        createLoginFragment();
    }

    public void populateListView() {
        listViewAd = (ListView) findViewById(R.id.listViewAd);
        ads.add(new Ad("Title1", "Description1", "SaloryRange1", new ArrayList<>(), "Comapny1", "LinkToImage1", new ArrayList<>()));
        ads.add(new Ad("Title2", "Description2", "SaloryRange2", new ArrayList<>(), "Comapny2", "LinkToImage2", new ArrayList<>()));
        ads.add(new Ad("Title3", "Description3", "SaloryRange3", new ArrayList<>(), "Comapny3", "LinkToImage3", new ArrayList<>()));

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