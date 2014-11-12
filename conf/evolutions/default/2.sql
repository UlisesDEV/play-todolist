# Tasks schema
 
# --- !Ups
CREATE SEQUENCE users_id_seq INCREMENT BY 1 NOCACHE NOCYCLE;
create table users (
  id integer auto_increment,
  email varchar(255) not null,
  login varchar(255),
  password varchar(255),
  constraint pk_users primary key (id));

ALTER TABLE task ADD users_id integer;

alter table task add constraint fk_task_users foreign key (users_id) references users (id) on delete cascade on update cascade;

insert into users (id,email,login,password) values (1,'user1@user1.com','user1','pass1');
insert into users (id,email,login,password) values (2,'user2@user2.com','user2','pass2');
insert into users (id,email,login,password) values (3,'user3@user3.com','user3','pass3');

insert into task (id,label,end,users_id) values (1,'User 1 - Tarea 1 - Id 1',NOW(),1);
insert into task (id,label,end,users_id) values (2,'User 1 - Tarea 2 - Id 2',NOW(),1);
insert into task (id,label,end,users_id) values (3,'User 1 - Tarea 3 - Id 3',NOW(),1);
insert into task (id,label,end,users_id) values (4,'User 2 - Tarea 1 - Id 4',NOW(),2);
insert into task (id,label,end,users_id) values (5,'User 2 - Tarea 2 - Id 5',NOW(),2);
insert into task (id,label,end,users_id) values (6,'User 2 - Tarea 3 - Id 6',NOW(),2);
insert into task (id,label,end,users_id) values (7,'User 3 - Tarea 1 - Id 7',NOW(),3);
insert into task (id,label,end,users_id) values (8,'User 3 - Tarea 2 - Id 8',NOW(),3);
insert into task (id,label,users_id) values (9,'User 3 - Tarea 3 - Id 9',3);
insert into task (id,label,end,users_id) values (10,'User 3 - Tarea 2 - Id 8','2014-11-11',3);
# --- !Downs
 
SET REFERENTIAL_INTEGRITY FALSE;

drop table users;

DROP SEQUENCE users_id_seq;

SET REFERENTIAL_INTEGRITY TRUE;