package is.hbv2.ComradeFinderApp;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.slider.RangeSlider;

import java.util.ArrayList;

import is.hbv2.ComradeFinderApp.Entities.Ad;

public class MakeAdvertisementActivity extends FragmentActivity { //implements View.OnClickListener, AcceptAdvertisementFragment. {DialogListener {


    private Button mAdButton;
    private Button mAddQuestionButton;
    private Button mAddTagsButton;


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
        createAdFragment();



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


        // add t
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


        // register ad
        mAdButton = findViewById(R.id.registerAd_button);
        mAdButton.setOnClickListener(view -> {
            Log.d("register", "register button pressed)");
            mTitle = findViewById(R.id.title_entry);
            mSalary = findViewById(R.id.priceRange_entry);
            mDescription = findViewById(R.id.description_entry);
            getInfoFromForm();
            showAdFragment();
        });
    }



    private void createAdFragment() {
        AdvertisementFragment fragment = AdvertisementFragment.newInstance();
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .add(R.id.advertisement_fragment_container, fragment)
                .addToBackStack(null)
                .hide(fragment)
                .commit();
    }

    private void showAdFragment() {

        FragmentManager fm = getSupportFragmentManager();

        AdvertisementFragment fragment = (AdvertisementFragment) fm.findFragmentById(R.id.advertisement_fragment_container);


        if (fragment != null) {
            fragment.setAd(mAd);
            Log.d("showadfragment", "not null");
            FragmentTransaction ft = fm.beginTransaction();
            ft.show(fragment).commit();

        }
    }

    private void getInfoFromForm() {
            mAd = new Ad(
                    mTitle.getText().toString(),
                    mDescription.getText().toString(),
                    mSalary.getValues().toString(),
                    questions,
                    "temp",
                    "temp",
                    addedTags
            );

        showAdFragment();

        Log.d("makeAdd","in getform form, ");


        // put into backend
    }


}