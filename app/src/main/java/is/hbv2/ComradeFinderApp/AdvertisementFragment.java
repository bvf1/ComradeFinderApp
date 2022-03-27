package is.hbv2.ComradeFinderApp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import is.hbv2.ComradeFinderApp.Entities.Ad;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdvertisementFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdvertisementFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "ad";
    private static final String TAG = "AdvertisementFragment";

    private TextView mCompany;
    private TextView mTitle;
    private TextView mDescription;
    private TextView mSalary;
    private TextView mQuestions;
    private TextView mPDF;
    private TextView mTags;

    private LinearLayout mAcceptReject;
    private Button mAcceptButton;
    private Button mBackButton;

    public AdvertisementFragment() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AdvertisementFragment.
     */
    public static AdvertisementFragment newInstance() {

        AdvertisementFragment fragment = new AdvertisementFragment();
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
        View view =  inflater.inflate(R.layout.fragment_advertisement, container, false);
        mCompany = view.findViewById(R.id.companyName);
        mTitle = view.findViewById(R.id.adTitle);
        mDescription = view.findViewById(R.id.adDescription);
        mSalary = view.findViewById(R.id.salary);
        mQuestions = view.findViewById(R.id.questions);
        mPDF = view.findViewById(R.id.pdf);
        mTags = view.findViewById(R.id.tags);

        mAcceptReject = view.findViewById(R.id.accept_reject);
        mAcceptButton = view.findViewById(R.id.accept_button);
        mBackButton = view.findViewById(R.id.reject_button);

        mBackButton.setOnClickListener(v -> {
            getActivity().onBackPressed();
        });

        mAcceptButton.setOnClickListener(v -> {
            mCallbacks.acceptAd();
        });

        return view;
    }

    private Callbacks mCallbacks;

    public interface Callbacks {
        void acceptAd();
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


    public void setup(Ad ad, boolean showAcceptReject) {
        if (showAcceptReject) {
            mAcceptReject.setVisibility(View.VISIBLE);
            mAcceptButton.setText("Submit Ad");
            mCompany.setVisibility(View.INVISIBLE);
        }


            Log.d("adValues", "not null");
            // mCompany.setText(ad.getCompany());
            mTitle.setText("Title: "+ ad.getTitle());
            mDescription.setText("Description: " + ad.getDescription());
            List<String> salary = ad.getSalaryRange();
            mSalary.setText("Salary: From " + salary.get(0) + " to " + salary.get(1));
            if (ad.getExtraQuestions().isEmpty()) mQuestions.setText("No added questions");
            else mQuestions.setText("Extra Questions: " + ad.getExtraQuestions().toString());
            mPDF.setText(ad.getLinkToPDFImage());
            if (ad.getTags().isEmpty()) mTags.setText("No tags Selected");
            else mTags.setText("Tags added: " + ad.getTags().toString());

    }
}