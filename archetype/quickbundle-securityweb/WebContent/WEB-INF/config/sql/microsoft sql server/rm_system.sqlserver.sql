/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2005                    */
/* Created on:     2011-06-27 00:09:00                          */
/*==============================================================*/

/*
if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('RM_NOTICE_RANGE') and o.name = 'FK_RM_NOTIC_REFERENCE_RM_NOTIC')
alter table RM_NOTICE_RANGE
   drop constraint FK_RM_NOTIC_REFERENCE_RM_NOTIC
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('RM_LOCK')
            and   name  = 'IDXU_LOCK_BK_CONTENT'
            and   indid > 0
            and   indid < 255)
   drop index RM_LOCK.IDXU_LOCK_BK_CONTENT
go

if exists (select 1
            from  sysobjects
           where  id = object_id('RM_LOCK')
            and   type = 'U')
   drop table RM_LOCK
go

if exists (select 1
            from  sysobjects
           where  id = object_id('RM_LOG')
            and   type = 'U')
   drop table RM_LOG
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('RM_LOG_TYPE')
            and   name  = 'IDXU_LOGTYPE_BK'
            and   indid > 0
            and   indid < 255)
   drop index RM_LOG_TYPE.IDXU_LOGTYPE_BK
go

if exists (select 1
            from  sysobjects
           where  id = object_id('RM_LOG_TYPE')
            and   type = 'U')
   drop table RM_LOG_TYPE
go

if exists (select 1
            from  sysobjects
           where  id = object_id('RM_NOTICE')
            and   type = 'U')
   drop table RM_NOTICE
go

if exists (select 1
            from  sysobjects
           where  id = object_id('RM_NOTICE_RANGE')
            and   type = 'U')
   drop table RM_NOTICE_RANGE
go

if exists (select 1
            from  sysobjects
           where  id = object_id('RM_SYSTEM_CALENDAR')
            and   type = 'U')
   drop table RM_SYSTEM_CALENDAR
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('RM_SYSTEM_PARAMETER')
            and   name  = 'IDXU_SYSPARA_KEY_ORG_PATH'
            and   indid > 0
            and   indid < 255)
   drop index RM_SYSTEM_PARAMETER.IDXU_SYSPARA_KEY_ORG_PATH
go

if exists (select 1
            from  sysobjects
           where  id = object_id('RM_SYSTEM_PARAMETER')
            and   type = 'U')
   drop table RM_SYSTEM_PARAMETER
go
*/

/*==============================================================*/
/* Table: RM_LOCK                                               */
/*==============================================================*/
create table RM_LOCK (
   ID                   bigint               not null,
   USER_ID              varchar(50)          null,
   BS_KEYWORD           varchar(200)         not null,
   LOCK_CONTENT         varchar(200)         not null,
   LOCK_DATE            datetime             not null,
   constraint PK_RM_LOCK primary key nonclustered (ID)
)

go
ALTER TABLE RM_LOCK ADD
USABLE_STATUS CHAR(1) DEFAULT '1',
MODIFY_DATE DATETIME,
MODIFY_IP VARCHAR(45),
MODIFY_USER_ID BIGINT
go

/*==============================================================*/
/* Index: IDXU_LOCK_BK_CONTENT                                  */
/*==============================================================*/
create unique index IDXU_LOCK_BK_CONTENT on RM_LOCK (
BS_KEYWORD ASC,
LOCK_CONTENT ASC
)
go

/*==============================================================*/
/* Table: RM_LOG                                                */
/*==============================================================*/
create table RM_LOG (
   ID                   bigint               not null,
   LOG_TYPE_ID          bigint               not null,
   ACTION_DATE          datetime             not null,
   ACTION_IP            varchar(45)          not null,
   ACTION_MODULE        varchar(200)         not null,
   ACTION_TYPE          varchar(50)          not null,
   OWNER_ORG_ID         varchar(50)          null,
   USER_ID              bigint               null,
   USER_ID_NAME         varchar(200)         null,
   ACTION_UUID          varchar(50)          null,
   CONTENT              varchar(4000)        null,
   constraint PK_RM_LOG primary key nonclustered (ID)
)

go
ALTER TABLE RM_LOG ADD
USABLE_STATUS CHAR(1) DEFAULT '1',
MODIFY_DATE DATETIME,
MODIFY_IP VARCHAR(45),
MODIFY_USER_ID BIGINT
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '$RM_OPERATION_TYPE=操作类型{
   0=新增类,
   1=删除类,
   2=修改类,
   3=查询类,
   9=通用
   }',
   'user', @CurrentUser, 'table', 'RM_LOG', 'column', 'ACTION_TYPE'
go

/*==============================================================*/
/* Table: RM_LOG_TYPE                                           */
/*==============================================================*/
create table RM_LOG_TYPE (
   ID                   bigint               not null,
   BS_KEYWORD           varchar(200)         not null,
   NAME                 varchar(200)         not null,
   IS_RECORD            char(1)              not null,
   IS_ALONE_TABLE       char(1)              not null,
   TABLE_NAME           varchar(50)          null,
   PATTERN_VALUE        varchar(1000)        not null,
   MATCH_PRIORITY       int                  not null,
   MAX_LOG_SUM          bigint               null,
   DESCRIPTION          varchar(1000)        null,
   CUSTOM_XML           varchar(4000)        null,
   constraint PK_RM_LOG_TYPE primary key nonclustered (ID)
)

go
ALTER TABLE RM_LOG_TYPE ADD
USABLE_STATUS CHAR(1) DEFAULT '1',
MODIFY_DATE DATETIME,
MODIFY_IP VARCHAR(45),
MODIFY_USER_ID BIGINT
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '$RM_YES_NOT=是、否{
   0=否,
   1=是
   }',
   'user', @CurrentUser, 'table', 'RM_LOG_TYPE', 'column', 'IS_RECORD'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '$RM_YES_NOT=是、否{
   0=否,
   1=是
   }',
   'user', @CurrentUser, 'table', 'RM_LOG_TYPE', 'column', 'IS_ALONE_TABLE'
go

/*==============================================================*/
/* Index: IDXU_LOGTYPE_BK                                       */
/*==============================================================*/
create unique index IDXU_LOGTYPE_BK on RM_LOG_TYPE (
BS_KEYWORD ASC
)
go

/*==============================================================*/
/* Table: RM_NOTICE                                             */
/*==============================================================*/
create table RM_NOTICE (
   ID                   bigint               not null,
   OWNER_ORG_ID         varchar(50)          null,
   SENDER_ID            bigint               null,
   IS_PUBLIC            char(1)              not null,
   NOTICE_CLASS         varchar(50)          not null,
   TITLE                varchar(1000)        not null,
   DISPLAY_TYPE         varchar(2)           null,
   BEGIN_TIME           datetime             null,
   END_TIME             datetime             null,
   IS_AFFIX             char(1)              null,
   CONTENT              text                 null,
   constraint PK_RM_NOTICE primary key nonclustered (ID)
)

go
ALTER TABLE RM_NOTICE ADD
USABLE_STATUS CHAR(1) DEFAULT '1',
MODIFY_DATE DATETIME,
MODIFY_IP VARCHAR(45),
MODIFY_USER_ID BIGINT
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '$RM_YES_NOT=是、否{
   0=否,
   1=是
   }',
   'user', @CurrentUser, 'table', 'RM_NOTICE', 'column', 'IS_PUBLIC'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '$RM_NOTICE_CLASS_CLASS=公告分类{
   1=系统维护,
   2=业务
   }',
   'user', @CurrentUser, 'table', 'RM_NOTICE', 'column', 'NOTICE_CLASS'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '$RM_DISPLAY_TYPE=公告展现类型{
   0=默认,
   1=滚动,
   2=走马灯
   }',
   'user', @CurrentUser, 'table', 'RM_NOTICE', 'column', 'DISPLAY_TYPE'
go

/*==============================================================*/
/* Table: RM_NOTICE_RANGE                                       */
/*==============================================================*/
create table RM_NOTICE_RANGE (
   NOTICE_ID            bigint               not null,
   RECEIVER_PARTY_ID    bigint               not null,
   constraint PK_RM_NOTICE_RANGE primary key nonclustered (NOTICE_ID, RECEIVER_PARTY_ID)
)

go
ALTER TABLE RM_NOTICE_RANGE ADD
USABLE_STATUS CHAR(1) DEFAULT '1',
MODIFY_DATE DATETIME,
MODIFY_IP VARCHAR(45),
MODIFY_USER_ID BIGINT
go

/*==============================================================*/
/* Table: RM_SYSTEM_CALENDAR                                    */
/*==============================================================*/
create table RM_SYSTEM_CALENDAR (
   ID                   bigint               not null,
   THE_DATE             datetime             not null,
   IS_WORK              char(1)              not null,
   OWNER_ORG_ID         varchar(50)          null,
   EXTENDS_SCRIPT       varchar(4000)        null,
   constraint PK_RM_SYSTEM_CALENDAR primary key nonclustered (ID)
)

go
ALTER TABLE RM_SYSTEM_CALENDAR ADD
USABLE_STATUS CHAR(1) DEFAULT '1',
MODIFY_DATE DATETIME,
MODIFY_IP VARCHAR(45),
MODIFY_USER_ID BIGINT
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '$RM_YES_NOT=是、否{
   0=否,
   1=是
   }',
   'user', @CurrentUser, 'table', 'RM_SYSTEM_CALENDAR', 'column', 'IS_WORK'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '关联角色时，指定所属的组织ID',
   'user', @CurrentUser, 'table', 'RM_SYSTEM_CALENDAR', 'column', 'OWNER_ORG_ID'
go

/*==============================================================*/
/* Table: RM_SYSTEM_PARAMETER                                   */
/*==============================================================*/
create table RM_SYSTEM_PARAMETER (
   ID                   bigint               not null,
   NAME                 varchar(200)         not null,
   PATH                 varchar(1000)        not null,
   PARAMETER_KEY        varchar(200)         not null,
   PARAMETER_VALUE      varchar(4000)        null,
   OWNER_ORG_ID         varchar(50)          null,
   IS_RECURSIVE         char(1)              null,
   DESCRIPTION          varchar(1000)        null,
   IS_TEMPLATE          char(1)              not null,
   VALUE_RANGE          varchar(1000)        null,
   constraint PK_RM_SYSTEM_PARAMETER primary key nonclustered (ID)
)

go
ALTER TABLE RM_SYSTEM_PARAMETER ADD
USABLE_STATUS CHAR(1) DEFAULT '1',
MODIFY_DATE DATETIME,
MODIFY_IP VARCHAR(45),
MODIFY_USER_ID BIGINT
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '关联角色时，指定所属的组织ID',
   'user', @CurrentUser, 'table', 'RM_SYSTEM_PARAMETER', 'column', 'OWNER_ORG_ID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '$RM_YES_NOT=是、否{
   0=否,
   1=是
   }',
   'user', @CurrentUser, 'table', 'RM_SYSTEM_PARAMETER', 'column', 'IS_RECURSIVE'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '$RM_YES_NOT=是、否{
   0=否,
   1=是
   }',
   'user', @CurrentUser, 'table', 'RM_SYSTEM_PARAMETER', 'column', 'IS_TEMPLATE'
go

/*==============================================================*/
/* Index: IDXU_SYSPARA_KEY_ORG_PATH                             */
/*==============================================================*/
create unique index IDXU_SYSPARA_KEY_ORG_PATH on RM_SYSTEM_PARAMETER (
PARAMETER_KEY ASC,
OWNER_ORG_ID ASC,
PATH ASC
)
go

alter table RM_NOTICE_RANGE
   add constraint FK_RM_NOTIC_REFERENCE_RM_NOTIC foreign key (NOTICE_ID)
      references RM_NOTICE (ID)
go

