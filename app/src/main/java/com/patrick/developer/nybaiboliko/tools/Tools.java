package com.patrick.developer.nybaiboliko.tools;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.Button;

import com.patrick.developer.nybaiboliko.R;

/**
 * Created by developer on 10/5/16.
 */

public class Tools {

    protected int[] colorBible;

    protected Context context;
    public Tools(Context context) {
        this.context = context;
        this.colorBible = context.getResources().getIntArray(R.array.colorBible);
    }

    public void colorRefBible(int i, Button b){
        if(i>=0 && i<5) b.setBackgroundColor(colorBible[0]);
        else if(i>=5 && i<17) b.setBackgroundColor(colorBible[1]);
        else if(i>=17 && i<22) b.setBackgroundColor(colorBible[2]);
        else if(i>=22 && i<39) b.setBackgroundColor(colorBible[3]);
        else if(i>=39 && i<43) b.setBackgroundColor(colorBible[4]);
        else if(i== 43) b.setBackgroundColor(colorBible[5]);
        else if(i>=44 && i<57) b.setBackgroundColor(colorBible[6]);
        else if(i>=57 && i<65) b.setBackgroundColor(colorBible[7]);
        else if(i==65) b.setBackgroundColor(colorBible[8]);
    }

    public int recupColorBible(int i){
        if(i>=0 && i<5) return 0;
        else if(i>=5 && i<17) return 1;
        else if(i>=17 && i<22) return 2;
        else if(i>=22 && i<39) return 3;
        else if(i>=39 && i<43) return 4;
        else if(i== 43) return 5;
        else if(i>=44 && i<57) return 6;
        else if(i>=57 && i<65) return 7;
        else return 8;
    }

    public String formatTitleBook(String title) {
        title = title.replace(" ", "");
        return title.substring(0, 3).toUpperCase();
    }

    public String formatTitleBookToView(String title) {
        if(title.startsWith("1") || title.startsWith("2") || title.startsWith("3") )
            return title.substring(0,5);
        return title.substring(0, 3);
    }
    public Integer getSizeForSquare(){
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager windowManager = ((Activity)context).getWindowManager();
        windowManager.getDefaultDisplay().getMetrics(metrics);
        int hauteur = metrics.heightPixels;
        int largeur = metrics.widthPixels;
        return (int)Math.ceil(largeur/(float)7)-5;
    }

    public Integer getWidthSreenSize() {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager windowManager = ((Activity)context).getWindowManager();
        windowManager.getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }

    public int[] getColorBible() {
        return colorBible;
    }
}
