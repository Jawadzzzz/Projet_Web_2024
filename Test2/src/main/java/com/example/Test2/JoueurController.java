package com.example.Test2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;


public class JoueurController implements CommandLineRunner, PersonDAO {



    @Override
    public Joueur getJoueurById(int id) { // Trouver un joueur selon son Id
        return jdbcTemplate.queryForObject("SELECT * FROM joueur WHERE id_joueur = ?", new Object[] { id }, new JoueurMapper());
    }

    @Override
    public Question getQuestionById(int id) { // Trouver une question avec son Id
        return jdbcTemplate.queryForObject("SELECT * FROM question WHERE id_question = ?", new Object[] { id }, new QuestionMapper());
    }

    @Override
    public Reponse getReponseById(int id) { // Trouver une réponse selon son Id
        return jdbcTemplate.queryForObject("SELECT * FROM reponse WHERE id_reponse = ?", new Object[] { id }, new ReponseMapper());
    }

    @Override
    public Sujet getSujetById(int id) { // Trouver un sujet avec son Id
        return jdbcTemplate.queryForObject("SELECT * FROM sujet WHERE id_sujet = ?", new Object[] { id }, new SujetMapper());
    }

    @Override
    public Note getNoteById(int id) { // Retrouver une note avec l'Id
        return jdbcTemplate.queryForObject("SELECT * FROM note WHERE id_note = ?", new Object[] { id }, new NoteMapper());
    }

    @Override
    public List<Joueur> findjoueur() { // Lister tous les joueurs
        String sql = """
           SELECT *
           FROM joueur
           ;
           """;
        return jdbcTemplate.query(sql,new JoueurMapper());
    }

    @Override
    public List<Question> findQuestion() { // Lister toutes les questions
        String sql = """
           SELECT *
           FROM question
           ;
           """;
        return jdbcTemplate.query(sql,new QuestionMapper());
    }

    @Override
    public List<Reponse> findReponse() { // Lister toutes les réponses
        String sql = """
           SELECT *
           FROM reponse
           ;
           """;
        return jdbcTemplate.query(sql,new ReponseMapper());
    }

    @Override
    public List<Sujet> findSujet() { // Lister tous les sujets
        String sql = """
           SELECT *
           FROM sujet
           ;
           """;
        return jdbcTemplate.query(sql,new SujetMapper());
    }

    @Override
    public List<Note> findNote() { // Lister toutes les notes
        String sql = """
           SELECT *
           FROM note
           ;
           """;
        return jdbcTemplate.query(sql,new NoteMapper());
    }


    @Autowired
    private JdbcTemplate jdbcTemplate;
    public static void main(String[] args) {
        SpringApplication.run(JoueurController.class, args);
    }

    @PostMapping("/addjoueur")
    public String addjoueur(@RequestParam String nom) { // Inscription d'un nouveau joueur
        Joueur joueur = new Joueur();
        int ab = 0;
        List<Joueur> listejoueur = findjoueur();
        List<Integer> nombrejoueur = new ArrayList<Integer>();

        String sql = "INSERT INTO joueur (id_joueur, nom) VALUES (?, ?)";
        int rows;
        try {

            for (int i = 0; i < findjoueur().size(); i++){
                nombrejoueur.add(listejoueur.get(i).getId_joueur());
            }

            for (int i = 1; i < findjoueur().size() + 2; i++) {
                if (!(nombrejoueur.contains(i))) {
                    ab = i;
                    break;
                } else if (i == findjoueur().size() + 1) {
                    ab = i;
                    break;
                }
            }
            joueur.setId_joueur(ab);
            joueur.setNom(nom);

            rows = jdbcTemplate.update(sql, joueur.getId_joueur(), joueur.getNom());
        }
        catch (Exception e){
            rows = 0;
        }


        String a = "";
        a += "<div style='color:green; background-color:yellow;'>";

        if (rows > 0) {
            a += "<h1>Added new player to repo!</h1><br>";
        }
        else{
            a += "<h1>Erreur</h1>";
        }
        for (int i = 0; i < findjoueur().size(); i++){
            a += "<br>Id : " + findjoueur().get(i).getId_joueur() + "<br>Nom : " + findjoueur().get(i).getNom() + "<br>";
        }
        a += "<br><a href='/Administrateur' style='color:blue;'>Menu</a></div>";
        return a;
    }

    @GetMapping("/testjoueur")
    public String testjoueur(){ // Paramétrer le nom d'un nouveau joueur
        String a = "<form action='/addjoueur' method='POST' id='nameForm'>";
        a += "<div style='color:red; background-color:black;'>";
        a += "<label for='nameField2'>Nom : </label>";
        a += "<input name='nom' id='nameField2'><br>";
        a += "<button type='submit'>Greet me</button>";
        a += "</div>";
        a += "</form>";
        a += "<br><a href='/Administrateur' style='color:blue;'>Menu</a>";
        return a;
    }

    @GetMapping("listejoueur")
    public String listejoueur(){
        String a = "";
        a += "<div style='color:green; background-color:yellow;'>";
        List<Joueur> joueur = findjoueur();
        try{
            for (int i = 0; i < joueur.size(); i++){
                a += "<br>Id : " + findjoueur().get(i).getId_joueur() + "<br>Nom : " + findjoueur().get(i).getNom() + "<br>";
            }
        }
        catch (Exception e){
            a += "Vide<br>";
        }
        a += "</div>";
        a += "<a href='/Administrateur'>Menu</a>";
        return a;
    }

    @GetMapping("/deletejoueur")
    public String deletejoueur(){
        String a = "";
        a += "<form method='POST' action='/resultatjoueur'>";
        a += "<div style='color:green; background-color:yellow;'>";
        a += "<label for='namefield'>Id : </label>";
        a += "<input id='namesfield' type='text' name='nom'>";
        a += "<button type='submit'>Valider</button>";
        a += "</div></form>";
        a += "<br><a href='/Administrateur'>Menu</a>";
        return a;
    }

    @PostMapping("/resultatjoueur")
    public String resultatjoueur(String nom){
        String a = "";
        a += "<div style='color:green; background-color:yellow;'>";
        try{
            String sql = "DELETE FROM note WHERE id_joueur = ?";
            jdbcTemplate.update(sql, Integer.parseInt(nom));
            sql = "DELETE FROM joueur WHERE id_joueur = ?";
            jdbcTemplate.update(sql, Integer.parseInt(nom));
            a += "<h3>Vous avez supprimé un joueur</h3>";
        }
        catch (Exception e){
            a += "<h3>Une erreur s'est produite</h3>";
        }
        a += "</div>";
        a += "<a href='/Administrateur'>Menu</a>";
        return a;
    }



    public void run(String... args) throws Exception {

    }
}

