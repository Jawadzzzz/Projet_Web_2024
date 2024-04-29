package com.example.Test2;

import jakarta.persistence.*;

@Entity
@Table(name = "question")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_question;

    private String nom_question;
    private int id_sujet;

    public Integer getId_question() {
        return id_question;
    }

    public void setId_question(Integer id_question) {
        this.id_question = id_question;
    }

    public String getNom_question() {
        return nom_question;
    }

    public void setNom_question(String nom_question) {
        this.nom_question = nom_question;
    }

    public int getSujet() {
        return id_sujet;
    }

    public void setSujet(int sujet) {
        this.id_sujet = sujet;
    }
}
