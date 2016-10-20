package com.patrick.developer.nybaiboliko.tools.bible;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TabHost;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.patrick.developer.nybaiboliko.R;
import com.patrick.developer.nybaiboliko.dao.HistoryVersetDao;
import com.patrick.developer.nybaiboliko.fragment.bible.BibleFragment;
import com.patrick.developer.nybaiboliko.models.entity.HistoryVerset;
import com.patrick.developer.nybaiboliko.tools.GlobalVariable;
import com.patrick.developer.nybaiboliko.tools.JsonParser;
import com.patrick.developer.nybaiboliko.tools.Tools;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by developer on 10/5/16.
 */

public class VersetManager {
    Context context;

    View rootView;

    Tools tools;

    TabHost tabHost;

    JsonParser jsonParse;

    ArrayList<Button> buttonVersets = null;

    LinearLayout layoutVerset = null;

    Integer numberOfVerset;

    Integer refColorBible;

    Integer ref;

    int widthButton = 0;
    int heightButton = 0;
    int textButtonSize = 0;

    int windowProportion = 0;

    GlobalVariable globalVariable;

    Toolbar toolbar;

    public VersetManager(Context context, View rootView, Tools tools, TabHost tabHost, Integer numberOfVerset, Integer refColorBible, Integer ref) {
        this.rootView = rootView;
        this.context = context;
        this.tabHost = tabHost;
        this.numberOfVerset = numberOfVerset;
        this.refColorBible = refColorBible;
        this.ref = ref;

        this.tools = new Tools(context);
        this.jsonParse = new JsonParser();
        layoutVerset = (LinearLayout) rootView.findViewById(R.id.versets);
        globalVariable = (GlobalVariable) context.getApplicationContext();
        toolbar = (Toolbar) ((Activity)context).findViewById(R.id.toolbar);

        widthButton = globalVariable.squareWidthMax;
        heightButton = globalVariable.squareWidthMax;

        this.windowProportion = tools.getSizeForSquare();

        if(globalVariable.squareWidthMax > windowProportion) {
            widthButton = windowProportion;
            heightButton = windowProportion;
        }

        textButtonSize = globalVariable.bibleTextBottonSize;
    }

    public void creationButtonVerset() {
        buttonVersets = new ArrayList<>();
        int numerosVerse = 1;
        layoutVerset.removeAllViews();
        ScrollView scroller = new ScrollView(context);
        layoutVerset.addView(scroller);

        LinearLayout baseLayout = new LinearLayout(context);
        baseLayout.setOrientation(LinearLayout.VERTICAL);

        scroller.addView(baseLayout);

        TextView titleTextView = (TextView) ((Activity)context).getLayoutInflater().inflate(R.layout.indication_textwiew,null);;
        titleTextView.setTextColor(tools.getColorBible()[globalVariable.colorRef]);
        switch (ref) {
            case 0:
                titleTextView.setText("Andininy faha:");
                break;
            case 1:
                numerosVerse = globalVariable.bookRef.versetStart;
                titleTextView.setText("Hatramin'ny faha:");
        }

        baseLayout.addView(titleTextView);

        TableLayout tabChap = new TableLayout(context);
        baseLayout.addView(tabChap);

        int nbLigne = (int) Math.ceil(numberOfVerset / (float) 7);
        for (int j = 0; j < nbLigne; j++) {
            TableRow r = new TableRow(context);
            r.setGravity(Gravity.CENTER);
            for (int k = 0; k < 7; k++) {
                if (numerosVerse > numberOfVerset) {
                    break;
                } else {
                    Button b = new Button(context);
                    r.addView(b);
                    ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) b.getLayoutParams();
                    params.setMargins(1, 1, 1, 1);
                    params.width = widthButton;
                    params.height = heightButton;
                    b.setLayoutParams(params);
                    b.setText(String.valueOf(numerosVerse));
                    b.setTextSize(textButtonSize);
                    buttonVersets.add(b);
                    b.setTag(numerosVerse - 1);
                    b.setTextColor(context.getResources().getColor(R.color.white));
                    b.setBackgroundColor(tools.getColorBible()[refColorBible]);
                    b.setOnClickListener(listenerVerset);
                    numerosVerse++;
                }
            }
            tabChap.addView(r);
        }
    }

    public View.OnClickListener listenerVerset = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Integer index = (Integer)v.getTag();
            for(Button b: buttonVersets) {
                if(b.getTag() == index) {
                    switch (ref) {
                        case 0:
                            globalVariable.bookRef.versetStart = index+1;
                            toolbar.setTitle(globalVariable.bookRef.bookTitle + " "+ globalVariable.bookRef.chapitre+": "+ globalVariable.bookRef.versetStart+"-");
                            new VersetManager(context,rootView,tools,tabHost,numberOfVerset,refColorBible,1).creationButtonVerset();
                            tabHost.setCurrentTab(2);
                            break;
                        case 1:
                            globalVariable.bookRef.versetLast = index+1;
                            ref = -1;
                            openBible();
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    };

    public void openBible() {
        Fragment fragment = new BibleFragment();
        if (fragment != null) {
            RelativeLayout maLayout = (RelativeLayout) ((Activity)context).findViewById(R.id.contenaire);
            maLayout.removeAllViews();
            FragmentManager fragmentManager = ((Activity)context).getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.contenaire, fragment).commit();
        }

        HistoryVersetDao historyVersetDao = new HistoryVersetDao(context);
        HistoryVerset historyVerset = new HistoryVerset(new Tools(context).formatTitleBookToView(globalVariable.bookRef.bookTitle), globalVariable.bookRef.bookIndex, globalVariable.bookRef.chapitre, globalVariable.bookRef.versetStart, globalVariable.bookRef.versetLast);

        try {
            historyVersetDao.create(historyVerset);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
