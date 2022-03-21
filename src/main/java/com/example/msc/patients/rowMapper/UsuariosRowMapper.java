package com.example.msc.patients.rowMapper;



import com.example.msc.patients.entity.Usuarios;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuariosRowMapper implements RowMapper<Usuarios> {

    @Override
    public Usuarios mapRow(final ResultSet resultSet, final int rowNum) throws SQLException {

        final Usuarios usuarios= new Usuarios();
        usuarios.setIdUsuario(resultSet.getInt("id_usuarios"));
        usuarios.setUsername(resultSet.getString("username"));
        usuarios.setPassword(resultSet.getString("password"));
        usuarios.setEstado(resultSet.getString("estado"));
        usuarios.setIdDatosPersonales(resultSet.getInt("id_datos_personales"));
        return usuarios;
    }
}
