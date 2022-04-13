package is.hbv2.ComradeFinderApp;

import android.content.Context;
import android.content.Intent;
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

    private View mThisView;
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

    private Callbacks mCallbacks;

    // Host activity must implement this
    public interface Callbacks {
      //  void onLogOut();
        void login();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallbacks = (Callbacks) context;
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
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
        mThisView = inflater.inflate(R.layout.fragment_login_status, container, false);

        setLoggedUser(mLoggedUser);

        return mThisView;
    }



    // Function used to update this fragment with a logged user.
    // Pass "" or null to logout.
    public void setLoggedUser(String user) {
        mLogButton = (Button) mThisView.findViewById(R.id.buttonLogin);
        if (user != "" && user != null) {
            mLoggedUser = user;
            mLoginText = (TextView) mThisView.findViewById(R.id.textViewSignedUser);
            mLoginText.setText(mLoggedUser);
            Log.d(TAG, "onCreateView: " + mLoggedUser + ".");
            mLogButton.setText(R.string.logout_text);
            mLogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    setLoggedUser("");
                    mLogButton.setText(R.string.login_text);

                }
            });
        } else {
            mLogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mCallbacks.login();
                }
            });
        }
    }
}