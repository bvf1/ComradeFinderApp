package is.hbv2.ComradeFinderApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import is.hbv2.ComradeFinderApp.Entities.Account;
import is.hbv2.ComradeFinderApp.Entities.Company;
import is.hbv2.ComradeFinderApp.Entities.User;
import is.hbv2.ComradeFinderApp.Network.NetworkCallback;
import is.hbv2.ComradeFinderApp.Network.NetworkManager;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";


    private NetworkManager mNetworkManager;

    private EditText mUsernameText;
    private EditText mPasswordText;
    private Button mLoginButton;
    private Button mRegisterButton;
    private ProgressBar mLoading;
    private TextView mWrongLoginText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        mNetworkManager = NetworkManager.getInstance(this);

        // Fetch text inputs
        mUsernameText = (EditText) findViewById(R.id.editTextUsername);
        mPasswordText = (EditText) findViewById(R.id.editTextPassword);
        mLoading = (ProgressBar) findViewById(R.id.loadingAnimation);
        mWrongLoginText = (TextView) findViewById(R.id.incorrectLoginText);

        // Setup login
        mRegisterButton = (Button) findViewById(R.id.register_button);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });
        mLoginButton = (Button) findViewById(R.id.login_button);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                disableControls();

                String username = mUsernameText.getText().toString();
                String password = mPasswordText.getText().toString();

                LoginRunnable loginRunnable = new LoginRunnable(username, password, savedInstanceState);
                Thread t = new Thread(new ThreadGroup("fetchLogin"), loginRunnable);
                t.start();
            }
        });


    }


    // Thread used to login.
    class LoginRunnable implements Runnable {
        String username;
        String password;
        Bundle savedInstanceState;
        LoginRunnable(String username, String password, Bundle savedInstanceState) {
            this.username = username;
            this.password = password;
            this.savedInstanceState = savedInstanceState;
        }

        @Override
        public void run() {
            // TODO: We might want to add a timer in case the login takes too long
            // TODO: Maybe skip login if login request has been sent previously with same info
            // PERFORM LOGIN OPERATION HERE

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
            mNetworkManager.login(this.username, this.password, new NetworkCallback<Object>() {
                @Override
                public void onSuccess(Object result) {
                    if (result == null) {
                        enableControls(R.string.incorrect_login);
                        return;
                    }
                    Log.d(TAG, "run: Class type: " + result.getClass());
                    Log.d(TAG, "Object result contains: " + result.toString());
                    //Log.d(TAG, "CompanyPhone: " + ((Company) result).getCompanyPhone());

                    User user;
                    Company company;
                    Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                    try {
                        if (((Company) result).getCompanyPhone() != null) {
                            company = (Company) result;
                            Log.d(TAG, "run: Success. User logged in: " + company.getUsername());
                            Log.d(TAG, "run: Class type: " + company.getClass());
                            i.putExtra("user", company.getUsername());
                            i.putExtra("isCompany", true);
                        }
                    } catch (Exception e) {
                        Log.d(TAG, "Login not of type Company. Got error: " + e.toString());
                    }
                    try {
                        user = (User) result;
                        Log.d(TAG, "run: Success. User logged in: " + user.getUsername());
                        Log.d(TAG, "run: Class type: " + user.getClass());
                        i.putExtra("user", user.getUsername());
                        i.putExtra("isCompany", false);
                    } catch (Exception e) {
                        Log.d(TAG, "Login not of type User. Got error: " + e.toString());
                    }

                    // return to home activity
                    setResult(RESULT_OK,i);
                    setResult(RESULT_OK,i);
                    finish();
                }

                @Override
                public void onFailure(String errorString) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            enableControls(R.string.incorrect_login);
                        }
                    });
                }
            });
            Log.d(TAG, "Login thread finished");
        }
    }



    private void disableControls() {
        mWrongLoginText.setText("");
        mLoading.setVisibility(View.VISIBLE);
        mUsernameText.setEnabled(false);
        mPasswordText.setEnabled(false);
        mLoginButton.setEnabled(false);
    }

    private void enableControls(int stringID) {
        mWrongLoginText.setText(stringID);
        mLoading.setVisibility(View.GONE);
        mUsernameText.setEnabled(true);
        mPasswordText.setEnabled(true);
        mLoginButton.setEnabled(true);
    }

}