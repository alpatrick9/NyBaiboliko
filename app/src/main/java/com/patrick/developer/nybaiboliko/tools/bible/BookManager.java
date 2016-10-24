package com.patrick.developer.nybaiboliko.tools.bible;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Switch;
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

public class BookManager {

    Context context;

    View rootView;

    Tools tools;

    TabHost tabHost;

    JsonParser jsonParse;

    GlobalVariable globalVariable;

    Toolbar toolbar;

    ArrayList<Button> buttonLivres = null;

    int widthButton = 0;
    int heightButton = 0;
    int textButtonSize = 0;

    int windowProportion = 0;

    public BookManager(Context context, View rootView, TabHost tabHost) {
        this.rootView = rootView;
        this.context = context;
        this.tools = new Tools(context);
        this.tabHost = tabHost;
        this.jsonParse = new JsonParser();
        globalVariable = (GlobalVariable) context.getApplicationContext();

        toolbar = (Toolbar) ((Activity)context).findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
        toolbar.setTitle(context.getResources().getString(R.string.bible_section));
        toolbar.setTitleTextColor(context.getResources().getColor(R.color.white));

        widthButton = globalVariable.squareWidthMax;
        heightButton = globalVariable.squareWidthMax;

        this.windowProportion = tools.getSizeForSquare();

        if(globalVariable.squareWidthMax > windowProportion) {
            widthButton = windowProportion;
            heightButton = windowProportion;
        }

        globalVariable.bibleTextBottonSize = (int)Math.ceil(widthButton*0.17);
        textButtonSize = globalVariable.bibleTextBottonSize;
    }

    public void creationBoutonLivre() {

        buttonLivres = new ArrayList<>();

        boolean isfusionCol = false;

        int compteurLivre = 0;

        TableLayout tableAncien = (TableLayout) rootView.findViewById(R.id.ancienTestament);

        TableLayout tableNouveau = (TableLayout) rootView.findViewById(R.id.nouveauTestament);

        for (int i = 0; i < 10; i++) {
            TableRow row = new TableRow(context);
            row.setGravity(Gravity.CENTER);
            for (int j = 0; j < 7; j++) {
                if (isfusionCol) {
                    isfusionCol = false;
                    continue;
                }

                Button b = new Button(context);

                row.addView(b);

                buttonLivres.add(b);

                ViewGroup.MarginLayoutParams paramsButtonMargin = (ViewGroup.MarginLayoutParams) b.getLayoutParams();
                paramsButtonMargin.setMargins(1, 1, 1, 1);
                paramsButtonMargin.width = widthButton;
                paramsButtonMargin.height = heightButton;

                b.setLayoutParams(paramsButtonMargin);
                b.setText(getAbreviationLivre(compteurLivre));
                b.setTextSize(textButtonSize);
                b.setAllCaps(false);

                if ((i == 0 && j == 0) || (i == 2 && j == 5) || (i == 3 && j == 3) || (i == 6 && j == 4)) {
                    TableRow.LayoutParams paramCol = (TableRow.LayoutParams) b.getLayoutParams();
                    paramCol.span = 2;
                    b.setLayoutParams(paramCol);
                    isfusionCol = true;
                }
                b.setTextColor(context.getResources().getColor(R.color.white));
                tools.colorRefBible(compteurLivre, b);
                b.setTag(compteurLivre);
                b.setOnClickListener(listenerLivres);
                compteurLivre++;
            }
            if (i < 6) tableAncien.addView(row);
            else tableNouveau.addView(row);
        }
    }

    public String getAbreviationLivre(int index) {
        String nomLivre = jsonParse.getBook(context, index);
        nomLivre = nomLivre.replace(" ", "");
        return nomLivre.substring(0, 3);
    }

    public void getTitleBook(int index) {
        String nomLivre = jsonParse.getBook(context, index);
        switch (globalVariable.numTabBook) {
            case 0:
                globalVariable.bookRef.bookTitle = nomLivre;
                break;
            case 1:
                globalVariable.bookRef1.bookTitle = nomLivre;
                break;
            case 2:
                globalVariable.bookRef2.bookTitle = nomLivre;
                break;
        }
    }


    public View.OnClickListener listenerLivres = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Integer index = (Integer) v.getTag();
            for (Button b : buttonLivres) {
                if (b.getTag() == index) {

                    Integer nbChap = jsonParse.getNumberChapOf(context, index);
                    Integer refColor = tools.recupColorBible(index);

                    getTitleBook(index);

                    new ChapitreManager(context, rootView, tabHost, nbChap, refColor).creationBoutonChap();

                    switch (globalVariable.numTabBook) {
                        case 0:
                            globalVariable.bookRef.bookIndex = index;
                            toolbar.setTitle(globalVariable.bookRef.bookTitle);
                            break;
                        case 1:
                            globalVariable.bookRef1.bookIndex = index;
                            toolbar.setTitle(globalVariable.bookRef1.bookTitle);
                            break;
                        case 2:
                            globalVariable.bookRef2.bookIndex = index;
                            toolbar.setTitle(globalVariable.bookRef2.bookTitle);
                            break;
                    }
                    toolbar.setBackgroundColor(tools.getColorBible()[refColor]);

                    globalVariable.colorRef = refColor;

                    tabHost.setCurrentTab(1);
                }
            }
        }
    };
}
