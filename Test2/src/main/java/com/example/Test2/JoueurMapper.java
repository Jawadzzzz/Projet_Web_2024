package com.example.Test2;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class JoueurMapper implements RowMapper<Joueur> {
    public Joueur mapRow(ResultSet resultSet, int i) throws SQLException {

        Joueur person = new Joueur();
        person.setId_joueur(resultSet.getInt("id_joueur"));
        person.setNom(resultSet.getString("nom"));
        return person;

    }
}
