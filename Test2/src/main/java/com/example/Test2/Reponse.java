package com.example.Test2;

import jakarta.persistence.*;

@Entity
@Table(name = "reponse")
public class Reponse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_reponse;
    private String nom_reponse;
    private int id_question;
    private int resultat;

    public Reponse(){

    }

    public int getId_reponse() {
        return id_reponse;
    }

    public void setId_reponse(int id_reponse) {
        this.id_reponse = id_reponse;
    }

    public String getNom_reponse() {
        return nom_reponse;
    }

    public void setNom_reponse(String nom_reponse) {
        this.nom_reponse = nom_reponse;
    }

    public int getId_question() {
        return id_question;
    }

    public void setId_question(int id_question) {
        this.id_question = id_question;
    }

    public int getResultat() {
        return resultat;
    }

    public void setResultat(int resultat) {
        this.resultat = resultat;
    }
}
