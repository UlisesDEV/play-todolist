# Tasks schema
 
# --- !Ups

CREATE SEQUENCE task_id_seq INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE TABLE task (
    id integer auto_increment,
    label varchar(255),
    end datetime NULL,
    constraint pk_tasks primary key (id)
);
 
# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;
DROP TABLE task;
DROP SEQUENCE task_id_seq;

SET REFERENTIAL_INTEGRITY TRUE;