package com.example.Test2;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReponseMapper implements RowMapper<Reponse> {
    public Reponse mapRow(ResultSet resultSet, int i) throws SQLException {

        Reponse person = new Reponse();
        person.setId_reponse(resultSet.getInt("id_reponse"));
        person.setNom_reponse(resultSet.getString("nom_reponse"));
        person.setId_question(resultSet.getInt("id_question"));
        person.setResultat(resultSet.getInt("resultat"));
        return person;

    }
}
