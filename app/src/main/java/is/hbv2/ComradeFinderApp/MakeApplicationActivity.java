package is.hbv2.ComradeFinderApp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import is.hbv2.ComradeFinderApp.Entities.Application;

public class MakeApplicationActivity extends FragmentActivity implements ApplicationFragment.Callbacks {

    private Button mAppButton;

    private LinearLayout mMakeApplication;


    private EditText mName;
    private EditText mEmail;
    private EditText mPhoneNumber;
    private EditText mPosition;
    private EditText mLastCompany;
    private EditText mExtra;
    private Application mApp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_application);
        mMakeApplication = findViewById(R.id.MakeApplication);


        createAppFragment();
        createLoginFragment();

        mAppButton = findViewById(R.id.registerApp_button);
        mAppButton.setOnClickListener(view -> {
            mName = findViewById(R.id.name_Entry);
            mEmail = findViewById(R.id.email_Entry);
            mPhoneNumber = findViewById(R.id.phone_Entry);
            mPosition = findViewById(R.id.position_Entry);
            mLastCompany = findViewById(R.id.lastCompany_Entry);
            mExtra = findViewById(R.id.adExtra_Entry);


            getInfoFromForm();
        });
    }


    @Override
    public void onBackPressed() {
        toggleAppFragment(false);
    }

    private void createAppFragment() {

        ApplicationFragment fragment = ApplicationFragment.newInstance();
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.application_fragment_container, fragment)
                // .addToBackStack(null)
                .hide(fragment)
                .commit();
    }

    private void toggleAppFragment(boolean toShow) {
        FragmentManager fm = getSupportFragmentManager();
        ApplicationFragment fragment =
                (ApplicationFragment) fm.findFragmentById(R.id.application_fragment_container);

        if (fragment != null) {
            fragment.setup(mApp, true);
            FragmentTransaction ft = fm.beginTransaction();
            if (toShow) {
                ft.show(fragment);
                mMakeApplication.setVisibility(View.INVISIBLE);

            } else {
                ft.hide(fragment);
                mMakeApplication.setVisibility(View.VISIBLE);
            }
            ft.commit();
        }
    }


    private void getInfoFromForm() {
        String name = mName.getText().toString();
        if (name.equals("")) {
            Toast.makeText(getBaseContext(), "Name is Empty", Toast.LENGTH_LONG).show();
            return;
        }
        String email = mEmail.getText().toString();
        if (email.equals("")) {
            Toast.makeText(getBaseContext(), "Email is Empty", Toast.LENGTH_LONG).show();
            return;
        }
        String phoneNumber = mPhoneNumber.getText().toString();
        if (phoneNumber.equals("")) {
            Toast.makeText(getBaseContext(), "Phone number is Empty", Toast.LENGTH_LONG).show();
            return;
        }
        String position = mPosition.getText().toString();
        String lastCompany = mLastCompany.getText().toString();
        if (!lastCompany.equals("")) lastCompany = "Last Company Worked For: " + lastCompany;
        String extra = mExtra.getText().toString();

        String application =
                "Name: " + name + '\n' +
                "Email: " + email + "\n" +
                "Phone Number: " + phoneNumber + "\n" +
                lastCompany + "\n" +
                extra + "\n";

        mApp = new Application(null, null);
        mApp.setExtraInfo(application);

        toggleAppFragment(true);

    }

    public void acceptApp() {
        Log.d("accept App", "acceptApp");
        // TODO
        // PUT AD INTO BACKEND
        // GO TO HOMEPAGE?
        Intent i = new Intent(MakeApplicationActivity.this, HomeActivity.class);
        startActivity(i);
    }


    // Puts LoginStatus fragment in login_fragment_container
    private void createLoginFragment() {
        Fragment login = new LoginStatusFragment();
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .add(R.id.login_fragment_container, login)
                .commit();
    }
}
