package com.raben.axfone.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.raben.axfone.POJO.Customer;
import com.raben.axfone.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by raben on 19-Oct-17.
 */
public class CustomerAdapter extends ArrayAdapter<Customer> {

    Context context;
    private List<Customer> customerList = null;
    private ArrayList<Customer> arrayList;

    // View lookup cache
    private static class ViewHolder {
        TextView name;
        TextView id;
        TextView slots;
        TextView usedSlots;
        TextView parkId;
        TextView parkName;
        //Button view;
        //ImageView icon;
    }

    public CustomerAdapter(Context context, ArrayList<Customer> customers) {
        super(context, R.layout.activity_single_layout_view, customers);
        this.context=context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Customer customer = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null)
        {
            viewHolder = new ViewHolder();

            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.activity_single_layout_view, parent, false);

            viewHolder.id = (TextView) convertView.findViewById(R.id.activity_single_layout_view_id);
            viewHolder.name = (TextView) convertView.findViewById(R.id.activity_single_layout_view_name);
            viewHolder.slots = (TextView) convertView.findViewById(R.id.activity_single_layout_view_slots);
            viewHolder.usedSlots = (TextView) convertView.findViewById(R.id.activity_single_layout_view_used_slots);
            viewHolder.parkId = (TextView) convertView.findViewById(R.id.activity_single_layout_view_park_id);
            viewHolder.parkName = (TextView) convertView.findViewById(R.id.activity_single_customer_park_place_name);
            //viewHolder.action = (Button) convertView.findViewById(R.id.activity_single_layout_view_action);
            //viewHolder.icon = (ImageView) convertView.findViewById(R.id.single_layout_list_view_image);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Populate the data into the template view using the data object
        viewHolder.id.setText(customer.getId());
        viewHolder.name.setText(customer.getName());
        viewHolder.slots.setText(customer.getSlots());
        viewHolder.usedSlots.setText(customer.getSlotsUsed());
        viewHolder.parkId.setText(customer.getParkPlaceName());
        //viewHolder.parkName.setText(customer.get);

        //Glide.with(context).load(course.getImage_path()).into(viewHolder.icon);
        //Glide.with(context).load(customer.getImage_path()).into(viewHolder.icon);
        // Return the completed view to render on screen
        return convertView;
    }

    public void filter(String charText){
        charText = charText.toLowerCase(Locale.getDefault());
        customerList.clear();

        if(charText.length() == 0){
            customerList.addAll(arrayList);
        }
        else{
            for(Customer c : arrayList)
            {
                if(c.getName().toLowerCase(Locale.getDefault()).contains(charText));
                {
                    customerList.add(c);
                }
            }
        }
        notifyDataSetChanged();
    }
}
