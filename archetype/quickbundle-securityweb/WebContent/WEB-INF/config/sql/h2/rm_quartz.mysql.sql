/*==============================================================*/
/* DBMS name:      MySQL 5.0_qb                                 */
/* Created on:     2012/4/4 17:40:50                            */
/*==============================================================*/

/*
drop table if exists RM_SCHEDULER_EVENT;
*/
/*==============================================================*/
/* Table: RM_SCHEDULER_EVENT                                    */
/*==============================================================*/
create table RM_SCHEDULER_EVENT
(
   ID                   BIGINT not null,
   JOB_NAME             varchar(200) not null,
   JOB_GROUP            varchar(200) not null,
   TRIGGER_NAME         varchar(200) not null,
   TRIGGER_GROUP        varchar(200) not null,
   FIRE_INSTANCE_ID     varchar(200),
   EVENT_TYPE           varchar(50) not null,
   COST_MILLIS          bigint not null,
   RESULT_STATUS        varchar(2),
   CREATE_TIME          datetime not null,
   CREATE_IP            varchar(50),
   UUID                 varchar(50),
   IS_ARCHIVE           char(1),
   RESULT               varchar(4000),
   primary key (ID)
);