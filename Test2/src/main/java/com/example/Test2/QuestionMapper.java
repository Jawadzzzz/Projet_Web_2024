package com.example.Test2;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class QuestionMapper implements RowMapper<Question> {
    public Question mapRow(ResultSet resultSet, int i) throws SQLException {

        Question person = new Question();
        person.setId_question(resultSet.getInt("id_question"));
        person.setNom_question(resultSet.getString("nom_question"));
        person.setSujet(resultSet.getInt("id_sujet"));
        return person;

    }
}
