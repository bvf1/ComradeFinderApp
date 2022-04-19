package is.hbv2.ComradeFinderApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

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
        String parsedStringID = previousIntent.getStringExtra("id");
        selectedAd = HomeActivity.ads.get(Integer.valueOf(parsedStringID));
    }

    private void setValues() {
        TextView adName = (TextView) findViewById(R.id.adName);
        TextView adSalary = (TextView) findViewById(R.id.adSalary);

        adName.setText(selectedAd.getTitle());
        adSalary.setText(selectedAd.getSalaryRange());
    }
}