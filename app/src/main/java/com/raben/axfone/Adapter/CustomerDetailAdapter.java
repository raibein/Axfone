package com.raben.axfone.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.raben.axfone.POJO.CustomerDetail;
import com.raben.axfone.R;

import java.util.List;

/**
 * Created by raben on 20-Oct-17.
 */
public class CustomerDetailAdapter extends BaseAdapter {


    private Activity activity;
    private LayoutInflater inflater;
    private List<CustomerDetail> customerItems;


    /*
    private ArrayList<CustomerDetail> listData;
    private LayoutInflater layoutInflater;
    private Context context;
    */

    public CustomerDetailAdapter(Activity activity, List<CustomerDetail> customerItems){

        this.activity= activity;
        this.customerItems =  customerItems;

        /*
        this.listData = listData;
        this.context =  context;
        layoutInflater = LayoutInflater.from(context);
        */
    }




    @Override
    public int getCount(){
        return customerItems.size();
    }

    @Override
    public Object getItem(int location){
        return customerItems.get(location);
    }

    @Override
    public long getItemId(int position){
        return position;
    }





    public View getView(int position, View convertView, ViewGroup parent){

        if(inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView == null)
            convertView = inflater.inflate(R.layout.activity_single_layout_view, null);




        TextView id = (TextView) convertView.findViewById(R.id.activity_single_layout_view_id);
        TextView name = (TextView) convertView.findViewById(R.id.activity_single_layout_view_name);


        CustomerDetail c = customerItems.get(position);


        id.setText(c.getId());
        name.setText(c.getName());


        return convertView;
    }

    static class ViewHolder{
        TextView name;
        TextView id;
        TextView time;
    }
}
