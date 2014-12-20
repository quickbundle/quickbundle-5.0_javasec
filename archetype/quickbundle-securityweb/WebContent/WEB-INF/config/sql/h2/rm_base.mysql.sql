/*==============================================================*/
/* DBMS name:      MySQL 5.0_qb                                 */
/* Created on:     2011-05-11 22:45:06                          */
/*==============================================================*/

/*
drop table if exists RM_AFFIX;
*/
/* drop index IDXU_TYPE_KEY on RM_CODE_DATA */;
/*
drop table if exists RM_CODE_DATA;
*/
/* drop index IDXU_TYPEKEYWORD on RM_CODE_TYPE */;
/*
drop table if exists RM_CODE_TYPE;
*/
/*==============================================================*/
/* Table: RM_AFFIX                                              */
/*==============================================================*/
create table RM_AFFIX
(
   ID                   BIGINT not null,
   BS_KEYWORD           varchar(200) not null,
   RECORD_ID            varchar(50) not null,
   ORDER_NUMBER         int not null,
   TITLE                varchar(200),
   OLD_NAME             varchar(200) not null,
   SAVE_NAME            varchar(200) not null,
   SAVE_SIZE            bigint,
   MIME_TYPE            varchar(1000) not null comment '$RM_MINE_TYPE=内容类型{
	image/bmp=Bitmap Image,
	image/cgm=CGM Image,
	message/rfc822=EMail,
	application/eps=EPS Type PostScript,
	video/x-flv=Flash Video,
	image/gif=GIF Image
}',
   ENCODING             varchar(200),
   DESCRIPTION          varchar(4000),
   AUTHOR               varchar(200),
   primary key (ID)
)
;

ALTER TABLE RM_AFFIX ADD (
USABLE_STATUS CHAR(1) DEFAULT '1',
MODIFY_DATE DATETIME,
MODIFY_IP VARCHAR(45),
MODIFY_USER_ID BIGINT
);

/*==============================================================*/
/* Table: RM_CODE_DATA                                          */
/*==============================================================*/
create table RM_CODE_DATA
(
   ID                   BIGINT not null,
   CODE_TYPE_ID         BIGINT not null,
   DATA_KEY             varchar(200) not null,
   ENABLE_STATUS        char(1) not null comment '1--enable,
            0--disable',
   DATA_VALUE           varchar(4000),
   TOTAL_CODE           varchar(4000),
   REMARK               varchar(4000),
   primary key (ID)
)
;

ALTER TABLE RM_CODE_DATA ADD (
USABLE_STATUS CHAR(1) DEFAULT '1',
MODIFY_DATE DATETIME,
MODIFY_IP VARCHAR(45),
MODIFY_USER_ID BIGINT
);

/*==============================================================*/
/* Index: IDXU_TYPE_KEY                                         */
/*==============================================================*/
create unique index IDXU_TYPE_KEY on RM_CODE_DATA
(
   CODE_TYPE_ID,
   DATA_KEY
);

/*==============================================================*/
/* Table: RM_CODE_TYPE                                          */
/*==============================================================*/
create table RM_CODE_TYPE
(
   ID                   BIGINT not null,
   TYPE_KEYWORD         varchar(200) not null,
   NAME                 varchar(200),
   REMARK               varchar(4000),
   MULTI_VALUE_DESC     varchar(4000),
   primary key (ID)
)
;

ALTER TABLE RM_CODE_TYPE ADD (
USABLE_STATUS CHAR(1) DEFAULT '1',
MODIFY_DATE DATETIME,
MODIFY_IP VARCHAR(45),
MODIFY_USER_ID BIGINT
);

/*==============================================================*/
/* Index: IDXU_TYPEKEYWORD                                      */
/*==============================================================*/
create unique index IDXU_TYPEKEYWORD on RM_CODE_TYPE
(
   TYPE_KEYWORD
);

alter table RM_CODE_DATA add constraint FK_REFERM_CODETYPE_DATA foreign key (CODE_TYPE_ID)
      references RM_CODE_TYPE (ID) on delete restrict on update restrict;


create table RM_ID_POOL
(
   ID                   varchar(50) not null,
   VERSION              int not null,
   LAST_ID              bigint,
   primary key (ID)
);

create table RM_NODE_HEARTBEAT
(
   ID                   varchar(50) not null,
   VERSION              bigint not null,
   SHARDING_PREFIX		bigint,
   LAST_HEARTBEAT		DATETIME,
   BASE_URL				varchar(200),
   primary key (ID)
);