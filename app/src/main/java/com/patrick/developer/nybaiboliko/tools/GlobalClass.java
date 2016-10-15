package com.patrick.developer.nybaiboliko.tools;

import android.app.Application;
import android.content.Context;
import android.support.annotation.ColorInt;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.patrick.developer.nybaiboliko.R;
import com.patrick.developer.nybaiboliko.models.Fihirana;
import com.patrick.developer.nybaiboliko.models.Verset;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by developer on 10/5/16.
 */

public class GlobalClass extends Application {

    protected static List<Verset> resultFindVerset = new ArrayList<>();

    protected static List<Fihirana> resultFindFihirana = new ArrayList<>();

    protected static String keyWord = "";

    protected static Integer bookIndex;
    protected static String bookTitle;
    protected static Integer chapitre;
    protected static Integer versetFirst;
    protected static Integer versetLast;

    public static Integer squareWidthMax = 200;

    public int colorRef =  0;

    public static Integer getBookIndex() {
        return bookIndex;
    }

    public static void setBookIndex(Integer bookIndex) {
        GlobalClass.bookIndex = bookIndex;
    }

    public static String getBookTitle() {
        return bookTitle;
    }

    public static void setBookTitle(String bookTitle) {
        GlobalClass.bookTitle = bookTitle;
    }

    public static Integer getChapitre() {
        return chapitre;
    }

    public static void setChapitre(Integer chapitre) {
        GlobalClass.chapitre = chapitre;
    }

    public static Integer getversetFirst() {
        return versetFirst;
    }

    public static void setversetFirst(Integer versetFirst) {
        GlobalClass.versetFirst = versetFirst;
    }

    public static Integer getversetLast() {
        return versetLast;
    }

    public static void setversetLast(Integer versetLast) {
        GlobalClass.versetLast = versetLast;
    }

    public static List<Verset> getResultFindVerset() {
        return resultFindVerset;
    }

    public static void setResultFindVerset(List<Verset> resultFindVerset) {
        GlobalClass.resultFindVerset = resultFindVerset;
    }

    public static List<Fihirana> getResultFindFihirana() {
        return resultFindFihirana;
    }

    public static void setResultFindFihirana(List<Fihirana> resultFindFihirana) {
        GlobalClass.resultFindFihirana = resultFindFihirana;
    }

    public static String getKeyWord() {
        return keyWord;
    }

    public static void setKeyWord(String keyWord) {
        GlobalClass.keyWord = keyWord;
    }

    public static void setAnimation(Context context, View view) {
        Animation scaleUp = AnimationUtils.loadAnimation(context, R.anim.scale_up_fast);
        view.setAnimation(scaleUp);
    }
}
