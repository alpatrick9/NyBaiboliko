package com.patrick.developer.nybaiboliko.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by developer on 10/7/16.
 */

@DatabaseTable(tableName = "fihirana")
public class Fihirana {

    @DatabaseField(id = true)
    protected String id;

    @DatabaseField
    protected String title;

    @DatabaseField
    protected String description;

    public Fihirana() {
    }

    public Fihirana(String id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
