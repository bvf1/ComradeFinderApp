package is.hbv2.ComradeFinderApp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdsHome#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdsHome extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView mTitleText;
    private TextView mTitleSalary;
    private View mThisView;


    public AdsHome() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AdsHome.
     */
    // TODO: Rename and change types and number of parameters
    public static AdsHome newInstance(String tile, List salary) {
        AdsHome fragment = new AdsHome();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, tile);
        //args.putString(ARG_PARAM2, salary);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mThisView = inflater.inflate(R.layout.fragment_ads_home, container, false);
        mTitleText = (TextView) mThisView.findViewById(R.id.ads_home_title);
        mTitleSalary = (TextView) mThisView.findViewById(R.id.ads_home_salary);

        mTitleText.setText(mParam1);
        mTitleSalary.setText(mParam2);
        return mThisView;
    }
}