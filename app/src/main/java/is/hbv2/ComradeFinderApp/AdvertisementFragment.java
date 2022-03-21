package is.hbv2.ComradeFinderApp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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

        Log.d("here", "here 2");
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

        Log.d("onCreate", "here");

        return view;
    }

    public void setAd(Ad ad) {
        try {
            Log.d("adValues", "not null");

            mCompany.setText(ad.getCompany());
            mTitle.setText(ad.getTitle());
            mDescription.setText(ad.getDescription());
            mSalary.setText(ad.getSalaryRange());

            mQuestions.setText("temp");
            mPDF.setText(ad.getLinkToPDFImage());

            mTags.setText("temp");
        } catch (NullPointerException e) {
            Log.d("setAd", e.toString());

        }
    }
}