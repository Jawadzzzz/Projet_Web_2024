package com.example.Test2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

public class AutreController implements CommandLineRunner, PersonDAO{


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

    @GetMapping("/Administrateur")
    public String Administrateur(){
        String a = "";
        a += "<div style='color:green; background-color:orange;'>";
        a += "<h3>Afficher</h3>";
        a += "<a href='/listejoueur'>Joueur</a><br>";
        a += "<a href='/listesujet'>Sujet</a><br>";
        a += "<a href='/listequestion'>Question</a><br>";
        a += "<a href='/listereponse'>Reponse</a><br>";
        a += "<a href='/listenote'>Note</a><br>";

        a += "<h3>Ajouter</h3>";
        a += "<a href='/testjoueur'>Joueur</a><br>";
        a += "<a href='/testsujet'>Sujet</a><br>";
        a += "<a href='/testquestion'>Question</a><br>";
        a += "<a href='/testreponse'>Reponse</a><br>";

        a += "<h3>Supprimer</h3>";
        a += "<a href='/deletejoueur'>Joueur</a><br>";
        a += "<a href='/deletesujet'>Sujet</a><br>";
        a += "<a href='/deletequestion'>Question</a><br>";
        a += "<a href='/deletereponse'>Reponse</a><br>";
        a += "<a href='/deletenote'>Note</a><br>";
        a += "</div>";
        return a;
    }

    @GetMapping("/testquiz")
    public String testquiz(){ // Choisir le sujet du Quiz
        String a = "";
        int b = findSujet().size();
        List<Sujet> c = findSujet();
        a += "<form action='/addquiz' method='POST' id='nameForm'>";
        a += "<div style='color:red; background-color:black;'>";
        for (int i = 0; i < b; i++){
            a += "<pre><input type='radio' name='nom' id='nameField2" + String.valueOf(i) + "' value = '" + c.get(i).getId_sujet() + "'>";
            a += "<label for='nameField" + String.valueOf(i) + "'>Sujet : " + c.get(i).getId_sujet() + "<br>   Titre : " + c.get(i).getTitre() + "</label></pre><br><br>";

        }
        a += "<button type='submit'>Greet me</button>";
        a += "</div>";
        a += "</form><br>";
        a += "<a href='/listenotebis'>Notes passées</a><br>";
        a += "<a href='/index.html'>Deconnexion</a>";
        return a;
    }

    @PostMapping("/addquiz")
    public String addquiz(int nom){ // Réalisation d'un quiz avec les questions/réponses du sujet choisi
        String a = "";
        a += "<div style='color:green; background-color:orange;'>";
        List<Reponse> b;
        try{
            List<Question> person = jdbcTemplate.query("SELECT * FROM question WHERE id_sujet = ?", new Object[] { nom }, new QuestionMapper());
            ;

            a += "<script language='javascript'>" + "const cars = [];";
            for (int i = 0; i < person.size(); i++){
                a += "cars[" + String.valueOf(i) + "] = 0;";
            }
            a += "</script>";
            int abc = 0;

            a += "<script>";
            for (int i = 0; i < person.size(); i++){
                b = jdbcTemplate.query("SELECT * FROM reponse WHERE id_question = ?", new Object[] { person.get(i).getId_question() }, new ReponseMapper());
                for (int j = 0; j < b.size(); j++){
                    a += "function submit" + String.valueOf(abc + 1) + "(){" +
                            "cars[" + String.valueOf(i) + "] = document.getElementById('demo" + String.valueOf(abc + 1) + "').value;" +
                            "document.getElementById('demo').innerHTML = `<form action='/resultat' method='POST'><input type='hidden' name='nom' value ='` + cars.toString() + `'><button type='submit'>Greet Me</button></form>`;}";
                    abc++;
                }
            }
            a += "</script>";

            abc = 0;
            for (int i = 0; i < person.size(); i++){
                a += "<div><fieldset>";
                a += "<h2>Question : " + person.get(i).getId_question() + "</h2>";
                a += person.get(i).getNom_question() + "<br>";
                a += "<h2>Reponse : </h2>";
                b = jdbcTemplate.query("SELECT * FROM reponse WHERE id_question = ?", new Object[] { person.get(i).getId_question() }, new ReponseMapper());
                for (int j = 0; j < b.size(); j++){
                    a += "<pre><input name='demo" + String.valueOf(i + 1) + "' onclick='submit" + String.valueOf(abc + 1) + "();' id='demo" + String.valueOf(abc + 1) + "' type='radio' value = " + b.get(j).getId_reponse() + ">";
                    a += "<label for='demo" + String.valueOf(abc + 1) + "'>" + b.get(j).getNom_reponse() + "</label></pre><br>";
                    abc++;
                }
                a += "</fieldset></div>";
            }
            a += "<br><span id='demo'></span>";
        }
        catch (Exception e){
            a += String.valueOf(e);
            a += "<br>" + String.valueOf(nom);
        }
        a += "</div>";
        a += "<br><a href='/index.html'>Menu</a>";
        return a;
    }

    /* @PostMapping("/resultat") // Afficher le résultat du quiz
    public String resultat(String... nom){
        int a = 0;
        int b = 0;
        int d = 0;
        List<Note> listenote = findNote();
        String sql;
        int rows = 0;
        int ab = 0;
        String ba = "";
        ba += "<div style='color:green; background-color:yellow;'>";
        List<Question> c = findQuestion();

        List<Integer> nombrenote = new ArrayList<Integer>();

        try{

            for (int i = 0; i < findNote().size(); i++){
                nombrenote.add(listenote.get(i).getId_note());
            }

            for (String nm : nom){
                if (Integer.parseInt(nm) != 0 && getReponseById(Integer.parseInt(nm)).getResultat() == 0){
                    a++;
                }
                d = getReponseById(Integer.parseInt(nm)).getId_question();
            }
            //ba += "<br>d = " + String.valueOf(d) + "<br>";
            //ba += "<br>c = " + String.valueOf(c.size()) + "<br>";

            for (int i = 0; i < c.size(); i++){
                //ba += "<br>sujet = " + String.valueOf(c.get(i).getSujet()) + "<br>";
                //ba += "<br>SUJET = " + String.valueOf(getQuestionById(d).getSujet()) + "<br>";
                if (c.get(i).getSujet() == getQuestionById(d).getSujet()){
                    b++;
                }
            }
            // try {
            rows = jdbcTemplate.update("DELETE FROM note WHERE id_sujet = " + String.valueOf(getQuestionById(d).getSujet()) + ";");
            //}
            // catch (Exception f){

            //}

            for (int i = 1; i < findNote().size() + 2; i++) {
                if (!(nombrenote.contains(i))) {
                    ab = i;
                    break;
                } else if (i == findNote().size() + 1) {
                    ab = i;
                    break;
                }
            }

            double bac = (double) a;
            bac *= 1.0;
            bac /= ((double) b * 1.0);
            bac *= 100.0;
            sql = "INSERT INTO note (id_note, num, id_sujet) VALUES (?, ?, ?);";
            rows = jdbcTemplate.update(sql, ab, bac, getQuestionById(d).getSujet());

            List<Double> classement = new ArrayList<Double>();
            for (int i = 0; i < findNote().size(); i++){
                classement.add(findNote().get(i).getNum());
            }
            Collections.sort(classement);
            Collections.reverse(classement);

            int rang = 1;

            List<Integer> liste = new ArrayList<Integer>();
            for (int j = 0; j < classement.size(); j++) {
                for (int i = 0; i < findNote().size(); i++) {
                    if (findNote().get(i).getNum() == classement.get(j) && !(liste.contains(findNote().get(i).getId_note()))) {

                        ba += "<br>Rang : " + String.valueOf(rang) + "<br>Id : " + String.valueOf(findNote().get(i).getId_note()) + "<br>Joueur : " + String.valueOf(findNote().get(i).getId_joueur()) + "<br>Sujet : " + String.valueOf(findNote().get(i).getId_sujet()) + "<br>Note : " + String.valueOf(findNote().get(i).getNum()) + " %<br>";
                        liste.add(findNote().get(i).getId_note());
                        rang += 1;
                    }
                    // ba += "<br>Id : " + String.valueOf(findNote().get(i).getId_note()) + "<br>Sujet : " + String.valueOf(findNote().get(i).getId_sujet()) + "<br>Note : " + String.valueOf(findNote().get(i).getNum()) + " %<br>";
                }
            }

        }
        catch (Exception e){
            rows = 0;
            ba += String.valueOf(e);
        }


        if (rows > 0) {
            ba += "<h1>Added new result to repo!</h1><br>";
        }
        else{
            ba += "<h1>Erreur</h1>";
        }
        //for (int i = 0; i < findNote().size(); i++){
          //  ba += "<br>Id : " + String.valueOf(findNote().get(i).getId_note()) + "<br>Sujet : " + String.valueOf(findNote().get(i).getId_sujet()) + "<br>Note : " + String.valueOf(findNote().get(i).getNum()) + " %<br>";
        //}
        ba += "</div>";
        ba += "<br><a href='/testquiz'>Menu</a>";
        return ba;
    } */

}
