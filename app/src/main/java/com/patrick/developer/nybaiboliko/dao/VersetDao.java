package com.patrick.developer.nybaiboliko.dao;

import android.content.Context;

import com.google.inject.Inject;
import com.patrick.developer.nybaiboliko.configuration.DaoManager;
import com.patrick.developer.nybaiboliko.models.Verset;

import java.sql.SQLException;


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

}
