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

        String paroleText = paroles.get(i);
        paroleText = paroleText.replace("Fiv :","<html><br/><font color=\"red\"><b><i>Fiv:</i></b></font></html>");

        int numbertParole = i+1;

        TextView number = (TextView)view.findViewById(R.id.parole_number);

        if(paroles.get(0).startsWith("Fiv :")) {
            numbertParole = i;
            switch (i) {
                case 0:
                    number.setText("");
                    break;
                default:
                    number.setText(String.valueOf(numbertParole)+".");
            }
        }
        else {

            number.setText(String.valueOf(numbertParole)+".");
        }

        TextView parole = (TextView)view.findViewById(R.id.parole);
        parole.setText(Html.fromHtml(paroleText));
        return view;
    }
}
