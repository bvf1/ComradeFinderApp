package is.hbv2.ComradeFinderApp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginStatusFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginStatusFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "loggedUser";
    private static final String TAG = "LoginStatusFragment";

    private String mLoggedUser;
    private TextView mLoginText;
    private Button mLogButton;

    public LoginStatusFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param loggedUser String of user that is logged in. "" if not logged in.
     * @return A new instance of fragment LoginStatusFragment.
     */
    public static LoginStatusFragment newInstance(String loggedUser) {
        LoginStatusFragment fragment = new LoginStatusFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, loggedUser);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mLoggedUser = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login_status, container, false);

        mLogButton = (Button) view.findViewById(R.id.buttonLogin);
        if (mLoggedUser != "" && mLoggedUser != null) {
            mLoginText = (TextView) view.findViewById(R.id.textViewSignedUser);
            mLoginText.setText(mLoggedUser);
            Log.d(TAG, "onCreateView: " + mLoggedUser + ".");
            mLogButton.setText("Logout"); // Note that this is terrible but doesn't want to cooperate. So we're stuck with this.
            mLogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // TODO: Handle logout
                }
            });
        } else {
            mLogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // TODO: Handle login redirect
                }
            });
        }

        return view;
    }
}