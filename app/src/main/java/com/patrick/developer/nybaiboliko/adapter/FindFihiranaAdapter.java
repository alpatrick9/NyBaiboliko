package com.patrick.developer.nybaiboliko.adapter;

import android.content.Context;
import android.provider.ContactsContract;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.patrick.developer.nybaiboliko.R;
import com.patrick.developer.nybaiboliko.models.Fihirana;
import com.patrick.developer.nybaiboliko.tools.GlobalClass;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

/**
 * Created by developer on 10/10/16.
 */

public class FindFihiranaAdapter extends BaseAdapter {
    Context context;

    List<Fihirana> fihiranas;

    public FindFihiranaAdapter(Context context, List<Fihirana> fihiranas) {
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
            view = infalInflater.inflate(R.layout.fihirana_item_find, null);
        }

        String keyWord = ((GlobalClass)context.getApplicationContext()).getKeyWord();

        Fihirana fihirana = fihiranas.get(i);

        TextView is = (TextView)view.findViewById(R.id.id);
        is.setText(Html.fromHtml(fihirana.getId().replace("_F","F").replace("_"," ")));

        TextView title = (TextView)view.findViewById(R.id.title);
        title.setText(Html.fromHtml(fihirana.getTitle()));

        TextView content = (TextView)view.findViewById(R.id.content);
        String paroles = "";
        try {
            JSONArray array = new JSONArray(fihirana.getDescription());
            for(int j = 0; j < array.length(); j++) {
                String temp = array.getString(j);
                int num = j+1;
                if(temp.toLowerCase().contains(keyWord.toLowerCase())) {
                    String pattern = "(?i)"+keyWord;
                    paroles = "... <br/>"+num+". "+temp.replaceAll(pattern,"<html><font color=\"red\">"+keyWord+"</font></html>")+" ... ";
                    if(j == 0) {
                        paroles = num+". "+temp.replaceAll(pattern,"<html><font color=\"red\">"+keyWord+"</font></html>")+" ... ";
                    }
                    break;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        content.setText(Html.fromHtml(paroles));
        return view;
    }
}
