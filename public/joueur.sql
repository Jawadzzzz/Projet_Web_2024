create table joueur
(
    id_joueur integer not null
        primary key,
    nom       varchar(255)
);

alter table joueur
    owner to root;

