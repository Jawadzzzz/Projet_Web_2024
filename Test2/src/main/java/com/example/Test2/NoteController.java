package com.example.Test2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NoteController implements CommandLineRunner, PersonDAO {



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

    @Override
    public void run(String... args) throws Exception {

    }

    @GetMapping("listenote")
    public String listenote(){
        String a = "";
        a += "<div style='color:green; background-color:yellow;'>";
        List<Note> note = findNote();
        try{

            List<Double> classement = new ArrayList<Double>();
            for (int i = 0; i < findNote().size(); i++){
                classement.add(findNote().get(i).getNum());
            }
            Collections.sort(classement);
            Collections.reverse(classement);

            List<Integer> liste = new ArrayList<Integer>();
            int rang = 0;
            for (int j = 0; j < classement.size(); j++) {
                for (int i = 0; i < findNote().size(); i++) {
                    if (findNote().get(i).getNum() == classement.get(j) && !(liste.contains(findNote().get(i).getId_note()))) {
                        a += "<br>Rang : " + String.valueOf(rang) + "<br>Id : " + String.valueOf(findNote().get(i).getId_note()) + "<br>Joueur : " + String.valueOf(findNote().get(i).getId_joueur()) + "<br>Sujet : " + String.valueOf(findNote().get(i).getId_sujet()) + "<br>Note : " + String.valueOf(findNote().get(i).getNum()) + " %<br>";
                        liste.add(findNote().get(i).getId_note());
                        rang++;
                    }
                    // ba += "<br>Id : " + String.valueOf(findNote().get(i).getId_note()) + "<br>Sujet : " + String.valueOf(findNote().get(i).getId_sujet()) + "<br>Note : " + String.valueOf(findNote().get(i).getNum()) + " %<br>";
                }
            }

            // for (int i = 0; i < note.size(); i++){
               // a += "<br>Id : " + String.valueOf(findNote().get(i).getId_note()) + "<br>Sujet : " + String.valueOf(findNote().get(i).getId_sujet()) + "<br>Note : " + String.valueOf(findNote().get(i).getNum()) + " %<br>";
            //}
        }
        catch (Exception e){
            a += "Vide<br>";
        }
        a += "</div>";
        a += "<a href='/Administrateur'>Menu</a>";
        return a;
    }

    @GetMapping("/deletenote")
    public String deletenote(){
        String a = "";
        a += "<form method='POST' action='/resultatnote'>";
        a += "<div style='color:red; background-color:black;'>";
        a += "<label for='namefield'>Id : </label>";
        a += "<input id='namesfield' type='text' name='nom'>";
        a += "<button type='submit'>Valider</button>";
        a += "</div></form>";
        a += "<br><a href='/Administrateur'>Menu</a>";
        return a;
    }

    @PostMapping("/resultatnote")
    public String resultatnote(String nom){
        String a = "";
        a += "<div style='color:green; background-color:yellow;'>";
        try{
            String sql = "DELETE FROM note WHERE id_note = ?";
            jdbcTemplate.update(sql, Integer.parseInt(nom));
            a += "<h3>Vous avez supprimé une note</h3>";
        }
        catch (Exception e){
            a += "<h3>Une erreur s'est produite</h3>";
        }
        a += "</div>";
        a += "<a href='/Administrateur'>Menu</a>";
        return a;
    }

    @GetMapping("listenotebis")
    public String listenotebis(){
        String a = "";
        a += "<div style='color:green; background-color:yellow;'>";
        List<Note> note = findNote();
        try{

            List<Double> classement = new ArrayList<Double>();
            for (int i = 0; i < findNote().size(); i++){
                classement.add(findNote().get(i).getNum());
            }
            Collections.sort(classement);
            Collections.reverse(classement);

            List<Integer> liste = new ArrayList<Integer>();
            int rang = 1;
            for (int j = 0; j < classement.size(); j++) {
                for (int i = 0; i < findNote().size(); i++) {
                    if (findNote().get(i).getNum() == classement.get(j) && !(liste.contains(findNote().get(i).getId_note()))) {
                        a += "<br>Rang : " + String.valueOf(j + 1) + "<br>Id : " + String.valueOf(findNote().get(i).getId_note()) + "<br>Joueur : " + String.valueOf(findNote().get(i).getId_joueur()) + "<br>Sujet : " + String.valueOf(findNote().get(i).getId_sujet()) + "<br>Note : " + String.valueOf(findNote().get(i).getNum()) + " %<br>";
                        liste.add(findNote().get(i).getId_note());
                        rang++;
                    }
                    // ba += "<br>Id : " + String.valueOf(findNote().get(i).getId_note()) + "<br>Sujet : " + String.valueOf(findNote().get(i).getId_sujet()) + "<br>Note : " + String.valueOf(findNote().get(i).getNum()) + " %<br>";
                }
            }

            //for (int i = 0; i < note.size(); i++){
              //  a += "<br>Id : " + String.valueOf(findNote().get(i).getId_note()) + "<br>Sujet : " + String.valueOf(findNote().get(i).getId_sujet()) + "<br>Note : " + String.valueOf(findNote().get(i).getNum()) + " %<br>";
            //}
        }
        catch (Exception e){
            a += "Vide<br>";
        }
        a += "</div>";
        a += "<a href='/testquiz'>Menu</a>";
        return a;
    }

}
