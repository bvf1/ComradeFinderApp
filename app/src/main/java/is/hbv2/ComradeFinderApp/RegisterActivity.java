package is.hbv2.ComradeFinderApp;

import androidx.appcompat.app.AppCompatActivity;

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
        mLoading = (ProgressBar) findViewById(R.id.loadingAnimation);
        mWrongLoginText = (TextView) findViewById(R.id.incorrectLoginText);

        mRegisterButton = (Button) findViewById(R.id.register_button);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = mUsernameText.getText().toString();
                // TODO: Handle registration
            }
        });

        mCompanyNameText = (EditText) findViewById(R.id.editTextCompanyName);
        mSSNText = (EditText) findViewById(R.id.editTextSSN);

        mCompanySwitch = (Switch) findViewById(R.id.isCompanySwitch);
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

    // Thread used to login.
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

            // TODO: Decide what fields in the registration are required
            if (this.username == null || this.password == null ||
                    this.username.equals("") || this.password.equals("")) {
                Log.d(TAG, "run: Empty login");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        enableControls(R.string.empty_login_error);
                    }
                });
                return;
            }
            Log.d(TAG, "run: Not empty login:" + this.username);
            /*
            mNetworkManager.login(this.username, this.password, new NetworkCallback<Account>() {
                @Override
                public void onSuccess(Account result) {
                    // TODO: We have confirmed login. Now we need to apply this login.
                    if (result == null) {
                        enableControls(R.string.incorrect_login);
                        return;
                    }
                    Log.d(TAG, "run: Success. User logged in: " + result.getUsername());
                    Log.d(TAG, "run: Class type: " + result.getClass());
                    //savedInstanceState.putString("loggedUser", result.getUsername());

                    // TODO: Then we need to redirect back from LoginActivity.
                    // Go to homepage
                    //Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                    //startActivity(i);
                }

                @Override
                public void onFailure(String errorString) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            enableControls(R.string.incorrect_login);
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