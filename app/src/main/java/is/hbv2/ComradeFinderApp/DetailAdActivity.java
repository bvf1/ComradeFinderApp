package is.hbv2.ComradeFinderApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import is.hbv2.ComradeFinderApp.Entities.Ad;

public class DetailAdActivity extends AppCompatActivity {

    Ad selectedAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_ad);
        getSlecetedAd();
        setValues();
    }

    private void getSlecetedAd() {
        Intent previousIntent = getIntent();
        Long parsedStringID = previousIntent.getLongExtra("id", 0);
        if (HomeActivity.ads.size() == 0) {
            HomeActivity.ads.addAll(dummyAds());
        }
        selectedAd = HomeActivity.ads.get(Math.toIntExact((parsedStringID)));
    }

    private void setValues() {
        TextView adName = (TextView) findViewById(R.id.adName);
        TextView adSalary = (TextView) findViewById(R.id.adSalary);

        adName.setText(selectedAd.getTitle());
        adSalary.setText(selectedAd.getSalaryRange());
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
}