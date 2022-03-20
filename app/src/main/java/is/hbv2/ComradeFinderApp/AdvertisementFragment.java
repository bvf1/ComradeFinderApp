package is.hbv2.ComradeFinderApp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    private String[] mAd;
    private View mViewAd;

    public AdvertisementFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param adValues Parameter 1.
     * @return A new instance of fragment AdvertisementFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AdvertisementFragment newInstance(String[] adValues) {

        AdvertisementFragment fragment = new AdvertisementFragment();
        Bundle args = new Bundle();
        args.putStringArray(ARG_PARAM1, adValues);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("here", "here 2");

        if (getArguments() != null) {
            mAd = getArguments().getStringArray(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_advertisement, container, false);
        mViewAd = view.findViewById(R.id.ad_view);

        setAd(mAd);

        return view;
    }

    public void setAd(String[] adValues) {
        if (adValues == null) Log.d("adValues", "null");

        Log.d(TAG, "adValues: " + adValues);
    }
}