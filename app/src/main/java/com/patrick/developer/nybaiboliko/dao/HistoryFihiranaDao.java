package com.patrick.developer.nybaiboliko.dao;

import android.content.Context;

import com.j256.ormlite.stmt.DeleteBuilder;
import com.patrick.developer.nybaiboliko.configuration.DaoManager;
import com.patrick.developer.nybaiboliko.models.entity.HistoryFihirana;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by patmi on 16/10/2016.
 */
public class HistoryFihiranaDao extends AbstractDao<HistoryFihirana,Long> {

    Context context;

    public HistoryFihiranaDao(Context context) {
        this.context = context;
        try {
            this.dao = new DaoManager(context).getHistoryFihiranaDao();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Integer create(HistoryFihirana s) throws SQLException {
        DeleteBuilder<HistoryFihirana,Long> deleteBuilder = dao.deleteBuilder();
        deleteBuilder.where().eq("id_fihirana",s.getIdFihirana());
        deleteBuilder.delete();
        return super.create(s);
    }

    public List<HistoryFihirana> findAllOrder() {
        List<HistoryFihirana> historyFihiranas = new ArrayList<>();

        try {
            historyFihiranas = dao.queryBuilder().orderBy("id",false).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return historyFihiranas;
    }

}
