package is.hbv2.ComradeFinderApp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.slider.RangeSlider;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import is.hbv2.ComradeFinderApp.Entities.Ad;
import is.hbv2.ComradeFinderApp.Network.NetworkCallback;
import is.hbv2.ComradeFinderApp.Network.NetworkManager;

public class MakeAdvertisementActivity extends FragmentActivity implements AdvertisementFragment.Callbacks, LoginStatusFragment.Callbacks { //implements View.OnClickListener, AcceptAdvertisementFragment. {DialogListener {

    private static final String TAG = "MakeAdvertisementActivity";
    private NetworkManager mNetworkManager;

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
    private String mCompany;
    private Ad mAd;
    private ProgressBar mLoading;
    private TextView mErrorText;
    ActivityResultLauncher<Intent> resultLauncher;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_advertisement);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        mNetworkManager = NetworkManager.getInstance(this);
        mMakeAdvertisement = findViewById(R.id.MakeAdvertisement);
        mLoading = findViewById(R.id.loadingAnimationAdCreate);
        mErrorText = findViewById(R.id.incorrectAdCreateText);
        Intent prevIntent = getIntent();
        mCompany = prevIntent.getStringExtra("username");

        resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        Intent intent = result.getData();

                        if (intent != null) {
                            Intent data = result.getData();
                            String user = data.getStringExtra("username");
                            Log.d(TAG, "Checking whether user is null: " + user);
                            mCompany = user;
                        }
                    }
                }
        );

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
        String salary = String.format(Locale.getDefault(), "%.0f kr", s.get(0));
        Log.d("salary",salary);

        mAd = new Ad(
                title,
                description,
                salary,
                questions,
                mCompany,
                "Empty for now",
                addedTags
        );

        toggleAdFragment(true);
    }

    public void acceptAd() {
        Log.d("accept Ad", "acceptAd");
        mNetworkManager.createAd(mAd.getTitle(), mAd.getDescription(), mAd.getSalaryRange(), mAd.getExtraQuestions(), mAd.getCompanyUsername(), mAd.getTags(), new NetworkCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean result) {
                Intent i = new Intent(MakeAdvertisementActivity.this, HomeActivity.class);
                startActivity(i);
            }

            @Override
            public void onFailure(String errorString) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //enableControls(R.string.ad_create_failed_text);
                    }
                });
            }
        });


    }
    // Puts LoginStatus fragment in login_fragment_container
    private void createLoginFragment() {
        LoginStatusFragment login = new LoginStatusFragment().newInstance("me");
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
        Intent i = new Intent(MakeAdvertisementActivity.this, HomeActivity.class);
        startActivity(i);
    }

    private void disableControls() {
        mErrorText.setText("");
        mLoading.setVisibility(View.VISIBLE);
        mTitle.setEnabled(false);
        mDescription.setEnabled(false);
        mSalary.setEnabled(false);
        mTags.setEnabled(false);
        mMakeAdvertisement.setEnabled(false);
        mAdButton.setEnabled(false);
        mAddQuestionButton.setEnabled(false);
        mAddTagsButton.setEnabled(false);
    }

    private void enableControls(int stringID) {
        mErrorText.setText(stringID);
        mLoading.setVisibility(View.GONE);
        mTitle.setEnabled(true);
        mDescription.setEnabled(true);
        mSalary.setEnabled(true);
        mTags.setEnabled(true);
        mMakeAdvertisement.setEnabled(true);
        mAdButton.setEnabled(true);
        mAddQuestionButton.setEnabled(true);
        mAddTagsButton.setEnabled(true);
    }
}