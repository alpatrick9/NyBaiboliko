package com.patrick.developer.nybaiboliko.tools;

import android.app.Application;

/**
 * Created by developer on 10/5/16.
 */

public class GlobalClass extends Application {
    protected static Integer bookIndex;
    protected static String bookTitle;
    protected static Integer chapitre;
    protected static Integer versetFirst;
    protected static Integer versetLast;

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
}
