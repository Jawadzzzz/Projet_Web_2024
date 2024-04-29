package com.example.Test2;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SujetMapper implements RowMapper<Sujet> {
    public Sujet mapRow(ResultSet resultSet, int i) throws SQLException {

        Sujet person = new Sujet();
        person.setId_sujet(resultSet.getInt("id_sujet"));
        person.setTitre(resultSet.getString("Titre"));
        return person;

    }
}
