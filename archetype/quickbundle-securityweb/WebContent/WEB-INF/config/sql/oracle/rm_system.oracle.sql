/*==============================================================*/
/* DBMS name:      ORACLE Version 11g_qb                        */
/* Created on:     2011-05-12 00:21:25                          */
/*==============================================================*/

/*
alter table RM_NOTICE_RANGE
   drop constraint FK_RM_NOTIC_REFERENCE_RM_NOTIC;

drop index IDXU_LOCK_BK_CONTENT;

drop table RM_LOCK cascade constraints;

drop table RM_LOG cascade constraints;

drop index IDXU_LOGTYPE_BK;

drop table RM_LOG_TYPE cascade constraints;

drop table RM_NOTICE cascade constraints;

drop table RM_NOTICE_RANGE cascade constraints;

drop table RM_SYSTEM_CALENDAR cascade constraints;

drop index IDXU_SYSPARA_KEY_ORG_PATH;

drop table RM_SYSTEM_PARAMETER cascade constraints;
*/
/*==============================================================*/
/* Table: RM_LOCK                                               */
/*==============================================================*/
create table RM_LOCK 
(
   ID                   NUMBER(19)           not null,
   USER_ID              VARCHAR2(50),
   BS_KEYWORD           VARCHAR2(200)        not null,
   LOCK_CONTENT         VARCHAR2(200)        not null,
   LOCK_DATE            DATE                 not null,
   constraint PK_RM_LOCK primary key (ID)
)

/
ALTER TABLE RM_LOCK ADD (
USABLE_STATUS CHAR(1) DEFAULT '1',
MODIFY_DATE DATE DEFAULT SYSDATE,
MODIFY_IP VARCHAR2(45),
MODIFY_USER_ID NUMBER(19)
);

/*==============================================================*/
/* Index: IDXU_LOCK_BK_CONTENT                                  */
/*==============================================================*/
create unique index IDXU_LOCK_BK_CONTENT on RM_LOCK (
   BS_KEYWORD ASC,
   LOCK_CONTENT ASC
);

/*==============================================================*/
/* Table: RM_LOG                                                */
/*==============================================================*/
create table RM_LOG 
(
   ID                   NUMBER(19)           not null,
   LOG_TYPE_ID          NUMBER(19)           not null,
   ACTION_DATE          DATE                 not null,
   ACTION_IP            VARCHAR2(45)         not null,
   ACTION_MODULE        VARCHAR2(200)        not null,
   ACTION_TYPE          VARCHAR2(50)         not null,
   OWNER_ORG_ID         VARCHAR2(50),
   USER_ID              NUMBER(19),
   USER_ID_NAME         VARCHAR2(200),
   ACTION_UUID          VARCHAR2(50),
   CONTENT              VARCHAR2(4000),
   constraint PK_RM_LOG primary key (ID)
)

/
ALTER TABLE RM_LOG ADD (
USABLE_STATUS CHAR(1) DEFAULT '1',
MODIFY_DATE DATE DEFAULT SYSDATE,
MODIFY_IP VARCHAR2(45),
MODIFY_USER_ID NUMBER(19)
);

comment on column RM_LOG.ACTION_TYPE is
'$RM_OPERATION_TYPE=操作类型{
0=新增类,
1=删除类,
2=修改类,
3=查询类,
9=通用
}';

/*==============================================================*/
/* Table: RM_LOG_TYPE                                           */
/*==============================================================*/
create table RM_LOG_TYPE 
(
   ID                   NUMBER(19)           not null,
   BS_KEYWORD           VARCHAR2(200)        not null,
   NAME                 VARCHAR2(200)        not null,
   IS_RECORD            CHAR(1)              not null,
   IS_ALONE_TABLE       CHAR(1)              not null,
   TABLE_NAME           VARCHAR2(50),
   PATTERN_VALUE        VARCHAR2(1000)       not null,
   MATCH_PRIORITY       NUMBER(10)           not null,
   MAX_LOG_SUM          NUMBER(38),
   DESCRIPTION          VARCHAR2(1000),
   CUSTOM_XML           VARCHAR2(4000),
   constraint PK_RM_LOG_TYPE primary key (ID)
)

/
ALTER TABLE RM_LOG_TYPE ADD (
USABLE_STATUS CHAR(1) DEFAULT '1',
MODIFY_DATE DATE DEFAULT SYSDATE,
MODIFY_IP VARCHAR2(45),
MODIFY_USER_ID NUMBER(19)
);

comment on column RM_LOG_TYPE.IS_RECORD is
'$RM_YES_NOT=是、否{
0=否,
1=是
}';

comment on column RM_LOG_TYPE.IS_ALONE_TABLE is
'$RM_YES_NOT=是、否{
0=否,
1=是
}';

/*==============================================================*/
/* Index: IDXU_LOGTYPE_BK                                       */
/*==============================================================*/
create unique index IDXU_LOGTYPE_BK on RM_LOG_TYPE (
   BS_KEYWORD ASC
);

/*==============================================================*/
/* Table: RM_NOTICE                                             */
/*==============================================================*/
create table RM_NOTICE 
(
   ID                   NUMBER(19)           not null,
   OWNER_ORG_ID         VARCHAR2(50),
   SENDER_ID            NUMBER(19),
   IS_PUBLIC            CHAR(1)              not null,
   NOTICE_CLASS         VARCHAR2(50)         not null,
   TITLE                VARCHAR2(1000)       not null,
   DISPLAY_TYPE         VARCHAR2(2),
   BEGIN_TIME           DATE,
   END_TIME             DATE,
   IS_AFFIX             CHAR(1),
   CONTENT              CLOB,
   constraint PK_RM_NOTICE primary key (ID)
)

/
ALTER TABLE RM_NOTICE ADD (
USABLE_STATUS CHAR(1) DEFAULT '1',
MODIFY_DATE DATE DEFAULT SYSDATE,
MODIFY_IP VARCHAR2(45),
MODIFY_USER_ID NUMBER(19)
);

comment on column RM_NOTICE.IS_PUBLIC is
'$RM_YES_NOT=是、否{
0=否,
1=是
}';

comment on column RM_NOTICE.NOTICE_CLASS is
'$RM_NOTICE_CLASS_CLASS=公告分类{
1=系统维护,
2=业务
}';

comment on column RM_NOTICE.DISPLAY_TYPE is
'$RM_DISPLAY_TYPE=公告展现类型{
0=默认,
1=滚动,
2=走马灯
}';

/*==============================================================*/
/* Table: RM_NOTICE_RANGE                                       */
/*==============================================================*/
create table RM_NOTICE_RANGE 
(
   NOTICE_ID            NUMBER(19)           not null,
   RECEIVER_PARTY_ID    NUMBER(19)           not null,
   constraint PK_RM_NOTICE_RANGE primary key (NOTICE_ID, RECEIVER_PARTY_ID)
)

/
ALTER TABLE RM_NOTICE_RANGE ADD (
USABLE_STATUS CHAR(1) DEFAULT '1',
MODIFY_DATE DATE DEFAULT SYSDATE,
MODIFY_IP VARCHAR2(45),
MODIFY_USER_ID NUMBER(19)
);

/*==============================================================*/
/* Table: RM_SYSTEM_CALENDAR                                    */
/*==============================================================*/
create table RM_SYSTEM_CALENDAR 
(
   ID                   NUMBER(19)           not null,
   THE_DATE             DATE                 not null,
   IS_WORK              CHAR(1)              not null,
   OWNER_ORG_ID         VARCHAR2(50),
   EXTENDS_SCRIPT       VARCHAR2(4000),
   constraint PK_RM_SYSTEM_CALENDAR primary key (ID)
)

/
ALTER TABLE RM_SYSTEM_CALENDAR ADD (
USABLE_STATUS CHAR(1) DEFAULT '1',
MODIFY_DATE DATE DEFAULT SYSDATE,
MODIFY_IP VARCHAR2(45),
MODIFY_USER_ID NUMBER(19)
);

comment on column RM_SYSTEM_CALENDAR.IS_WORK is
'$RM_YES_NOT=是、否{
0=否,
1=是
}';

comment on column RM_SYSTEM_CALENDAR.OWNER_ORG_ID is
'关联角色时，指定所属的组织ID';

/*==============================================================*/
/* Table: RM_SYSTEM_PARAMETER                                   */
/*==============================================================*/
create table RM_SYSTEM_PARAMETER 
(
   ID                   NUMBER(19)           not null,
   NAME                 VARCHAR2(200)        not null,
   PATH                 VARCHAR2(1000)       not null,
   PARAMETER_KEY        VARCHAR2(200)        not null,
   PARAMETER_VALUE      VARCHAR2(4000),
   OWNER_ORG_ID         VARCHAR2(50),
   IS_RECURSIVE         CHAR(1),
   DESCRIPTION          VARCHAR2(1000),
   IS_TEMPLATE          CHAR(1)              not null,
   VALUE_RANGE          VARCHAR2(1000),
   constraint PK_RM_SYSTEM_PARAMETER primary key (ID)
)

/
ALTER TABLE RM_SYSTEM_PARAMETER ADD (
USABLE_STATUS CHAR(1) DEFAULT '1',
MODIFY_DATE DATE DEFAULT SYSDATE,
MODIFY_IP VARCHAR2(45),
MODIFY_USER_ID NUMBER(19)
);

comment on column RM_SYSTEM_PARAMETER.OWNER_ORG_ID is
'关联角色时，指定所属的组织ID';

comment on column RM_SYSTEM_PARAMETER.IS_RECURSIVE is
'$RM_YES_NOT=是、否{
0=否,
1=是
}';

comment on column RM_SYSTEM_PARAMETER.IS_TEMPLATE is
'$RM_YES_NOT=是、否{
0=否,
1=是
}';

/*==============================================================*/
/* Index: IDXU_SYSPARA_KEY_ORG_PATH                             */
/*==============================================================*/
create unique index IDXU_SYSPARA_KEY_ORG_PATH on RM_SYSTEM_PARAMETER (
   PARAMETER_KEY ASC,
   OWNER_ORG_ID ASC,
   PATH ASC
);

alter table RM_NOTICE_RANGE
   add constraint FK_RM_NOTIC_REFERENCE_RM_NOTIC foreign key (NOTICE_ID)
      references RM_NOTICE (ID);

