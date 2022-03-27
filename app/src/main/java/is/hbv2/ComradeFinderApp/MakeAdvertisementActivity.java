package is.hbv2.ComradeFinderApp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.slider.RangeSlider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import is.hbv2.ComradeFinderApp.Entities.Ad;

public class MakeAdvertisementActivity extends FragmentActivity implements AdvertisementFragment.Callbacks { //implements View.OnClickListener, AcceptAdvertisementFragment. {DialogListener {


    private Button mAdButton;
    private Button mAddQuestionButton;
    private Button mAddTagsButton;

    private LinearLayout mMakeAdvertisement;

    private EditText mTitle;
    private EditText mDescription;
    private RangeSlider mSalary ; // what kind of widget to use
    private ArrayList<String> questions = new ArrayList<>();
    private ArrayList<String> addedTags = new ArrayList<>();
    private ListView mQuestionsView;
    private ListView mAddedTagsView;
    private Spinner mTags;
    private Ad mAd;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_advertisement);
        mMakeAdvertisement = findViewById(R.id.MakeAdvertisement);

        createAdFragment();
        createLoginFragment();


        // adding questions to list view
        mQuestionsView = findViewById(R.id.extraQuestionsView);
        ArrayAdapter<String> questionAdapter = new ArrayAdapter<String>(
                MakeAdvertisementActivity.
                        this, android.R.layout.simple_list_item_1, questions);
        mQuestionsView.setAdapter(questionAdapter);

        // adding item to questions
        mAddQuestionButton = (Button) findViewById(R.id.addQuestion_button);
        mAddQuestionButton.setOnClickListener(view -> {
            EditText text = findViewById(R.id.addQuestion);
            String question = text.getText().toString();
            if(questions.contains(question)) {
                Toast.makeText(getBaseContext(), "Item Already Added to the Array", Toast.LENGTH_LONG).show();
            }

            else if (question == null || question.trim().equals("")) {
                Toast.makeText(getBaseContext(), "Input Fields is Empty", Toast.LENGTH_LONG).show();
            }
            else {
                questions.add(question);
                mQuestionsView.setAdapter(questionAdapter);
                ((EditText) findViewById(R.id.addQuestion)).setText("");
            }
        });

        // changing items
        mQuestionsView.setOnItemClickListener((adapterView, view, i, l) -> {
            questions.remove(i);
            questionAdapter.notifyDataSetChanged();
        });

        // priceRange
        mSalary = findViewById(R.id.priceRange_entry);
        mSalary.setLabelFormatter(value -> {
            //It is just an example
            if (value == 3.0f)
                return "TEST";
            return String.format("%,.0f", value);
        });

        // adding tags
        String[] tags = { "tag 1", "tag 2", "tag 3"};
        mTags = findViewById(R.id.allTags);
        ArrayAdapter tagAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,tags);
        tagAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        mTags.setAdapter(tagAdapter);

        // adding tags to list view
        mAddedTagsView = findViewById(R.id.addedTagsView);
        ArrayAdapter<String> addedTagsAdapter = new ArrayAdapter<String>(
                MakeAdvertisementActivity.this,
                android.R.layout.simple_list_item_1, addedTags);
        mAddedTagsView.setAdapter(addedTagsAdapter);


        // add tag to ad
        mAddTagsButton = (Button) findViewById(R.id.addTag_button);
        mAddTagsButton.setOnClickListener(view -> {
            int index = mTags.getLastVisiblePosition();
            String tag = tags[index];

            if(addedTags.contains(tag)) {
                Toast.makeText(getBaseContext(), "Item Already Added to the Array", Toast.LENGTH_LONG).show();
            }
            else {
                addedTags.add(tag);
                mAddedTagsView.setAdapter(addedTagsAdapter);
            }
        });

        // PRESS REGISTER AD
        mAdButton = findViewById(R.id.registerAd_button);
        mAdButton.setOnClickListener(view -> {
            Log.d("register", "register button pressed)");
            mTitle = findViewById(R.id.title_entry);
            mSalary = findViewById(R.id.priceRange_entry);
            mDescription = findViewById(R.id.description_entry);
            getInfoFromForm();
        });
    }



    @Override
    public void onBackPressed() {
        toggleAdFragment(false);
    }

    private void createAdFragment() {

        AdvertisementFragment fragment = AdvertisementFragment.newInstance();
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.advertisement_fragment_container, fragment)
               // .addToBackStack(null)
                .hide(fragment)
                .commit();
    }

    private void toggleAdFragment(boolean toShow) {
        FragmentManager fm = getSupportFragmentManager();
        AdvertisementFragment fragment = (AdvertisementFragment) fm.findFragmentById(R.id.advertisement_fragment_container);
        Log.d("back", "1");

        if (fragment != null) {
            fragment.setup(mAd, true);
            FragmentTransaction ft = fm.beginTransaction();
            if (toShow) {
                ft.show(fragment);
                mMakeAdvertisement.setVisibility(View.INVISIBLE);

            } else {
                ft.hide(fragment);
                mMakeAdvertisement.setVisibility(View.VISIBLE);
            }
            ft.commit();
        }
    }

    private void getInfoFromForm() {
        String title = mTitle.getText().toString();
        if (title.equals("")) { Toast.makeText(getBaseContext(), "Title is Empty", Toast.LENGTH_LONG).show();return; }
        String description = mDescription.getText().toString();
        if (description.equals("")) {
            Toast.makeText(getBaseContext(), "Description is Empty", Toast.LENGTH_LONG).show();
            return;
        }

        List<Float> s = mSalary.getValues();
        List<String> salary = new ArrayList<String>();
        salary.add(String.format("%,.0f", s.get(0)));
        salary.add(String.format("%,.0f", s.get(1)));
        mAd = new Ad(
                title,
                description,
                salary,
                questions,
                null,
                "Empty for now",
                addedTags
        );

        toggleAdFragment(true);
    }

    public void acceptAd() {
        Log.d("accept Ad", "acceptAd");
        // TODO
        // PUT AD INTO BACKEND
        // GO TO HOMEPAGE?
    }
    // Puts LoginStatus fragment in login_fragment_container
    private void createLoginFragment() {
        Fragment login = new LoginStatusFragment();
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .add(R.id.login_fragment_container, login)
                .commit();
    }


}