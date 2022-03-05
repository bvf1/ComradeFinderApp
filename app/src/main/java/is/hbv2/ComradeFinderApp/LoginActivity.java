package is.hbv2.ComradeFinderApp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText mUsernameText;
    private EditText mPasswordText;
    private Button mLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null){
            // User may already be logged in

        }
        setContentView(R.layout.activity_login);


        // Fetch text inputs
        mUsernameText = (EditText) findViewById(R.id.editTextUsername);
        mPasswordText = (EditText) findViewById(R.id.editTextPassword);

        // Setup login
        mLoginButton = (Button) findViewById(R.id.login_button);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Handle login request
                // TODO: case1: Login failed
                // TODO: case2: Login successfull
                savedInstanceState.putString("username", mUsernameText.getText().toString());
                // savedInstanceState.putString("userID", ???);
            }
        });

    }
}