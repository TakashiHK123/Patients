package com.example.msc.patients.rowMapper;

import com.example.msc.patients.entity.Pacientes;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PacientesRowMapper implements RowMapper<Pacientes> {

@Override
public Pacientes mapRow(final ResultSet resultSet, final int rowNum) throws SQLException {

        final Pacientes pacientes= new Pacientes();
        pacientes.setIdPaciente(resultSet.getInt("id_paciente"));
        pacientes.setIdDatosPersonales(resultSet.getInt("id_datos_personales"));
        pacientes.setFechaIngreso(resultSet.getDate("fecha_ingreso"));
        pacientes.setEstado(resultSet.getString("estado"));

        return pacientes;
    }
}
