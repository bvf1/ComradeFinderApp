package is.hbv2.ComradeFinderApp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {

    private EditText mUsername;
    private EditText mPassword;
    private Button mRegisterBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mUsername = (EditText) findViewById(R.id.editTextRegisterUsername);
        mPassword = (EditText) findViewById(R.id.editTextPassword);

        mRegisterBtn = (Button) findViewById(R.id.register_button);
        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = mUsername.getText().toString();
                // TODO: Handle registration
            }
        });

    }
}