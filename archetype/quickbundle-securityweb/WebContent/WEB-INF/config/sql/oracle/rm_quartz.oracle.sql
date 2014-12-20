/*==============================================================*/
/* DBMS name:      ORACLE Version 11g_qb                        */
/* Created on:     2012-04-02 00:19:47                          */
/*==============================================================*/

/*
drop table RM_SCHEDULER_EVENT cascade constraints;
*/
/*==============================================================*/
/* Table: RM_SCHEDULER_EVENT                                    */
/*==============================================================*/
create table RM_SCHEDULER_EVENT 
(
   ID                   NUMBER(19)           not null,
   JOB_NAME             VARCHAR2(200)        not null,
   JOB_GROUP            VARCHAR2(200)        not null,
   TRIGGER_NAME         VARCHAR2(200)        not null,
   TRIGGER_GROUP        VARCHAR2(200)        not null,
   FIRE_INSTANCE_ID     VARCHAR2(200),
   EVENT_TYPE           VARCHAR2(50)         not null,
   COST_MILLIS          NUMBER(38)           not null,
   RESULT_STATUS        VARCHAR2(2),
   CREATE_TIME          DATE                 not null,
   CREATE_IP            VARCHAR2(50),
   UUID                 VARCHAR2(50),
   IS_ARCHIVE           CHAR(1),
   RESULT               VARCHAR2(4000),
   constraint PK_RM_SCHEDULER_EVENT primary key (ID)
);