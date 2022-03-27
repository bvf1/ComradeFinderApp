package is.hbv2.ComradeFinderApp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import is.hbv2.ComradeFinderApp.Entities.Ad;

public class AdsAdapter extends ArrayAdapter<Ad>
{
    public AdsAdapter(Context context, int resource, List<Ad> adList)
    {
        super(context,resource,adList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        Ad ad = getItem(position);

        if (convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.ads_cell, parent, false);
        }
        TextView tvName = (TextView) convertView.findViewById(R.id.)

        return super.getView(position, convertView, parent);
    }
}
