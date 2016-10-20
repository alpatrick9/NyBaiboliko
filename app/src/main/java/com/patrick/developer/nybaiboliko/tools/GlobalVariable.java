package com.patrick.developer.nybaiboliko.tools;

import android.app.Application;

import com.patrick.developer.nybaiboliko.models.ebean.BookRef;
import com.patrick.developer.nybaiboliko.models.entity.Fihirana;
import com.patrick.developer.nybaiboliko.models.entity.Verset;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by developer on 10/5/16.
 */

public class GlobalVariable extends Application {

    /**
     * resume all reference of holy bible to read
     */
    public BookRef bookRef = new BookRef();

    public BookRef bookRef1 = new BookRef();

    public BookRef bookRef2 = new BookRef();

    public Integer numTabBook = 0;

    public Integer nbBook = 0;

    /**
     * size max of button to choise holy bible
     */
    public Integer squareWidthMax = 200;

    /**
     * Text button size of button to choise holy bible
     */
    public Integer bibleTextBottonSize = 0;

    /**
     * color of the book choise
     */
    public Integer colorRef =  0;

    public String keyWord = "";

    public List<Verset> resultFindVerset = new ArrayList<>();

    public List<Fihirana> resultFindFihirana = new ArrayList<>();

}
