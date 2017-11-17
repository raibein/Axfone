package com.raben.axfone.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.raben.axfone.POJO.ParkingID;
import com.raben.axfone.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by raben on 30-Oct-17.
 */
public class ParkingIDAdapter extends ArrayAdapter<ParkingID> {

    Context context;
    private List<ParkingID> parkingList = null;
    private ArrayList<ParkingID> arrayList;

    // View lookup cache
    private static class ViewHolder {
        TextView name;
        TextView id;
    }

    public ParkingIDAdapter(Context context, ArrayList<ParkingID> parkingIDs) {
        super(context, R.layout.activity_single_layout_view, parkingIDs);
        this.context=context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        ParkingID parkingID = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null)
        {
            viewHolder = new ViewHolder();

            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.activity_single_layout_view, parent, false);

            viewHolder.id = (TextView) convertView.findViewById(R.id.activity_single_layout_view_id);
            viewHolder.name = (TextView) convertView.findViewById(R.id.activity_single_layout_view_name);

            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.id.setText(parkingID.getId());
        viewHolder.name.setText(parkingID.getName());

        return convertView;
    }

    public void filter(String charText){
        charText = charText.toLowerCase(Locale.getDefault());
        parkingList.clear();

        if(charText.length() == 0){
            parkingList.addAll(arrayList);
        }
        else{
            for(ParkingID c : arrayList)
            {
                if(c.getName().toLowerCase(Locale.getDefault()).contains(charText));
                {
                    parkingList.add(c);
                }
            }
        }
        notifyDataSetChanged();
    }
}