/*==============================================================*/
/* DBMS name:      MySQL 5.0_qb                                 */
/* Created on:     2011-05-11 23:12:07                          */
/*==============================================================*/


/* drop index IDXU_LOCK_BK_CONTENT on RM_LOCK */;
/*
drop table if exists RM_LOCK;

drop table if exists RM_LOG;
*/
/* drop index IDXU_LOGTYPE_BK on RM_LOG_TYPE */;
/*
drop table if exists RM_LOG_TYPE;

drop table if exists RM_NOTICE;

drop table if exists RM_NOTICE_RANGE;

drop table if exists RM_SYSTEM_CALENDAR;
*/
/* drop index IDXU_SYSPARA_KEY_ORG_PATH on RM_SYSTEM_PARAMETER */;
/*
drop table if exists RM_SYSTEM_PARAMETER;
*/
/*==============================================================*/
/* Table: RM_LOCK                                               */
/*==============================================================*/
create table RM_LOCK
(
   ID                   BIGINT not null,
   USER_ID              varchar(50),
   BS_KEYWORD           varchar(200) not null,
   LOCK_CONTENT         varchar(200) not null,
   LOCK_DATE            datetime not null,
   primary key (ID)
)
;

ALTER TABLE RM_LOCK ADD (
USABLE_STATUS CHAR(1) DEFAULT '1',
MODIFY_DATE DATETIME,
MODIFY_IP VARCHAR(45),
MODIFY_USER_ID BIGINT
);

/*==============================================================*/
/* Index: IDXU_LOCK_BK_CONTENT                                  */
/*==============================================================*/
create unique index IDXU_LOCK_BK_CONTENT on RM_LOCK
(
   BS_KEYWORD,
   LOCK_CONTENT
);

/*==============================================================*/
/* Table: RM_LOG                                                */
/*==============================================================*/
create table RM_LOG
(
   ID                   BIGINT not null,
   LOG_TYPE_ID          BIGINT not null,
   ACTION_DATE          datetime not null,
   ACTION_IP            varchar(45) not null,
   ACTION_MODULE        varchar(200) not null,
   ACTION_TYPE          varchar(50) not null comment '$RM_OPERATION_TYPE=操作类型{
            0=新增类,
            1=删除类,
            2=修改类,
            3=查询类,
            9=通用
            }',
   OWNER_ORG_ID         varchar(50),
   USER_ID              BIGINT,
   USER_ID_NAME         varchar(200),
   ACTION_UUID          varchar(50),
   CONTENT              varchar(4000),
   primary key (ID)
)
;

ALTER TABLE RM_LOG ADD (
USABLE_STATUS CHAR(1) DEFAULT '1',
MODIFY_DATE DATETIME,
MODIFY_IP VARCHAR(45),
MODIFY_USER_ID BIGINT
);

/*==============================================================*/
/* Table: RM_LOG_TYPE                                           */
/*==============================================================*/
create table RM_LOG_TYPE
(
   ID                   BIGINT not null,
   BS_KEYWORD           varchar(200) not null,
   NAME                 varchar(200) not null,
   IS_RECORD            char(1) not null comment '$RM_YES_NOT=是、否{
            0=否,
            1=是
            }',
   IS_ALONE_TABLE       char(1) not null comment '$RM_YES_NOT=是、否{
            0=否,
            1=是
            }',
   TABLE_NAME           varchar(50),
   PATTERN_VALUE        varchar(1000) not null,
   MATCH_PRIORITY       int not null,
   MAX_LOG_SUM          bigint,
   DESCRIPTION          varchar(1000),
   CUSTOM_XML           varchar(4000),
   primary key (ID)
)
;

ALTER TABLE RM_LOG_TYPE ADD (
USABLE_STATUS CHAR(1) DEFAULT '1',
MODIFY_DATE DATETIME,
MODIFY_IP VARCHAR(45),
MODIFY_USER_ID BIGINT
);

/*==============================================================*/
/* Index: IDXU_LOGTYPE_BK                                       */
/*==============================================================*/
create unique index IDXU_LOGTYPE_BK on RM_LOG_TYPE
(
   BS_KEYWORD
);

/*==============================================================*/
/* Table: RM_NOTICE                                             */
/*==============================================================*/
create table RM_NOTICE
(
   ID                   BIGINT not null,
   OWNER_ORG_ID         varchar(50),
   SENDER_ID            BIGINT,
   IS_PUBLIC            char(1) not null comment '$RM_YES_NOT=是、否{
            0=否,
            1=是
            }',
   NOTICE_CLASS         varchar(50) not null comment '$RM_NOTICE_CLASS_CLASS=公告分类{
            1=系统维护,
            2=业务
            }',
   TITLE                varchar(1000) not null,
   DISPLAY_TYPE         varchar(2) comment '$RM_DISPLAY_TYPE=公告展现类型{
            0=默认,
            1=滚动,
            2=走马灯
            }',
   BEGIN_TIME           datetime,
   END_TIME             datetime,
   IS_AFFIX             char(1),
   CONTENT              text,
   primary key (ID)
)
;

ALTER TABLE RM_NOTICE ADD (
USABLE_STATUS CHAR(1) DEFAULT '1',
MODIFY_DATE DATETIME,
MODIFY_IP VARCHAR(45),
MODIFY_USER_ID BIGINT
);

/*==============================================================*/
/* Table: RM_NOTICE_RANGE                                       */
/*==============================================================*/
create table RM_NOTICE_RANGE
(
   NOTICE_ID            BIGINT not null,
   RECEIVER_PARTY_ID    BIGINT not null,
   primary key (NOTICE_ID, RECEIVER_PARTY_ID)
)
;

ALTER TABLE RM_NOTICE_RANGE ADD (
USABLE_STATUS CHAR(1) DEFAULT '1',
MODIFY_DATE DATETIME,
MODIFY_IP VARCHAR(45),
MODIFY_USER_ID BIGINT
);

/*==============================================================*/
/* Table: RM_SYSTEM_CALENDAR                                    */
/*==============================================================*/
create table RM_SYSTEM_CALENDAR
(
   ID                   BIGINT not null,
   THE_DATE             datetime not null,
   IS_WORK              char(1) not null comment '$RM_YES_NOT=是、否{
            0=否,
            1=是
            }',
   OWNER_ORG_ID         varchar(50) comment '关联角色时，指定所属的组织ID',
   EXTENDS_SCRIPT       varchar(4000),
   primary key (ID)
)
;

ALTER TABLE RM_SYSTEM_CALENDAR ADD (
USABLE_STATUS CHAR(1) DEFAULT '1',
MODIFY_DATE DATETIME,
MODIFY_IP VARCHAR(45),
MODIFY_USER_ID BIGINT
);

/*==============================================================*/
/* Table: RM_SYSTEM_PARAMETER                                   */
/*==============================================================*/
create table RM_SYSTEM_PARAMETER
(
   ID                   BIGINT not null,
   NAME                 varchar(200) not null,
   PATH                 varchar(1000) not null,
   PARAMETER_KEY        varchar(200) not null,
   PARAMETER_VALUE      varchar(4000),
   OWNER_ORG_ID         varchar(50) comment '关联角色时，指定所属的组织ID',
   IS_RECURSIVE         char(1) comment '$RM_YES_NOT=是、否{
            0=否,
            1=是
            }',
   DESCRIPTION          varchar(1000),
   IS_TEMPLATE          char(1) not null comment '$RM_YES_NOT=是、否{
            0=否,
            1=是
            }',
   VALUE_RANGE          varchar(1000),
   primary key (ID)
)
;

ALTER TABLE RM_SYSTEM_PARAMETER ADD (
USABLE_STATUS CHAR(1) DEFAULT '1',
MODIFY_DATE DATETIME,
MODIFY_IP VARCHAR(45),
MODIFY_USER_ID BIGINT
);

/*==============================================================*/
/* Index: IDXU_SYSPARA_KEY_ORG_PATH                             */
/*==============================================================*/
create unique index IDXU_SYSPARA_KEY_ORG_PATH on RM_SYSTEM_PARAMETER
(
   PARAMETER_KEY,
   OWNER_ORG_ID,
   PATH
);

alter table RM_NOTICE_RANGE add constraint FK_Reference_951 foreign key (NOTICE_ID)
      references RM_NOTICE (ID) on delete restrict on update restrict;

