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

public class ReponseController implements CommandLineRunner, PersonDAO {

    @Override
    public Student getPersonById(int id) {
        return null;
    }

    @Override
    public List<Student> finds() {
        return null;
    }

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

    @PostMapping("/addreponse")
    public String addreponse(@RequestParam String... nom) { // Création d'une réponse à une question
        Reponse reponse = new Reponse();
        int ba = 0;
        int ab = 0;


        List<Reponse> listereponse = findReponse();
        List<Integer> nombrereponse = new ArrayList<Integer>();

        String sql = "INSERT INTO question (id_question, nom_question, id_sujet) VALUES (?, ?, ?)";
        int rows;
        String b = "";
        try {

            for (int i = 0; i < findReponse().size(); i++){
                nombrereponse.add(listereponse.get(i).getId_reponse());
            }

            for (int i = 1; i < findReponse().size() + 2; i++) {
                if (!(nombrereponse.contains(i))) {
                    ab = i;
                    break;
                } else if (i == findReponse().size() + 1) {
                    ab = i;
                    break;
                }
            }
            reponse.setId_reponse(ab);

            for (String nm : nom){
                if (ba == 0){
                    reponse.setNom_reponse(nm);
                    b += nm + " <br>";
                    ba++;
                }
                else if (ba == 1) {
                    reponse.setId_question(Integer.parseInt(nm));
                    b += nm + " <br>";
                    ba++;
                }
                else {
                    if (Integer.parseInt(nm) == 0){
                        reponse.setResultat(0);
                    }
                    else {
                        reponse.setResultat(1);
                    }
                    ba++;
                    b += nm + " <br>";
                }
            }
            sql = "INSERT INTO reponse (id_reponse, nom_reponse, id_question, resultat) VALUES (?, ?, ?, ?)";
            rows = jdbcTemplate.update(sql, reponse.getId_reponse(), reponse.getNom_reponse(), reponse.getId_question(), reponse.getResultat());

        }
        catch (Exception e){
            rows = 0;
            b += e.toString();
        }


        String a = "";
        a += "<div style='color:green; background-color:yellow;'>";

        if (rows > 0) {
            a += "<h1>Added new response to repo!</h1><br>";
            for (int i = 0; i < findReponse().size(); i++){
                a += "<br>Id : " + findReponse().get(i).getId_reponse() + "<br>Reponse : " + findReponse().get(i).getNom_reponse() + "<br>Question : " + findReponse().get(i).getId_question() + "<br>Vrai ou Faux (0 ou 1) : " + findReponse().get(i).getResultat() + "<br>";
            }
        }
        else{
            a += "<h1>Erreur</h1>";
        }
        a += "</div>";
        a += "<br><a href='/Administrateur' style='color:blue;'>Menu</a>";
        return a;
    }

    @GetMapping("/testreponse")
    public String testreponse(){ // Ecrire les paramètres pour mettre une réponse
        String a = "<form action='/addreponse' method='POST' id='nameForm'>";
        a += "<div style='color:red; background-color:black;'>";
        a += "<label for='nameField2'>Reponse : </label>";
        a += "<input name='nom' id='nameField2'><br>";
        a += "<label for='nameField3'>Numero Question : </label>";
        a += "<input name='nom' id='nameField3'><br>";
        a += "<label for='nameField3'>Vrai ou Faux (0, 1) ? : </label>";
        a += "<input name='nom' id='nameField4'><br>";
        a += "<button type='submit'>Greet me</button>";
        a += "</div>";
        a += "</form>";
        a += "<br><a href='/Administrateur'>Menu</a>";
        return a;
    }

    @GetMapping("listereponse")
    public String listereponse(){
        String a = "";
        a += "<div style='color:green; background-color:yellow;'>";
        List<Reponse> reponse = findReponse();
        try{
            for (int i = 0; i < reponse.size(); i++){
                a += "<br>Id : " + findReponse().get(i).getId_reponse() + "<br>Reponse : " + findReponse().get(i).getNom_reponse() + "<br>Question : " + findReponse().get(i).getId_question() + "<br>Vrai ou Faux (0 ou 1) : " + findReponse().get(i).getResultat() + "<br>";
            }
        }
        catch (Exception e){
            a += "Vide<br>";
        }
        a += "</div>";
        a += "<a href='/Administrateur'>Menu</a>";
        return a;
    }

    @GetMapping("/deletereponse")
    public String deletereponse(){
        String a = "";
        a += "<form method='POST' action='/resultatreponse'>";
        a += "<div style='color:red; background-color:black;'>";
        a += "<label for='namefield'>Id : </label>";
        a += "<input id='namesfield' type='text' name='nom'>";
        a += "<button type='submit'>Valider</button>";
        a += "</div></form>";
        a += "<br><a href='/Administrateur'>Menu</a>";
        return a;
    }

    @PostMapping("/resultatreponse")
    public String resultatreponse(String nom){
        String a = "";
        a += "<div style='color:green; background-color:yellow;'>";
        try{
            String sql = "DELETE FROM reponse WHERE id_reponse = ?";
            jdbcTemplate.update(sql, Integer.parseInt(nom));
            a += "<h3>Vous avez supprimé une reponse</h3>";
        }
        catch (Exception e){
            a += "<h3>Une erreur s'est produite</h3>";
        }
        a += "</div>";
        a += "<a href='/Administrateur'>Menu</a>";
        return a;
    }

}
