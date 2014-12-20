/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2005                    */
/* Created on:     2011-06-27 00:06:45                          */
/*==============================================================*/

/*
if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('RM_AUTHORIZE_ACCESS_RULE') and o.name = 'FK_RM_AUTHO_REFERM_AU_RM_AUTHO')
alter table RM_AUTHORIZE_ACCESS_RULE
   drop constraint FK_RM_AUTHO_REFERM_AU_RM_AUTHO
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('RM_AUTHORIZE_AFFIX') and o.name = 'FK_RM_ACCES_REFERENCE_RM_AUTH2')
alter table RM_AUTHORIZE_AFFIX
   drop constraint FK_RM_ACCES_REFERENCE_RM_AUTH2
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('RM_AUTHORIZE_AFFIX') and o.name = 'FK_AU_ACCES_REFERENCE_AU_RESO2')
alter table RM_AUTHORIZE_AFFIX
   drop constraint FK_AU_ACCES_REFERENCE_AU_RESO2
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('RM_AUTHORIZE_AFFIX') and o.name = 'FK_AU_ACCES_REFERENCE_AU_ACCE2')
alter table RM_AUTHORIZE_AFFIX
   drop constraint FK_AU_ACCES_REFERENCE_AU_ACCE2
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('RM_AUTHORIZE_AFFIX_RULE') and o.name = 'FK_RM_ACCES_REFERENCE_RM_AUTH3')
alter table RM_AUTHORIZE_AFFIX_RULE
   drop constraint FK_RM_ACCES_REFERENCE_RM_AUTH3
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('RM_AUTHORIZE_CONFIG') and o.name = 'FK_RM_AUTHO_REFERENCE_RM_AUTH2')
alter table RM_AUTHORIZE_CONFIG
   drop constraint FK_RM_AUTHO_REFERENCE_RM_AUTH2
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('RM_AUTHORIZE_CONFIG_AFFIX') and o.name = 'FK_RM_AUTHO_REFERENCE_RM_AUTH3')
alter table RM_AUTHORIZE_CONFIG_AFFIX
   drop constraint FK_RM_AUTHO_REFERENCE_RM_AUTH3
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('RM_AUTHORIZE_CONFIG_AFFIX') and o.name = 'FK_RM_AUTHO_REFERENCE_RM_AUTH4')
alter table RM_AUTHORIZE_CONFIG_AFFIX
   drop constraint FK_RM_AUTHO_REFERENCE_RM_AUTH4
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('RM_AUTHORIZE_RESOURCE') and o.name = 'FK_RM_AUTHO_REFERENCE_RM_AUTH6')
alter table RM_AUTHORIZE_RESOURCE
   drop constraint FK_RM_AUTHO_REFERENCE_RM_AUTH6
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('RM_AUTHORIZE_RESOURCE_RECORD') and o.name = 'FK_RM_AUTHO_REFERENCE_RM_AUTH5')
alter table RM_AUTHORIZE_RESOURCE_RECORD
   drop constraint FK_RM_AUTHO_REFERENCE_RM_AUTH5
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('RM_AUTHORIZE_RESOURCE_RECORD') and o.name = 'FK_RM_AUTHO_REFERM_PA_RM_PARTY')
alter table RM_AUTHORIZE_RESOURCE_RECORD
   drop constraint FK_RM_AUTHO_REFERM_PA_RM_PARTY
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('RM_PARTY') and o.name = 'FK_RM_PARTY_REFERENCE_RM_PART2')
alter table RM_PARTY
   drop constraint FK_RM_PARTY_REFERENCE_RM_PART2
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('RM_PARTY_RELATION') and o.name = 'FK_RM_PARTY_REFERENCE_RM_PART5')
alter table RM_PARTY_RELATION
   drop constraint FK_RM_PARTY_REFERENCE_RM_PART5
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('RM_PARTY_RELATION') and o.name = 'FK_RM_PARTY_REFERENCE_RM_PART3')
alter table RM_PARTY_RELATION
   drop constraint FK_RM_PARTY_REFERENCE_RM_PART3
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('RM_PARTY_RELATION') and o.name = 'FK_RM_PARTY_REFERENCE_RM_PART4')
alter table RM_PARTY_RELATION
   drop constraint FK_RM_PARTY_REFERENCE_RM_PART4
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('RM_PARTY_RELATION') and o.name = 'FK_RM_PARTY_REFERENCE_RM_PART9')
alter table RM_PARTY_RELATION
   drop constraint FK_RM_PARTY_REFERENCE_RM_PART9
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('RM_PARTY_RELATION') and o.name = 'FK_RM_PARTY_REFERENCE_RM_PARTY')
alter table RM_PARTY_RELATION
   drop constraint FK_RM_PARTY_REFERENCE_RM_PARTY
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('RM_PARTY_ROLE') and o.name = 'FK_RM_PARTY_REFERM_PA_RM_PARTY')
alter table RM_PARTY_ROLE
   drop constraint FK_RM_PARTY_REFERM_PA_RM_PARTY
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('RM_PARTY_ROLE') and o.name = 'FK_RM_PARTY_REFERM_RO_RM_ROLE')
alter table RM_PARTY_ROLE
   drop constraint FK_RM_PARTY_REFERM_RO_RM_ROLE
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('RM_PARTY_TYPE_RELATION_RULE') and o.name = 'FK_RM_PARTY_REFERENCE_RM_PART7')
alter table RM_PARTY_TYPE_RELATION_RULE
   drop constraint FK_RM_PARTY_REFERENCE_RM_PART7
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('RM_PARTY_TYPE_RELATION_RULE') and o.name = 'FK_RM_PARTY_REFERENCE_RM_PART8')
alter table RM_PARTY_TYPE_RELATION_RULE
   drop constraint FK_RM_PARTY_REFERENCE_RM_PART8
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('RM_PARTY_TYPE_RELATION_RULE') and o.name = 'FK_RM_PARTY_REFERENCE_RM_PART6')
alter table RM_PARTY_TYPE_RELATION_RULE
   drop constraint FK_RM_PARTY_REFERENCE_RM_PART6
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('RM_PASSWORD_HISTORY') and o.name = 'FK_RM_PASSW_REFERENCE_RM_USER')
alter table RM_PASSWORD_HISTORY
   drop constraint FK_RM_PASSW_REFERENCE_RM_USER
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('RM_PASSWORD_LOGIN_FAIL') and o.name = 'FK_RM_PASSW_REFERENCE_RM_USER2')
alter table RM_PASSWORD_LOGIN_FAIL
   drop constraint FK_RM_PASSW_REFERENCE_RM_USER2
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('RM_PASSWORD_STRATEGY_USER') and o.name = 'FK_密码策略关联_REFE2')
alter table RM_PASSWORD_STRATEGY_USER
   drop constraint FK_密码策略关联_REFE2
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('RM_USER_AGENT') and o.name = 'FK_RM_USER__REFERENCE_RM_USER2')
alter table RM_USER_AGENT
   drop constraint FK_RM_USER__REFERENCE_RM_USER2
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('RM_USER_AGENT') and o.name = 'FK_RM_USER__REFERENCE_RM_USER')
alter table RM_USER_AGENT
   drop constraint FK_RM_USER__REFERENCE_RM_USER
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('RM_USER_ONLINE_RECORD') and o.name = 'FK_RM_USER__REFERM_US_RM_USER')
alter table RM_USER_ONLINE_RECORD
   drop constraint FK_RM_USER__REFERM_US_RM_USER
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('RM_AUTHORIZE')
            and   name  = 'IDXU_AUTHORIZE_BK'
            and   indid > 0
            and   indid < 255)
   drop index RM_AUTHORIZE.IDXU_AUTHORIZE_BK
go

if exists (select 1
            from  sysobjects
           where  id = object_id('RM_AUTHORIZE')
            and   type = 'U')
   drop table RM_AUTHORIZE
go

if exists (select 1
            from  sysobjects
           where  id = object_id('RM_AUTHORIZE_ACCESS_RULE')
            and   type = 'U')
   drop table RM_AUTHORIZE_ACCESS_RULE
go

if exists (select 1
            from  sysobjects
           where  id = object_id('RM_AUTHORIZE_AFFIX')
            and   type = 'U')
   drop table RM_AUTHORIZE_AFFIX
go

if exists (select 1
            from  sysobjects
           where  id = object_id('RM_AUTHORIZE_AFFIX_RULE')
            and   type = 'U')
   drop table RM_AUTHORIZE_AFFIX_RULE
go

if exists (select 1
            from  sysobjects
           where  id = object_id('RM_AUTHORIZE_CONFIG')
            and   type = 'U')
   drop table RM_AUTHORIZE_CONFIG
go

if exists (select 1
            from  sysobjects
           where  id = object_id('RM_AUTHORIZE_CONFIG_AFFIX')
            and   type = 'U')
   drop table RM_AUTHORIZE_CONFIG_AFFIX
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('RM_AUTHORIZE_RESOURCE')
            and   name  = 'IDXU_AUTHRES_AUTHID_OLDRES'
            and   indid > 0
            and   indid < 255)
   drop index RM_AUTHORIZE_RESOURCE.IDXU_AUTHRES_AUTHID_OLDRES
go

if exists (select 1
            from  sysobjects
           where  id = object_id('RM_AUTHORIZE_RESOURCE')
            and   type = 'U')
   drop table RM_AUTHORIZE_RESOURCE
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('RM_AUTHORIZE_RESOURCE_RECORD')
            and   name  = 'IDX_AUTHORIZE_RESOURCE_PARTYID'
            and   indid > 0
            and   indid < 255)
   drop index RM_AUTHORIZE_RESOURCE_RECORD.IDX_AUTHORIZE_RESOURCE_PARTYID
go

if exists (select 1
            from  sysobjects
           where  id = object_id('RM_AUTHORIZE_RESOURCE_RECORD')
            and   type = 'U')
   drop table RM_AUTHORIZE_RESOURCE_RECORD
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('RM_FUNCTION_NODE')
            and   name  = 'IDXU_FUNCTIONNODE_TOTALCODE'
            and   indid > 0
            and   indid < 255)
   drop index RM_FUNCTION_NODE.IDXU_FUNCTIONNODE_TOTALCODE
go

if exists (select 1
            from  sysobjects
           where  id = object_id('RM_FUNCTION_NODE')
            and   type = 'U')
   drop table RM_FUNCTION_NODE
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('RM_PARTY')
            and   name  = 'IDXU_PARTY_TYPEID_OLDPARTY'
            and   indid > 0
            and   indid < 255)
   drop index RM_PARTY.IDXU_PARTY_TYPEID_OLDPARTY
go

if exists (select 1
            from  sysobjects
           where  id = object_id('RM_PARTY')
            and   type = 'U')
   drop table RM_PARTY
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('RM_PARTY_RELATION')
            and   name  = 'IDXU_PARTYREL_CHILDCODE_VIEW'
            and   indid > 0
            and   indid < 255)
   drop index RM_PARTY_RELATION.IDXU_PARTYREL_CHILDCODE_VIEW
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('RM_PARTY_RELATION')
            and   name  = 'IDX_PARTYREL_PARENTCODE_VIEW'
            and   indid > 0
            and   indid < 255)
   drop index RM_PARTY_RELATION.IDX_PARTYREL_PARENTCODE_VIEW
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('RM_PARTY_RELATION')
            and   name  = 'IDX_PARTYREL_CHILDID_VIEW'
            and   indid > 0
            and   indid < 255)
   drop index RM_PARTY_RELATION.IDX_PARTYREL_CHILDID_VIEW
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('RM_PARTY_RELATION')
            and   name  = 'IDX_PARTYREL_PARENTID_VIEW'
            and   indid > 0
            and   indid < 255)
   drop index RM_PARTY_RELATION.IDX_PARTYREL_PARENTID_VIEW
go

if exists (select 1
            from  sysobjects
           where  id = object_id('RM_PARTY_RELATION')
            and   type = 'U')
   drop table RM_PARTY_RELATION
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('RM_PARTY_ROLE')
            and   name  = 'IDXU_PARTY_ROLE_ORG'
            and   indid > 0
            and   indid < 255)
   drop index RM_PARTY_ROLE.IDXU_PARTY_ROLE_ORG
go

if exists (select 1
            from  sysobjects
           where  id = object_id('RM_PARTY_ROLE')
            and   type = 'U')
   drop table RM_PARTY_ROLE
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('RM_PARTY_TYPE')
            and   name  = 'IDXU_PARTYTYPE_BK'
            and   indid > 0
            and   indid < 255)
   drop index RM_PARTY_TYPE.IDXU_PARTYTYPE_BK
go

if exists (select 1
            from  sysobjects
           where  id = object_id('RM_PARTY_TYPE')
            and   type = 'U')
   drop table RM_PARTY_TYPE
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('RM_PARTY_TYPE_RELATION_RULE')
            and   name  = 'IDXU_VIEW_PARTY_CHILD'
            and   indid > 0
            and   indid < 255)
   drop index RM_PARTY_TYPE_RELATION_RULE.IDXU_VIEW_PARTY_CHILD
go

if exists (select 1
            from  sysobjects
           where  id = object_id('RM_PARTY_TYPE_RELATION_RULE')
            and   type = 'U')
   drop table RM_PARTY_TYPE_RELATION_RULE
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('RM_PARTY_VIEW')
            and   name  = 'IDXU_PARTYVIEW_BK'
            and   indid > 0
            and   indid < 255)
   drop index RM_PARTY_VIEW.IDXU_PARTYVIEW_BK
go

if exists (select 1
            from  sysobjects
           where  id = object_id('RM_PARTY_VIEW')
            and   type = 'U')
   drop table RM_PARTY_VIEW
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('RM_ROLE')
            and   name  = 'IDXU_ROLE_ROLECODE'
            and   indid > 0
            and   indid < 255)
   drop index RM_ROLE.IDXU_ROLE_ROLECODE
go

if exists (select 1
            from  sysobjects
           where  id = object_id('RM_ROLE')
            and   type = 'U')
   drop table RM_ROLE
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('RM_USER')
            and   name  = 'IDXU_USER_LOGINID'
            and   indid > 0
            and   indid < 255)
   drop index RM_USER.IDXU_USER_LOGINID
go

if exists (select 1
            from  sysobjects
           where  id = object_id('RM_USER')
            and   type = 'U')
   drop table RM_USER
go

if exists (select 1
            from  sysobjects
           where  id = object_id('RM_USER_AGENT')
            and   type = 'U')
   drop table RM_USER_AGENT
go

if exists (select 1
            from  sysobjects
           where  id = object_id('RM_USER_ONLINE_RECORD')
            and   type = 'U')
   drop table RM_USER_ONLINE_RECORD
go
*/

/*==============================================================*/
/* Table: RM_AUTHORIZE                                          */
/*==============================================================*/
create table RM_AUTHORIZE (
   ID                   bigint               not null,
   NAME                 varchar(200)         not null,
   BS_KEYWORD           varchar(200)         not null,
   IS_ALONE_TABLE       char(1)              not null,
   AUTHORIZE_RESOURCE_TABLE_NAME varchar(50)          null,
   AUTHORIZE_RESREC_TABLE_NAME varchar(50)          null,
   AUTHORIZE_AFFIX_TABLE_NAME varchar(50)          null,
   SETTIING_OPTION      char(1)              null,
   CUSTOM_CODE          varchar(4000)        null,
   DESCRIPTION          varchar(1000)        null,
   constraint PK_RM_AUTHORIZE primary key nonclustered (ID)
)

go
ALTER TABLE RM_AUTHORIZE ADD
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
   'user', @CurrentUser, 'table', 'RM_AUTHORIZE', 'column', 'IS_ALONE_TABLE'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '$RM_OPTION_TYPE=权限配置{
   1=单选,
   2=多选,
   3=定制
   }',
   'user', @CurrentUser, 'table', 'RM_AUTHORIZE', 'column', 'SETTIING_OPTION'
go

/*==============================================================*/
/* Index: IDXU_AUTHORIZE_BK                                     */
/*==============================================================*/
create unique index IDXU_AUTHORIZE_BK on RM_AUTHORIZE (
BS_KEYWORD ASC
)
go

/*==============================================================*/
/* Table: RM_AUTHORIZE_ACCESS_RULE                              */
/*==============================================================*/
create table RM_AUTHORIZE_ACCESS_RULE (
   ID                   bigint               not null,
   AUTHORIZE_ID         bigint               not null,
   POSITION_NUMBER      int                  not null,
   NAME                 varchar(200)         not null,
   CODE                 varchar(50)          null,
   DEFAULT_CHECKED      char(1)              null,
   DESCRIPTION          varchar(1000)        null,
   constraint PK_RM_AUTHORIZE_ACCESS_RULE primary key nonclustered (ID)
)

go
ALTER TABLE RM_AUTHORIZE_ACCESS_RULE ADD
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
   'user', @CurrentUser, 'table', 'RM_AUTHORIZE_ACCESS_RULE', 'column', 'DEFAULT_CHECKED'
go

/*==============================================================*/
/* Table: RM_AUTHORIZE_AFFIX                                    */
/*==============================================================*/
create table RM_AUTHORIZE_AFFIX (
   ID                   bigint               not null,
   AUTHORIZE_AFFIX_RULE_ID bigint               not null,
   AUTHORIZE_RESOURCE_RECORD_ID bigint               null,
   AUTHORIZE_RESOURCE_ID bigint               null,
   DATA_VALUE           varchar(4000)        null,
   constraint PK_RM_AUTHORIZE_AFFIX primary key nonclustered (ID)
)

go
ALTER TABLE RM_AUTHORIZE_AFFIX ADD
USABLE_STATUS CHAR(1) DEFAULT '1',
MODIFY_DATE DATETIME,
MODIFY_IP VARCHAR(45),
MODIFY_USER_ID BIGINT
go

/*==============================================================*/
/* Table: RM_AUTHORIZE_AFFIX_RULE                               */
/*==============================================================*/
create table RM_AUTHORIZE_AFFIX_RULE (
   ID                   bigint               not null,
   AUTHORIZE_ID         bigint               not null,
   NAME                 varchar(200)         not null,
   CODE                 varchar(50)          null,
   AFFIX_DATA_TRANSLATE varchar(4000)        null,
   DESCRIPTION          varchar(1000)        null,
   constraint PK_RM_AUTHORIZE_AFFIX_RULE primary key nonclustered (ID)
)

go
ALTER TABLE RM_AUTHORIZE_AFFIX_RULE ADD
USABLE_STATUS CHAR(1) DEFAULT '1',
MODIFY_DATE DATETIME,
MODIFY_IP VARCHAR(45),
MODIFY_USER_ID BIGINT
go

/*==============================================================*/
/* Table: RM_AUTHORIZE_CONFIG                                   */
/*==============================================================*/
create table RM_AUTHORIZE_CONFIG (
   ID                   bigint               not null,
   AUTHORIZE_ID         bigint               not null,
   RECORD_ID            varchar(50)          not null,
   DATA_VALUE           varchar(4000)        null,
   DESCRIPTION          varchar(1000)        null,
   constraint PK_RM_AUTHORIZE_CONFIG_INSTAN2 primary key nonclustered (ID)
)

go
ALTER TABLE RM_AUTHORIZE_CONFIG ADD
USABLE_STATUS CHAR(1) DEFAULT '1',
MODIFY_DATE DATETIME,
MODIFY_IP VARCHAR(45),
MODIFY_USER_ID BIGINT
go

/*==============================================================*/
/* Table: RM_AUTHORIZE_CONFIG_AFFIX                             */
/*==============================================================*/
create table RM_AUTHORIZE_CONFIG_AFFIX (
   ID                   bigint               not null,
   AUTHORIZE_AFFIX_RULE_ID bigint               not null,
   AUTHORIZE_CONFIG_ID  bigint               not null,
   DATA_VALUE           varchar(4000)        null,
   constraint PK_RM_AUTHORIZE_CONFIG_AFFIX primary key nonclustered (ID)
)

go
ALTER TABLE RM_AUTHORIZE_CONFIG_AFFIX ADD
USABLE_STATUS CHAR(1) DEFAULT '1',
MODIFY_DATE DATETIME,
MODIFY_IP VARCHAR(45),
MODIFY_USER_ID BIGINT
go

/*==============================================================*/
/* Table: RM_AUTHORIZE_RESOURCE                                 */
/*==============================================================*/
create table RM_AUTHORIZE_RESOURCE (
   ID                   bigint               not null,
   AUTHORIZE_ID         bigint               not null,
   OLD_RESOURCE_ID      varchar(200)         not null,
   DEFAULT_ACCESS       char(1)              not null,
   DEFAULT_IS_AFFIX_DATA char(1)              not null,
   DEFAULT_IS_RECURSIVE char(1)              not null,
   DEFAULT_ACCESS_TYPE  varchar(1000)        not null,
   TOTAL_CODE           varchar(200)         null,
   NAME                 varchar(200)         null,
   constraint PK_RM_AUTHORIZE_RESOURCE primary key nonclustered (ID)
)

go
ALTER TABLE RM_AUTHORIZE_RESOURCE ADD
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
   'user', @CurrentUser, 'table', 'RM_AUTHORIZE_RESOURCE', 'column', 'DEFAULT_ACCESS'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '$RM_YES_NOT=是、否{
   0=否,
   1=是
   }',
   'user', @CurrentUser, 'table', 'RM_AUTHORIZE_RESOURCE', 'column', 'DEFAULT_IS_AFFIX_DATA'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '$RM_YES_NOT=是、否{
   0=否,
   1=是
   }',
   'user', @CurrentUser, 'table', 'RM_AUTHORIZE_RESOURCE', 'column', 'DEFAULT_IS_RECURSIVE'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '如果资源是树，可用编码体系存储树形关系',
   'user', @CurrentUser, 'table', 'RM_AUTHORIZE_RESOURCE', 'column', 'TOTAL_CODE'
go

/*==============================================================*/
/* Index: IDXU_AUTHRES_AUTHID_OLDRES                            */
/*==============================================================*/
create unique index IDXU_AUTHRES_AUTHID_OLDRES on RM_AUTHORIZE_RESOURCE (
OLD_RESOURCE_ID ASC,
AUTHORIZE_ID ASC
)
go

/*==============================================================*/
/* Table: RM_AUTHORIZE_RESOURCE_RECORD                          */
/*==============================================================*/
create table RM_AUTHORIZE_RESOURCE_RECORD (
   ID                   bigint               not null,
   AUTHORIZE_RESOURCE_ID bigint               not null,
   PARTY_ID             bigint               not null,
   AUTHORIZE_STATUS     char(1)              not null,
   IS_AFFIX_DATA        char(1)              not null,
   IS_RECURSIVE         char(1)              not null,
   ACCESS_TYPE          varchar(1000)        not null,
   constraint PK_RM_AUTHORIZE_RESOURCE_RECOR primary key nonclustered (ID)
)

go
ALTER TABLE RM_AUTHORIZE_RESOURCE_RECORD ADD
USABLE_STATUS CHAR(1) DEFAULT '1',
MODIFY_DATE DATETIME,
MODIFY_IP VARCHAR(45),
MODIFY_USER_ID BIGINT
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '$RM_AUTHORIZE_STATUS=授权情况{
   0=拒绝,
   1=允许
   }',
   'user', @CurrentUser, 'table', 'RM_AUTHORIZE_RESOURCE_RECORD', 'column', 'AUTHORIZE_STATUS'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '$RM_YES_NOT=是、否{
   0=否,
   1=是
   }',
   'user', @CurrentUser, 'table', 'RM_AUTHORIZE_RESOURCE_RECORD', 'column', 'IS_AFFIX_DATA'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '$RM_YES_NOT=是、否{
   0=否,
   1=是
   }',
   'user', @CurrentUser, 'table', 'RM_AUTHORIZE_RESOURCE_RECORD', 'column', 'IS_RECURSIVE'
go

/*==============================================================*/
/* Index: IDX_AUTHORIZE_RESOURCE_PARTYID                        */
/*==============================================================*/
create unique index IDX_AUTHORIZE_RESOURCE_PARTYID on RM_AUTHORIZE_RESOURCE_RECORD (
AUTHORIZE_RESOURCE_ID ASC,
PARTY_ID ASC
)
go

/*==============================================================*/
/* Table: RM_FUNCTION_NODE                                      */
/*==============================================================*/
create table RM_FUNCTION_NODE (
   ID                   bigint               not null,
   NODE_TYPE            varchar(2)           not null,
   FUNCTION_PROPERTY    varchar(50)          not null,
   NAME                 varchar(200)         not null,
   ENABLE_STATUS        varchar(50)          not null,
   TOTAL_CODE           varchar(200)         null,
   ORDER_CODE           varchar(50)          null,
   FUNCNODE_AUTHORIZE_TYPE char(1)              null,
   DESCRIPTION          varchar(1000)        null,
   DATA_VALUE           varchar(1000)        null,
   PATTERN_VALUE        varchar(1000)        null,
   IS_LEAF              char(1)              null,
   ICON                 varchar(200)         null,
   HELP_NAME            varchar(200)         null,
   constraint PK_RM_FUNCTION_NODE primary key nonclustered (ID)
)

go
ALTER TABLE RM_FUNCTION_NODE ADD
USABLE_STATUS CHAR(1) DEFAULT '1',
MODIFY_DATE DATETIME,
MODIFY_IP VARCHAR(45),
MODIFY_USER_ID BIGINT
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '$RM_FUNCTION_NODE_TYPE=功能节点类型{
   1=子系统、一级模块,
   2=子模块、功能,
   3=页面按钮
   }',
   'user', @CurrentUser, 'table', 'RM_FUNCTION_NODE', 'column', 'NODE_TYPE'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '$FUNCTION_PROPERTY=功能性质{
   0=可执行功能节点,
   1=虚功能节点,
   2=可执行功能帧
   }',
   'user', @CurrentUser, 'table', 'RM_FUNCTION_NODE', 'column', 'FUNCTION_PROPERTY'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '$RM_ENABLE_STATUS=启用、禁用{
   0=禁用,
   1=启用
   }',
   'user', @CurrentUser, 'table', 'RM_FUNCTION_NODE', 'column', 'ENABLE_STATUS'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '$RM_FUNCNODE_AUTHORIZE_TYPE=功能节点权限类型{
   0=超级管理员初始化节点,
   1=普通节点
   }',
   'user', @CurrentUser, 'table', 'RM_FUNCTION_NODE', 'column', 'FUNCNODE_AUTHORIZE_TYPE'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '$RM_YES_NOT=是、否{
   0=否,
   1=是
   }',
   'user', @CurrentUser, 'table', 'RM_FUNCTION_NODE', 'column', 'IS_LEAF'
go

/*==============================================================*/
/* Index: IDXU_FUNCTIONNODE_TOTALCODE                           */
/*==============================================================*/
create unique index IDXU_FUNCTIONNODE_TOTALCODE on RM_FUNCTION_NODE (
TOTAL_CODE ASC
)
go

/*==============================================================*/
/* Table: RM_PARTY                                              */
/*==============================================================*/
create table RM_PARTY (
   ID                   bigint               not null,
   PARTY_TYPE_ID        bigint               not null,
   OLD_PARTY_ID         varchar(50)          not null,
   NAME                 varchar(200)         not null,
   IS_INHERIT           char(1)              not null,
   EMAIL                varchar(200)         null,
   DESCRIPTION          varchar(1000)        null,
   CUSTOM1              varchar(200)         null,
   CUSTOM2              varchar(200)         null,
   CUSTOM3              varchar(200)         null,
   CUSTOM4              varchar(200)         null,
   CUSTOM5              varchar(200)         null,
   constraint PK_RM_PARTY primary key nonclustered (ID)
)

go
ALTER TABLE RM_PARTY ADD
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
   'user', @CurrentUser, 'table', 'RM_PARTY', 'column', 'IS_INHERIT'
go

/*==============================================================*/
/* Index: IDXU_PARTY_TYPEID_OLDPARTY                            */
/*==============================================================*/
create unique index IDXU_PARTY_TYPEID_OLDPARTY on RM_PARTY (
PARTY_TYPE_ID ASC,
OLD_PARTY_ID ASC
)
go

/*==============================================================*/
/* Table: RM_PARTY_RELATION                                     */
/*==============================================================*/
create table RM_PARTY_RELATION (
   ID                   bigint               not null,
   PARTY_VIEW_ID        bigint               not null,
   PARENT_PARTY_ID      bigint               null,
   CHILD_PARTY_ID       bigint               not null,
   PARENT_PARTY_CODE    varchar(200)         not null,
   CHILD_PARTY_CODE     varchar(200)         not null,
   CHILD_PARTY_LEVEL    int                  null,
   CHILD_IS_MAIN_RELATION char(1)              null,
   ORDER_CODE           varchar(50)          null,
   PARENT_PARTY_NAME    varchar(200)         null,
   CHILD_PARTY_NAME     varchar(200)         not null,
   PARENT_PARTY_TYPE_ID bigint               null,
   CHILD_PARTY_TYPE_ID  bigint               not null,
   CHILD_IS_LEAF        char(1)              null,
   CUSTOM1              varchar(200)         null,
   CUSTOM2              varchar(200)         null,
   CUSTOM3              varchar(200)         null,
   CUSTOM4              varchar(200)         null,
   CUSTOM5              varchar(200)         null,
   constraint PK_RM_PARTY_RELATION primary key nonclustered (ID)
)

go
ALTER TABLE RM_PARTY_RELATION ADD
USABLE_STATUS CHAR(1) DEFAULT '1',
MODIFY_DATE DATETIME,
MODIFY_IP VARCHAR(45),
MODIFY_USER_ID BIGINT
go

/*==============================================================*/
/* Index: IDX_PARTYREL_PARENTID_VIEW                            */
/*==============================================================*/
create index IDX_PARTYREL_PARENTID_VIEW on RM_PARTY_RELATION (
PARENT_PARTY_ID ASC,
PARTY_VIEW_ID ASC
)
go

/*==============================================================*/
/* Index: IDX_PARTYREL_CHILDID_VIEW                             */
/*==============================================================*/
create index IDX_PARTYREL_CHILDID_VIEW on RM_PARTY_RELATION (
CHILD_PARTY_ID ASC,
PARTY_VIEW_ID ASC
)
go

/*==============================================================*/
/* Index: IDX_PARTYREL_PARENTCODE_VIEW                          */
/*==============================================================*/
create index IDX_PARTYREL_PARENTCODE_VIEW on RM_PARTY_RELATION (
PARENT_PARTY_CODE ASC,
PARTY_VIEW_ID ASC
)
go

/*==============================================================*/
/* Index: IDXU_PARTYREL_CHILDCODE_VIEW                          */
/*==============================================================*/
create unique index IDXU_PARTYREL_CHILDCODE_VIEW on RM_PARTY_RELATION (
CHILD_PARTY_CODE ASC,
PARTY_VIEW_ID ASC
)
go

/*==============================================================*/
/* Table: RM_PARTY_ROLE                                         */
/*==============================================================*/
create table RM_PARTY_ROLE (
   ID                   bigint               not null,
   OWNER_PARTY_ID       bigint               not null,
   ROLE_ID              bigint               not null,
   OWNER_ORG_ID         varchar(50)          null,
   constraint PK_RM_PARTY_ROLE primary key nonclustered (ID)
)

go
ALTER TABLE RM_PARTY_ROLE ADD
USABLE_STATUS CHAR(1) DEFAULT '1',
MODIFY_DATE DATETIME,
MODIFY_IP VARCHAR(45),
MODIFY_USER_ID BIGINT
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '关联角色时，指定所属的组织ID',
   'user', @CurrentUser, 'table', 'RM_PARTY_ROLE', 'column', 'OWNER_ORG_ID'
go

/*==============================================================*/
/* Index: IDXU_PARTY_ROLE_ORG                                   */
/*==============================================================*/
create unique index IDXU_PARTY_ROLE_ORG on RM_PARTY_ROLE (
OWNER_PARTY_ID ASC,
ROLE_ID ASC,
OWNER_ORG_ID ASC
)
go

/*==============================================================*/
/* Table: RM_PARTY_TYPE                                         */
/*==============================================================*/
create table RM_PARTY_TYPE (
   ID                   bigint               not null,
   BS_KEYWORD           varchar(200)         not null,
   NAME                 varchar(200)         not null,
   ICON                 varchar(200)         null,
   DESCRIPTION          varchar(1000)        null,
   IS_CUSTOM_ATTRIBUTE  char(1)              not null,
   constraint PK_RM_PARTY_TYPE primary key nonclustered (ID)
)

go
ALTER TABLE RM_PARTY_TYPE ADD
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
   'user', @CurrentUser, 'table', 'RM_PARTY_TYPE', 'column', 'IS_CUSTOM_ATTRIBUTE'
go

/*==============================================================*/
/* Index: IDXU_PARTYTYPE_BK                                     */
/*==============================================================*/
create unique index IDXU_PARTYTYPE_BK on RM_PARTY_TYPE (
BS_KEYWORD ASC
)
go

/*==============================================================*/
/* Table: RM_PARTY_TYPE_RELATION_RULE                           */
/*==============================================================*/
create table RM_PARTY_TYPE_RELATION_RULE (
   ID                   bigint               not null,
   PARTY_VIEW_ID        bigint               not null,
   PARENT_PARTY_TYPE_ID bigint               null,
   CHILD_PARTY_TYPE_ID  bigint               not null,
   RULE_DESC            varchar(4000)        null,
   IS_INSERT_CHILD_PARTY char(1)              null,
   constraint PK_RM_PARTY_TYPE_RELATION_RULE primary key nonclustered (ID)
)

go
ALTER TABLE RM_PARTY_TYPE_RELATION_RULE ADD
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
   'user', @CurrentUser, 'table', 'RM_PARTY_TYPE_RELATION_RULE', 'column', 'IS_INSERT_CHILD_PARTY'
go

/*==============================================================*/
/* Index: IDXU_VIEW_PARTY_CHILD                                 */
/*==============================================================*/
create unique index IDXU_VIEW_PARTY_CHILD on RM_PARTY_TYPE_RELATION_RULE (
PARTY_VIEW_ID ASC,
PARENT_PARTY_TYPE_ID ASC,
CHILD_PARTY_TYPE_ID ASC
)
go

/*==============================================================*/
/* Table: RM_PARTY_VIEW                                         */
/*==============================================================*/
create table RM_PARTY_VIEW (
   ID                   bigint               not null,
   BS_KEYWORD           varchar(50)          not null,
   NAME                 varchar(200)         not null,
   VIEW_DESC            varchar(4000)        null,
   constraint PK_RM_PARTY_VIEW primary key nonclustered (ID)
)

go
ALTER TABLE RM_PARTY_VIEW ADD
USABLE_STATUS CHAR(1) DEFAULT '1',
MODIFY_DATE DATETIME,
MODIFY_IP VARCHAR(45),
MODIFY_USER_ID BIGINT
go

/*==============================================================*/
/* Index: IDXU_PARTYVIEW_BK                                     */
/*==============================================================*/
create unique index IDXU_PARTYVIEW_BK on RM_PARTY_VIEW (
BS_KEYWORD ASC
)
go

/*==============================================================*/
/* Table: RM_ROLE                                               */
/*==============================================================*/
create table RM_ROLE (
   ID                   bigint               not null,
   ROLE_CODE            varchar(200)         not null,
   NAME                 varchar(200)         not null,
   ENABLE_STATUS        char(1)              not null,
   IS_SYSTEM_LEVEL      char(1)              not null,
   OWNER_ORG_ID         varchar(50)          null,
   IS_RECURSIVE         char(1)              null,
   MATRIX_CODE          varchar(1000)        null,
   DESCRIPTION          varchar(1000)        null,
   FUNCTION_PERMISSION  varchar(4000)        null,
   DATA_PERMISSION      varchar(4000)        null,
   constraint PK_RM_ROLE primary key nonclustered (ID)
)

go
ALTER TABLE RM_ROLE ADD
USABLE_STATUS CHAR(1) DEFAULT '1',
MODIFY_DATE DATETIME,
MODIFY_IP VARCHAR(45),
MODIFY_USER_ID BIGINT
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '$RM_ENABLE_STATUS=启用、禁用{
   1=启用,
   0=禁用
   }',
   'user', @CurrentUser, 'table', 'RM_ROLE', 'column', 'ENABLE_STATUS'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '$RM_YES_NOT=是、否{
   0=否,
   1=是
   }
   如果不是全局角色，则OWNER_ORG_ID、IS_RECURSIVE必填',
   'user', @CurrentUser, 'table', 'RM_ROLE', 'column', 'IS_SYSTEM_LEVEL'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '$RM_YES_NOT=是、否{
   0=否,
   1=是
   }',
   'user', @CurrentUser, 'table', 'RM_ROLE', 'column', 'IS_RECURSIVE'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '逗号分隔',
   'user', @CurrentUser, 'table', 'RM_ROLE', 'column', 'FUNCTION_PERMISSION'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '逗号分隔',
   'user', @CurrentUser, 'table', 'RM_ROLE', 'column', 'DATA_PERMISSION'
go

/*==============================================================*/
/* Index: IDXU_ROLE_ROLECODE                                    */
/*==============================================================*/
create unique index IDXU_ROLE_ROLECODE on RM_ROLE (
ROLE_CODE ASC
)
go

/*==============================================================*/
/* Table: RM_USER                                               */
/*==============================================================*/
create table RM_USER (
   ID                   bigint               not null,
   NAME                 varchar(200)         not null,
   LOCK_STATUS          char(1)              not null,
   LOGIN_ID             varchar(200)         not null,
   PASSWORD             varchar(200)         null,
   AUTHEN_TYPE          varchar(200)         null,
   ORGANIZATION_ID      varchar(200)         null,
   EMPLOYEE_ID          varchar(50)          null,
   EMAIL                varchar(200)         null,
   ADMIN_TYPE           varchar(2)           null,
   DESCRIPTION          varchar(1000)        null,
   AGENT_STATUS         char(1)              null,
   LOGIN_STATUS         char(1)              null,
   LAST_LOGIN_DATE      datetime             null,
   LAST_LOGIN_IP        varchar(50)          null,
   LOGIN_SUM            bigint               null,
   LAST_CUSTOM_CSS      varchar(200)         null,
   IS_AFFIX             char(1)              null,
   FUNCTION_PERMISSION  varchar(4000)        null,
   DATA_PERMISSION      varchar(4000)        null,
   CUSTOM1              varchar(200)         null,
   CUSTOM2              varchar(200)         null,
   CUSTOM3              varchar(200)         null,
   CUSTOM4              varchar(200)         null,
   CUSTOM5              varchar(200)         null,
   CUSTOM_XML           varchar(4000)        null,
   constraint PK_RM_USER primary key nonclustered (ID)
)

go
ALTER TABLE RM_USER ADD
USABLE_STATUS CHAR(1) DEFAULT '1',
MODIFY_DATE DATETIME,
MODIFY_IP VARCHAR(45),
MODIFY_USER_ID BIGINT
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '$RM_LOCK_STATUS=激活、锁定状态{
   0=锁定,
   1=激活状态
   }',
   'user', @CurrentUser, 'table', 'RM_USER', 'column', 'LOCK_STATUS'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '$RM_ADMIN_TYPE=用户权限类型{
   0=临时用户,
   1=前台用户,
   2=普通用户,
   9=超级管理员(admin,一般用于数据初始化)
   }',
   'user', @CurrentUser, 'table', 'RM_USER', 'column', 'ADMIN_TYPE'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '$RM_AGENT_STATUS=代理状态{
   0=未代理,
   1=已代理
   }',
   'user', @CurrentUser, 'table', 'RM_USER', 'column', 'AGENT_STATUS'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '逗号分隔',
   'user', @CurrentUser, 'table', 'RM_USER', 'column', 'FUNCTION_PERMISSION'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '逗号分隔',
   'user', @CurrentUser, 'table', 'RM_USER', 'column', 'DATA_PERMISSION'
go

/*==============================================================*/
/* Index: IDXU_USER_LOGINID                                     */
/*==============================================================*/
create unique index IDXU_USER_LOGINID on RM_USER (
LOGIN_ID ASC
)
go

/*==============================================================*/
/* Table: RM_USER_AGENT                                         */
/*==============================================================*/
create table RM_USER_AGENT (
   ID                   bigint               not null,
   AGENTED_ID           bigint               not null,
   AGENT_ID             bigint               not null,
   BEGIN_TIME           datetime             null,
   END_TIME             datetime             null,
   AGENT_AUTHORIZE      varchar(4000)        null,
   constraint PK_RM_USER_AGENT primary key nonclustered (ID)
)

go
ALTER TABLE RM_USER_AGENT ADD
USABLE_STATUS CHAR(1) DEFAULT '1',
MODIFY_DATE DATETIME,
MODIFY_IP VARCHAR(45),
MODIFY_USER_ID BIGINT
go

/*==============================================================*/
/* Table: RM_USER_ONLINE_RECORD                                 */
/*==============================================================*/
create table RM_USER_ONLINE_RECORD (
   ID                   bigint               not null,
   USER_ID              bigint               not null,
   LOGIN_TIME           datetime             not null,
   CLUSTER_NODE_ID      varchar(50)          null,
   LOGIN_SIGN           varchar(200)         null,
   LOGIN_IP             varchar(50)          null,
   LOGIN_UUID           varchar(50)          null,
   LOGOUT_TIME          datetime             null,
   LOGOUT_TYPE          varchar(50)          null,
   ONLINE_TIME          bigint               null,
   LAST_OPERATION       varchar(1000)        null,
   constraint PK_RM_USER_ONLINE_RECORD primary key nonclustered (ID)
)

go
ALTER TABLE RM_USER_ONLINE_RECORD ADD
USABLE_STATUS CHAR(1) DEFAULT '1',
MODIFY_DATE DATETIME,
MODIFY_IP VARCHAR(45),
MODIFY_USER_ID BIGINT
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '$RM_LOGOUT_TYPE=注销类型{
   1=正常注销,
   2=超时退出,
   3=被强制登录替换,
   4=被管理员强制退出
   }',
   'user', @CurrentUser, 'table', 'RM_USER_ONLINE_RECORD', 'column', 'LOGOUT_TYPE'
go

alter table RM_AUTHORIZE_ACCESS_RULE
   add constraint FK_RM_AUTHO_REFERM_AU_RM_AUTHO foreign key (AUTHORIZE_ID)
      references RM_AUTHORIZE (ID)
go

alter table RM_AUTHORIZE_AFFIX
   add constraint FK_RM_ACCES_REFERENCE_RM_AUTH2 foreign key (AUTHORIZE_RESOURCE_RECORD_ID)
      references RM_AUTHORIZE_RESOURCE_RECORD (ID)
go

alter table RM_AUTHORIZE_AFFIX
   add constraint FK_AU_ACCES_REFERENCE_AU_RESO2 foreign key (AUTHORIZE_RESOURCE_ID)
      references RM_AUTHORIZE_RESOURCE (ID)
go

alter table RM_AUTHORIZE_AFFIX
   add constraint FK_AU_ACCES_REFERENCE_AU_ACCE2 foreign key (AUTHORIZE_AFFIX_RULE_ID)
      references RM_AUTHORIZE_AFFIX_RULE (ID)
go

alter table RM_AUTHORIZE_AFFIX_RULE
   add constraint FK_RM_ACCES_REFERENCE_RM_AUTH3 foreign key (AUTHORIZE_ID)
      references RM_AUTHORIZE (ID)
go

alter table RM_AUTHORIZE_CONFIG
   add constraint FK_RM_AUTHO_REFERENCE_RM_AUTH2 foreign key (AUTHORIZE_ID)
      references RM_AUTHORIZE (ID)
go

alter table RM_AUTHORIZE_CONFIG_AFFIX
   add constraint FK_RM_AUTHO_REFERENCE_RM_AUTH3 foreign key (AUTHORIZE_CONFIG_ID)
      references RM_AUTHORIZE_CONFIG (ID)
go

alter table RM_AUTHORIZE_CONFIG_AFFIX
   add constraint FK_RM_AUTHO_REFERENCE_RM_AUTH4 foreign key (AUTHORIZE_AFFIX_RULE_ID)
      references RM_AUTHORIZE_AFFIX_RULE (ID)
go

alter table RM_AUTHORIZE_RESOURCE
   add constraint FK_RM_AUTHO_REFERENCE_RM_AUTH6 foreign key (AUTHORIZE_ID)
      references RM_AUTHORIZE (ID)
go

alter table RM_AUTHORIZE_RESOURCE_RECORD
   add constraint FK_RM_AUTHO_REFERENCE_RM_AUTH5 foreign key (AUTHORIZE_RESOURCE_ID)
      references RM_AUTHORIZE_RESOURCE (ID)
go

alter table RM_AUTHORIZE_RESOURCE_RECORD
   add constraint FK_RM_AUTHO_REFERM_PA_RM_PARTY foreign key (PARTY_ID)
      references RM_PARTY (ID)
go

alter table RM_PARTY
   add constraint FK_RM_PARTY_REFERENCE_RM_PART2 foreign key (PARTY_TYPE_ID)
      references RM_PARTY_TYPE (ID)
go

alter table RM_PARTY_RELATION
   add constraint FK_RM_PARTY_REFERENCE_RM_PART5 foreign key (PARTY_VIEW_ID)
      references RM_PARTY_VIEW (ID)
go

alter table RM_PARTY_RELATION
   add constraint FK_RM_PARTY_REFERENCE_RM_PART3 foreign key (CHILD_PARTY_ID)
      references RM_PARTY (ID)
go

alter table RM_PARTY_RELATION
   add constraint FK_RM_PARTY_REFERENCE_RM_PART4 foreign key (PARENT_PARTY_ID)
      references RM_PARTY (ID)
go

alter table RM_PARTY_RELATION
   add constraint FK_RM_PARTY_REFERENCE_RM_PART9 foreign key (PARENT_PARTY_TYPE_ID)
      references RM_PARTY_TYPE (ID)
go

alter table RM_PARTY_RELATION
   add constraint FK_RM_PARTY_REFERENCE_RM_PARTY foreign key (CHILD_PARTY_TYPE_ID)
      references RM_PARTY_TYPE (ID)
go

alter table RM_PARTY_ROLE
   add constraint FK_RM_PARTY_REFERM_PA_RM_PARTY foreign key (OWNER_PARTY_ID)
      references RM_PARTY (ID)
go

alter table RM_PARTY_ROLE
   add constraint FK_RM_PARTY_REFERM_RO_RM_ROLE foreign key (ROLE_ID)
      references RM_ROLE (ID)
go

alter table RM_PARTY_TYPE_RELATION_RULE
   add constraint FK_RM_PARTY_REFERENCE_RM_PART7 foreign key (CHILD_PARTY_TYPE_ID)
      references RM_PARTY_TYPE (ID)
go

alter table RM_PARTY_TYPE_RELATION_RULE
   add constraint FK_RM_PARTY_REFERENCE_RM_PART8 foreign key (PARENT_PARTY_TYPE_ID)
      references RM_PARTY_TYPE (ID)
go

alter table RM_PARTY_TYPE_RELATION_RULE
   add constraint FK_RM_PARTY_REFERENCE_RM_PART6 foreign key (PARTY_VIEW_ID)
      references RM_PARTY_VIEW (ID)
go

alter table RM_USER_AGENT
   add constraint FK_RM_USER__REFERENCE_RM_USER2 foreign key (AGENT_ID)
      references RM_USER (ID)
go

alter table RM_USER_AGENT
   add constraint FK_RM_USER__REFERENCE_RM_USER foreign key (AGENTED_ID)
      references RM_USER (ID)
go

alter table RM_USER_ONLINE_RECORD
   add constraint FK_RM_USER__REFERM_US_RM_USER foreign key (USER_ID)
      references RM_USER (ID)
go

INSERT INTO RM_PARTY_TYPE VALUES (1000200800000000001, 'RM_USER', '用户', '', '', '0', '1', '2011-7-14 00:30:32', '0:0:0:0:0:0:0:1', NULL);
INSERT INTO RM_PARTY_TYPE VALUES (1000200800000000002, 'RM_ROLE', '角色', '', '', '0', '1', '2011-7-14 00:31:02', '0:0:0:0:0:0:0:1', NULL);

INSERT INTO RM_PARTY_VIEW VALUES (1000200700000000001, 'A', '行政视图', '', '1', '2011-7-14 00:31:26', '0:0:0:0:0:0:0:1', NULL);

INSERT INTO RM_PARTY_TYPE_RELATION_RULE VALUES (1000200900000000001, 1000200700000000001, NULL, 1000200800000000001, '', '0', '1', '2011-7-14 00:31:38', '0:0:0:0:0:0:0:1', NULL);
INSERT INTO RM_PARTY_TYPE_RELATION_RULE VALUES (1000200900000000002, 1000200700000000001, NULL, 1000200800000000002, '', '0', '1', '2011-7-14 00:31:45', '0:0:0:0:0:0:0:1', NULL);

INSERT INTO RM_PARTY VALUES (1000201100000000001, 1000200800000000001, '1000201100000000001', 'admin', '0', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1', '2011-7-14 00:32:16', '0:0:0:0:0:0:0:1', NULL);
INSERT INTO RM_USER VALUES (1000201100000000001, 'ADMIN', '_WJ4Cqip_aUGNAXXJp0kn19qfVKOOU85g6AtgJul6q0', 'admin', '111111', NULL, '', NULL, 'admin@quickbundle.org', '3', '', NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1', '2011-7-14 00:32:16', '0:0:0:0:0:0:0:1', NULL);

INSERT INTO RM_PARTY_RELATION VALUES (1000201000000000001, 1000200700000000001, NULL, 1000201100000000001, 'A', 'A001', -1, '1', NULL, '', 'admin', NULL, 1000200800000000001, '1', NULL, NULL, NULL, NULL, NULL, '1', '2011-7-14 00:32:17', '0:0:0:0:0:0:0:1', NULL);

INSERT INTO RM_AUTHORIZE VALUES (1000202400000000001, '功能权限', 'FUNCTION_NODE', '0', '', '', '', '1', '	', '', '1', '2011-11-4 15:11:09', '0:0:0:0:0:0:0:1', 1000201100000000001);