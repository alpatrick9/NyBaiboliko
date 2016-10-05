package com.patrick.developer.nybaiboliko.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by developer on 10/5/16.
 */

@DatabaseTable(tableName = "verset")
public class Verset {

    @DatabaseField(generatedId = true)
    protected Long id;

    @DatabaseField(columnName = "book")
    protected String book;

    @DatabaseField(columnName = "chapitre_number")
    protected Integer chapitreNumber;

    @DatabaseField(columnName = "verset_number")
    protected Integer versetNumber;

    @DatabaseField(columnName = "verset_text")
    protected String versetText;

    public Verset() {
    }

    public Verset(String book, Integer chapitreNumber, Integer versetNumber, String versetText) {
        this.book = book;
        this.chapitreNumber = chapitreNumber;
        this.versetNumber = versetNumber;
        this.versetText = versetText;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public Integer getChapitreNumber() {
        return chapitreNumber;
    }

    public void setChapitreNumber(Integer chapitreNumber) {
        this.chapitreNumber = chapitreNumber;
    }

    public Integer getVersetNumber() {
        return versetNumber;
    }

    public void setVersetNumber(Integer versetNumber) {
        this.versetNumber = versetNumber;
    }

    public String getVersetText() {
        return versetText;
    }

    public void setVersetText(String versetText) {
        this.versetText = versetText;
    }
}
