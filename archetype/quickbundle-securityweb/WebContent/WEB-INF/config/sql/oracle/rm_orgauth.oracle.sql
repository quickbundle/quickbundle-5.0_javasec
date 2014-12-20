/*==============================================================*/
/* DBMS name:      ORACLE Version 11g_qb                        */
/* Created on:     2011-05-12 00:18:49                          */
/*==============================================================*/

/*
alter table RM_AUTHORIZE_ACCESS_RULE
   drop constraint FK_RM_AUTHO_REFERM_AU_RM_AUTHO;

alter table RM_AUTHORIZE_AFFIX
   drop constraint FK_RM_ACCES_REFERENCE_RM_AUTH2;

alter table RM_AUTHORIZE_AFFIX
   drop constraint FK_AU_ACCES_REFERENCE_AU_RESO2;

alter table RM_AUTHORIZE_AFFIX
   drop constraint FK_AU_ACCES_REFERENCE_AU_ACCE2;

alter table RM_AUTHORIZE_AFFIX_RULE
   drop constraint FK_RM_ACCES_REFERENCE_RM_AUTH3;

alter table RM_AUTHORIZE_CONFIG
   drop constraint FK_RM_AUTHO_REFERENCE_RM_AUTH2;

alter table RM_AUTHORIZE_CONFIG_AFFIX
   drop constraint FK_RM_AUTHO_REFERENCE_RM_AUTH3;

alter table RM_AUTHORIZE_CONFIG_AFFIX
   drop constraint FK_RM_AUTHO_REFERENCE_RM_AUTH4;

alter table RM_AUTHORIZE_RESOURCE
   drop constraint FK_RM_AUTHO_REFERENCE_RM_AUTH6;

alter table RM_AUTHORIZE_RESOURCE_RECORD
   drop constraint FK_RM_AUTHO_REFERENCE_RM_AUTH5;

alter table RM_AUTHORIZE_RESOURCE_RECORD
   drop constraint FK_RM_AUTHO_REFERM_PA_RM_PARTY;

alter table RM_PARTY
   drop constraint FK_RM_PARTY_REFERENCE_RM_PART2;

alter table RM_PARTY_RELATION
   drop constraint FK_RM_PARTY_REFERENCE_RM_PART5;

alter table RM_PARTY_RELATION
   drop constraint FK_RM_PARTY_REFERENCE_RM_PART3;

alter table RM_PARTY_RELATION
   drop constraint FK_RM_PARTY_REFERENCE_RM_PART4;

alter table RM_PARTY_RELATION
   drop constraint FK_RM_PARTY_REFERENCE_RM_PART9;

alter table RM_PARTY_RELATION
   drop constraint FK_RM_PARTY_REFERENCE_RM_PARTY;

alter table RM_PARTY_ROLE
   drop constraint FK_RM_PARTY_REFERM_PA_RM_PARTY;

alter table RM_PARTY_ROLE
   drop constraint FK_RM_PARTY_REFERM_RO_RM_ROLE;

alter table RM_PARTY_TYPE_RELATION_RULE
   drop constraint FK_RM_PARTY_REFERENCE_RM_PART7;

alter table RM_PARTY_TYPE_RELATION_RULE
   drop constraint FK_RM_PARTY_REFERENCE_RM_PART8;

alter table RM_PARTY_TYPE_RELATION_RULE
   drop constraint FK_RM_PARTY_REFERENCE_RM_PART6;

alter table RM_PASSWORD_HISTORY
   drop constraint FK_RM_PASSW_REFERENCE_RM_USER;

alter table RM_PASSWORD_LOGIN_FAIL
   drop constraint FK_RM_PASSW_REFERENCE_RM_USER2;

alter table RM_PASSWORD_STRATEGY_USER
   drop constraint FK_密码策略关联_REFE2;

alter table RM_USER_AGENT
   drop constraint FK_RM_USER__REFERENCE_RM_USER2;

alter table RM_USER_AGENT
   drop constraint FK_RM_USER__REFERENCE_RM_USER;

alter table RM_USER_ONLINE_RECORD
   drop constraint FK_RM_USER__REFERM_US_RM_USER;

drop index IDXU_AUTHORIZE_BK;

drop table RM_AUTHORIZE cascade constraints;

drop table RM_AUTHORIZE_ACCESS_RULE cascade constraints;

drop table RM_AUTHORIZE_AFFIX cascade constraints;

drop table RM_AUTHORIZE_AFFIX_RULE cascade constraints;

drop table RM_AUTHORIZE_CONFIG cascade constraints;

drop table RM_AUTHORIZE_CONFIG_AFFIX cascade constraints;

drop index IDXU_AUTHRES_AUTHID_OLDRES;

drop table RM_AUTHORIZE_RESOURCE cascade constraints;

drop index IDX_AUTHORIZE_RESOURCE_PARTYID;

drop table RM_AUTHORIZE_RESOURCE_RECORD cascade constraints;

drop index IDXU_FUNCTIONNODE_TOTALCODE;

drop table RM_FUNCTION_NODE cascade constraints;

drop index IDXU_PARTY_TYPEID_OLDPARTY;

drop table RM_PARTY cascade constraints;

drop index IDXU_PARTYREL_CHILDCODE_VIEW;

drop index IDX_PARTYREL_PARENTCODE_VIEW;

drop index IDX_PARTYREL_CHILDID_VIEW;

drop index IDX_PARTYREL_PARENTID_VIEW;

drop table RM_PARTY_RELATION cascade constraints;

drop index IDXU_PARTY_ROLE_ORG;

drop table RM_PARTY_ROLE cascade constraints;

drop index IDXU_PARTYTYPE_BK;

drop table RM_PARTY_TYPE cascade constraints;

drop index IDXU_VIEW_PARTY_CHILD;

drop table RM_PARTY_TYPE_RELATION_RULE cascade constraints;

drop index IDXU_PARTYVIEW_BK;

drop table RM_PARTY_VIEW cascade constraints;

drop index IDXU_ROLE_ROLECODE;

drop table RM_ROLE cascade constraints;

drop index IDXU_USER_LOGINID;

drop table RM_USER cascade constraints;

drop table RM_USER_AGENT cascade constraints;

drop table RM_USER_ONLINE_RECORD cascade constraints;
*/
/*==============================================================*/
/* Table: RM_AUTHORIZE                                          */
/*==============================================================*/
create table RM_AUTHORIZE 
(
   ID                   NUMBER(19)           not null,
   NAME                 VARCHAR2(200)        not null,
   BS_KEYWORD           VARCHAR2(200)        not null,
   IS_ALONE_TABLE       CHAR(1)              not null,
   AUTHORIZE_RESOURCE_TABLE_NAME VARCHAR2(50),
   AUTHORIZE_RESREC_TABLE_NAME VARCHAR2(50),
   AUTHORIZE_AFFIX_TABLE_NAME VARCHAR2(50),
   SETTIING_OPTION      CHAR(1),
   CUSTOM_CODE          VARCHAR2(4000),
   DESCRIPTION          VARCHAR2(1000),
   constraint PK_RM_AUTHORIZE primary key (ID)
)

/
ALTER TABLE RM_AUTHORIZE ADD (
USABLE_STATUS CHAR(1) DEFAULT '1',
MODIFY_DATE DATE DEFAULT SYSDATE,
MODIFY_IP VARCHAR2(45),
MODIFY_USER_ID NUMBER(19)
);

comment on column RM_AUTHORIZE.IS_ALONE_TABLE is
'$RM_YES_NOT=是、否{
0=否,
1=是
}';

comment on column RM_AUTHORIZE.SETTIING_OPTION is
'$RM_OPTION_TYPE=权限配置{
1=单选,
2=多选,
3=定制
}';

/*==============================================================*/
/* Index: IDXU_AUTHORIZE_BK                                     */
/*==============================================================*/
create unique index IDXU_AUTHORIZE_BK on RM_AUTHORIZE (
   BS_KEYWORD ASC
);

/*==============================================================*/
/* Table: RM_AUTHORIZE_ACCESS_RULE                              */
/*==============================================================*/
create table RM_AUTHORIZE_ACCESS_RULE 
(
   ID                   NUMBER(19)           not null,
   AUTHORIZE_ID         NUMBER(19)           not null,
   POSITION_NUMBER      NUMBER(10)           not null,
   NAME                 VARCHAR2(200)        not null,
   CODE                 VARCHAR2(50),
   DEFAULT_CHECKED      CHAR(1),
   DESCRIPTION          VARCHAR2(1000),
   constraint PK_RM_AUTHORIZE_ACCESS_RULE primary key (ID)
)

/
ALTER TABLE RM_AUTHORIZE_ACCESS_RULE ADD (
USABLE_STATUS CHAR(1) DEFAULT '1',
MODIFY_DATE DATE DEFAULT SYSDATE,
MODIFY_IP VARCHAR2(45),
MODIFY_USER_ID NUMBER(19)
);

comment on column RM_AUTHORIZE_ACCESS_RULE.DEFAULT_CHECKED is
'$RM_YES_NOT=是、否{
0=否,
1=是
}';

/*==============================================================*/
/* Table: RM_AUTHORIZE_AFFIX                                    */
/*==============================================================*/
create table RM_AUTHORIZE_AFFIX 
(
   ID                   NUMBER(19)           not null,
   AUTHORIZE_AFFIX_RULE_ID NUMBER(19)           not null,
   AUTHORIZE_RESOURCE_RECORD_ID NUMBER(19),
   AUTHORIZE_RESOURCE_ID NUMBER(19),
   DATA_VALUE           VARCHAR2(4000),
   constraint PK_RM_AUTHORIZE_AFFIX primary key (ID)
)

/
ALTER TABLE RM_AUTHORIZE_AFFIX ADD (
USABLE_STATUS CHAR(1) DEFAULT '1',
MODIFY_DATE DATE DEFAULT SYSDATE,
MODIFY_IP VARCHAR2(45),
MODIFY_USER_ID NUMBER(19)
);

/*==============================================================*/
/* Table: RM_AUTHORIZE_AFFIX_RULE                               */
/*==============================================================*/
create table RM_AUTHORIZE_AFFIX_RULE 
(
   ID                   NUMBER(19)           not null,
   AUTHORIZE_ID         NUMBER(19)           not null,
   NAME                 VARCHAR2(200)        not null,
   CODE                 VARCHAR2(50),
   AFFIX_DATA_TRANSLATE VARCHAR2(4000),
   DESCRIPTION          VARCHAR2(1000),
   constraint PK_RM_AUTHORIZE_AFFIX_RULE primary key (ID)
)

/
ALTER TABLE RM_AUTHORIZE_AFFIX_RULE ADD (
USABLE_STATUS CHAR(1) DEFAULT '1',
MODIFY_DATE DATE DEFAULT SYSDATE,
MODIFY_IP VARCHAR2(45),
MODIFY_USER_ID NUMBER(19)
);

/*==============================================================*/
/* Table: RM_AUTHORIZE_CONFIG                                   */
/*==============================================================*/
create table RM_AUTHORIZE_CONFIG 
(
   ID                   NUMBER(19)           not null,
   AUTHORIZE_ID         NUMBER(19)           not null,
   RECORD_ID            VARCHAR2(50)         not null,
   DATA_VALUE           VARCHAR2(4000),
   DESCRIPTION          VARCHAR2(1000),
   constraint PK_RM_AUTHORIZE_CONFIG_INSTAN2 primary key (ID)
)

/
ALTER TABLE RM_AUTHORIZE_CONFIG ADD (
USABLE_STATUS CHAR(1) DEFAULT '1',
MODIFY_DATE DATE DEFAULT SYSDATE,
MODIFY_IP VARCHAR2(45),
MODIFY_USER_ID NUMBER(19)
);

/*==============================================================*/
/* Table: RM_AUTHORIZE_CONFIG_AFFIX                             */
/*==============================================================*/
create table RM_AUTHORIZE_CONFIG_AFFIX 
(
   ID                   NUMBER(19)           not null,
   AUTHORIZE_AFFIX_RULE_ID NUMBER(19)           not null,
   AUTHORIZE_CONFIG_ID  NUMBER(19)           not null,
   DATA_VALUE           VARCHAR2(4000),
   constraint PK_RM_AUTHORIZE_CONFIG_AFFIX primary key (ID)
)

/
ALTER TABLE RM_AUTHORIZE_CONFIG_AFFIX ADD (
USABLE_STATUS CHAR(1) DEFAULT '1',
MODIFY_DATE DATE DEFAULT SYSDATE,
MODIFY_IP VARCHAR2(45),
MODIFY_USER_ID NUMBER(19)
);

/*==============================================================*/
/* Table: RM_AUTHORIZE_RESOURCE                                 */
/*==============================================================*/
create table RM_AUTHORIZE_RESOURCE 
(
   ID                   NUMBER(19)           not null,
   AUTHORIZE_ID         NUMBER(19)           not null,
   OLD_RESOURCE_ID      VARCHAR2(200)        not null,
   DEFAULT_ACCESS       CHAR(1)              not null,
   DEFAULT_IS_AFFIX_DATA CHAR(1)              not null,
   DEFAULT_IS_RECURSIVE CHAR(1)              not null,
   DEFAULT_ACCESS_TYPE  VARCHAR2(1000)       not null,
   TOTAL_CODE           VARCHAR2(200),
   NAME                 VARCHAR2(200),
   constraint PK_RM_AUTHORIZE_RESOURCE primary key (ID)
)

/
ALTER TABLE RM_AUTHORIZE_RESOURCE ADD (
USABLE_STATUS CHAR(1) DEFAULT '1',
MODIFY_DATE DATE DEFAULT SYSDATE,
MODIFY_IP VARCHAR2(45),
MODIFY_USER_ID NUMBER(19)
);

comment on column RM_AUTHORIZE_RESOURCE.DEFAULT_ACCESS is
'$RM_YES_NOT=是、否{
0=否,
1=是
}';

comment on column RM_AUTHORIZE_RESOURCE.DEFAULT_IS_AFFIX_DATA is
'$RM_YES_NOT=是、否{
0=否,
1=是
}';

comment on column RM_AUTHORIZE_RESOURCE.DEFAULT_IS_RECURSIVE is
'$RM_YES_NOT=是、否{
0=否,
1=是
}';

comment on column RM_AUTHORIZE_RESOURCE.TOTAL_CODE is
'如果资源是树，可用编码体系存储树形关系';

/*==============================================================*/
/* Index: IDXU_AUTHRES_AUTHID_OLDRES                            */
/*==============================================================*/
create unique index IDXU_AUTHRES_AUTHID_OLDRES on RM_AUTHORIZE_RESOURCE (
   OLD_RESOURCE_ID ASC,
   AUTHORIZE_ID ASC
);

/*==============================================================*/
/* Table: RM_AUTHORIZE_RESOURCE_RECORD                          */
/*==============================================================*/
create table RM_AUTHORIZE_RESOURCE_RECORD 
(
   ID                   NUMBER(19)           not null,
   AUTHORIZE_RESOURCE_ID NUMBER(19)           not null,
   PARTY_ID             NUMBER(19)           not null,
   AUTHORIZE_STATUS     CHAR(1)              not null,
   IS_AFFIX_DATA        CHAR(1)              not null,
   IS_RECURSIVE         CHAR(1)              not null,
   ACCESS_TYPE          VARCHAR2(1000)       not null,
   constraint PK_RM_AUTHORIZE_RESOURCE_RECOR primary key (ID)
)

/
ALTER TABLE RM_AUTHORIZE_RESOURCE_RECORD ADD (
USABLE_STATUS CHAR(1) DEFAULT '1',
MODIFY_DATE DATE DEFAULT SYSDATE,
MODIFY_IP VARCHAR2(45),
MODIFY_USER_ID NUMBER(19)
);

comment on column RM_AUTHORIZE_RESOURCE_RECORD.AUTHORIZE_STATUS is
'$RM_AUTHORIZE_STATUS=授权情况{
0=拒绝,
1=允许
}';

comment on column RM_AUTHORIZE_RESOURCE_RECORD.IS_AFFIX_DATA is
'$RM_YES_NOT=是、否{
0=否,
1=是
}';

comment on column RM_AUTHORIZE_RESOURCE_RECORD.IS_RECURSIVE is
'$RM_YES_NOT=是、否{
0=否,
1=是
}';

/*==============================================================*/
/* Index: IDX_AUTHORIZE_RESOURCE_PARTYID                        */
/*==============================================================*/
create unique index IDX_AUTHORIZE_RESOURCE_PARTYID on RM_AUTHORIZE_RESOURCE_RECORD (
   AUTHORIZE_RESOURCE_ID ASC,
   PARTY_ID ASC
);

/*==============================================================*/
/* Table: RM_FUNCTION_NODE                                      */
/*==============================================================*/
create table RM_FUNCTION_NODE 
(
   ID                   NUMBER(19)           not null,
   NODE_TYPE            VARCHAR2(2)          not null,
   FUNCTION_PROPERTY    VARCHAR2(50)         not null,
   NAME                 VARCHAR2(200)        not null,
   ENABLE_STATUS        VARCHAR2(50)         not null,
   TOTAL_CODE           VARCHAR2(200),
   ORDER_CODE           VARCHAR2(50),
   FUNCNODE_AUTHORIZE_TYPE CHAR(1),
   DESCRIPTION          VARCHAR2(1000),
   DATA_VALUE           VARCHAR2(1000),
   PATTERN_VALUE        VARCHAR2(1000),
   IS_LEAF              CHAR(1),
   ICON                 VARCHAR2(200),
   HELP_NAME            VARCHAR2(200),
   constraint PK_RM_FUNCTION_NODE primary key (ID)
)

/
ALTER TABLE RM_FUNCTION_NODE ADD (
USABLE_STATUS CHAR(1) DEFAULT '1',
MODIFY_DATE DATE DEFAULT SYSDATE,
MODIFY_IP VARCHAR2(45),
MODIFY_USER_ID NUMBER(19)
);

comment on column RM_FUNCTION_NODE.NODE_TYPE is
'$RM_FUNCTION_NODE_TYPE=功能节点类型{
1=子系统、一级模块,
2=子模块、功能,
3=页面按钮
}';

comment on column RM_FUNCTION_NODE.FUNCTION_PROPERTY is
'$FUNCTION_PROPERTY=功能性质{
0=可执行功能节点,
1=虚功能节点,
2=可执行功能帧
}';

comment on column RM_FUNCTION_NODE.ENABLE_STATUS is
'$RM_ENABLE_STATUS=启用、禁用{
0=禁用,
1=启用
}';

comment on column RM_FUNCTION_NODE.FUNCNODE_AUTHORIZE_TYPE is
'$RM_FUNCNODE_AUTHORIZE_TYPE=功能节点权限类型{
0=超级管理员初始化节点,
1=普通节点
}';

comment on column RM_FUNCTION_NODE.IS_LEAF is
'$RM_YES_NOT=是、否{
0=否,
1=是
}';

/*==============================================================*/
/* Index: IDXU_FUNCTIONNODE_TOTALCODE                           */
/*==============================================================*/
create unique index IDXU_FUNCTIONNODE_TOTALCODE on RM_FUNCTION_NODE (
   TOTAL_CODE ASC
);

/*==============================================================*/
/* Table: RM_PARTY                                              */
/*==============================================================*/
create table RM_PARTY 
(
   ID                   NUMBER(19)           not null,
   PARTY_TYPE_ID        NUMBER(19)           not null,
   OLD_PARTY_ID         VARCHAR2(50)         not null,
   NAME                 VARCHAR2(200)        not null,
   IS_INHERIT           CHAR(1)              not null,
   EMAIL                VARCHAR2(200),
   DESCRIPTION          VARCHAR2(1000),
   CUSTOM1              VARCHAR2(200),
   CUSTOM2              VARCHAR2(200),
   CUSTOM3              VARCHAR2(200),
   CUSTOM4              VARCHAR2(200),
   CUSTOM5              VARCHAR2(200),
   constraint PK_RM_PARTY primary key (ID)
)

/
ALTER TABLE RM_PARTY ADD (
USABLE_STATUS CHAR(1) DEFAULT '1',
MODIFY_DATE DATE DEFAULT SYSDATE,
MODIFY_IP VARCHAR2(45),
MODIFY_USER_ID NUMBER(19)
);

comment on column RM_PARTY.IS_INHERIT is
'$RM_YES_NOT=是、否{
0=否,
1=是
}';

/*==============================================================*/
/* Index: IDXU_PARTY_TYPEID_OLDPARTY                            */
/*==============================================================*/
create unique index IDXU_PARTY_TYPEID_OLDPARTY on RM_PARTY (
   PARTY_TYPE_ID ASC,
   OLD_PARTY_ID ASC
);

/*==============================================================*/
/* Table: RM_PARTY_RELATION                                     */
/*==============================================================*/
create table RM_PARTY_RELATION 
(
   ID                   NUMBER(19)           not null,
   PARTY_VIEW_ID        NUMBER(19)           not null,
   PARENT_PARTY_ID      NUMBER(19),
   CHILD_PARTY_ID       NUMBER(19)           not null,
   PARENT_PARTY_CODE    VARCHAR2(200)        not null,
   CHILD_PARTY_CODE     VARCHAR2(200)        not null,
   CHILD_PARTY_LEVEL    NUMBER(10),
   CHILD_IS_MAIN_RELATION CHAR(1),
   ORDER_CODE           VARCHAR2(50),
   PARENT_PARTY_NAME    VARCHAR2(200),
   CHILD_PARTY_NAME     VARCHAR2(200)        not null,
   PARENT_PARTY_TYPE_ID NUMBER(19),
   CHILD_PARTY_TYPE_ID  NUMBER(19)           not null,
   CHILD_IS_LEAF        CHAR(1),
   CUSTOM1              VARCHAR2(200),
   CUSTOM2              VARCHAR2(200),
   CUSTOM3              VARCHAR2(200),
   CUSTOM4              VARCHAR2(200),
   CUSTOM5              VARCHAR2(200),
   constraint PK_RM_PARTY_RELATION primary key (ID)
)

/
ALTER TABLE RM_PARTY_RELATION ADD (
USABLE_STATUS CHAR(1) DEFAULT '1',
MODIFY_DATE DATE DEFAULT SYSDATE,
MODIFY_IP VARCHAR2(45),
MODIFY_USER_ID NUMBER(19)
);

/*==============================================================*/
/* Index: IDX_PARTYREL_PARENTID_VIEW                            */
/*==============================================================*/
create index IDX_PARTYREL_PARENTID_VIEW on RM_PARTY_RELATION (
   PARENT_PARTY_ID ASC,
   PARTY_VIEW_ID ASC
);

/*==============================================================*/
/* Index: IDX_PARTYREL_CHILDID_VIEW                             */
/*==============================================================*/
create index IDX_PARTYREL_CHILDID_VIEW on RM_PARTY_RELATION (
   CHILD_PARTY_ID ASC,
   PARTY_VIEW_ID ASC
);

/*==============================================================*/
/* Index: IDX_PARTYREL_PARENTCODE_VIEW                          */
/*==============================================================*/
create index IDX_PARTYREL_PARENTCODE_VIEW on RM_PARTY_RELATION (
   PARENT_PARTY_CODE ASC,
   PARTY_VIEW_ID ASC
);

/*==============================================================*/
/* Index: IDXU_PARTYREL_CHILDCODE_VIEW                          */
/*==============================================================*/
create unique index IDXU_PARTYREL_CHILDCODE_VIEW on RM_PARTY_RELATION (
   CHILD_PARTY_CODE ASC,
   PARTY_VIEW_ID ASC
);

/*==============================================================*/
/* Table: RM_PARTY_ROLE                                         */
/*==============================================================*/
create table RM_PARTY_ROLE 
(
   ID                   NUMBER(19)           not null,
   OWNER_PARTY_ID       NUMBER(19)           not null,
   ROLE_ID              NUMBER(19)           not null,
   OWNER_ORG_ID         VARCHAR2(50),
   constraint PK_RM_PARTY_ROLE primary key (ID)
)

/
ALTER TABLE RM_PARTY_ROLE ADD (
USABLE_STATUS CHAR(1) DEFAULT '1',
MODIFY_DATE DATE DEFAULT SYSDATE,
MODIFY_IP VARCHAR2(45),
MODIFY_USER_ID NUMBER(19)
);

comment on column RM_PARTY_ROLE.OWNER_ORG_ID is
'关联角色时，指定所属的组织ID';

/*==============================================================*/
/* Index: IDXU_PARTY_ROLE_ORG                                   */
/*==============================================================*/
create unique index IDXU_PARTY_ROLE_ORG on RM_PARTY_ROLE (
   OWNER_PARTY_ID ASC,
   ROLE_ID ASC,
   OWNER_ORG_ID ASC
);

/*==============================================================*/
/* Table: RM_PARTY_TYPE                                         */
/*==============================================================*/
create table RM_PARTY_TYPE 
(
   ID                   NUMBER(19)           not null,
   BS_KEYWORD           VARCHAR2(200)        not null,
   NAME                 VARCHAR2(200)        not null,
   ICON                 VARCHAR2(200),
   DESCRIPTION          VARCHAR2(1000),
   IS_CUSTOM_ATTRIBUTE  CHAR(1)              not null,
   constraint PK_RM_PARTY_TYPE primary key (ID)
)

/
ALTER TABLE RM_PARTY_TYPE ADD (
USABLE_STATUS CHAR(1) DEFAULT '1',
MODIFY_DATE DATE DEFAULT SYSDATE,
MODIFY_IP VARCHAR2(45),
MODIFY_USER_ID NUMBER(19)
);

comment on column RM_PARTY_TYPE.IS_CUSTOM_ATTRIBUTE is
'$RM_YES_NOT=是、否{
0=否,
1=是
}';

/*==============================================================*/
/* Index: IDXU_PARTYTYPE_BK                                     */
/*==============================================================*/
create unique index IDXU_PARTYTYPE_BK on RM_PARTY_TYPE (
   BS_KEYWORD ASC
);

/*==============================================================*/
/* Table: RM_PARTY_TYPE_RELATION_RULE                           */
/*==============================================================*/
create table RM_PARTY_TYPE_RELATION_RULE 
(
   ID                   NUMBER(19)           not null,
   PARTY_VIEW_ID        NUMBER(19)           not null,
   PARENT_PARTY_TYPE_ID NUMBER(19),
   CHILD_PARTY_TYPE_ID  NUMBER(19)           not null,
   RULE_DESC            VARCHAR2(4000),
   IS_INSERT_CHILD_PARTY CHAR(1),
   constraint PK_RM_PARTY_TYPE_RELATION_RULE primary key (ID)
)

/
ALTER TABLE RM_PARTY_TYPE_RELATION_RULE ADD (
USABLE_STATUS CHAR(1) DEFAULT '1',
MODIFY_DATE DATE DEFAULT SYSDATE,
MODIFY_IP VARCHAR2(45),
MODIFY_USER_ID NUMBER(19)
);

comment on column RM_PARTY_TYPE_RELATION_RULE.IS_INSERT_CHILD_PARTY is
'$RM_YES_NOT=是、否{
0=否,
1=是
}';

/*==============================================================*/
/* Index: IDXU_VIEW_PARTY_CHILD                                 */
/*==============================================================*/
create unique index IDXU_VIEW_PARTY_CHILD on RM_PARTY_TYPE_RELATION_RULE (
   PARTY_VIEW_ID ASC,
   PARENT_PARTY_TYPE_ID ASC,
   CHILD_PARTY_TYPE_ID ASC
);

/*==============================================================*/
/* Table: RM_PARTY_VIEW                                         */
/*==============================================================*/
create table RM_PARTY_VIEW 
(
   ID                   NUMBER(19)           not null,
   BS_KEYWORD           VARCHAR2(50)         not null,
   NAME                 VARCHAR2(200)        not null,
   VIEW_DESC            VARCHAR2(4000),
   constraint PK_RM_PARTY_VIEW primary key (ID)
)

/
ALTER TABLE RM_PARTY_VIEW ADD (
USABLE_STATUS CHAR(1) DEFAULT '1',
MODIFY_DATE DATE DEFAULT SYSDATE,
MODIFY_IP VARCHAR2(45),
MODIFY_USER_ID NUMBER(19)
);

/*==============================================================*/
/* Index: IDXU_PARTYVIEW_BK                                     */
/*==============================================================*/
create unique index IDXU_PARTYVIEW_BK on RM_PARTY_VIEW (
   BS_KEYWORD ASC
);

/*==============================================================*/
/* Table: RM_ROLE                                               */
/*==============================================================*/
create table RM_ROLE 
(
   ID                   NUMBER(19)           not null,
   ROLE_CODE            VARCHAR2(200)        not null,
   NAME                 VARCHAR2(200)        not null,
   ENABLE_STATUS        CHAR(1)              not null,
   IS_SYSTEM_LEVEL      CHAR(1)              not null,
   OWNER_ORG_ID         VARCHAR2(50),
   IS_RECURSIVE         CHAR(1),
   MATRIX_CODE          VARCHAR2(1000),
   DESCRIPTION          VARCHAR2(1000),
   FUNCTION_PERMISSION  VARCHAR2(4000),
   DATA_PERMISSION      VARCHAR2(4000),
   constraint PK_RM_ROLE primary key (ID)
)

/
ALTER TABLE RM_ROLE ADD (
USABLE_STATUS CHAR(1) DEFAULT '1',
MODIFY_DATE DATE DEFAULT SYSDATE,
MODIFY_IP VARCHAR2(45),
MODIFY_USER_ID NUMBER(19)
);

comment on column RM_ROLE.ENABLE_STATUS is
'$RM_ENABLE_STATUS=启用、禁用{
1=启用,
0=禁用
}';

comment on column RM_ROLE.IS_SYSTEM_LEVEL is
'$RM_YES_NOT=是、否{
0=否,
1=是
}
如果不是全局角色，则OWNER_ORG_ID、IS_RECURSIVE必填';

comment on column RM_ROLE.IS_RECURSIVE is
'$RM_YES_NOT=是、否{
0=否,
1=是
}';

comment on column RM_ROLE.FUNCTION_PERMISSION is
'逗号分隔';

comment on column RM_ROLE.DATA_PERMISSION is
'逗号分隔';

/*==============================================================*/
/* Index: IDXU_ROLE_ROLECODE                                    */
/*==============================================================*/
create unique index IDXU_ROLE_ROLECODE on RM_ROLE (
   ROLE_CODE ASC
);

/*==============================================================*/
/* Table: RM_USER                                               */
/*==============================================================*/
create table RM_USER 
(
   ID                   NUMBER(19)           not null,
   NAME                 VARCHAR2(200)        not null,
   LOCK_STATUS          CHAR(1)              not null,
   LOGIN_ID             VARCHAR2(200)        not null,
   PASSWORD             VARCHAR2(200),
   AUTHEN_TYPE          VARCHAR2(200),
   ORGANIZATION_ID      VARCHAR2(200),
   EMPLOYEE_ID          VARCHAR2(50),
   EMAIL                VARCHAR2(200),
   ADMIN_TYPE           VARCHAR2(2),
   DESCRIPTION          VARCHAR2(1000),
   AGENT_STATUS         CHAR(1),
   LOGIN_STATUS         CHAR(1),
   LAST_LOGIN_DATE      DATE,
   LAST_LOGIN_IP        VARCHAR2(50),
   LOGIN_SUM            NUMBER(38),
   LAST_CUSTOM_CSS      VARCHAR2(200),
   IS_AFFIX             CHAR(1),
   FUNCTION_PERMISSION  VARCHAR2(4000),
   DATA_PERMISSION      VARCHAR2(4000),
   CUSTOM1              VARCHAR2(200),
   CUSTOM2              VARCHAR2(200),
   CUSTOM3              VARCHAR2(200),
   CUSTOM4              VARCHAR2(200),
   CUSTOM5              VARCHAR2(200),
   CUSTOM_XML           VARCHAR2(4000),
   constraint PK_RM_USER primary key (ID)
)

/
ALTER TABLE RM_USER ADD (
USABLE_STATUS CHAR(1) DEFAULT '1',
MODIFY_DATE DATE DEFAULT SYSDATE,
MODIFY_IP VARCHAR2(45),
MODIFY_USER_ID NUMBER(19)
);

comment on column RM_USER.LOCK_STATUS is
'$RM_LOCK_STATUS=激活、锁定状态{
0=锁定,
1=激活状态
}';

comment on column RM_USER.ADMIN_TYPE is
'$RM_ADMIN_TYPE=用户权限类型{
0=临时用户,
1=前台用户,
2=普通用户,
9=超级管理员(admin,一般用于数据初始化)
}';

comment on column RM_USER.AGENT_STATUS is
'$RM_AGENT_STATUS=代理状态{
0=未代理,
1=已代理
}';

comment on column RM_USER.FUNCTION_PERMISSION is
'逗号分隔';

comment on column RM_USER.DATA_PERMISSION is
'逗号分隔';

/*==============================================================*/
/* Index: IDXU_USER_LOGINID                                     */
/*==============================================================*/
create unique index IDXU_USER_LOGINID on RM_USER (
   LOGIN_ID ASC
);

/*==============================================================*/
/* Table: RM_USER_AGENT                                         */
/*==============================================================*/
create table RM_USER_AGENT 
(
   ID                   NUMBER(19)           not null,
   AGENTED_ID           NUMBER(19)           not null,
   AGENT_ID             NUMBER(19)           not null,
   BEGIN_TIME           DATE,
   END_TIME             DATE,
   AGENT_AUTHORIZE      VARCHAR2(4000),
   constraint PK_RM_USER_AGENT primary key (ID)
)

/
ALTER TABLE RM_USER_AGENT ADD (
USABLE_STATUS CHAR(1) DEFAULT '1',
MODIFY_DATE DATE DEFAULT SYSDATE,
MODIFY_IP VARCHAR2(45),
MODIFY_USER_ID NUMBER(19)
);

/*==============================================================*/
/* Table: RM_USER_ONLINE_RECORD                                 */
/*==============================================================*/
create table RM_USER_ONLINE_RECORD 
(
   ID                   NUMBER(19)           not null,
   USER_ID              NUMBER(19)           not null,
   LOGIN_TIME           DATE                 not null,
   CLUSTER_NODE_ID      VARCHAR2(50),
   LOGIN_SIGN           VARCHAR2(200),
   LOGIN_IP             VARCHAR2(50),
   LOGIN_UUID           VARCHAR2(50),
   LOGOUT_TIME          DATE,
   LOGOUT_TYPE          VARCHAR2(50),
   ONLINE_TIME          NUMBER(38),
   LAST_OPERATION       VARCHAR2(1000),
   constraint PK_RM_USER_ONLINE_RECORD primary key (ID)
)

/
ALTER TABLE RM_USER_ONLINE_RECORD ADD (
USABLE_STATUS CHAR(1) DEFAULT '1',
MODIFY_DATE DATE DEFAULT SYSDATE,
MODIFY_IP VARCHAR2(45),
MODIFY_USER_ID NUMBER(19)
);

comment on column RM_USER_ONLINE_RECORD.LOGOUT_TYPE is
'$RM_LOGOUT_TYPE=注销类型{
1=正常注销,
2=超时退出,
3=被强制登录替换,
4=被管理员强制退出
}';

alter table RM_AUTHORIZE_ACCESS_RULE
   add constraint FK_RM_AUTHO_REFERM_AU_RM_AUTHO foreign key (AUTHORIZE_ID)
      references RM_AUTHORIZE (ID);

alter table RM_AUTHORIZE_AFFIX
   add constraint FK_RM_ACCES_REFERENCE_RM_AUTH2 foreign key (AUTHORIZE_RESOURCE_RECORD_ID)
      references RM_AUTHORIZE_RESOURCE_RECORD (ID);

alter table RM_AUTHORIZE_AFFIX
   add constraint FK_AU_ACCES_REFERENCE_AU_RESO2 foreign key (AUTHORIZE_RESOURCE_ID)
      references RM_AUTHORIZE_RESOURCE (ID);

alter table RM_AUTHORIZE_AFFIX
   add constraint FK_AU_ACCES_REFERENCE_AU_ACCE2 foreign key (AUTHORIZE_AFFIX_RULE_ID)
      references RM_AUTHORIZE_AFFIX_RULE (ID);

alter table RM_AUTHORIZE_AFFIX_RULE
   add constraint FK_RM_ACCES_REFERENCE_RM_AUTH3 foreign key (AUTHORIZE_ID)
      references RM_AUTHORIZE (ID);

alter table RM_AUTHORIZE_CONFIG
   add constraint FK_RM_AUTHO_REFERENCE_RM_AUTH2 foreign key (AUTHORIZE_ID)
      references RM_AUTHORIZE (ID);

alter table RM_AUTHORIZE_CONFIG_AFFIX
   add constraint FK_RM_AUTHO_REFERENCE_RM_AUTH3 foreign key (AUTHORIZE_CONFIG_ID)
      references RM_AUTHORIZE_CONFIG (ID);

alter table RM_AUTHORIZE_CONFIG_AFFIX
   add constraint FK_RM_AUTHO_REFERENCE_RM_AUTH4 foreign key (AUTHORIZE_AFFIX_RULE_ID)
      references RM_AUTHORIZE_AFFIX_RULE (ID);

alter table RM_AUTHORIZE_RESOURCE
   add constraint FK_RM_AUTHO_REFERENCE_RM_AUTH6 foreign key (AUTHORIZE_ID)
      references RM_AUTHORIZE (ID);

alter table RM_AUTHORIZE_RESOURCE_RECORD
   add constraint FK_RM_AUTHO_REFERENCE_RM_AUTH5 foreign key (AUTHORIZE_RESOURCE_ID)
      references RM_AUTHORIZE_RESOURCE (ID);

alter table RM_AUTHORIZE_RESOURCE_RECORD
   add constraint FK_RM_AUTHO_REFERM_PA_RM_PARTY foreign key (PARTY_ID)
      references RM_PARTY (ID);

alter table RM_PARTY
   add constraint FK_RM_PARTY_REFERENCE_RM_PART2 foreign key (PARTY_TYPE_ID)
      references RM_PARTY_TYPE (ID);

alter table RM_PARTY_RELATION
   add constraint FK_RM_PARTY_REFERENCE_RM_PART5 foreign key (PARTY_VIEW_ID)
      references RM_PARTY_VIEW (ID);

alter table RM_PARTY_RELATION
   add constraint FK_RM_PARTY_REFERENCE_RM_PART3 foreign key (CHILD_PARTY_ID)
      references RM_PARTY (ID);

alter table RM_PARTY_RELATION
   add constraint FK_RM_PARTY_REFERENCE_RM_PART4 foreign key (PARENT_PARTY_ID)
      references RM_PARTY (ID);

alter table RM_PARTY_RELATION
   add constraint FK_RM_PARTY_REFERENCE_RM_PART9 foreign key (PARENT_PARTY_TYPE_ID)
      references RM_PARTY_TYPE (ID);

alter table RM_PARTY_RELATION
   add constraint FK_RM_PARTY_REFERENCE_RM_PARTY foreign key (CHILD_PARTY_TYPE_ID)
      references RM_PARTY_TYPE (ID);

alter table RM_PARTY_ROLE
   add constraint FK_RM_PARTY_REFERM_PA_RM_PARTY foreign key (OWNER_PARTY_ID)
      references RM_PARTY (ID);

alter table RM_PARTY_ROLE
   add constraint FK_RM_PARTY_REFERM_RO_RM_ROLE foreign key (ROLE_ID)
      references RM_ROLE (ID);

alter table RM_PARTY_TYPE_RELATION_RULE
   add constraint FK_RM_PARTY_REFERENCE_RM_PART7 foreign key (CHILD_PARTY_TYPE_ID)
      references RM_PARTY_TYPE (ID);

alter table RM_PARTY_TYPE_RELATION_RULE
   add constraint FK_RM_PARTY_REFERENCE_RM_PART8 foreign key (PARENT_PARTY_TYPE_ID)
      references RM_PARTY_TYPE (ID);

alter table RM_PARTY_TYPE_RELATION_RULE
   add constraint FK_RM_PARTY_REFERENCE_RM_PART6 foreign key (PARTY_VIEW_ID)
      references RM_PARTY_VIEW (ID);

alter table RM_USER_AGENT
   add constraint FK_RM_USER__REFERENCE_RM_USER2 foreign key (AGENT_ID)
      references RM_USER (ID);

alter table RM_USER_AGENT
   add constraint FK_RM_USER__REFERENCE_RM_USER foreign key (AGENTED_ID)
      references RM_USER (ID);

alter table RM_USER_ONLINE_RECORD
   add constraint FK_RM_USER__REFERM_US_RM_USER foreign key (USER_ID)
      references RM_USER (ID);

INSERT INTO RM_PARTY_TYPE VALUES (1000200800000000001, 'RM_USER', '用户', '', '', '0', '1', TO_DATE('2011-7-14 00:30:32', 'YYYY-MM-DD HH24:MI:SS'), '0:0:0:0:0:0:0:1', NULL);
INSERT INTO RM_PARTY_TYPE VALUES (1000200800000000002, 'RM_ROLE', '角色', '', '', '0', '1', TO_DATE('2011-7-14 00:31:02', 'YYYY-MM-DD HH24:MI:SS'), '0:0:0:0:0:0:0:1', NULL);

INSERT INTO RM_PARTY_VIEW VALUES (1000200700000000001, 'A', '行政视图', '', '1', TO_DATE('2011-7-14 00:31:26', 'YYYY-MM-DD HH24:MI:SS'), '0:0:0:0:0:0:0:1', NULL);

INSERT INTO RM_PARTY_TYPE_RELATION_RULE VALUES (1000200900000000001, 1000200700000000001, NULL, 1000200800000000001, '', '0', '1', TO_DATE('2011-7-14 00:31:38', 'YYYY-MM-DD HH24:MI:SS'), '0:0:0:0:0:0:0:1', NULL);
INSERT INTO RM_PARTY_TYPE_RELATION_RULE VALUES (1000200900000000002, 1000200700000000001, NULL, 1000200800000000002, '', '0', '1', TO_DATE('2011-7-14 00:31:45', 'YYYY-MM-DD HH24:MI:SS'), '0:0:0:0:0:0:0:1', NULL);

INSERT INTO RM_PARTY VALUES (1000201100000000001, 1000200800000000001, '1000201100000000001', 'admin', '0', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1', TO_DATE('2011-7-14 00:32:16', 'YYYY-MM-DD HH24:MI:SS'), '0:0:0:0:0:0:0:1', NULL);
INSERT INTO RM_USER VALUES (1000201100000000001, 'ADMIN', '_WJ4Cqip_aUGNAXXJp0kn19qfVKOOU85g6AtgJul6q0', 'admin', '111111', NULL, '', NULL, 'admin@quickbundle.org', '3', '', NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1', TO_DATE('2011-7-14 00:32:16', 'YYYY-MM-DD HH24:MI:SS'), '0:0:0:0:0:0:0:1', NULL);

INSERT INTO RM_PARTY_RELATION VALUES (1000201000000000001, 1000200700000000001, NULL, 1000201100000000001, 'A', 'A001', -1, '1', NULL, '', 'admin', NULL, 1000200800000000001, '1', NULL, NULL, NULL, NULL, NULL, '1', TO_DATE('2011-7-14 00:32:17', 'YYYY-MM-DD HH24:MI:SS'), '0:0:0:0:0:0:0:1', NULL);

INSERT INTO RM_AUTHORIZE VALUES (1000202400000000001, '功能权限', 'FUNCTION_NODE', '0', '', '', '', '1', '	', '', '1', TO_DATE('2011-11-4 15:11:09', 'YYYY-MM-DD HH24:MI:SS'), '0:0:0:0:0:0:0:1', 1000201100000000001);