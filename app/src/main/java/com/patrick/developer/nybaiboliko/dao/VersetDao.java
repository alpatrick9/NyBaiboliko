package com.patrick.developer.nybaiboliko.dao;

import android.content.Context;

import com.patrick.developer.nybaiboliko.configuration.DaoManager;
import com.patrick.developer.nybaiboliko.models.Verset;
import com.patrick.developer.nybaiboliko.tools.Tools;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by developer on 10/5/16.
 */

public class VersetDao extends AbstractDao<Verset,Long> {

    Context context;

    public VersetDao(Context context) {
        this.context = context;
        try {
            this.dao = new DaoManager(context).getVersetDao();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Integer countRow() throws SQLException {
        return (int)dao.countOf();
    }

    public List<Verset> findBy(String book, Integer chapitre, Integer versetFirst, Integer versetLast) {
        List<Verset> versets = new ArrayList<>();
        try {
            versets = dao.queryBuilder().where().eq("book",new Tools(context).formatTitleBook(book)).and().eq("chapitre_number",chapitre)
            .and().between("verset_number",versetFirst,versetLast).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return versets;
    }

}
