create table note
(
    id_note   integer          not null
        primary key,
    num       double precision not null,
    id_joueur integer,
    id_sujet  integer          not null
);

alter table note
    owner to root;

