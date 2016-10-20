package com.patrick.developer.nybaiboliko.dao;

import android.content.Context;

import com.j256.ormlite.stmt.DeleteBuilder;
import com.patrick.developer.nybaiboliko.configuration.DaoManager;
import com.patrick.developer.nybaiboliko.models.entity.HistoryVerset;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by developer on 10/17/16.
 */

public class HistoryVersetDao extends AbstractDao<HistoryVerset,Long> {

    Context context;

    public HistoryVersetDao(Context context) {
        this.context = context;
        try {
            this.dao = new DaoManager(context).getHistoryVersetDao();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Integer create(HistoryVerset s) throws SQLException {
        DeleteBuilder<HistoryVerset,Long> deleteBuilder = dao.deleteBuilder();
        deleteBuilder.where().eq("book",s.getBook()).and().eq("chapitre_number",s.getChapitreNumber())
                .and().eq("verset_number_start",s.getVersetNumberStart()).and().eq("verset_number_last",s.getVersetNumberLast());
        deleteBuilder.delete();
        return super.create(s);
    }

    public List<HistoryVerset> findAllOrder() {
        List<HistoryVerset> historyVersets = new ArrayList<>();

        try {
            historyVersets = dao.queryBuilder().orderBy("id",false).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return historyVersets;
    }
}
