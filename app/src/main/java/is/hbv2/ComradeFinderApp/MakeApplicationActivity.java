package is.hbv2.ComradeFinderApp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class MakeApplicationActivity extends AppCompatActivity {

    private Button mAppButton;

    private EditText mName;
    private EditText mEmail;
    private EditText mPhoneNumber;
    private EditText mPosition;
    private EditText mLastCompany;
    private EditText mExtra;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_application);

        mAppButton = findViewById(R.id.registerApp_button);
        mAppButton.setOnClickListener(view -> {
            mName = findViewById(R.id.name_Entry);
            mEmail = findViewById(R.id.email_Entry);
            mPhoneNumber = findViewById(R.id.phone_Entry);
            mPosition = findViewById(R.id.position_Entry);
            mLastCompany = findViewById(R.id.lastCompany_Entry);
            mExtra = findViewById(R.id.adExtra_Entry);


            getInfoFromForm();
            /// where do we want to got
            // put info in backend
        });
    }



    private void getInfoFromForm() {
        String name = mName.getText().toString();
        String email = mEmail.getText().toString();
        String phoneNumber = mPhoneNumber.getText().toString();
        String position = mPosition.getText().toString();
        String lastCompany = mLastCompany.getText().toString();
        String extra = mExtra.getText().toString();

        // put info into database

    }
}
