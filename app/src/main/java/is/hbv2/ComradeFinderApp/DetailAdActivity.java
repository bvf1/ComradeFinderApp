package is.hbv2.ComradeFinderApp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import is.hbv2.ComradeFinderApp.Entities.Ad;
import is.hbv2.ComradeFinderApp.Network.NetworkCallback;
import is.hbv2.ComradeFinderApp.Network.NetworkManager;

public class DetailAdActivity extends AppCompatActivity implements LoginStatusFragment.Callbacks{
    private final String TAG = "DetailAdActivity";
    private NetworkManager mNetworkManager;
    ActivityResultLauncher<Intent> resultLauncher;


    Ad selectedAd;
    private ListView listView;
    private String mUsername;
    private boolean mIsCompany;
    private TextView mErrorText;
    private Button mApplyButton;
    private ProgressBar mLoading;
    private LoginStatusFragment mLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_ad);

        Intent prevIntent = getIntent();
        mUsername = prevIntent.getStringExtra("username");
        Log.d("user", ""+mUsername);


        resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        Intent intent = result.getData();

                        if (intent != null) {
                            Intent data = result.getData();

                            mUsername = data.getStringExtra("user");
                            mIsCompany = data.getBooleanExtra("isCompany", false);
                            updateLoginFragment();
                            if (mIsCompany) {
                                updateIsCompany();
                            } else {
                                updateIsUser();
                            }
                            Log.d("user", mUsername);
                            Log.d("user", "is company"+ mIsCompany);
                        }
                    }
                });


        mNetworkManager = NetworkManager.getInstance(this);
        getSelectedAd();
        setValues();
        createLoginFragment();
    }

    private void getSelectedAd() {
        Intent previousIntent = getIntent();
        Long parsedStringID = previousIntent.getLongExtra("id", 0);
        mUsername = previousIntent.getStringExtra("username");
        mIsCompany = previousIntent.getBooleanExtra("isCompany", false);
        Log.d(TAG,"Selected ad ID: "+parsedStringID.toString());
        if (HomeActivity.ads.size() == 0) {
            HomeActivity.ads.addAll(dummyAds());
        }
        selectedAd = HomeActivity.ads.get(Math.toIntExact((parsedStringID)));
    }

    private void setValues() {
        //TODO: taka test og setja alvöru questions
        List<String> test = selectedAd.getExtraQuestions();
        //test.add("banana?");
        //test.add("is jon?");

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

        if (mUsername == null || mUsername.equals("")) {
            updateNoUser();
        } else if (mIsCompany) {
            updateIsCompany();
        } else {
            updateIsUser();
        }
    }

    private void updateIsCompany() {
        mApplyButton = (Button) findViewById(R.id.makeApplicationButton);
        mErrorText = (TextView) findViewById(R.id.adsDetailUserError);
        mLoading = (ProgressBar) findViewById(R.id.adsDetailLoadingAnimation);
        mErrorText.setVisibility(View.GONE);
        mApplyButton.setText(R.string.delete_text);
        if (!mUsername.equals(selectedAd.getCompanyUsername())) {
            mApplyButton.setEnabled(false);
            mApplyButton.setVisibility(View.GONE);
            return;
        }
        mApplyButton.setEnabled(true);
        mApplyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("click","yes");
                mErrorText.setVisibility(View.GONE);
                mApplyButton.setEnabled(false);
                mLoading.setVisibility(View.VISIBLE);
                mNetworkManager.deleteAdByID(selectedAd.getID(), new NetworkCallback<Boolean>() {
                    @Override
                    public void onSuccess(Boolean result) {
                        if (result == null || !result) {
                            Log.d(TAG, "delete ad failed");
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mErrorText.setText(R.string.deleteFailed_text);
                                    mErrorText.setVisibility(View.VISIBLE);
                                    mApplyButton.setEnabled(true);
                                    mLoading.setVisibility(View.GONE);
                                }
                            });
                            return;
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        });
                    }

                    @Override
                    public void onFailure(String errorString) {
                        Log.d(TAG, "delete ad failed");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mErrorText.setText(R.string.deleteFailed_text);
                                mErrorText.setVisibility(View.VISIBLE);
                                mApplyButton.setEnabled(true);
                                mLoading.setVisibility(View.GONE);
                            }
                        });
                    }
                });
            }
        });
    }

    private void updateNoUser() {
        mApplyButton = (Button) findViewById(R.id.makeApplicationButton);
        mApplyButton.setEnabled(false);
        mApplyButton.setText(R.string.create_application_text);
        mApplyButton.setVisibility(View.VISIBLE);
        mErrorText = (TextView) findViewById(R.id.adsDetailUserError);
        mErrorText.setText(R.string.applyError_notLoggedIn);
        mErrorText.setVisibility(View.VISIBLE);
    }

    private void updateIsUser() {
        mApplyButton = (Button) findViewById(R.id.makeApplicationButton);
        mApplyButton.setEnabled(true);
        mApplyButton.setText(R.string.create_application_text);
        mApplyButton.setVisibility(View.VISIBLE);
        mErrorText = (TextView) findViewById(R.id.adsDetailUserError);
        mErrorText.setVisibility(View.GONE);
        mApplyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Go to Application Activity
            }
        });
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
        // user is logged in
        mLogin = new LoginStatusFragment().newInstance(mUsername);
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .add(R.id.login_fragment_container, mLogin)
                .commit();
    }

    private void updateLoginFragment() {
        FragmentManager fm = getSupportFragmentManager();
        LoginStatusFragment mLogin = (LoginStatusFragment) fm.findFragmentById(R.id.login_fragment_container);
        if (mLogin != null) {
            Log.d("update LoginFragment", mUsername+mLogin.toString());
            mLogin.setLoggedUser(mUsername);
        } else {
            createLoginFragment();
        }
    }

    public void login() {
        Intent i = new Intent(DetailAdActivity.this, LoginActivity.class);

        i.setType("String");
        resultLauncher.launch(i);
    }

    public void register() {
        login();
        Intent i = new Intent(DetailAdActivity.this, RegisterActivity.class);
        startActivity(i);
    }

    public void logout() {
        mUsername = "";
        updateLoginFragment();
        updateNoUser();
    }
}

