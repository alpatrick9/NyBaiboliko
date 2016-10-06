package com.patrick.developer.nybaiboliko.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.patrick.developer.nybaiboliko.R;

import java.util.List;

/**
 * Created by Rajaonarison on 29/07/2015.
 */
public class SlideMenuAdapter extends BaseAdapter {
    List<String> menu;
    Context context;

    public SlideMenuAdapter(List<String> menu, Context context) {
        this.menu = menu;
        this.context = context;
    }

    @Override
    public int getCount() {
        return menu.size();
    }

    @Override
    public Object getItem(int position) {
        return menu.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.item_slide_menu, null);
        }
        TextView titre = (TextView)convertView.findViewById(R.id.titre_);

        titre.setText(menu.get(position));

        return convertView;
    }
}
