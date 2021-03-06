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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.patrick.developer.nybaiboliko.R;
import com.patrick.developer.nybaiboliko.dao.HistoryVersetDao;
import com.patrick.developer.nybaiboliko.fragment.bible.BibleFragment;
import com.patrick.developer.nybaiboliko.fragment.historique.HistoryFragment;
import com.patrick.developer.nybaiboliko.models.entity.HistoryVerset;
import com.patrick.developer.nybaiboliko.tools.GlobalVariable;
import com.patrick.developer.nybaiboliko.tools.JsonParser;
import com.patrick.developer.nybaiboliko.tools.Tools;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by developer on 10/17/16.
 */

public class HistoryBibleAdapter extends BaseAdapter {

    Context context;

    List<HistoryVerset> historyVersets;

    public HistoryBibleAdapter(Context context, List<HistoryVerset> historyVersets) {
        this.context = context;
        this.historyVersets = historyVersets;
    }

    @Override
    public int getCount() {
        return historyVersets.size();
    }

    @Override
    public Object getItem(int i) {
        return historyVersets.get(i);
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

        ImageView imageView = (ImageView)view.findViewById(R.id.ic_story);
        imageView.setBackground(context.getResources().getDrawable(R.mipmap.ic_text));

        HistoryVerset verset = historyVersets.get(i);

        String title = new Tools(context).formatTitleBookToView(verset.getBook())+" "+verset.getChapitreNumber();
        if(verset.getVersetNumberLast() - verset.getVersetNumberStart() == 0) {
            title = title + ": " + verset.getVersetNumberStart();
        } else {
            title = title + ": " + verset.getVersetNumberStart() + "-"+verset.getVersetNumberLast();
        }
        TextView titleView = (TextView)view.findViewById(R.id.title_item);
        titleView.setMaxWidth(new Tools(context).getWidthSreenSize() - 200);

        titleView.setText(Html.fromHtml(title));
        titleView.setTag("t"+i);

        titleView.setOnClickListener(openListener);

        int widthScreen = new Tools(context).getWidthSreenSize();

        if (590 > widthScreen) {
            ViewGroup.LayoutParams layoutParams = titleView.getLayoutParams();
            layoutParams.width = widthScreen - 100;
            titleView.setLayoutParams(layoutParams);
        }

        ImageButton delFihiranaButton = (ImageButton)view.findViewById(R.id.delete_story);
        delFihiranaButton.setTag("d"+i);

        delFihiranaButton.setOnClickListener(deleteListener);

        return view;
    }

    protected View.OnClickListener openListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String tagCourrant = (String)view.getTag();
            for(int i = 0; i<historyVersets.size();i++){
                if(tagCourrant.equals("t"+i)){
                    HistoryVerset historyVerset = historyVersets.get(i);

                    GlobalVariable globalVariable = (GlobalVariable) context.getApplicationContext();

                    globalVariable.bookRef.bookIndex = historyVerset.getIndexBook();
                    globalVariable.bookRef.bookTitle = new JsonParser().getBook(context, globalVariable.bookRef.bookIndex);

                    globalVariable.bookRef.chapitre = historyVerset.getChapitreNumber();
                    globalVariable.bookRef.versetStart = historyVerset.getVersetNumberStart();
                    globalVariable.bookRef.versetLast = historyVerset.getVersetNumberLast();

                    Fragment fragment = new BibleFragment();

                    changeFragment(fragment);
                }
            }
        }
    };

    protected View.OnClickListener deleteListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String tagCourrant = (String)view.getTag();
            for(int i = 0; i<historyVersets.size();i++){
                if(tagCourrant.equals("d"+i)){
                    HistoryVersetDao historyVersetDao = new HistoryVersetDao(context);
                    try {
                        historyVersetDao.delete(historyVersets.get(i));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    HistoryFragment fragment = new HistoryFragment();

                    Bundle bundleHistory = new Bundle();
                    bundleHistory.putInt("tabHostId",0);

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
