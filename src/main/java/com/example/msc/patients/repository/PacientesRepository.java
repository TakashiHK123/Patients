package com.example.msc.patients.repository;

public class PacientesRepository {

    private static final String SQL="SELECT * FROM pacientes ";
    private static final String SQL_POST = "INSERT INTO pacientes (id_datos_personales, fecha_ingreso, estado) VALUES (?, ?, ?)";
    private static final String SQL_DELETE = "DELETE FROM pacientes WHERE id_paciente=?";
    private static final String SQL_GET = "SELECT * FROM pacientes WHERE id_paciente = ?";


}
