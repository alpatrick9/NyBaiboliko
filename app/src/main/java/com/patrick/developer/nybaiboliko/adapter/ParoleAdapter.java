package com.patrick.developer.nybaiboliko.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.patrick.developer.nybaiboliko.R;

import java.util.List;

/**
 * Created by developer on 10/7/16.
 */

public class ParoleAdapter extends BaseAdapter {

    Context context;

    List<String> paroles;

    public ParoleAdapter(Context context, List<String> paroles) {
        this.context = context;
        this.paroles = paroles;
    }

    @Override
    public int getCount() {
        return paroles.size();
    }

    @Override
    public Object getItem(int i) {
        return paroles.get(i);
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
            view = infalInflater.inflate(R.layout.fihirana_parole_item, null);
        }

        TextView number = (TextView)view.findViewById(R.id.parole_number);
        number.setText(String.valueOf(i+1)+".");

        String paroleText = paroles.get(i);
        if(i == 0 && paroleText.contains("Fiv :")) {
            paroleText = paroleText.replace("Fiv :","<html><font color=\"red\"><br/>Fiv:</font></html>");
        }

        TextView parole = (TextView)view.findViewById(R.id.parole);
        parole.setText(Html.fromHtml(paroleText));
        return view;
    }
}
