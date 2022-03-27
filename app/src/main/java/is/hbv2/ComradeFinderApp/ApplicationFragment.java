package is.hbv2.ComradeFinderApp;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import is.hbv2.ComradeFinderApp.Entities.Application;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ApplicationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ApplicationFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "app";
    private static final String TAG = "ApplicationFragment";

    private TextView mShowApplication;

    private LinearLayout mAcceptReject;
    private Button mAcceptButton;
    private Button mBackButton;

    public ApplicationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ApplicationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ApplicationFragment newInstance() {
        ApplicationFragment fragment = new ApplicationFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_application, container, false);
        mShowApplication = view.findViewById(R.id.application);

        mAcceptReject = view.findViewById(R.id.accept_reject);
        mAcceptButton = view.findViewById(R.id.accept_button);
        mBackButton = view.findViewById(R.id.reject_button);

        mBackButton.setOnClickListener(v -> {
            getActivity().onBackPressed();
        });

        mAcceptButton.setOnClickListener(v -> {
            mCallbacks.acceptApp();
        });

        return view;
    }

    private Callbacks mCallbacks;

    public interface Callbacks {
        void acceptApp();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallbacks = (ApplicationFragment.Callbacks) context;
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    public void setup(Application app, boolean showAcceptReject) {
        if (showAcceptReject) {
            mAcceptReject.setVisibility(View.VISIBLE);
            mAcceptButton.setText("Submit Application");
        }

        mShowApplication.setText(app.getExtraInfo());

    }
}