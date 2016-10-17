package com.patrick.developer.nybaiboliko.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by patmi on 16/10/2016.
 */

@DatabaseTable(tableName = "history_fihirana")
public class HistoryFihirana {

    @DatabaseField(generatedId = true)
    protected Long id;

    @DatabaseField(columnName = "id_fihirana")
    protected String idFihirana;

    @DatabaseField
    protected String title;

    public HistoryFihirana() {
    }

    public HistoryFihirana(String idFihirana, String title) {
        this.idFihirana = idFihirana;
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdFihirana() {
        return idFihirana;
    }

    public void setIdFihirana(String idFihirana) {
        this.idFihirana = idFihirana;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
