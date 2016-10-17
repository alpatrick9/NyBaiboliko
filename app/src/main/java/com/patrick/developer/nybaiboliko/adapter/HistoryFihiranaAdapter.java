package com.patrick.developer.nybaiboliko.adapter;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.patrick.developer.nybaiboliko.R;
import com.patrick.developer.nybaiboliko.dao.HistoryFihiranaDao;
import com.patrick.developer.nybaiboliko.fragment.Song.FihiranaFfpmFragment;
import com.patrick.developer.nybaiboliko.fragment.historique.HistoryFragment;
import com.patrick.developer.nybaiboliko.models.HistoryFihirana;
import com.patrick.developer.nybaiboliko.tools.Tools;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by developer on 10/17/16.
 */

public class HistoryFihiranaAdapter extends BaseAdapter {

    Context context;

    List<HistoryFihirana> historyFihiranas;

    public HistoryFihiranaAdapter(Context context, List<HistoryFihirana> historyFihiranas) {
        this.context = context;
        this.historyFihiranas = historyFihiranas;
    }

    @Override
    public int getCount() {
        return historyFihiranas.size();
    }

    @Override
    public Object getItem(int i) {
        return historyFihiranas.get(i);
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
            view = infalInflater.inflate(R.layout.story_items, null);
        }

        TextView title = (TextView)view.findViewById(R.id.title_item);
        title.setText(Html.fromHtml(historyFihiranas.get(i).getTitle()));
        title.setTag("t"+i);

        title.setOnClickListener(openFihiranaListener);

        int widthScreen = new Tools(context).getWidthSreenSize();

        if (590 > widthScreen) {
            ViewGroup.LayoutParams layoutParams = title.getLayoutParams();
            layoutParams.width = widthScreen - 100;
            title.setLayoutParams(layoutParams);
        }

        ImageButton delFihiranaButton = (ImageButton)view.findViewById(R.id.delete_story);
        delFihiranaButton.setTag("d"+i);

        delFihiranaButton.setOnClickListener(deleteFihiranaListener);
        return view;
    }

    protected View.OnClickListener openFihiranaListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String tagCourrant = (String)view.getTag();
            for(int i = 0; i<historyFihiranas.size();i++){
                if(tagCourrant.equals("t"+i)){
                    Fragment fragment = new FihiranaFfpmFragment();

                    Bundle bundle = new Bundle();
                    bundle.putString("id", historyFihiranas.get(i).getIdFihirana());

                    fragment.setArguments(bundle);
                    changeFragment(fragment);
                }
            }
        }
    };

    protected View.OnClickListener deleteFihiranaListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String tagCourrant = (String)view.getTag();
            for(int i = 0; i<historyFihiranas.size();i++){
                if(tagCourrant.equals("d"+i)){
                    HistoryFihiranaDao historyFihiranaDao = new HistoryFihiranaDao(context);
                    try {
                        historyFihiranaDao.delete(historyFihiranas.get(i));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    HistoryFragment fragment = new HistoryFragment();

                    Bundle bundleHistory = new Bundle();
                    bundleHistory.putInt("tabHostId",1);

                    fragment.setArguments(bundleHistory);
                    changeFragment(fragment);
                }
            }
        }
    };

    protected void changeFragment(Fragment fragment) {
        if (fragment != null) {
            RelativeLayout maLayout = (RelativeLayout) ((Activity)context).findViewById(R.id.contenaire);
            maLayout.removeAllViews();
            FragmentManager fragmentManager = ((Activity)context).getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.contenaire, fragment).commit();
        }
    }
}
