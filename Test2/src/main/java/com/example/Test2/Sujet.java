package com.example.Test2;

import jakarta.persistence.*;

@Entity
@Table(name = "sujet")
public class Sujet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_sujet;

    private String titre;

    public Integer getId_sujet() {
        return id_sujet;
    }

    public void setId_sujet(Integer id_sujet) {
        this.id_sujet = id_sujet;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }
}
