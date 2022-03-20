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
    private static final String ARG_PARAM1 = "advert";
    private static final String TAG = "AdvertisementFragment";

    private String mAd;
    private View mViewAd;

    public AdvertisementFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param ad Parameter 1.
     * @return A new instance of fragment AdvertisementFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AdvertisementFragment newInstance(Ad ad) {
        Log.d("aDjfowifew", ad.toString());
        AdvertisementFragment fragment = new AdvertisementFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, ad.toString());
        fragment.setArguments(args);
        Log.d("here", "here 1");
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("here", "here 2");

        if (getArguments() != null) {
            mAd = getArguments().getString(ARG_PARAM1);
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

    public void setAd(String ad) {
        Log.d("here", "here setad");

        Log.d(TAG, "onCreateView: " + mAd);
    }
}