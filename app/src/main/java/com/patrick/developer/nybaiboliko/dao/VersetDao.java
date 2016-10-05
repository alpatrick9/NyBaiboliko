package com.patrick.developer.nybaiboliko.dao;

import android.content.Context;

import com.google.inject.Inject;
import com.patrick.developer.nybaiboliko.configuration.DaoManager;
import com.patrick.developer.nybaiboliko.models.Verset;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by developer on 10/5/16.
 */

public class VersetDao extends AbstractDao<Verset,Long> {

    public VersetDao(Context context) {
        try {
            this.dao = new DaoManager(context).getVersetDao();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Verset> findBy(String book, Integer chapitre, Integer versetStart, Integer versetLast) {
        List<Verset> versets = new ArrayList<>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("book",book);
        map.put("chapitre_number",chapitre);
        map.put("verset_number",versetStart);
        try {
            versets = dao.queryForFieldValues(map);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return versets;
    }

}
