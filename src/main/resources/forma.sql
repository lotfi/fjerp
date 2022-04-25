/*
  date:    06/02/20
  version: 0.2
 */

create database forma character set latin1;

use forma;

create table codif
(
 code       varchar(20) not null,
 categorie  varchar(20) default '',
 label      varchar(50) default '',
 etat	    varchar(10) default '',
 ref_parent varchar(20),
 date_enreg datetime default current_timestamp,
 
 primary key(code)
) engine=innodb;

alter table codif add constraint ctn1_codif foreign key(ref_parent) references codif(code);

create table tiers
(
 code     varchar(20) not null,
 nom_rs   varchar(40) default '',
 prenom   varchar(40) default '',
 ref_nomen  varchar(20) not null,
 tel1     varchar(20) default '',
 tel2     varchar(20) default '',
 email    varchar(30) default '',
 adresse  varchar(50) default '',
 web      varchar(50) default '',
 age      tinyint default 0,
 sexe     tinyint default 0,
 ref_parent  varchar(20),
 comment     varchar(50) default '',
 date_enreg datetime default current_timestamp,
 
 primary key(code)
) engine=innodb;

alter table tiers add constraint ctn1_tiers foreign key(ref_parent) references tiers(code);

alter table tiers add constraint ctn2_tiers foreign key(ref_nomen) references codif(code);

create table theme
(
  code varchar(20) not null,
  ref_parent varchar(20), 
  label      varchar(50) default '',
  niveau   tinyint default 0,
  date_enreg datetime default current_timestamp,
  
  primary key(code)
) engine=innodb;

alter table theme add constraint ctn1_theme foreign key(ref_parent) references theme(code) on delete cascade;

alter table theme add constraint ctn2_theme foreign key(code) references codif(code);


create table session
(
  code   varchar(20) not null,
  
  ref_formateur  varchar(20) not null,
  ref_entite varchar(20) not null,
  ref_theme      varchar(20) not null,
  vol_horaire    tinyint default 12,
  date_debut     date not null,
  date_fin       date not null,
  duree_seance   tinyint default 6,
  heure_debut    time,
  heure_fin      time,
  nbr_inscrit    tinyint default 0,
  tot_charge double default 0,
  tot_caisse double default 0,
  etat  varchar(10) default '',
  comment  varchar(50) default '',
  date_enreg datetime default current_timestamp,
  
  primary key(code)
) engine=innodb;

alter table session add constraint ctn1_session foreign key(ref_formateur) references tiers(code);

alter table session add constraint ctn2_session foreign key(ref_entite) references tiers(code);

alter table session add constraint ctn3_session foreign key(ref_theme) references theme(code);

create table inscrit
(
 	id int not null auto_increment,
 	ref_session  varchar(20) not null,
 	ref_client   varchar(20) not null,
 	montant_percu  double default 0,
    etat  varchar(10) default '',
    comment  varchar(50) default '',
    date_enreg datetime default current_timestamp,
    
    primary key(id)
)
engine=innodb;

alter table inscrit add constraint ctn1_inscrit foreign key(ref_session) references session(code);

alter table inscrit add constraint ctn2_inscrit foreign key(ref_client) references tiers(code);

create table util
(
   uid  varchar(20) not null,
   pwd  varchar(40) not null,
   code_profil varchar(20) default '',
   etat  varchar(10) default '', 
   primary key(uid)
)
engine=innodb;







