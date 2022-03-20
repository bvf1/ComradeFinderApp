package is.hbv2.ComradeFinderApp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

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

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final App ARG_PARAM1 = "advert";
    private static final String TAG = "AdvertisementFragment";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AdvertisementFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AdvertisementFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AdvertisementFragment newInstance(Ad ad) {
        AdvertisementFragment fragment = new AdvertisementFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_advertisement, container, false);
    }
}