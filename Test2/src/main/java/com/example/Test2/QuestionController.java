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

public class QuestionController implements CommandLineRunner, PersonDAO {

    /* @Override
    public Student getPersonById(int id) {
        return null;
    }

    @Override
    public List<Student> finds() {
        return null;
    } */

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

    @PostMapping("/addquestion")
    public String addquestion(@RequestParam String... nom) { // Création d'une question
        Question question = new Question();
        int ba = 0;
        int ab = 0;


        List<Question> listequestion = findQuestion();
        List<Integer> nombrequestion = new ArrayList<Integer>();

        String sql = "INSERT INTO question (id_question, nom_question, id_sujet) VALUES (?, ?, ?)";
        int rows;
        try {

            for (int i = 0; i < findQuestion().size(); i++){
                nombrequestion.add(listequestion.get(i).getId_question());
            }

            for (int i = 1; i < findQuestion().size() + 2; i++) {
                if (!(nombrequestion.contains(i))) {
                    ab = i;
                    break;
                } else if (i == findQuestion().size() + 1) {
                    ab = i;
                    break;
                }
            }
            question.setId_question(ab);

            for (String nm : nom){
                if (ba == 0){
                    question.setNom_question(nm);
                    ba++;
                }
                else {
                    question.setSujet(Integer.parseInt(nm));
                    ba++;
                }
            }
            sql = "INSERT INTO question (id_question, nom_question, id_sujet) VALUES (?, ?, ?)";
            rows = jdbcTemplate.update(sql, question.getId_question(), question.getNom_question(), question.getSujet());

        }
        catch (Exception e){
            rows = 0;
        }


        String a = "";
        a += "<div style='color:green; background-color:yellow;'>";

        if (rows > 0) {
            a += "<h1>Added new question to repo!</h1><br>";
        }
        else{
            a += "<h1>Erreur</h1>";
        }
        for (int i = 0; i < findQuestion().size(); i++){
            a += "<br>Id : " + findQuestion().get(i).getId_question() + "<br>Nom : " + findQuestion().get(i).getNom_question() + "<br>Sujet : " + findQuestion().get(i).getSujet() + "<br>";
        }
        a += "</div>";
        a += "<br><a href='/Administrateur' style='color:blue;'>Menu</a>";
        return a;
    }

    @GetMapping("/testquestion") // Paramétrer une question à un sujet de Quiz
    public String testquestion(){
        String a = "<form action='/addquestion' method='POST' id='nameForm'>";
        a += "<div style='color:red; background-color:black;'>";
        a += "<label for='nameField2'>Nom : </label>";
        a += "<input name='nom' id='nameField2'><br>";
        a += "<label for='nameField3'>Numero sujet : </label>";
        a += "<input name='nom' id='nameField3'><br>";
        a += "<button type='submit'>Greet me</button>";
        a += "</div>";
        a += "</form>";
        a += "<br><a href='/Administrateur' style='color:blue;'>Menu</a>";
        return a;
    }

    @GetMapping("listequestion")
    public String listequestion(){
        String a = "";
        a += "<div style='color:green; background-color:yellow;'>";
        List<Question> question = findQuestion();
        try{
            for (int i = 0; i < question.size(); i++){
                a += "<br>Id : " + findQuestion().get(i).getId_question() + "<br>Nom : " + findQuestion().get(i).getNom_question() + "<br>Sujet : " + findQuestion().get(i).getSujet() + "<br>";
            }
        }
        catch (Exception e){
            a += "Vide<br>";
        }
        a += "</div>";
        a += "<a href='/Administrateur'>Menu</a>";
        return a;
    }

    @GetMapping("/deletequestion")
    public String deletequestion(){
        String a = "";
        a += "<form method='POST' action='/resultatquestion'>";
        a += "<div style='color:red; background-color:black;'>";
        a += "<label for='namefield'>Id : </label>";
        a += "<input id='namesfield' type='text' name='nom'>";
        a += "<button type='submit'>Valider</button>";
        a += "</div></form>";
        a += "<br><a href='/Administrateur'>Menu</a>";
        return a;
    }

    @PostMapping("/resultatquestion")
    public String resultatquestion(String nom){
        String a = "";
        a += "<div style='color:green; background-color:yellow;'>";
        try{
            String sql = "DELETE FROM question WHERE id_question = ?";
            jdbcTemplate.update(sql, Integer.parseInt(nom));
            sql = "DELETE FROM reponse WHERE id_question = ?";
            jdbcTemplate.update(sql, Integer.parseInt(nom));
            a += "<h3>Vous avez supprimé une question</h3>";
        }
        catch (Exception e){
            a += "<h3>Une erreur s'est produite</h3>";
        }
        a += "</div>";
        a += "<a href='/Administrateur'>Menu</a>";
        return a;
    }

}
