package is.hbv2.ComradeFinderApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import is.hbv2.ComradeFinderApp.Entities.Account;
import is.hbv2.ComradeFinderApp.Network.NetworkCallback;
import is.hbv2.ComradeFinderApp.Network.NetworkManager;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";

    private NetworkManager mNetworkManager;

    private EditText mUsernameText;
    private EditText mPasswordText;
    private EditText mPhoneText;
    private EditText mEmailText;
    private Button mRegisterButton;
    private Switch mCompanySwitch;
    private EditText mCompanyNameText;
    private EditText mSSNText;
    private ProgressBar mLoading;
    private TextView mWrongLoginText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mNetworkManager = NetworkManager.getInstance(this);

        mUsernameText = (EditText) findViewById(R.id.editTextRegisterUsername);
        mPasswordText = (EditText) findViewById(R.id.editTextPassword);
        mPhoneText = (EditText) findViewById(R.id.editTextPhone);
        mEmailText = (EditText) findViewById(R.id.editTextEmailAddress);
        mLoading = (ProgressBar) findViewById(R.id.loadingAnimation2);
        mWrongLoginText = (TextView) findViewById(R.id.incorrectLoginText2);

        mCompanyNameText = (EditText) findViewById(R.id.editTextCompanyName);
        mSSNText = (EditText) findViewById(R.id.editTextSSN);
        mCompanySwitch = (Switch) findViewById(R.id.isCompanySwitch);

        mRegisterButton = (Button) findViewById(R.id.register_button);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                disableControls();

                String username = mUsernameText.getText().toString();
                String password = mPasswordText.getText().toString();
                String phone = mPhoneText.getText().toString();
                String email = mEmailText.getText().toString();

                if (mCompanySwitch.isChecked()) {
                    // Register company instead if switch is active
                    String companyName = mCompanyNameText.getText().toString();
                    String SSN = mSSNText.getText().toString();
                    RegisterActivity.RegisterCompanyRunnable registerRunnable = new RegisterActivity.RegisterCompanyRunnable(username, password, phone, email, companyName, SSN, savedInstanceState);
                    Thread t = new Thread(new ThreadGroup("registerUser"), registerRunnable);
                    t.start();
                    return;
                }

                RegisterActivity.RegisterUserRunnable registerRunnable = new RegisterActivity.RegisterUserRunnable(username, password, phone, email, savedInstanceState);
                Thread t = new Thread(new ThreadGroup("registerUser"), registerRunnable);
                t.start();
            }
        });

        mCompanySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    mCompanyNameText.setVisibility(View.VISIBLE);
                    mSSNText.setVisibility(View.VISIBLE);
                } else {
                    mCompanyNameText.setVisibility(View.GONE);
                    mSSNText.setVisibility(View.GONE);
                }
            }
        });

    }

    // Thread used to register User.
    class RegisterUserRunnable implements Runnable {
        String username;
        String password;
        String phone;
        String email;
        Bundle savedInstanceState;
        RegisterUserRunnable(String username, String password, String phone, String email, Bundle savedInstanceState) {
            this.username = username;
            this.password = password;
            this.phone = phone;
            this.email = email;
            this.savedInstanceState = savedInstanceState;
        }

        @Override
        public void run() {
            // TODO: We might want to add a timer in case the register takes too long
            // PERFORM LOGIN OPERATION HERE

            if (this.username == null || this.password == null ||
                    this.username.equals("") || this.password.equals("")) {
                Log.d(TAG, "run: Empty registry");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        enableControls(R.string.empty_register_error);
                    }
                });
                return;
            }
            Log.d(TAG, "run: Not empty login:" + this.username);
            mNetworkManager.registerUser(this.username, this.password, this.phone, this.email, new NetworkCallback<Boolean>() {
                @Override
                public void onSuccess(Boolean result) {
                    // Registration complete
                    Log.d(TAG, "run: Success. User created");

                    // Goes back to login
                    Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(i);
                }

                @Override
                public void onFailure(String errorString) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            enableControls(R.string.register_error);
                            Log.e(TAG, errorString);
                        }
                    });
                    return;
                }
            });
            /* == Register isn't implemented network side yet. == */
            Log.d(TAG, "Login thread finished");
        }
    }

    // Thread used to register Company
    class RegisterCompanyRunnable implements Runnable {
        String username;
        String password;
        String phone;
        String email;
        String companyName;
        String SSN;
        Bundle savedInstanceState;
        RegisterCompanyRunnable(String username, String password, String phone, String email, String companyName, String SSN, Bundle savedInstanceState) {
            this.username = username;
            this.password = password;
            this.phone = phone;
            this.email = email;
            this.companyName = companyName;
            this.SSN = SSN;
            this.savedInstanceState = savedInstanceState;
        }

        @Override
        public void run() {
            // TODO: We might want to add a timer in case the register takes too long
            // PERFORM LOGIN OPERATION HERE

            if (this.username == null || this.password == null ||
                    this.username.equals("") || this.password.equals("")) {
                Log.d(TAG, "run: Empty registry");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        enableControls(R.string.empty_register_error);
                    }
                });
                return;
            }
            Log.d(TAG, "run: Not empty login:" + this.username);
            mNetworkManager.registerCompany(this.username, this.password, this.phone, this.email, this.companyName, this.SSN, new NetworkCallback<Boolean>() {
                @Override
                public void onSuccess(Boolean result) {
                    // Registration complete
                    Log.d(TAG, "run: Success. User created");

                    // Goes back to login
                    Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(i);
                }

                @Override
                public void onFailure(String errorString) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            enableControls(R.string.register_error);
                            Log.e(TAG, errorString);
                        }
                    });
                    return;
                }
            });
            /* == Register isn't implemented network side yet. == */
            Log.d(TAG, "Login thread finished");
        }
    }



    private void disableControls() {
        mWrongLoginText.setText("");
        mLoading.setVisibility(View.VISIBLE);
        mUsernameText.setEnabled(false);
        mPasswordText.setEnabled(false);
        mPhoneText.setEnabled(false);
        mEmailText.setEnabled(false);
        mCompanyNameText.setEnabled(false);
        mSSNText.setEnabled(false);
        mRegisterButton.setEnabled(false);
    }

    private void enableControls(int stringID) {
        mWrongLoginText.setText(stringID);
        mLoading.setVisibility(View.GONE);
        mUsernameText.setEnabled(true);
        mPasswordText.setEnabled(true);
        mPhoneText.setEnabled(true);
        mEmailText.setEnabled(true);
        mCompanyNameText.setEnabled(true);
        mSSNText.setEnabled(true);
        mRegisterButton.setEnabled(true);
    }

}