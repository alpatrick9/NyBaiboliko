package com.patrick.developer.nybaiboliko.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by developer on 10/17/16.
 */

@DatabaseTable(tableName = "history_verset")
public class HistoryVerset {

    @DatabaseField(generatedId = true)
    protected Long id;

    @DatabaseField(columnName = "book")
    protected String book;

    @DatabaseField(columnName = "index_book")
    protected Integer indexBook;

    @DatabaseField(columnName = "chapitre_number")
    protected Integer chapitreNumber;

    @DatabaseField(columnName = "verset_number_start")
    protected Integer versetNumberStart;

    @DatabaseField(columnName = "verset_number_last")
    protected Integer versetNumberLast;

    public HistoryVerset() {
    }

    public HistoryVerset(String book, Integer indexBook, Integer chapitreNumber, Integer versetNumberStart, Integer versetNumberLast) {
        this.book = book;
        this.indexBook = indexBook;
        this.chapitreNumber = chapitreNumber;
        this.versetNumberStart = versetNumberStart;
        this.versetNumberLast = versetNumberLast;
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

    public Integer getIndexBook() {
        return indexBook;
    }

    public void setIndexBook(Integer indexBook) {
        this.indexBook = indexBook;
    }

    public Integer getChapitreNumber() {
        return chapitreNumber;
    }

    public void setChapitreNumber(Integer chapitreNumber) {
        this.chapitreNumber = chapitreNumber;
    }

    public Integer getVersetNumberStart() {
        return versetNumberStart;
    }

    public void setVersetNumberStart(Integer versetNumberStart) {
        this.versetNumberStart = versetNumberStart;
    }

    public Integer getVersetNumberLast() {
        return versetNumberLast;
    }

    public void setVersetNumberLast(Integer versetNumberLast) {
        this.versetNumberLast = versetNumberLast;
    }
}
