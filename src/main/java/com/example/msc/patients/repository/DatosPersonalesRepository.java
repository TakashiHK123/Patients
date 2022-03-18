package com.example.msc.patients.repository;

import com.example.msc.patients.entity.DatosPersonales;
import com.example.msc.patients.rowMapper.DatosPersonalesRowMapper;
import com.example.msc.patients.sqlerrorcode.CustomSQLErrorCodeTranslator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Map;


@Repository
public class DatosPersonalesRepository {

    private static final String SQL="SELECT * FROM datospersonales ";
    private static final String SQL_POST = "INSERT INTO datospersonales (nombre, apellido, nro_documento, fecha_nacimiento, peso) VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_DELETE = "DELETE FROM datospersonales WHERE id_datos_personales=?";
    private static final String SQL_GET = "SELECT * FROM datospersonales WHERE id_contacto = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(final DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
        final CustomSQLErrorCodeTranslator customSQLErrorCodeTranslator = new CustomSQLErrorCodeTranslator();
        jdbcTemplate.setExceptionTranslator(customSQLErrorCodeTranslator);
    }

    public List<DatosPersonales> getAll() {  //ver si se va a usar, si no se elimina
        return jdbcTemplate.query(SQL, new DatosPersonalesRowMapper());
    }
    //retorna un retorna el id si se genera, y si no retorna un 0
    public int addDatosPersonales(String nombre, String apellido, String nroDocumento, Date fechaNacimiento, double peso){

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_POST, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, nombre);
            preparedStatement.setString(2, apellido);
            preparedStatement.setString(3,nroDocumento);
            preparedStatement.setDate(4,fechaNacimiento);
            preparedStatement.setDouble(5,peso);
            return preparedStatement;
        },keyHolder);
        Integer id = (Integer) keyHolder.getKeys()
                .entrySet().stream()
                .filter(m  -> m.getKey().equalsIgnoreCase("id_datos_personales"))
                .map(Map.Entry::getValue)
                .findFirst().orElse(null);
        int idReturn=0;
        if(id!=null){
            idReturn = id;
        }else{
            idReturn=0;
        }
        return idReturn;
    }




}

