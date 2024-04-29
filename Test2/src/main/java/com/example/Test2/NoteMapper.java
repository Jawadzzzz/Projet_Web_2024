package com.example.Test2;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NoteMapper implements RowMapper<Note> {
    public Note mapRow(ResultSet resultSet, int i) throws SQLException {

        Note person = new Note();
        person.setId_note(resultSet.getInt("id_note"));
        person.setNum(resultSet.getInt("num"));
        person.setId_joueur(resultSet.getInt("id_joueur"));
        person.setId_sujet(resultSet.getInt("id_sujet"));
        return person;

    }
}
