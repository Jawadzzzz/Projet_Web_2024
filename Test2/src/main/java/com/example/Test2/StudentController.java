package com.example.Test2;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@RestController
public class StudentController implements CommandLineRunner, PersonDAO  {
    @Autowired
    private StudentRepository studentrepo;

    @Autowired
    private JdbcTemplate jdbcTemplate;
    public static void main(String[] args) {
        SpringApplication.run(StudentController.class, args);
    }

    public StudentController(){

    }

    static List<String> liste = new ArrayList<>();

  /*  @Override
    public void run(String... args) throws Exception{
        List<Student> students = studentrepo.findAll();
        students.forEach(System.out :: println);
        // System.exit(0 );
    } */

/*
    @PostMapping("/add")
    public String addCustomer(@RequestParam(value = "id") int id, @RequestParam(value = "name") String name, @RequestParam(value = "email") String email) {
        Student student = new Student(id, name, email);
        String sql = "DELETE FROM student WHERE id = " + String.valueOf(id) + "; INSERT INTO student (id, name, email) VALUES (" + String.valueOf(student.getId()) + ", '" + student.getName() + "', '" + student.getEmail() + "')";
        int rows = jdbcTemplate.update(sql);

        String quer = "SELECT * FROM student WHERE id = ?";
        quer = "SELECT * FROM student WHERE id = " + String.valueOf(id) + ";";
        List<Map<String, Object>> names = jdbcTemplate.queryForList(quer);
        String test1 = "";
        for (int i = 0; i < ((List<?>) names).size(); i++){
            test1 += names.get(i) + "<br><br>";
        }

        //String names = jdbcTemplate.queryForObject(quer, String.class, new Object[] { id });
        //System.out.println(names);

        if (rows > 0) {
            // System.out.println("A new row has been inserted.");
        }
        String a = "";
        for (int i = 0; i < finds().size(); i++){
            a += "<br>Id : " + finds().get(i).getId() + "<br>Nom : " + finds().get(i).getName() + "<br>E-mail : " + finds().get(i).getEmail() + "<br>";
        }
        return "<h1>Added new customer to repo!</h1><br>" + a;
    } */

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

    @PostMapping("/acces")
    public String acces(String myName){
        String a = "";
        a += "<div style='color:green; background-color:yellow;'>";
        try {
            List<Joueur> joueur = findjoueur();
            for (int i = 0; i < joueur.size(); i++){
                if (joueur.get(i).getNom().equals(myName)){
                    a += "<h3>Vous êtes authentifiés</h3><a href='/testquiz'>Menu</a><br>";
                    break;
                }
                else if (!(joueur.get(i).getNom().equals(myName)) && i == joueur.size() - 1){
                    a += "<h3>Le joueur " + myName + " n'existe pas</h3><a href='index.html'>Reessayer</a>";
                }
            }
        }
        catch (Exception e) {
            a += "<h3>Une erreur s'est produite</h3><a href='index.html'>Reessayer</a>";
        }
        a += "</div>";
        return a;
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

    @PostMapping("/addreponse")
    public String addreponse(@RequestParam String... nom) { // Création d'une réponse à une question
        Reponse reponse = new Reponse();
        int ba = 0;
        int ab = 0;


        List<Reponse> listereponse = findReponse();
        List<Integer> nombrereponse = new ArrayList<Integer>();

        String sql = "";
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

    @PostMapping("/addsujet")
    public String addsujet(@RequestParam String... nom) { // Création d'un sujet quiz
        Sujet sujet = new Sujet();
        int ba = 0;
        int ab = 0;
        List<Sujet> listesujet = findSujet();
        List<Integer> nombresujet= new ArrayList<Integer>();


        String sql = "INSERT INTO question (id_question, nom_question, id_sujet) VALUES (?, ?, ?)";
        int rows;
        try {

            for (int i = 0; i < findSujet().size(); i++){
                nombresujet.add(listesujet.get(i).getId_sujet());
            }

            for (int i = 1; i < findSujet().size() + 2; i++) {
                if (!(nombresujet.contains(i))) {
                    ab = i;
                    break;
                } else if (i == findSujet().size() + 1) {
                    ab = i;
                    break;
                }
            }

            sujet.setId_sujet(ab);

            for (String nm : nom){
                sujet.setTitre(nm);
            }
            sql = "INSERT INTO sujet (id_sujet, titre) VALUES (?, ?)";
            rows = jdbcTemplate.update(sql, sujet.getId_sujet(), sujet.getTitre());

        }
        catch (Exception e){
            rows = 0;
        }


        String a = "";
        a += "<div style='color:green; background-color:yellow;'>";

        if (rows > 0) {
            a += "<h1>Added new subject to repo!</h1><br>";
        }
        else{
            a += "<h1>Erreur</h1>";
        }
        for (int i = 0; i < findSujet().size(); i++){
            a += "<br>Id : " + findSujet().get(i).getId_sujet() + "<br>Sujet : " + findSujet().get(i).getTitre() + "<br>";
        }
        a += "</div>";
        a += "<br><a href='/Administrateur'>Menu</a>";
        return a;
    }

    @GetMapping("/testsujet")
    public String testsujet(){ // Selectionner les paramètres permettant de créer un sujet
        String a = "<form action='/addsujet' method='POST' id='nameForm'>";
        a += "<div style='color:red; background-color:black;'>";
        a += "<label for='nameField2'>Titre : </label>";
        a += "<input name='nom' id='nameField2'><br>";
        a += "<button type='submit'>Greet me</button>";
        a += "</div>";
        a += "</form>";
        a += "<br><a href='/Administrateur'>Menu</a>";
        return a;
    }

    @GetMapping("listesujet")
    public String listesujet(){
        String a = "";
        a += "<div style='color:green; background-color:yellow;'>";
        List<Sujet> sujet = findSujet();
        try{
            for (int i = 0; i < sujet.size(); i++){
                a += "<br>Id : " + findSujet().get(i).getId_sujet() + "<br>Sujet : " + findSujet().get(i).getTitre() + "<br>";
            }
        }
        catch (Exception e){
            a += "Vide<br>";
        }
        a += "</div>";
        a += "<a href='/Administrateur'>Menu</a>";
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

    @GetMapping("listenote")
    public String listenote(){
        String a = "";
        a += "<div style='color:green; background-color:yellow;'>";
        List<Note> note = findNote();
        try{
            for (int i = 0; i < note.size(); i++){
                a += "<br>Id : " + String.valueOf(findNote().get(i).getId_note()) + "<br>Sujet : " + String.valueOf(findNote().get(i).getId_sujet()) + "<br>Note : " + String.valueOf(findNote().get(i).getNum()) + " %<br>";
            }
        }
        catch (Exception e){
            a += "Vide<br>";
        }
        a += "</div>";
        a += "<a href='/Administrateur'>Menu</a>";
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
            String sql = "DELETE FROM joueur WHERE id_joueur = ?";
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

    @GetMapping("/deletesujet")
    public String deletesujet(){
        String a = "";
        a += "<form method='POST' action='/resultatsujet'>";
        a += "<div style='color:red; background-color:black;'>";
        a += "<label for='namefield'>Id : </label>";
        a += "<input id='namesfield' type='text' name='nom'>";
        a += "<button type='submit'>Valider</button>";
        a += "</div></form>";
        a += "<br><a href='/Administrateur'>Menu</a>";
        return a;
    }

    @PostMapping("/resultatsujet")
    public String resultatsujet(String nom){
        String a = "";
        a += "<div style='color:green; background-color:yellow;'>";
        try{
            List<Question> question = findQuestion();
            for (int i = 0; i < question.size(); i++){
                if (question.get(i).getSujet() == Integer.parseInt(nom)){
                    jdbcTemplate.update("DELETE FROM reponse WHERE id_question = ?", question.get(i).getId_question());
                }
            }
            String sql = "DELETE FROM sujet WHERE id_sujet = ?";
            jdbcTemplate.update(sql, Integer.parseInt(nom));
            sql = "DELETE FROM question WHERE id_sujet = ?";
            jdbcTemplate.update(sql, Integer.parseInt(nom));
            sql = "DELETE FROM note WHERE id_sujet = ?";
            jdbcTemplate.update(sql, Integer.parseInt(nom));
            a += "<h3>Vous avez supprimé un sujet</h3>";
        }
        catch (Exception e){
            a += "<h3>Une erreur s'est produite</h3>";
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
            for (int i = 0; i < note.size(); i++){
                a += "<br>Id : " + String.valueOf(findNote().get(i).getId_note()) + "<br>Sujet : " + String.valueOf(findNote().get(i).getId_sujet()) + "<br>Note : " + String.valueOf(findNote().get(i).getNum()) + " %<br>";
            }
        }
        catch (Exception e){
            a += "Vide<br>";
        }
        a += "</div>";
        a += "<a href='/testquiz'>Menu</a>";
        return a;
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

    @PostMapping("/resultat") // Afficher le résultat du quiz
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
        for (int i = 0; i < findNote().size(); i++){
            ba += "<br>Id : " + String.valueOf(findNote().get(i).getId_note()) + "<br>Sujet : " + String.valueOf(findNote().get(i).getId_sujet()) + "<br>Note : " + String.valueOf(findNote().get(i).getNum()) + " %<br>";
        }
        ba += "</div>";
        ba += "<br><a href='/testquiz'>Menu</a>";
        return ba;
    }
/*
    @GetMapping("/testfonction")
    public String testfonction(){
        String a = "";

        a += "<script language='javascript'>" +
                "const cars = [];";
        for (int i = 0; i < 3; i++){
            a += "cars[" + String.valueOf(i) + "] = 0;";
        }
        a += "</script>";

        a += "<script language='javascript'>";

        for (int i = 0; i < 3; i++){
            a += "function submit" + String.valueOf(i + 1) + "(){" +
                    "cars[" + String.valueOf(i) + "] = document.getElementById('demo" + String.valueOf(i + 1) + "').value;" +
                    "document.getElementById('demo').innerHTML = `<form action='/testfonctionbis' method='POST'><input type='text' name='nom' value ='` + cars.toString() + `'><button type='submit'>Greet Me</button></form>`;}";
                    //"document.getElementById('demo').innerHTML = cars.toString()";
        }
        a += "</script>";
        for (int i = 0; i < 3; i++){
            a += "<div>" +
                    "    <input type='radio' onclick='submit" + String.valueOf(i + 1) + "();' id='demo" + String.valueOf(i + 1) + "' value='" + String.valueOf(i + 1) + "'>" + String.valueOf(i + 1) + "</div>" +
                    "";
        }
        a += "<br><span id='demo'></span>";
        return a;
    }

    @PostMapping("/testfonctionbis")
    public String testfonctionbis(String... nom){
        String a = "";
        try {
            for (String nm : nom){
                a += nm + "<br>";
            }
        }
        catch (Exception e){
            a += String.valueOf(e);
        }

        return a;
    } */

    @Override
    public Student getPersonById(int id) {
        return jdbcTemplate.queryForObject("SELECT * FROM student WHERE id = ?", new Object[] { id }, new PersonMapper());
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

/*
    @GetMapping("/test1")
    public String test1(@RequestParam String ... i ){
        String a = "";
        for (String b : i){
            a += "<h3>" + b + "</h3>";
        }
        return a;
    }

    @GetMapping("/test2")
    public String test2(){
        String a = "<form action='/test1' method='GET' id='nameForm'>";
        a += "<div>";
        a += "<label for='nameField1'>Id : </label>";
        a += "<input name='i' id='nameField1'><br>";
        a += "<label for='nameField2'>Nom : </label>";
        a += "<input name='i' id='nameField2'><br>";
        a += "<label for='nameField3'>E-mail : </label>";
        a += "<input name='i' id='nameField3'><br>";
        a += "<button type='submit'>Greet me</button>";
        a += "</div>";
        a += "</form>";
        return a;
    } */

    @Override
    public List<Student> finds() {
        String sql = """
           SELECT *
           FROM student
           ;
           """;
        return jdbcTemplate.query(sql,new PersonMapper());
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

    @GetMapping("/list")
    public Iterable<Student> getCustomers() {
        return studentrepo.findAll();
    }

    @Override
    public void run(String... args) throws Exception {
        //Student student = new Student("Rosa", "rosa.parks@derecho.org");
        //String sql = "INSERT INTO student (name, email) VALUES ('" + student.getName() + "', '" + student.getEmail() + "')";
        //String sql = "INSERT INTO student (id, name, email) VALUES (3, 'Rosa', 'rosa.parks@derecho.org')";
        //int rows = jdbcTemplate.update(sql);
        //if (rows > 0) {
            // System.out.println("A new row has been inserted.");
        //}

        /* Student student = new Student(3, "Rosa", "rosa.parks@derecho.org");
        String sql = "DELETE FROM student WHERE id = 3; INSERT INTO student (id, name, email) VALUES (" + String.valueOf(student.getId()) + ", '" + student.getName() + "', '" + student.getEmail() + "')";
        int rows = jdbcTemplate.update(sql);
        if (rows > 0) {
            System.out.println("A new row has been inserted.");
        } */

    }

    @GetMapping("/compte")
    public String Creaccount(){
        String a = "<form action='/add' method='POST' id='nameForm'>";
        a += "<div>";
        a += "<label for='nameField1'>Id : </label>";
        a += "<input name='id' id='nameField1'><br>";
        a += "<label for='nameField2'>Nom : </label>";
        a += "<input name='name' id='nameField2'><br>";
        a += "<label for='nameField3'>E-mail : </label>";
        a += "<input name='email' id='nameField3'><br>";
        a += "<button type='submit'>Greet me</button>";
        a += "</div>";
        a += "</form>";
        return a;
    }

    @GetMapping("/find/{id}")
    public Student findCustomerById(@PathVariable Integer id) {
        return studentrepo.findCustomerById(id);
    }

}
