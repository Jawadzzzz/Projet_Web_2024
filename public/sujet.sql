create table sujet
(
    id_sujet integer      not null
        primary key,
    titre    varchar(255) not null
);

alter table sujet
    owner to root;

