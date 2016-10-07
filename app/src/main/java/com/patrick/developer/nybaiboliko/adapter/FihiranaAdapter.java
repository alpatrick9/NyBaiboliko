package com.patrick.developer.nybaiboliko.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.patrick.developer.nybaiboliko.R;
import com.patrick.developer.nybaiboliko.models.Fihirana;

import java.util.List;

/**
 * Created by developer on 10/7/16.
 */

public class FihiranaAdapter extends BaseAdapter {

    Context context;

    List<Fihirana> fihiranas;

    public FihiranaAdapter(Context context, List<Fihirana> fihiranas) {
        this.context = context;
        this.fihiranas = fihiranas;
    }

    @Override
    public int getCount() {
        return fihiranas.size();
    }

    @Override
    public Object getItem(int i) {
        return fihiranas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = infalInflater.inflate(R.layout.fihirana_title_item, null);
        }

        TextView title = (TextView)view.findViewById(R.id.title_fihirana_item);
        title.setText(fihiranas.get(i).getTitle());
        return view;
    }
}