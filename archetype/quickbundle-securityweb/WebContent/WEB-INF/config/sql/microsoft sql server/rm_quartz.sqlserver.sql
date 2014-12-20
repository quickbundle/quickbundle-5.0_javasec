/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2005                    */
/* Created on:     2012/4/4 18:19:36                            */
/*==============================================================*/


if exists (select 1
            from  sysobjects
           where  id = object_id('RM_SCHEDULER_EVENT')
            and   type = 'U')
   drop table RM_SCHEDULER_EVENT
go

/*==============================================================*/
/* Table: RM_SCHEDULER_EVENT                                    */
/*==============================================================*/
create table RM_SCHEDULER_EVENT (
   ID                   bigint               not null,
   JOB_NAME             varchar(200)         not null,
   JOB_GROUP            varchar(200)         not null,
   TRIGGER_NAME         varchar(200)         not null,
   TRIGGER_GROUP        varchar(200)         not null,
   FIRE_INSTANCE_ID     varchar(200)         null,
   EVENT_TYPE           varchar(50)          not null,
   COST_MILLIS          bigint               not null,
   RESULT_STATUS        varchar(2)           null,
   CREATE_TIME          datetime             not null,
   CREATE_IP            varchar(50)          null,
   UUID                 varchar(50)          null,
   IS_ARCHIVE           char(1)              null,
   RESULT               varchar(4000)        null,
   constraint PK_RM_SCHEDULER_EVENT primary key nonclustered (ID)
)

go