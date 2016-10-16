package com.patrick.developer.nybaiboliko.dao;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by developer on 10/5/16.
 */

public class AbstractDao<E,K> {

    protected Dao<E,K> dao;

    public List<E> findAll() throws SQLException {
        return dao.queryForAll();
    }

    public Integer create(E s) throws SQLException {
        return dao.create(s);
    }

    public Integer delete(E s) throws SQLException {
        return dao.delete(s);
    }

    public Integer update(E s) throws SQLException {
        return dao.update(s);
    }



    public Integer countRow() throws SQLException {
        return (int)dao.countOf();
    }
}
