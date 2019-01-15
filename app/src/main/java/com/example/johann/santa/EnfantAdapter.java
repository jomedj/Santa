package com.example.johann.santa;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class EnfantAdapter extends ArrayAdapter<Enfant> {

    private int myItemLayout;
    private LayoutInflater li;

    public EnfantAdapter(Context context, int resourceId) {
        super(context, resourceId);
        this.myItemLayout = resourceId;
        this.li = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if (view == null)
        {
            view = this.li.inflate(this.myItemLayout, null);
        }

        Enfant p = this.getItem(position);
        if(p != null)
        {
            TextView tv1 = (TextView)view.findViewById(R.id.textView_prenom);
            tv1.setText(String.valueOf(p.getPrenom()));

        }
        return view;
    }

}