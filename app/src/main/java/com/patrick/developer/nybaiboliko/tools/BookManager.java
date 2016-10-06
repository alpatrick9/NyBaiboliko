package com.patrick.developer.nybaiboliko.tools;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.patrick.developer.nybaiboliko.R;

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

    GlobalClass globalClass;

    Toolbar toolbar;

    ArrayList<Button> buttonLivres = null;

    protected ListView menuElementsList;

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
        globalClass = (GlobalClass) context.getApplicationContext();

        toolbar = (Toolbar) ((Activity)context).findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
        toolbar.setTitle(context.getResources().getString(R.string.bible_section));

        menuElementsList = (ListView) ((Activity)context).findViewById(R.id.menu_elements);
        menuElementsList.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));

        widthButton = globalClass.squareWidthMax;
        heightButton = globalClass.squareWidthMax;

        this.windowProportion = tools.getSizeForSquare();

        if(globalClass.squareWidthMax > windowProportion) {
            widthButton = windowProportion;
            heightButton = windowProportion;
        }

        textButtonSize = (int)Math.ceil(widthButton*0.1);
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
        return nomLivre.substring(0, 3).toUpperCase();
    }

    public void getTitleBook(int index) {
        String nomLivre = jsonParse.getBook(context, index);
        globalClass.setBookTitle(nomLivre);
    }


    public View.OnClickListener listenerLivres = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Integer index = (Integer) v.getTag();
            for (Button b : buttonLivres) {
                if (b.getTag() == index) {

                    Integer nbChap = jsonParse.getNumberChapOf(context, index);
                    Integer refColor = tools.recupColorBible(index);

                    globalClass.setBookIndex(index);

                    getTitleBook(index);

                    new ChapitreManager(context, rootView, tabHost, nbChap, refColor).creationBoutonChap();

                    toolbar.setTitle(globalClass.getBookTitle());
                    toolbar.setBackgroundColor(tools.colorBible[refColor]);
                    menuElementsList.setBackgroundColor(tools.colorBible[refColor]);
                    tabHost.setCurrentTab(1);
                }
            }
        }
    };
}
