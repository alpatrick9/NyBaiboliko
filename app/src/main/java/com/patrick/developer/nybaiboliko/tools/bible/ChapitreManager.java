package com.patrick.developer.nybaiboliko.tools.bible;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TabHost;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.patrick.developer.nybaiboliko.R;
import com.patrick.developer.nybaiboliko.tools.GlobalVariable;
import com.patrick.developer.nybaiboliko.tools.JsonParser;
import com.patrick.developer.nybaiboliko.tools.Tools;

import java.util.ArrayList;

/**
 * Created by developer on 10/5/16.
 */

public class ChapitreManager {

    Context context;

    View rootView;

    Tools tools;

    TabHost tabHost;

    JsonParser jsonParse;

    ArrayList<Button> buttonChapitres = null;

    LinearLayout layoutChapitre = null;

    LinearLayout layoutVerset = null;

    Integer numberOfChap;

    Integer refColorBible;

    GlobalVariable globalVariable;

    Toolbar toolbar;

    int widthButton = 0;
    int heightButton = 0;
    int textButtonSize = 0;

    int windowProportion = 0;

    public ChapitreManager(Context context, View rootView, TabHost tabHost, Integer numberOfChap, Integer refColorBible) {
        this.rootView = rootView;
        this.context = context;
        this.tabHost = tabHost;
        this.numberOfChap = numberOfChap;
        this.refColorBible = refColorBible;

        this.tools = new Tools(context);
        this.jsonParse = new JsonParser();
        layoutChapitre = (LinearLayout) rootView.findViewById(R.id.chapitres);
        layoutVerset = (LinearLayout) rootView.findViewById(R.id.versets);
        globalVariable = (GlobalVariable) context.getApplicationContext();
        toolbar = (Toolbar) ((Activity) context).findViewById(R.id.toolbar);

        widthButton = globalVariable.squareWidthMax;
        heightButton = globalVariable.squareWidthMax;

        this.windowProportion = tools.getSizeForSquare();

        if (globalVariable.squareWidthMax > windowProportion) {
            widthButton = windowProportion;
            heightButton = windowProportion;
        }

        textButtonSize = globalVariable.bibleTextBottonSize;
    }

    public void creationBoutonChap() {
        buttonChapitres = new ArrayList<>();

        int numerosChapitre = 1;

        layoutVerset.removeAllViews();
        layoutChapitre.removeAllViews();

        ScrollView scroller = new ScrollView(context);

        layoutChapitre.addView(scroller);

        TableLayout tabChap = new TableLayout(context);
        scroller.addView(tabChap);

        int nbLigne = (int) Math.ceil(numberOfChap / (float) 7);

        for (int j = 0; j < nbLigne; j++) {
            TableRow r = new TableRow(context);
            r.setGravity(Gravity.CENTER);

            for (int k = 0; k < 7; k++) {
                if (numerosChapitre > numberOfChap) {
                    break;
                }
                Button b = new Button(context);
                r.addView(b);
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) b.getLayoutParams();
                params.setMargins(1, 1, 1, 1);
                params.width = widthButton;
                params.height = heightButton;
                b.setLayoutParams(params);
                b.setText(String.valueOf(numerosChapitre));
                b.setTextSize(textButtonSize);
                b.setTag(numerosChapitre);
                b.setOnClickListener(listenerChapitre);
                buttonChapitres.add(b);
                b.setTextColor(context.getResources().getColor(R.color.white));
                b.setBackgroundColor(tools.getColorBible()[refColorBible]);
                numerosChapitre++;
            }
            tabChap.addView(r);
        }
    }

    public View.OnClickListener listenerChapitre = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Integer index = (Integer) v.getTag();
            for (Button b : buttonChapitres) {
                Integer nbVerset = 0;
                if (b.getTag() == index) {
                    switch (globalVariable.numTabBook) {
                        case 0:
                            globalVariable.bookRef.chapitre = index;
                            nbVerset = jsonParse.getNumberVersetOf(context, globalVariable.bookRef.bookIndex, globalVariable.bookRef.chapitre - 1);
                            toolbar.setTitle(globalVariable.bookRef.bookTitle + " " + globalVariable.bookRef.chapitre);
                            break;
                        case 1:
                            globalVariable.bookRef1.chapitre = index;
                            nbVerset = jsonParse.getNumberVersetOf(context, globalVariable.bookRef1.bookIndex, globalVariable.bookRef1.chapitre - 1);
                            toolbar.setTitle(globalVariable.bookRef1.bookTitle + " " + globalVariable.bookRef1.chapitre);
                            break;
                        case 2:
                            globalVariable.bookRef2.chapitre = index;
                            nbVerset = jsonParse.getNumberVersetOf(context, globalVariable.bookRef2.bookIndex, globalVariable.bookRef2.chapitre - 1);
                            toolbar.setTitle(globalVariable.bookRef2.bookTitle + " " + globalVariable.bookRef2.chapitre);
                            break;
                    }
                    new VersetManager(context, rootView, tools, tabHost, nbVerset, refColorBible, 0).creationButtonVerset();
                    tabHost.setCurrentTab(2);
                }
            }

        }
    };
}
