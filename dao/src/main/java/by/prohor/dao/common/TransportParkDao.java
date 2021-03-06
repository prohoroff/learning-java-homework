package by.prohor.dao.common;

import java.util.List;

/**
 * Created by Artsiom Prokharau 22.02.2021
 */


public interface TransportParkDao<T> {

    List<T> getAll();

    T save(T model);

    Integer delete(Integer id);

    Integer update(T model);


}
