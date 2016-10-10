package com.patrick.developer.nybaiboliko.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.patrick.developer.nybaiboliko.R;
import com.patrick.developer.nybaiboliko.models.Verset;
import com.patrick.developer.nybaiboliko.tools.GlobalClass;
import com.patrick.developer.nybaiboliko.tools.Tools;

import java.util.List;

/**
 * Created by developer on 10/10/16.
 */

public class FindVesetAdapter extends BaseAdapter {

    Context context;

    List<Verset> versets;

    public FindVesetAdapter(Context context, List<Verset> versets) {
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
            view = infalInflater.inflate(R.layout.verset_item_find, null);
        }
        String keyWord = ((GlobalClass)context.getApplicationContext()).getKeyWord();

        Verset verset = versets.get(i);

        String resumeBible = verset.getBook()+" "+verset.getChapitreNumber()+" : "+verset.getVersetNumber();
        TextView resumeTextView = (TextView)view.findViewById(R.id.resume_bible);
        resumeTextView.setText(resumeBible);

        String pattern = "(?i)"+keyWord;

        TextView itemViewText = (TextView)view.findViewById(R.id.text_find);
        itemViewText.setText(Html.fromHtml(verset.getVersetText().replaceAll(pattern,"<html><font color=\"red\">"+keyWord+"</font></html>")));

        return view;
    }
}
