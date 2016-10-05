package com.patrick.developer.nybaiboliko.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.patrick.developer.nybaiboliko.R;
import com.patrick.developer.nybaiboliko.models.Verset;

import java.util.List;

/**
 * Created by developer on 10/5/16.
 */

public class VersetsAdapter extends BaseAdapter {

    Context context;

    List<Verset> versets;

    public VersetsAdapter(Context context, List<Verset> versets) {
        this.context = context;
        this.versets = versets;
    }

    @Override
    public int getCount() {
        return versets.size();
    }

    @Override
    public Object getItem(int i) {
        return versets.get(i);
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
            view = infalInflater.inflate(R.layout.verset_item, null);
        }

        TextView itemViewNumber = (TextView)view.findViewById(R.id.versets_item_number);
        itemViewNumber.setText(versets.get(i).getVersetNumber().toString());

        TextView itemViewText = (TextView)view.findViewById(R.id.versets_item_text);
        itemViewText.setText(versets.get(i).getVersetText());

        return view;
    }
}