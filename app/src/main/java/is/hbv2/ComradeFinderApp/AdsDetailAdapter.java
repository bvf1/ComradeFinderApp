package is.hbv2.ComradeFinderApp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import is.hbv2.ComradeFinderApp.Entities.Ad;

public class AdsDetailAdapter extends ArrayAdapter<String> {

    public AdsDetailAdapter(Context context, int resource, List<String> adList)
    {
        super(context,resource,adList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        String ad = getItem(position);

        if(convertView == null){
            //TODO: Breyta ads_cell
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.ads_detail_cell, parent, false);
        }
        TextView adQuestion = (TextView) convertView.findViewById(R.id.adQuestion);

        adQuestion.setText(ad.toString());

        return convertView;
    }
}
