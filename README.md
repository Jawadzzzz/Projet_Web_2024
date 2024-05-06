# Introduction

REGGANI Jawad

NGUYEN Pha LUAN

ING4 APP SIC Groupe 1


Projet Web 2024

Jeu de Quiz




# Sommaire

Langage utilisé

Base de données

Emplacement des fichiers

Code

Essais réalisés

Liste des fonctions

Accéder au serveur via le navigateur Web

Démarrage du serveur


# Langage utilisé

Dans notre projet Web, nous utilisons l'application Spring qui utilise la langage Java pour fonctionner.

Pour la base de données, nous avons utilisé PostgreSQL fonctionnant avec Docker.

Dans le code Java, nous injections du code HTML/CSS dans les fonctions avec un peu de JavaScript dans la fonction addreponse().

L'object jdbcTemplate permettait d'exécuter des commandes SQL à partir du code JAVA.

Remarque : les serveurs front et back ne sont pas séparés.


# Base de données

Pour créer la base de données PostgreSQL, nous nous sommes d'abord placés dans le dossier initdb avec la commande 'cd' sur le terminal IntelliJ.

Ensuite, nous avons exécuté le fichier docker-compose.yml dans le dossier initdb avec la commande 'docker-compose up' sur le terminal IntelliJ.

Dès que le conteneur PostgreSQL est lancé, nous pouvons l'arrêter ou le redémarrer quand nous voulons via Docker Desktop.

A la fin, il ne faut pas oublier de configurer les paramètres de connexion à PostgreSQL sur IntelliJ (numéro de port 5432, utilisateur root, mot de passe toor).

Le dossier initdb contient également toutes les tables de la base de données avec leurs colonnes ainsi que les données que nous utilisions pour les essais.


# Emplacement des fichiers

Les fichiers java se trouvent dans le répertoire Test2\src\main\java\com\example\Test2.

Le fichier statique "index.html" se trouve dans le dossier Test2/src/main/resources/static.

Le fichier application.properties se trouve dans le dossier Test2/src/main/resources.


# Code

Les fichiers dont le nom se terminent par "Mapper" sont utilisés pour stocker dans un objet de plusieurs colonnes les données du résultat de la commande SQL "SELECT" exécutée via le code Java.

Par exemple, pour l'objet "Question", le fichier QuestionMapper permet de stocker dans l'objet Question les données de la table "question" de la base de données.

Le fichier PersonDAO est une interface initialisant des methodes abstraites définies dans le fichier StudentController. Ces methodes sont liées aux commandes SQL "SELECT".

Chaque objet Java est lié à une table de la base de données. Les nom et type d'attribut de chaque objet sont respectivement liés aux nom et type de colonne d'une table.

Chaque objet Java a un fichier spécifique permettant de le définir. Par exemple, le fichier Reponse.java permet de définir l'objet Reponse avec ses attributs (id_reponse, nom_reponse, id_question, resultat)

Un cookie utilisé par la variable entière statique "cookie" est nécessaire pour permettre au joueur de rester connecté.

Le joueur ne peut pas jouer sans authentification.

Lorsque l'authentification est validée, le cookie prend la valeur de l'Id du joueur.

Lorsque l'administrateur supprime un joueur ou que le serveur redémarre, tous les cookies sont remis à zero et une reconnexion est nécessaire.

Toutes les fonctions utilisées sont définies dans le fichier StudentController.java. Les contrôleurs GetMapping ou bien PostMapping contenant le nom du lien cible sont implémentés sur chaque fonction. La plupart des fonctions sont nécessaires pour la réalisation du Quiz mais d'autres (addCustomer(), testfonction(), testfonctionbis(), test1(), test2(), finds(), getCustomers() & Creaccount()) ne sont utilisées que pour des essais sans lien direct avec le Quiz.


# Essais réalisés

Les fonctions utilisées pour les essais ne sont généralement présentes que sous forme de commentaire (/* ... */).

Les fichiers SpringJdbcTemplate2PostgreSQLApplication.java, StudentRepository et Test2Apllication.java ne sont que des tests et ne font pas partie du projet.

Deux utilisateurs ne peuvent pas jouer en même temps comme les ressources ne sont pas séparées.


# Liste des fonctions

Toutefois, il est assez délicat de lire le code du fichier StudentController.java comme il contient plus de 1000 lignes !

Afin de le rendre plus lisible, nous avons copié-collé les fonctions vers différents fichiers (JoueurController.java, SujetController.java, QuestionController.java, ReponseController.java, NoteController.java) selon leur utilité.

Par exemple, les fonctions exécutant des actions relatives au joueur ont été copiées-collées vers le fichier JoueurController.

Cependant seules les fonctions resultat() et listenotebis() présentes dans StudentController.java ne sont pas dupliquées.

La fonction resultat() permet de calculer la note du joueur à chaque fin de quiz et affiche le résultat ainsi que le classement en fonction du sujet. Elle est codée entre les lignes 838 et 971 du fichier StudentController.java.

La fonction listenotebis() affiche le classement des joueurs selon l'énoncé de quiz et est codée de la ligne 684 à 723.


# Accéder au serveur via le navigateur Web

Pour le contrôleur GetMapping("/Administrateur"), il faut saisir l'URL localhost:8090/Administrateur pour accéder au résultat de la fonction associée au GetMapping("Administrateur").

Pour créer un sujet, il faut saisir l'URL "localhost:8090/testsujet".

Pour créer une question, il faut saisir l'URL "localhost:8090/testquestion".

Pour créer une réponse, il faut saisir l'URL "localhost:8090/testreponse".

Pour créer un joueur, il faut saisir l'URL "localhost:8090/testjoueur".

Pour faire une partie de Quiz, il faut saisir l'URL "localhost:8090/testquiz".
Attention, en tant que joueur, il ne faut pas saisir l'URL directement puisqu'il faut s'authentifier sur la page localhost:8090 ou localhost:8090/index.html pour paramétrer le cookie permettant de réaliser le quiz.

La page par défaut "localhost:8090" ou "localhost:8090/index.html" est la page de connexion pour le joueur.

La page "localhost:8090/Administrateur" est celle de l'administrateur.


# Démarrage du serveur

Il faut s'assurer que l'instance PostgreSQL est lancée sur Docker avant de démarrer le serveur.

Pour démarrer le serveur, il ne faut qu'exécuter le fichier StudentController.java

