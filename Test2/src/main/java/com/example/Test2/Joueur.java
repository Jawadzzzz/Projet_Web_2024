package com.example.Test2;

import jakarta.persistence.*;

@Entity
@Table(name = "joueur")
public class Joueur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_joueur;

    private String nom;

    public Joueur(){

    }

    public Joueur(int id, String nom){
        this.nom = nom;
        this.id_joueur = id;
    }

    public Integer getId_joueur() {
        return id_joueur;
    }

    public void setId_joueur(Integer id_joueur) {
        this.id_joueur = id_joueur;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
