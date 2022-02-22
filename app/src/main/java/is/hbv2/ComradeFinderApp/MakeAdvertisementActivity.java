package is.hbv2.ComradeFinderApp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.slider.RangeSlider;

public class MakeAdvertisementActivity extends AppCompatActivity {

    private Button mAdButton;

    private EditText mTitle;
    private RangeSlider mSalary ; // what kind of widget to use
    private EditText mDescription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_advertisement);

        mAdButton = findViewById(R.id.registerAd_button);
        mAdButton.setOnClickListener(view -> {

            mTitle = findViewById(R.id.title_entry);
            mSalary = findViewById(R.id.priceRange_entry);
            mDescription = findViewById(R.id.description_entry);

            getInfoFromForm();
            /// where do we want to go
        });
    }



    private void getInfoFromForm() {
        String title = mTitle.getText().toString();
        // mSalary
        String description = mDescription.getText().toString();

        // put into backend
    }
}