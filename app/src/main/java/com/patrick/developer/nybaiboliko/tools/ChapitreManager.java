package com.patrick.developer.nybaiboliko.tools;

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

    GlobalClass globalClass;

    Toolbar toolbar;

    int widthButton = 100;
    int heightButton = 100;
    int textButtonSize =10;

    public ChapitreManager(Context context, View rootView, TabHost tabHost, Integer numberOfChap, Integer refColorBible) {
        this.rootView = rootView;
        this.context = context;
        this.tabHost = tabHost;
        this.numberOfChap = numberOfChap;
        this.refColorBible = refColorBible;

        this.tools = new Tools(context);
        this.jsonParse = new JsonParser();
        layoutChapitre = (LinearLayout)rootView.findViewById(R.id.chapitres);
        layoutVerset = (LinearLayout)rootView.findViewById(R.id.versets);
        globalClass = (GlobalClass) context.getApplicationContext();
        toolbar = (Toolbar) ((Activity)context).findViewById(R.id.toolbar);

    }

    public void creationBoutonChap(){
        buttonChapitres = new ArrayList<>();

        int numerosChapitre = 1;

        layoutVerset.removeAllViews();
        layoutChapitre.removeAllViews();

        ScrollView scroller = new ScrollView(context);

        layoutChapitre.addView(scroller);

        TableLayout tabChap = new TableLayout(context);
        scroller.addView(tabChap);

        int nbLigne = (int)Math.ceil(numberOfChap/(float)7);

        for(int j =0; j< nbLigne; j++ ){
            TableRow r = new TableRow(context);
            r.setGravity(Gravity.CENTER);

            for(int k = 0; k < 7; k++){
                if(numerosChapitre > numberOfChap){
                    break;
                }
                Button b = new Button(context);
                r.addView(b);
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams)b.getLayoutParams();
                params.setMargins(1,1,1,1);
                params.width = widthButton;
                params.height = heightButton;
                b.setLayoutParams(params);
                b.setText(String.valueOf(numerosChapitre));
                b.setTextSize(textButtonSize);
                b.setTag(numerosChapitre);
                b.setOnClickListener(listenerChapitre);
                buttonChapitres.add(b);
                b.setTextColor(context.getResources().getColor(R.color.white));
                b.setBackgroundColor(tools.colorBible[refColorBible]);
                numerosChapitre++;
            }
            tabChap.addView(r);
        }
    }

    public View.OnClickListener listenerChapitre = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Integer index = (Integer)v.getTag();
            for(Button b: buttonChapitres) {
                if(b.getTag() == index){
                    globalClass.setChapitre(index);
                    Integer nbVerset = jsonParse.getNumberVersetOf(context,globalClass.getBookIndex(),globalClass.getChapitre()-1);
                    new VersetManager(context,rootView,tools,tabHost,nbVerset,refColorBible,0).creationButtonVerset();
                    toolbar.setTitle(globalClass.getBookTitle() + " "+ globalClass.getChapitre());
                    tabHost.setCurrentTab(2);
                }
            }

        }
    };
}
