package com.example.msc.patients.repository;

import com.example.msc.patients.entity.Contactos;
import com.example.msc.patients.rowMapper.ContactosRowMapper;
import com.example.msc.patients.sqlerrorcode.CustomSQLErrorCodeTranslator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Map;

@Repository
public class ContactosRepository2 {

    private static final String SQL="SELECT * FROM contactos";
    private static final String SQL_POST = "INSERT INTO contactos (type, value, id_datos_personales) VALUES (?, ?, ?)";
    private static final String SQL_DELETE = "DELETE FROM contactos WHERE id_contacto=?";
    private static final String SQL_GET = "SELECT * FROM contactos WHERE id_contacto = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(final DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
        final CustomSQLErrorCodeTranslator customSQLErrorCodeTranslator = new CustomSQLErrorCodeTranslator();
        jdbcTemplate.setExceptionTranslator(customSQLErrorCodeTranslator);
    }
    public int addContacto(String type, String value, int idDatosPersonales) {
        return jdbcTemplate.update(SQL_POST, type, value, idDatosPersonales);
    }
    public Contactos getContactos(int idContacto) {
        Contactos contactos = jdbcTemplate.queryForObject(SQL_GET, new Object[] { idContacto }, new ContactosRowMapper());
        if(contactos!=null){
            return contactos;
        }else{
            return null;
        }
    }
    public Contactos addContactoReturnAlu(String type, String value, int idDatosPersonales){

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_POST, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, type);
            preparedStatement.setString(2, value);
            preparedStatement.setInt(3,idDatosPersonales);
            return preparedStatement;
        },keyHolder);
        Integer id = (Integer) keyHolder.getKeys()
                .entrySet().stream()
                .filter(m  -> m.getKey().equalsIgnoreCase("idalumno"))
                .map(Map.Entry::getValue)
                .findFirst().orElse(null);
        Contactos contactos = new Contactos();
        contactos.setTipo(type);
        contactos.setValor(value);
        contactos.setIdDatosPersonales(idDatosPersonales);
        contactos.setIdContacto(id);
        return contactos;

    }


}
