package com.example.Test2;

import java.util.List;

public interface PersonDAO {
    Student getPersonById(int id);

    List<Student> finds();

    Joueur getJoueurById(int id);

    List<Joueur> findjoueur();

    Question getQuestionById(int id);

    List<Question> findQuestion();

    Sujet getSujetById(int id);

    List<Sujet> findSujet();

    Reponse getReponseById(int id);

    List<Reponse> findReponse();

    Note getNoteById(int id);

    List<Note> findNote();

    //List<Student> getAllPersons();

    //boolean deletePerson(Person person);

    //boolean updatePerson(Person person);

    //boolean createPerson(Person person);
}
