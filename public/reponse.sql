create table reponse
(
    id_reponse  integer      not null
        primary key,
    nom_reponse varchar(255) not null,
    id_question integer      not null,
    resultat    integer      not null
);

alter table reponse
    owner to root;

