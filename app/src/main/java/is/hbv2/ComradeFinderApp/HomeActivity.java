package is.hbv2.ComradeFinderApp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_home);

        Fragment login = new LoginStatusFragment();
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .add(R.id.login_fragment_container, login)
                .commit();


    }
}