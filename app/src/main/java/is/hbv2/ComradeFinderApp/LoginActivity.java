package is.hbv2.ComradeFinderApp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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
    // NOTE: This implementation of keeping users is only being used while testing.
    private HashMap<String, Integer> mUserFind;
    private ArrayList<User> mUserData;

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


        // Fetch text inputs
        mUsernameText = (EditText) findViewById(R.id.editTextUsername);
        mPasswordText = (EditText) findViewById(R.id.editTextPassword);
        mLoading = (ProgressBar) findViewById(R.id.loadingAnimation);

        // Setup login
        mLoginButton = (Button) findViewById(R.id.login_button);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoading.setVisibility(View.VISIBLE);

                LoginRunnable loginRunnable = new LoginRunnable(mUsernameText.getText().toString(), mPasswordText.getText().toString());
                Thread t = new Thread(loginRunnable);
                t.start();
                // TODO: Handle login
                try {
                    t.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //mLoading.setVisibility(View.GONE);

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

        LoginRunnable(String username, String password) {
            this.username = username;
            this.password = password;
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
        }
    }
}