/*==============================================================*/
/* DBMS name:      MySQL 5.0_qb                                 */
/* Created on:     2011-05-11 22:53:24                          */
/*==============================================================*/


/* drop index IDXU_AUTHORIZE_BK on RM_AUTHORIZE */;
/*
drop table if exists RM_AUTHORIZE;

drop table if exists RM_AUTHORIZE_ACCESS_RULE;

drop table if exists RM_AUTHORIZE_AFFIX;

drop table if exists RM_AUTHORIZE_AFFIX_RULE;

drop table if exists RM_AUTHORIZE_CONFIG;

drop table if exists RM_AUTHORIZE_CONFIG_AFFIX;
*/
/* drop index IDXU_AUTHRES_AUTHID_OLDRES on RM_AUTHORIZE_RESOURCE */;
/*
drop table if exists RM_AUTHORIZE_RESOURCE;
*/
/* drop index IDX_AUTHORIZE_RESOURCE_PARTYID on RM_AUTHORIZE_RESOURCE_RECORD */;
/*
drop table if exists RM_AUTHORIZE_RESOURCE_RECORD;
*/
/* drop index IDXU_FUNCTIONNODE_TOTALCODE on RM_FUNCTION_NODE */;
/*
drop table if exists RM_FUNCTION_NODE;
*/
/* drop index IDXU_PARTY_TYPEID_OLDPARTY on RM_PARTY */;
/*
drop table if exists RM_PARTY;
*/
/* drop index IDXU_PARTYREL_CHILDCODE_VIEW on RM_PARTY_RELATION */;

/* drop index IDX_PARTYREL_PARENTCODE_VIEW on RM_PARTY_RELATION */;

/* drop index IDX_PARTYREL_CHILDID_VIEW on RM_PARTY_RELATION */;

/* drop index IDX_PARTYREL_PARENTID_VIEW on RM_PARTY_RELATION */;
/*
drop table if exists RM_PARTY_RELATION;
*/
/* drop index IDXU_PARTY_ROLE_ORG on RM_PARTY_ROLE */;
/*
drop table if exists RM_PARTY_ROLE;
*/
/* drop index IDXU_PARTYTYPE_BK on RM_PARTY_TYPE */;
/*
drop table if exists RM_PARTY_TYPE;
*/
/* drop index IDXU_VIEW_PARTY_CHILD on RM_PARTY_TYPE_RELATION_RULE */;
/*
drop table if exists RM_PARTY_TYPE_RELATION_RULE;
*/
/* drop index IDXU_PARTYVIEW_BK on RM_PARTY_VIEW */;
/*
drop table if exists RM_PARTY_VIEW;
*/
/* drop index IDXU_ROLE_ROLECODE on RM_ROLE */;
/*
drop table if exists RM_ROLE;
*/
/* drop index IDXU_USER_LOGINID on RM_USER */;
/*
drop table if exists RM_USER;

drop table if exists RM_USER_AGENT;

drop table if exists RM_USER_ONLINE_RECORD;
*/
/*==============================================================*/
/* Table: RM_AUTHORIZE                                          */
/*==============================================================*/
create table RM_AUTHORIZE
(
   ID                   BIGINT not null,
   NAME                 varchar(200) not null,
   BS_KEYWORD           varchar(200) not null,
   IS_ALONE_TABLE       char(1) not null comment '$RM_YES_NOT=是、否{
            0=否,
            1=是
            }',
   AUTHORIZE_RESOURCE_TABLE_NAME varchar(50),
   AUTHORIZE_RESREC_TABLE_NAME varchar(50),
   AUTHORIZE_AFFIX_TABLE_NAME varchar(50),
   SETTIING_OPTION      char(1) comment '$RM_OPTION_TYPE=权限配置{
            1=单选,
            2=多选,
            3=定制
            }',
   CUSTOM_CODE          varchar(4000),
   DESCRIPTION          varchar(1000),
   primary key (ID)
)
;

ALTER TABLE RM_AUTHORIZE ADD (
USABLE_STATUS CHAR(1) DEFAULT '1',
MODIFY_DATE DATETIME,
MODIFY_IP VARCHAR(45),
MODIFY_USER_ID BIGINT
);

/*==============================================================*/
/* Index: IDXU_AUTHORIZE_BK                                     */
/*==============================================================*/
create unique index IDXU_AUTHORIZE_BK on RM_AUTHORIZE
(
   BS_KEYWORD
);

/*==============================================================*/
/* Table: RM_AUTHORIZE_ACCESS_RULE                              */
/*==============================================================*/
create table RM_AUTHORIZE_ACCESS_RULE
(
   ID                   BIGINT not null,
   AUTHORIZE_ID         BIGINT not null,
   POSITION_NUMBER      int not null,
   NAME                 varchar(200) not null,
   CODE                 varchar(50),
   DEFAULT_CHECKED      char(1) comment '$RM_YES_NOT=是、否{
            0=否,
            1=是
            }',
   DESCRIPTION          varchar(1000),
   primary key (ID)
)
;

ALTER TABLE RM_AUTHORIZE_ACCESS_RULE ADD (
USABLE_STATUS CHAR(1) DEFAULT '1',
MODIFY_DATE DATETIME,
MODIFY_IP VARCHAR(45),
MODIFY_USER_ID BIGINT
);

/*==============================================================*/
/* Table: RM_AUTHORIZE_AFFIX                                    */
/*==============================================================*/
create table RM_AUTHORIZE_AFFIX
(
   ID                   BIGINT not null,
   AUTHORIZE_AFFIX_RULE_ID BIGINT not null,
   AUTHORIZE_RESOURCE_RECORD_ID BIGINT,
   AUTHORIZE_RESOURCE_ID BIGINT,
   DATA_VALUE           varchar(4000),
   primary key (ID)
)
;

ALTER TABLE RM_AUTHORIZE_AFFIX ADD (
USABLE_STATUS CHAR(1) DEFAULT '1',
MODIFY_DATE DATETIME,
MODIFY_IP VARCHAR(45),
MODIFY_USER_ID BIGINT
);

/*==============================================================*/
/* Table: RM_AUTHORIZE_AFFIX_RULE                               */
/*==============================================================*/
create table RM_AUTHORIZE_AFFIX_RULE
(
   ID                   BIGINT not null,
   AUTHORIZE_ID         BIGINT not null,
   NAME                 varchar(200) not null,
   CODE                 varchar(50),
   AFFIX_DATA_TRANSLATE varchar(4000),
   DESCRIPTION          varchar(1000),
   primary key (ID)
)
;

ALTER TABLE RM_AUTHORIZE_AFFIX_RULE ADD (
USABLE_STATUS CHAR(1) DEFAULT '1',
MODIFY_DATE DATETIME,
MODIFY_IP VARCHAR(45),
MODIFY_USER_ID BIGINT
);

/*==============================================================*/
/* Table: RM_AUTHORIZE_CONFIG                                   */
/*==============================================================*/
create table RM_AUTHORIZE_CONFIG
(
   ID                   BIGINT not null,
   AUTHORIZE_ID         BIGINT not null,
   RECORD_ID            varchar(50) not null,
   DATA_VALUE           varchar(4000),
   DESCRIPTION          varchar(1000),
   primary key (ID)
)
;

ALTER TABLE RM_AUTHORIZE_CONFIG ADD (
USABLE_STATUS CHAR(1) DEFAULT '1',
MODIFY_DATE DATETIME,
MODIFY_IP VARCHAR(45),
MODIFY_USER_ID BIGINT
);

/*==============================================================*/
/* Table: RM_AUTHORIZE_CONFIG_AFFIX                             */
/*==============================================================*/
create table RM_AUTHORIZE_CONFIG_AFFIX
(
   ID                   BIGINT not null,
   AUTHORIZE_AFFIX_RULE_ID BIGINT not null,
   AUTHORIZE_CONFIG_ID  BIGINT not null,
   DATA_VALUE           varchar(4000),
   primary key (ID)
)
;

ALTER TABLE RM_AUTHORIZE_CONFIG_AFFIX ADD (
USABLE_STATUS CHAR(1) DEFAULT '1',
MODIFY_DATE DATETIME,
MODIFY_IP VARCHAR(45),
MODIFY_USER_ID BIGINT
);

/*==============================================================*/
/* Table: RM_AUTHORIZE_RESOURCE                                 */
/*==============================================================*/
create table RM_AUTHORIZE_RESOURCE
(
   ID                   BIGINT not null,
   AUTHORIZE_ID         BIGINT not null,
   OLD_RESOURCE_ID      varchar(200) not null,
   DEFAULT_ACCESS       char(1) not null comment '$RM_YES_NOT=是、否{
            0=否,
            1=是
            }',
   DEFAULT_IS_AFFIX_DATA char(1) not null comment '$RM_YES_NOT=是、否{
            0=否,
            1=是
            }',
   DEFAULT_IS_RECURSIVE char(1) not null comment '$RM_YES_NOT=是、否{
            0=否,
            1=是
            }',
   DEFAULT_ACCESS_TYPE  varchar(1000) not null,
   TOTAL_CODE           varchar(200) comment '如果资源是树，可用编码体系存储树形关系',
   NAME                 varchar(200),
   primary key (ID)
)
;

ALTER TABLE RM_AUTHORIZE_RESOURCE ADD (
USABLE_STATUS CHAR(1) DEFAULT '1',
MODIFY_DATE DATETIME,
MODIFY_IP VARCHAR(45),
MODIFY_USER_ID BIGINT
);

/*==============================================================*/
/* Index: IDXU_AUTHRES_AUTHID_OLDRES                            */
/*==============================================================*/
create unique index IDXU_AUTHRES_AUTHID_OLDRES on RM_AUTHORIZE_RESOURCE
(
   OLD_RESOURCE_ID,
   AUTHORIZE_ID
);

/*==============================================================*/
/* Table: RM_AUTHORIZE_RESOURCE_RECORD                          */
/*==============================================================*/
create table RM_AUTHORIZE_RESOURCE_RECORD
(
   ID                   BIGINT not null,
   AUTHORIZE_RESOURCE_ID BIGINT not null,
   PARTY_ID             BIGINT not null,
   AUTHORIZE_STATUS     char(1) not null comment '$RM_AUTHORIZE_STATUS=授权情况{
            0=拒绝,
            1=允许
            }',
   IS_AFFIX_DATA        char(1) not null comment '$RM_YES_NOT=是、否{
            0=否,
            1=是
            }',
   IS_RECURSIVE         char(1) not null comment '$RM_YES_NOT=是、否{
            0=否,
            1=是
            }',
   ACCESS_TYPE          varchar(1000) not null,
   primary key (ID)
)
;

ALTER TABLE RM_AUTHORIZE_RESOURCE_RECORD ADD (
USABLE_STATUS CHAR(1) DEFAULT '1',
MODIFY_DATE DATETIME,
MODIFY_IP VARCHAR(45),
MODIFY_USER_ID BIGINT
);

/*==============================================================*/
/* Index: IDX_AUTHORIZE_RESOURCE_PARTYID                        */
/*==============================================================*/
create unique index IDX_AUTHORIZE_RESOURCE_PARTYID on RM_AUTHORIZE_RESOURCE_RECORD
(
   AUTHORIZE_RESOURCE_ID,
   PARTY_ID
);

/*==============================================================*/
/* Table: RM_FUNCTION_NODE                                      */
/*==============================================================*/
create table RM_FUNCTION_NODE
(
   ID                   BIGINT not null,
   NODE_TYPE            varchar(2) not null comment '$RM_FUNCTION_NODE_TYPE=功能节点类型{
            1=子系统、一级模块,
            2=子模块、功能,
            3=页面按钮
            }',
   FUNCTION_PROPERTY    varchar(50) not null comment '$FUNCTION_PROPERTY=功能性质{
            0=可执行功能节点,
            1=虚功能节点,
            2=可执行功能帧
            }',
   NAME                 varchar(200) not null,
   ENABLE_STATUS        varchar(50) not null comment '$RM_ENABLE_STATUS=启用、禁用{
            0=禁用,
            1=启用
            }',
   TOTAL_CODE           varchar(200),
   ORDER_CODE           varchar(50),
   FUNCNODE_AUTHORIZE_TYPE char(1) comment '$RM_FUNCNODE_AUTHORIZE_TYPE=功能节点权限类型{
            0=超级管理员初始化节点,
            1=普通节点
            }',
   DESCRIPTION          varchar(1000),
   DATA_VALUE           varchar(1000),
   PATTERN_VALUE        varchar(1000),
   IS_LEAF              char(1) comment '$RM_YES_NOT=是、否{
            0=否,
            1=是
            }',
   ICON                 varchar(200),
   HELP_NAME            varchar(200),
   primary key (ID)
)
;

ALTER TABLE RM_FUNCTION_NODE ADD (
USABLE_STATUS CHAR(1) DEFAULT '1',
MODIFY_DATE DATETIME,
MODIFY_IP VARCHAR(45),
MODIFY_USER_ID BIGINT
);

/*==============================================================*/
/* Index: IDXU_FUNCTIONNODE_TOTALCODE                           */
/*==============================================================*/
create unique index IDXU_FUNCTIONNODE_TOTALCODE on RM_FUNCTION_NODE
(
   TOTAL_CODE
);

/*==============================================================*/
/* Table: RM_PARTY                                              */
/*==============================================================*/
create table RM_PARTY
(
   ID                   BIGINT not null,
   PARTY_TYPE_ID        BIGINT not null,
   OLD_PARTY_ID         varchar(50) not null,
   NAME                 varchar(200) not null,
   IS_INHERIT           char(1) not null comment '$RM_YES_NOT=是、否{
            0=否,
            1=是
            }',
   EMAIL                varchar(200),
   DESCRIPTION          varchar(1000),
   CUSTOM1              varchar(200),
   CUSTOM2              varchar(200),
   CUSTOM3              varchar(200),
   CUSTOM4              varchar(200),
   CUSTOM5              varchar(200),
   primary key (ID)
)
;

ALTER TABLE RM_PARTY ADD (
USABLE_STATUS CHAR(1) DEFAULT '1',
MODIFY_DATE DATETIME,
MODIFY_IP VARCHAR(45),
MODIFY_USER_ID BIGINT
);

/*==============================================================*/
/* Index: IDXU_PARTY_TYPEID_OLDPARTY                            */
/*==============================================================*/
create unique index IDXU_PARTY_TYPEID_OLDPARTY on RM_PARTY
(
   PARTY_TYPE_ID,
   OLD_PARTY_ID
);

/*==============================================================*/
/* Table: RM_PARTY_RELATION                                     */
/*==============================================================*/
create table RM_PARTY_RELATION
(
   ID                   BIGINT not null,
   PARTY_VIEW_ID        BIGINT not null,
   PARENT_PARTY_ID      BIGINT,
   CHILD_PARTY_ID       BIGINT not null,
   PARENT_PARTY_CODE    varchar(200) not null,
   CHILD_PARTY_CODE     varchar(200) not null,
   CHILD_PARTY_LEVEL    int,
   CHILD_IS_MAIN_RELATION char(1),
   ORDER_CODE           varchar(50),
   PARENT_PARTY_NAME    varchar(200),
   CHILD_PARTY_NAME     varchar(200) not null,
   PARENT_PARTY_TYPE_ID BIGINT,
   CHILD_PARTY_TYPE_ID  BIGINT not null,
   CHILD_IS_LEAF        char(1),
   CUSTOM1              varchar(200),
   CUSTOM2              varchar(200),
   CUSTOM3              varchar(200),
   CUSTOM4              varchar(200),
   CUSTOM5              varchar(200),
   primary key (ID)
)
;

ALTER TABLE RM_PARTY_RELATION ADD (
USABLE_STATUS CHAR(1) DEFAULT '1',
MODIFY_DATE DATETIME,
MODIFY_IP VARCHAR(45),
MODIFY_USER_ID BIGINT
);

/*==============================================================*/
/* Index: IDX_PARTYREL_PARENTID_VIEW                            */
/*==============================================================*/
create index IDX_PARTYREL_PARENTID_VIEW on RM_PARTY_RELATION
(
   PARENT_PARTY_ID,
   PARTY_VIEW_ID
);

/*==============================================================*/
/* Index: IDX_PARTYREL_CHILDID_VIEW                             */
/*==============================================================*/
create index IDX_PARTYREL_CHILDID_VIEW on RM_PARTY_RELATION
(
   CHILD_PARTY_ID,
   PARTY_VIEW_ID
);

/*==============================================================*/
/* Index: IDX_PARTYREL_PARENTCODE_VIEW                          */
/*==============================================================*/
create index IDX_PARTYREL_PARENTCODE_VIEW on RM_PARTY_RELATION
(
   PARENT_PARTY_CODE,
   PARTY_VIEW_ID
);

/*==============================================================*/
/* Index: IDXU_PARTYREL_CHILDCODE_VIEW                          */
/*==============================================================*/
create unique index IDXU_PARTYREL_CHILDCODE_VIEW on RM_PARTY_RELATION
(
   CHILD_PARTY_CODE,
   PARTY_VIEW_ID
);

/*==============================================================*/
/* Table: RM_PARTY_ROLE                                         */
/*==============================================================*/
create table RM_PARTY_ROLE
(
   ID                   BIGINT not null,
   OWNER_PARTY_ID       BIGINT not null,
   ROLE_ID              BIGINT not null,
   OWNER_ORG_ID         varchar(50) comment '关联角色时，指定所属的组织ID',
   primary key (ID)
)
;

ALTER TABLE RM_PARTY_ROLE ADD (
USABLE_STATUS CHAR(1) DEFAULT '1',
MODIFY_DATE DATETIME,
MODIFY_IP VARCHAR(45),
MODIFY_USER_ID BIGINT
);

/*==============================================================*/
/* Index: IDXU_PARTY_ROLE_ORG                                   */
/*==============================================================*/
create unique index IDXU_PARTY_ROLE_ORG on RM_PARTY_ROLE
(
   OWNER_PARTY_ID,
   ROLE_ID,
   OWNER_ORG_ID
);

/*==============================================================*/
/* Table: RM_PARTY_TYPE                                         */
/*==============================================================*/
create table RM_PARTY_TYPE
(
   ID                   BIGINT not null,
   BS_KEYWORD           varchar(200) not null,
   NAME                 varchar(200) not null,
   ICON                 varchar(200),
   DESCRIPTION          varchar(1000),
   IS_CUSTOM_ATTRIBUTE  char(1) not null comment '$RM_YES_NOT=是、否{
            0=否,
            1=是
            }',
   primary key (ID)
)
;

ALTER TABLE RM_PARTY_TYPE ADD (
USABLE_STATUS CHAR(1) DEFAULT '1',
MODIFY_DATE DATETIME,
MODIFY_IP VARCHAR(45),
MODIFY_USER_ID BIGINT
);

/*==============================================================*/
/* Index: IDXU_PARTYTYPE_BK                                     */
/*==============================================================*/
create unique index IDXU_PARTYTYPE_BK on RM_PARTY_TYPE
(
   BS_KEYWORD
);

/*==============================================================*/
/* Table: RM_PARTY_TYPE_RELATION_RULE                           */
/*==============================================================*/
create table RM_PARTY_TYPE_RELATION_RULE
(
   ID                   BIGINT not null,
   PARTY_VIEW_ID        BIGINT not null,
   PARENT_PARTY_TYPE_ID BIGINT,
   CHILD_PARTY_TYPE_ID  BIGINT not null,
   RULE_DESC            varchar(4000),
   IS_INSERT_CHILD_PARTY char(1) comment '$RM_YES_NOT=是、否{
            0=否,
            1=是
            }',
   primary key (ID)
)
;

ALTER TABLE RM_PARTY_TYPE_RELATION_RULE ADD (
USABLE_STATUS CHAR(1) DEFAULT '1',
MODIFY_DATE DATETIME,
MODIFY_IP VARCHAR(45),
MODIFY_USER_ID BIGINT
);

/*==============================================================*/
/* Index: IDXU_VIEW_PARTY_CHILD                                 */
/*==============================================================*/
create unique index IDXU_VIEW_PARTY_CHILD on RM_PARTY_TYPE_RELATION_RULE
(
   PARTY_VIEW_ID,
   PARENT_PARTY_TYPE_ID,
   CHILD_PARTY_TYPE_ID
);

/*==============================================================*/
/* Table: RM_PARTY_VIEW                                         */
/*==============================================================*/
create table RM_PARTY_VIEW
(
   ID                   BIGINT not null,
   BS_KEYWORD           varchar(50) not null,
   NAME                 varchar(200) not null,
   VIEW_DESC            varchar(4000),
   primary key (ID)
)
;

ALTER TABLE RM_PARTY_VIEW ADD (
USABLE_STATUS CHAR(1) DEFAULT '1',
MODIFY_DATE DATETIME,
MODIFY_IP VARCHAR(45),
MODIFY_USER_ID BIGINT
);

/*==============================================================*/
/* Index: IDXU_PARTYVIEW_BK                                     */
/*==============================================================*/
create unique index IDXU_PARTYVIEW_BK on RM_PARTY_VIEW
(
   BS_KEYWORD
);

/*==============================================================*/
/* Table: RM_ROLE                                               */
/*==============================================================*/
create table RM_ROLE
(
   ID                   BIGINT not null,
   ROLE_CODE            varchar(200) not null,
   NAME                 varchar(200) not null,
   ENABLE_STATUS        char(1) not null comment '$RM_ENABLE_STATUS=启用、禁用{
            1=启用,
            0=禁用
            }',
   IS_SYSTEM_LEVEL      char(1) not null comment '$RM_YES_NOT=是、否{
            0=否,
            1=是
            }
            如果不是全局角色，则OWNER_ORG_ID、IS_RECURSIVE必填',
   OWNER_ORG_ID         varchar(50),
   IS_RECURSIVE         char(1) comment '$RM_YES_NOT=是、否{
            0=否,
            1=是
            }',
   MATRIX_CODE          varchar(1000),
   DESCRIPTION          varchar(1000),
   FUNCTION_PERMISSION  varchar(4000) comment '逗号分隔',
   DATA_PERMISSION      varchar(4000) comment '逗号分隔',
   primary key (ID)
)
;

ALTER TABLE RM_ROLE ADD (
USABLE_STATUS CHAR(1) DEFAULT '1',
MODIFY_DATE DATETIME,
MODIFY_IP VARCHAR(45),
MODIFY_USER_ID BIGINT
);

/*==============================================================*/
/* Index: IDXU_ROLE_ROLECODE                                    */
/*==============================================================*/
create unique index IDXU_ROLE_ROLECODE on RM_ROLE
(
   ROLE_CODE
);

/*==============================================================*/
/* Table: RM_USER                                               */
/*==============================================================*/
create table RM_USER
(
   ID                   BIGINT not null,
   NAME                 varchar(200) not null,
   LOCK_STATUS          char(1) not null comment '$RM_LOCK_STATUS=激活、锁定状态{
            0=锁定,
            1=激活状态
            }',
   LOGIN_ID             varchar(200) not null,
   PASSWORD             varchar(200),
   AUTHEN_TYPE          varchar(200),
   ORGANIZATION_ID      varchar(200),
   EMPLOYEE_ID          varchar(50),
   EMAIL                varchar(200),
   ADMIN_TYPE           varchar(2) comment '$RM_ADMIN_TYPE=用户权限类型{
            0=临时用户,
            1=前台用户,
            2=普通用户,
            9=超级管理员(admin,一般用于数据初始化)
            }',
   DESCRIPTION          varchar(1000),
   AGENT_STATUS         char(1) comment '$RM_AGENT_STATUS=代理状态{
            0=未代理,
            1=已代理
            }',
   LOGIN_STATUS         char(1),
   LAST_LOGIN_DATE      datetime,
   LAST_LOGIN_IP        varchar(50),
   LOGIN_SUM            bigint,
   LAST_CUSTOM_CSS      varchar(200),
   IS_AFFIX             char(1),
   FUNCTION_PERMISSION  varchar(4000) comment '逗号分隔',
   DATA_PERMISSION      varchar(4000) comment '逗号分隔',
   CUSTOM1              varchar(200),
   CUSTOM2              varchar(200),
   CUSTOM3              varchar(200),
   CUSTOM4              varchar(200),
   CUSTOM5              varchar(200),
   CUSTOM_XML           varchar(4000),
   primary key (ID)
)
;

ALTER TABLE RM_USER ADD (
USABLE_STATUS CHAR(1) DEFAULT '1',
MODIFY_DATE DATETIME,
MODIFY_IP VARCHAR(45),
MODIFY_USER_ID BIGINT
);

/*==============================================================*/
/* Index: IDXU_USER_LOGINID                                     */
/*==============================================================*/
create unique index IDXU_USER_LOGINID on RM_USER
(
   LOGIN_ID
);

/*==============================================================*/
/* Table: RM_USER_AGENT                                         */
/*==============================================================*/
create table RM_USER_AGENT
(
   ID                   BIGINT not null,
   AGENTED_ID           BIGINT not null,
   AGENT_ID             BIGINT not null,
   BEGIN_TIME           datetime,
   END_TIME             datetime,
   AGENT_AUTHORIZE      varchar(4000),
   primary key (ID)
)
;

ALTER TABLE RM_USER_AGENT ADD (
USABLE_STATUS CHAR(1) DEFAULT '1',
MODIFY_DATE DATETIME,
MODIFY_IP VARCHAR(45),
MODIFY_USER_ID BIGINT
);

/*==============================================================*/
/* Table: RM_USER_ONLINE_RECORD                                 */
/*==============================================================*/
create table RM_USER_ONLINE_RECORD
(
   ID                   BIGINT not null,
   USER_ID              BIGINT not null,
   LOGIN_TIME           datetime not null,
   CLUSTER_NODE_ID      varchar(50),
   LOGIN_SIGN           varchar(200),
   LOGIN_IP             varchar(50),
   LOGIN_UUID           varchar(50),
   LOGOUT_TIME          datetime,
   LOGOUT_TYPE          varchar(50) comment '$RM_LOGOUT_TYPE=注销类型{
            1=正常注销,
            2=超时退出,
            3=被强制登录替换,
            4=被管理员强制退出
            }',
   ONLINE_TIME          bigint,
   LAST_OPERATION       varchar(1000),
   primary key (ID)
)
;

ALTER TABLE RM_USER_ONLINE_RECORD ADD (
USABLE_STATUS CHAR(1) DEFAULT '1',
MODIFY_DATE DATETIME,
MODIFY_IP VARCHAR(45),
MODIFY_USER_ID BIGINT
);

alter table RM_AUTHORIZE_ACCESS_RULE add constraint FK_REFERM_AUTHORIZE_ACCESS foreign key (AUTHORIZE_ID)
      references RM_AUTHORIZE (ID) on delete restrict on update restrict;

alter table RM_AUTHORIZE_AFFIX add constraint FK_RM_ACCES_REFERENCE_RM_AUTH2 foreign key (AUTHORIZE_RESOURCE_RECORD_ID)
      references RM_AUTHORIZE_RESOURCE_RECORD (ID) on delete restrict on update restrict;

alter table RM_AUTHORIZE_AFFIX add constraint FK_AU_ACCES_REFERENCE_AU_RESO2 foreign key (AUTHORIZE_RESOURCE_ID)
      references RM_AUTHORIZE_RESOURCE (ID) on delete restrict on update restrict;

alter table RM_AUTHORIZE_AFFIX add constraint FK_AU_ACCES_REFERENCE_AU_ACCE2 foreign key (AUTHORIZE_AFFIX_RULE_ID)
      references RM_AUTHORIZE_AFFIX_RULE (ID) on delete restrict on update restrict;

alter table RM_AUTHORIZE_AFFIX_RULE add constraint FK_RM_ACCES_REFERENCE_RM_AUTH3 foreign key (AUTHORIZE_ID)
      references RM_AUTHORIZE (ID) on delete restrict on update restrict;

alter table RM_AUTHORIZE_CONFIG add constraint FK_RM_AUTHO_REFERENCE_RM_AUTH2 foreign key (AUTHORIZE_ID)
      references RM_AUTHORIZE (ID) on delete restrict on update restrict;

alter table RM_AUTHORIZE_CONFIG_AFFIX add constraint FK_RM_AUTHO_REFERENCE_RM_AUTH3 foreign key (AUTHORIZE_CONFIG_ID)
      references RM_AUTHORIZE_CONFIG (ID) on delete restrict on update restrict;

alter table RM_AUTHORIZE_CONFIG_AFFIX add constraint FK_RM_AUTHO_REFERENCE_RM_AUTH4 foreign key (AUTHORIZE_AFFIX_RULE_ID)
      references RM_AUTHORIZE_AFFIX_RULE (ID) on delete restrict on update restrict;

alter table RM_AUTHORIZE_RESOURCE add constraint FK_RM_AUTHO_REFERENCE_RM_AUTH6 foreign key (AUTHORIZE_ID)
      references RM_AUTHORIZE (ID) on delete restrict on update restrict;

alter table RM_AUTHORIZE_RESOURCE_RECORD add constraint FK_RM_AUTHO_REFERENCE_RM_AUTH5 foreign key (AUTHORIZE_RESOURCE_ID)
      references RM_AUTHORIZE_RESOURCE (ID) on delete restrict on update restrict;

alter table RM_AUTHORIZE_RESOURCE_RECORD add constraint FK_REFERM_PARTY_AUTHORIZERECORD foreign key (PARTY_ID)
      references RM_PARTY (ID) on delete restrict on update restrict;

alter table RM_PARTY add constraint FK_RM_PARTY_REFERENCE_RM_PART2 foreign key (PARTY_TYPE_ID)
      references RM_PARTY_TYPE (ID) on delete restrict on update restrict;

alter table RM_PARTY_RELATION add constraint FK_RM_PARTY_REFERENCE_RM_PART5 foreign key (PARTY_VIEW_ID)
      references RM_PARTY_VIEW (ID) on delete restrict on update restrict;

alter table RM_PARTY_RELATION add constraint FK_RM_PARTY_REFERENCE_RM_PART3 foreign key (CHILD_PARTY_ID)
      references RM_PARTY (ID) on delete restrict on update restrict;

alter table RM_PARTY_RELATION add constraint FK_RM_PARTY_REFERENCE_RM_PART4 foreign key (PARENT_PARTY_ID)
      references RM_PARTY (ID) on delete restrict on update restrict;

alter table RM_PARTY_RELATION add constraint FK_RM_PARTY_REFERENCE_RM_PART9 foreign key (PARENT_PARTY_TYPE_ID)
      references RM_PARTY_TYPE (ID) on delete restrict on update restrict;

alter table RM_PARTY_RELATION add constraint FK_Reference_941 foreign key (CHILD_PARTY_TYPE_ID)
      references RM_PARTY_TYPE (ID) on delete restrict on update restrict;

alter table RM_PARTY_ROLE add constraint FK_REFERM_PARTY_PARTYROLE foreign key (OWNER_PARTY_ID)
      references RM_PARTY (ID) on delete restrict on update restrict;

alter table RM_PARTY_ROLE add constraint FK_REFERM_ROLE_PARTYROLE foreign key (ROLE_ID)
      references RM_ROLE (ID) on delete restrict on update restrict;

alter table RM_PARTY_TYPE_RELATION_RULE add constraint FK_RM_PARTY_REFERENCE_RM_PART7 foreign key (CHILD_PARTY_TYPE_ID)
      references RM_PARTY_TYPE (ID) on delete restrict on update restrict;

alter table RM_PARTY_TYPE_RELATION_RULE add constraint FK_RM_PARTY_REFERENCE_RM_PART8 foreign key (PARENT_PARTY_TYPE_ID)
      references RM_PARTY_TYPE (ID) on delete restrict on update restrict;

alter table RM_PARTY_TYPE_RELATION_RULE add constraint FK_RM_PARTY_REFERENCE_RM_PART6 foreign key (PARTY_VIEW_ID)
      references RM_PARTY_VIEW (ID) on delete restrict on update restrict;

alter table RM_USER_AGENT add constraint FK_Reference_955 foreign key (AGENT_ID)
      references RM_USER (ID) on delete restrict on update restrict;

alter table RM_USER_AGENT add constraint FK_Reference_956 foreign key (AGENTED_ID)
      references RM_USER (ID) on delete restrict on update restrict;

alter table RM_USER_ONLINE_RECORD add constraint FK_REFERM_USER_ONLINERECORD foreign key (USER_ID)
      references RM_USER (ID) on delete restrict on update restrict;

INSERT INTO RM_PARTY_TYPE VALUES (1000200800000000001, 'RM_USER', '用户', '', '', '0', '1', '2011-7-14 00:30:32', '0:0:0:0:0:0:0:1', NULL);
INSERT INTO RM_PARTY_TYPE VALUES (1000200800000000002, 'RM_ROLE', '角色', '', '', '0', '1', '2011-7-14 00:31:02', '0:0:0:0:0:0:0:1', NULL);

INSERT INTO RM_PARTY_VIEW VALUES (1000200700000000001, 'A', '行政视图', '', '1', '2011-7-14 00:31:26', '0:0:0:0:0:0:0:1', NULL);

INSERT INTO RM_PARTY_TYPE_RELATION_RULE VALUES (1000200900000000001, 1000200700000000001, NULL, 1000200800000000001, '', '0', '1', '2011-7-14 00:31:38', '0:0:0:0:0:0:0:1', NULL);
INSERT INTO RM_PARTY_TYPE_RELATION_RULE VALUES (1000200900000000002, 1000200700000000001, NULL, 1000200800000000002, '', '0', '1', '2011-7-14 00:31:45', '0:0:0:0:0:0:0:1', NULL);

INSERT INTO RM_PARTY VALUES (1000201100000000001, 1000200800000000001, '1000201100000000001', 'admin', '0', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1', '2011-7-14 00:32:16', '0:0:0:0:0:0:0:1', NULL);
INSERT INTO RM_USER VALUES (1000201100000000001, 'ADMIN', '_WJ4Cqip_aUGNAXXJp0kn19qfVKOOU85g6AtgJul6q0', 'admin', '111111', NULL, '', NULL, 'admin@quickbundle.org', '3', '', NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1', '2011-7-14 00:32:16', '0:0:0:0:0:0:0:1', NULL);

INSERT INTO RM_PARTY_RELATION VALUES (1000201000000000001, 1000200700000000001, NULL, 1000201100000000001, 'A', 'A001', -1, '1', NULL, '', 'admin', NULL, 1000200800000000001, '1', NULL, NULL, NULL, NULL, NULL, '1', '2011-7-14 00:32:17', '0:0:0:0:0:0:0:1', NULL);

INSERT INTO RM_AUTHORIZE VALUES (1000202400000000001, '功能权限', 'FUNCTION_NODE', '0', '', '', '', '1', '	', '', '1', '2011-11-4 15:11:09', '0:0:0:0:0:0:0:1', 1000201100000000001);