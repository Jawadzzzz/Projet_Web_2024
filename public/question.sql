create table question
(
    id_question  integer      not null
        primary key,
    nom_question varchar(255) not null,
    id_sujet     integer      not null
);

alter table question
    owner to root;

