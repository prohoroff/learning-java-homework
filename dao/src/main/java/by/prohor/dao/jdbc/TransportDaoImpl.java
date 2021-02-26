package by.prohor.dao.jdbc;

import by.prohor.dao.TransportDao;
import by.prohor.model.Transport;
import by.prohor.model.type.FuelType;
import by.prohor.model.type.TypeTransport;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Artsiom Prokharau 22.02.2021
 */

public class TransportDaoImpl implements TransportDao {

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    public TransportDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void init() {
        simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("TRANSPORT")
                .usingGeneratedKeyColumns("TRANSPORT_ID");
    }

    @Override
    public List<Transport> getAll() {
        String request = "SELECT * FROM TRANSPORT AS T ORDER BY T.TRANSPORT_ID";
        return jdbcTemplate.query(request, new TransportRowMapper());
    }

    @Override
    public Transport save(Transport model) {
        Number number = simpleJdbcInsert.executeAndReturnKey(mapTransport(model));
        model.setTransportId(number.intValue());
        return model;
    }

    @Override
    public Transport findById(Integer transportId) {
        return jdbcTemplate.queryForObject("SELECT * FROM TRANSPORT WHERE TRANSPORT_ID = ?", new TransportRowMapper(), transportId);
    }

    @Override
    public Integer delete(Integer transportId) {
        int delete = jdbcTemplate.update("DELETE FROM TRANSPORT WHERE TRANSPORT_ID = ?", transportId);
//        LOGGER.info("Удаление по id = {} и количество удаленных  данных = {}", id, delete);
        return delete;
    }

    @Override
    public Integer update(Transport model) {
        String request = "UPDATE TRANSPORT SET TRANSPORT_TYPE = ?,FUEL_TYPE = ?,REGISTER_NUMBER= ?,CAPACITY= ?, DATE_OF_MANUFACTURE = ? WHERE TRANSPORT_ID = ?";
        int update = jdbcTemplate.update(request, String.valueOf(model.getTransportType()), String.valueOf(model.getFuelType()), model.getRegisterNumber(), model.getCapacity(), model.getDateOfManufacture(), model.getTransportId());
//        LOGGER.info("Удаление по id = {} и количество удаленных  данных = {}", model.getRouteId()id, update);
        return update;
    }


    private Map<String, Object> mapTransport(Transport model) {
        Map<String, Object> mapRoute = new HashMap<>();
        mapRoute.put("TRANSPORT_TYPE", String.valueOf(model.getTransportType()));
        mapRoute.put("FUEL_TYPE", String.valueOf(model.getFuelType()));
        mapRoute.put("REGISTER_NUMBER", model.getRegisterNumber());
        mapRoute.put("CAPACITY", model.getCapacity());
        mapRoute.put("DATE_OF_MANUFACTURE", model.getDateOfManufacture());
        mapRoute.put("NUMBER_ROUTE", model.getNumberRoute());
        return mapRoute;
    }

    private class TransportRowMapper implements RowMapper<Transport> {

        @Override
        public Transport mapRow(ResultSet resultSet, int i) throws SQLException {
            Transport transport = new Transport();

            transport.setTransportId(resultSet.getInt("TRANSPORT_ID"));
            transport.setTransportType(TypeTransport.valueOf(resultSet.getString("TRANSPORT_TYPE")));
            transport.setFuelType(FuelType.valueOf(resultSet.getString("FUEL_TYPE")));
            transport.setRegisterNumber(resultSet.getString("REGISTER_NUMBER"));
            transport.setCapacity(resultSet.getInt("CAPACITY"));
            transport.setDateOfManufacture(resultSet.getDate("DATE_OF_MANUFACTURE"));
            transport.setNumberRoute(resultSet.getInt("NUMBER_ROUTE"));

            return transport;
        }
    }
}
