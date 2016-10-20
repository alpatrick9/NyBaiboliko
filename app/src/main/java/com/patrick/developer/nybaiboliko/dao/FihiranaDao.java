package com.patrick.developer.nybaiboliko.dao;

import android.content.Context;

import com.patrick.developer.nybaiboliko.configuration.DaoManager;
import com.patrick.developer.nybaiboliko.models.entity.Fihirana;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by developer on 10/7/16.
 */

public class FihiranaDao extends AbstractDao<Fihirana, String> {

    Context context;

    public FihiranaDao(Context context) {
        this.context = context;
        try {
            this.dao = new DaoManager(context).getFihiranaDao();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Fihirana findBy(String id) {
        Fihirana fihirana = null;
        try {
            fihirana = dao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fihirana;
    }

    public List<Fihirana> findAllTitleFFPM() {
        List<Fihirana> fihiranas = new ArrayList<>();
        try {
            fihiranas = dao.queryBuilder().selectColumns("id","title").where().like("id","FFPM%").query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fihiranas;
    }

    public List<Fihirana> findAllTitleFFPM(Integer filtre) {
        List<Fihirana> fihiranas = new ArrayList<>();
        try {
            fihiranas = dao.queryBuilder().selectColumns("id","title").where().like("id","FFPM_"+filtre+"%").query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fihiranas;
    }

    public List<Fihirana> findAllTitleFF() {
        List<Fihirana> fihiranas = new ArrayList<>();
        try {
            fihiranas = dao.queryBuilder().selectColumns("id","title").where().like("id","F_F_%").query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fihiranas;
    }

    public List<Fihirana> findAllTitleFF(Integer filtre) {
        List<Fihirana> fihiranas = new ArrayList<>();
        try {
            fihiranas = dao.queryBuilder().selectColumns("id","title").where().like("id","F_F_"+filtre+"%").query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fihiranas;
    }

    public List<Fihirana> findByKeyWord(String keyWord) {
        List<Fihirana> fihiranas = new ArrayList<>();

        try {
            fihiranas = dao.queryBuilder().where().like("description", "%"+keyWord+"%").or().like("title", "%"+keyWord+"%").query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fihiranas;
    }
}
