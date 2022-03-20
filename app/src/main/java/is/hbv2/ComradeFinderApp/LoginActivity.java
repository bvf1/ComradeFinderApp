package is.hbv2.ComradeFinderApp;

import androidx.appcompat.app.AppCompatActivity;

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

import is.hbv2.ComradeFinderApp.Entities.User;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

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


        // Fetch text inputs
        mUsernameText = (EditText) findViewById(R.id.editTextUsername);
        mPasswordText = (EditText) findViewById(R.id.editTextPassword);
        getLoading();
        getIncorrectLoginText();

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
            // Right now, this only tests what happens while logging in.
            Log.d(TAG, "run: thread started run");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.d(TAG, "run: thread finished run");

            if (this.username == null || this.password == null ||
                    this.username.equals("") || this.password.equals("")) {
                Log.d(TAG, "run: Empty login");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        enableControls(R.string.empty_login_error);
                    }
                });
            } else {
                Log.d(TAG, "run: Not empty login:" + this.username);
                if (!mUserFind.containsKey(this.username)) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            enableControls(R.string.incorrect_login);
                        }
                    });
                    return;
                }
                int index = mUserFind.get(this.username);
                User u = mUserData.get(index);
                if (this.username.equals(u.getUsername()) && this.password.equals(u.getPassword())) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // TODO: We have confirmed login. Now we need to apply this login.
                            Log.d(TAG, "run: Success. User logged in: " + u.getUsername());
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            enableControls(R.string.incorrect_login);
                        }
                    });
                }
            }
        }
    }
    /**/

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

    private void getIncorrectLoginText() {
        if (mWrongLoginText == null) {
            mWrongLoginText = (TextView) findViewById(R.id.incorrectLoginText);
        }
    }

    private void getLoading() {
        if (mLoading == null) {
            mLoading = (ProgressBar) findViewById(R.id.loadingAnimation);
        }
    }
    /**/
}