/*
 * date: 30/09/20
 * domaine: softqual
 * version: 0.1
 * */


/*
 * date:  04/08/20
 * version: 0.3
 * schema:  fjerp
 */

create database fjerp;

use fjerp;

create table maitre
(
  id         int not null auto_increment,
  code       varchar(20) not null,
  label      varchar(50) default '',
  id_parent  int,
  categ      varchar(10) default ' ',
  
 primary key(id)
) engine=innodb;

alter table maitre add constraint ctn1_maitre foreign key(id_parent) references maitre(id);

alter table maitre add constraint ctn2_maitre unique(categ,code);

create table compte
(
  id         int not null,
  
  flag_mouv  varchar(1) default ' ',
  type_compte varchar(1) default ' ',
  
 primary key(id)
) engine=innodb;

alter table compte add constraint ctn1_compte foreign key(id) references maitre(id);


/* tiers:  interne/entreprise, client, fournisseur, collab, ... */ 

create table operation
(
  id           int not null auto_increment,

  
  code_unite   varchar(20) not null,
  
  annee        smallint not null,
  mois         tinyint not null,
  jour         tinyint not null,
  piece        varchar(10) not null,
  numero       smallint not null,
  
  code_produit varchar(20) default '',
  code_tiers   varchar(20) default '',
  
  quantite     smallint default 1,
  
  compte1      varchar(20) not null,
  compte2      varchar(20) default '',
  
  label        varchar(50) default '',
  type_mouv    varchar(20) default '',
  date_mouv    datetime default current_timestamp,
  date_compt   date,
  codif        varchar(20) default '',
  
  debit        float(13,2) default 0,
  credit       float(13,2) default 0,
  valeur       float(13,2) default 0,
  
  etat         varchar(10) default '',
  
  sens         varchar(1) default '',
 primary key(id)
) engine=innodb;


create table solde
(
  id           int not null auto_increment,
  
  label        varchar(50) default '',
  
  date_solde   datetime default current_timestamp,
  
  id_entete    int not null,
  
  code_etat    varchar(10) default '',
  
  type_solde   varchar(10) default '',
  
  anc_deb      float(13,2) default 0,
  anc_cre      float(13,2) default 0,
  anc_val       float(13,2) default 0,
  
  ope_deb      float(13,2) default 0,
  ope_cre      float(13,2) default 0,
  ope_val      float(13,2) default 0,
  
  nou_deb      float(13,2) default 0,
  nou_cre      float(13,2) default 0,
  nou_val      float(13,2) default 0,
    
 primary key(id)
) engine=innodb;

alter table solde add constraint ctn1_solde foreign key(id_entete) references entete(id);


create table solde_detail
(
  id           int not null,
  
  id_oper      int not null,
  
  primary key(id)
) engine=innodb;

alter table solde_detail add constraint ctn1_solde_detail foreign key(id) references solde(id); 

alter table solde_detail add constraint ctn2_solde_detail foreign key(id_oper) references operation(id);


/* rapprochement */

/*
 * date:    01/12/19
 * version: 0.4
 */

create database vlinder;

use vlinder;

/* */
create table act
(
  id          int not null auto_increment,
  uid         varchar(20) not null,
  code        varchar(10) default '',
  start_time  datetime default current_timestamp,
  end_time    datetime,
  delay       smallint default 0,
  time_unit   tinyint default 0,
  class_id    tinyint default 0,
  next_id     int,
  prev_id     int,
  
  primary key(id)
) engine=innodb;

alter table act add constraint ctn1_act foreign key(next_id) references act(id);

alter table act add constraint ctn2_act foreign key(prev_id) references act(id);


create table refdata
(
	 id             int not null,
	 class_id       int default 0,
	 code           varchar(40) default '',
	 state_code     varchar(10) default '',
	 parent_id      int,
	 act_id         int,
	 state_time    datetime default current_timestamp,
 	 primary key(id)
) engine=innodb;

alter table refdata add constraint ctn1_refdata foreign key(parent_id) references refdata(id);

alter table refdata add constraint ctn2_refdata foreign key(act_id) references act(id);


create table partner
(
 id          int not null auto_increment,
 class_id    tinyint default 0,
 code        varchar(20) default '',
 name1       varchar(50) default ' ',
 name2       varchar(50) default ' ',
 address     varchar(50) default '',
 email       varchar(50) default '',
 web_url     varchar(70) default '',
 phone1      varchar(50) default '',
 phone2      varchar(50) default '',
 facebook    varchar(50) default '',
 twitter     varchar(50) default '',,
 linkedin    varchar(50) default '',
 parent_id   int
 refdata_id  int,
 state_id    int,
 state_code  varchar(10) default '',
 primary key(id)
) engine=innodb;

alter table partner add ctn1_partner foreign key(parent_id) references partner(id);

alter table partner add ctn2_partner foreign key(refdata_id) references refdata(id);

alter table partner add ctn3_partner foreign key(state_id) references state(id);

create table orgaunit
(
 id          int not null auto_increment,
 class_id    tinyint default 0,
 refdata_id  int not null,
 partner_id  int not null,
 state_id    int,
 primary key(id)
) engine=innodb;

alter table orgaunit add ctn1_orgaunit foreign key(partner_id) references partner(id);

alter table orgaunit add ctn2_orgaunit foreign key(refdata_id) references refdata(id);

alter table orgaunit add ctn3_orgaunit foreign key(state_id) references state(id);
 
create table user
( 
  id          varchar(20) not null, 
  pwd         varchar(20) not null,
  profile     varchar(10) default '',
  class_id    tinyint default 0,
  orgaunit_id int not null,
  state_code  varchar(10) default '',
  state_id    int,
  primary key(uid)
) engine=innodb;

alter table user add ctn1_user foreign key(id_person) references person(id);

alter table user add ctn2_user foreign key(state_id) references state(id);

create table team
(
  id          varchar(20) not null,
  class_id    tinyint default 0,  
  state_code  varchar(10) default '',
  state_id    int,
  primary key(code)
) engine=innodb;


alter table user add ctn1_team foreign key(state_id) references state(id);

create table team_member
(
  user_id   varchar(20) not null,
  team_id varchar(20) not null,
  primary key(user_id, team_code)
) engine=innodb;

alter table user add ctn1_team_member foreign key(user_id) references user(id);

alter table user add ctn1_team_member foreign key(team_id) references team(id);

/* */


create table job
(
   code       varchar(20) not null,
   owner_id   varchar(20) not null,
   comment    varchar(50) default '',
   create_ts  datetime default current_timestamp,
   start_ts   datetime,
   scheduled_end_ts datetime,
   actual_end      datetime,
   state_code  varchar(10) default '',
   state_id    int,
   orgaunit_id  not null,
   class_id    tinyint default 0,
   primary key(code)
) engine=innodb;

alter table job add ctn1_job foreign key(owner_id) references user(id);

alter table job add ctn2_job foreign key(state_id) references state(id);

alter table job add ctn3_job foreign key(orgaunit_id) references orgaunit(id);

create table stakeholder
(
   id          int not null auto_increment,
   job_code varchar(20) not null,
   class_id    tinyint default 0,
   user_id     varchar(20) not null,
   state_code  varchar(10) default '',
   state_id    int,
   primary key(code)
) engine=innodb;

alter table stakeholder add ctn1_stakeholder foreign key(job_code) references job(code);

alter table stakeholder add ctn2_stakeholder foreign key(user_id) references user(id);

alter table stakeholder add ctn3_stakeholder foreign key(state_id) references state(id);

create table issue
(
   id          int not null auto_increment,
   job_code    varchar(20) not null,
   create_ts        datetime default current_timestamp,
   start_ts         datetime,
   scheduled_end_ts datetime,
   actual_end       datetime,
   
   assignee_id    int,
   assignator_id  int not null,
   class_id       tinyint default 0,
   state_code  varchar(10) default '',
   state_id    int,
 
   primary key(id)
) engine=innodb;


alter table issue add ctn1_issue foreign key(job_code) references job(code);

alter table issue add ctn2_issue foreign key(assignee_id) references user(id);

alter table issue add ctn3_issue foreign key(assignator_id) references user(id);

alter table issue add ctn4_issue foreign key(assignator_id) references user(id);

alter table issue add ctn5_issue foreign key(state_id) references state(id);


create table tracklog
(
   id            int not null auto_increment,
   issue_id      int not null,
   action_id     int not null,
   class_id      tinyint default 0,
   time_spent    tinyint default 0,
   comment       varchar(20) default '',
   
   primary key(id)
) engine=innodb;

alter table tracklog add ctn1_tracklog foreign key(issue_id) references issue(id);

alter table tracklog add ctn2_tracklog foreign key(action_id) references actionlog(id);



/* comptabilite/accounting */

account_chart

compte_comptable   (account_record/document)

book/journal

piece_comptable

item/data

recap




