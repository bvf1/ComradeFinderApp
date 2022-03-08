package is.hbv2.ComradeFinderApp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

public class RegisterActivity extends AppCompatActivity {

    private EditText mUsername;
    private EditText mPassword;
    private EditText mPhone;
    private EditText mEmail;
    private Button mRegisterBtn;
    private Switch mCompanySwitch;
    private EditText mCompanyName;
    private EditText mSSN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mUsername = (EditText) findViewById(R.id.editTextRegisterUsername);
        mPassword = (EditText) findViewById(R.id.editTextPassword);
        mPhone = (EditText) findViewById(R.id.editTextPhone);
        mEmail = (EditText) findViewById(R.id.editTextEmailAddress);

        mRegisterBtn = (Button) findViewById(R.id.register_button);
        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = mUsername.getText().toString();
                // TODO: Handle registration
            }
        });

        mCompanyName = (EditText) findViewById(R.id.editTextCompanyName);
        mSSN = (EditText) findViewById(R.id.editTextSSN);

        mCompanySwitch = (Switch) findViewById(R.id.isCompanySwitch);
        mCompanySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    mCompanyName.setVisibility(View.VISIBLE);
                    mSSN.setVisibility(View.VISIBLE);
                } else {
                    mCompanyName.setVisibility(View.GONE);
                    mSSN.setVisibility(View.GONE);
                }
            }
        });

    }
}