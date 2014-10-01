# Tasks schema
 
# --- !Ups

create table users (
  id                        bigint not null,
  email                      varchar(255) not null,
  login                varchar(255),
  password              varchar(255),
  constraint pk_users primary key (id));

ALTER TABLE task ADD users_id bigint;

alter table task add constraint fk_task_task-user foreign key (users_id) references users (id) on delete restrict on update restrict;

insert into users (id,email,login,password) values (1,'user1@user1.com','user1','pass1');
insert into users (id,email,login,password) values (2,'user2@user2.com','user2','pass2');
insert into users (id,email,login,password) values (3,'user3@user3.com','user3','pass3');

insert into task (id,label,users_id) values (1,'User 1 - Tarea 1 - Id 1',1);
insert into task (id,label,users_id) values (2,'User 1 - Tarea 2 - Id 2',1);
insert into task (id,label,users_id) values (3,'User 1 - Tarea 3 - Id 3',1);
insert into task (id,label,users_id) values (4,'User 2 - Tarea 1 - Id 4',2);
insert into task (id,label,users_id) values (5,'User 2 - Tarea 2 - Id 5',2);
insert into task (id,label,users_id) values (6,'User 2 - Tarea 3 - Id 6',2);
insert into task (id,label,users_id) values (7,'User 3 - Tarea 1 - Id 7',3);
insert into task (id,label,users_id) values (8,'User 3 - Tarea 2 - Id 8',3);
insert into task (id,label,users_id) values (9,'User 3 - Tarea 3 - Id 9',3);
# --- !Downs
 
SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists users;