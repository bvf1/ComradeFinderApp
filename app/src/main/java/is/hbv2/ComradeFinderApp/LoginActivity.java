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
import is.hbv2.ComradeFinderApp.Entities.User;
import is.hbv2.ComradeFinderApp.Network.NetworkCallback;
import is.hbv2.ComradeFinderApp.Network.NetworkManager;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    private NetworkManager mNetworkManager;

    private EditText mUsernameText;
    private EditText mPasswordText;
    private Button mLoginButton;
    private ProgressBar mLoading;
    private TextView mWrongLoginText;


    // NOTE: This implementation of keeping users is only being used while testing.
    //       We will change this once we establish access to the backend.
    private static HashMap<String, Integer> mUserFind;
    private static ArrayList<User> mUserData;

    private void initializeUsers() {
        mUserFind = new HashMap<>();
        mUserData = new ArrayList<>();

        User user1 = new User("bvf1", "111", "8888888", "bvf1@hi.is", "Bara", "Bára the BEAST");
        mUserFind.put(user1.getUsername(), 0);
        mUserData.add(0, user1);
        User user2 = new User("gvg8", "222", "8885555", "gvg8@hi.is", "Gisli", "Best Bossman");
        mUserFind.put(user2.getUsername(), 1);
        mUserData.add(1, user2);
        User user3 = new User("jap13", "333", "8882222", "jap13@hi.is", "Jon", "Expert communicator");
        mUserFind.put(user3.getUsername(), 2);
        mUserData.add(2, user3);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            // User may already be logged in

        }
        setContentView(R.layout.activity_login);

        initializeUsers();
        mNetworkManager = NetworkManager.getInstance(this);

        // Fetch text inputs
        mUsernameText = (EditText) findViewById(R.id.editTextUsername);
        mPasswordText = (EditText) findViewById(R.id.editTextPassword);
        mLoading = (ProgressBar) findViewById(R.id.loadingAnimation);
        mWrongLoginText = (TextView) findViewById(R.id.incorrectLoginText);

        // Setup login
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
                /**/
                // savedInstanceState.putString("username", mUsernameText.getText().toString());
                // savedInstanceState.putString("userID", ???);
                // Use java.util.concurrent for this.
                // To make users stay logged in after closing app, use SQLite, cookies.
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
                    savedInstanceState.putString("loggedUser", result.getUsername());

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