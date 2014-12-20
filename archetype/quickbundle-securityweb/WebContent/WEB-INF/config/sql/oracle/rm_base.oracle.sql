/*==============================================================*/
/* DBMS name:      ORACLE Version 11g_qb                        */
/* Created on:     2011-05-12 00:15:59                          */
/*==============================================================*/

/*
alter table RM_CODE_DATA
   drop constraint FK_RM_CODE__REFERM_CO_RM_CODE_;

drop table RM_AFFIX cascade constraints;

drop index IDXU_TYPE_KEY;

drop table RM_CODE_DATA cascade constraints;

drop index IDXU_TYPEKEYWORD;

drop table RM_CODE_TYPE cascade constraints;

alter table RM_QUICK_QUERY_DATA
   drop constraint FK_RM_QUICK_REFERENCE_RM_QUICK;

drop index IDXU_REFER_INDEX_BK;

drop table RM_QUICK_QUERY cascade constraints;

drop index IDXU_QQDATA_RESOURCENAMEQQ;

drop index IDXU_QQDATA_INDEXNAMEQQ;

drop table RM_QUICK_QUERY_DATA cascade constraints;

*/
/*==============================================================*/
/* Table: RM_AFFIX                                              */
/*==============================================================*/
create table RM_AFFIX 
(
   ID                   NUMBER(19)           not null,
   BS_KEYWORD           VARCHAR2(200)        not null,
   RECORD_ID            VARCHAR2(50)         not null,
   ORDER_NUMBER         NUMBER(10)           not null,
   TITLE                VARCHAR2(200),
   OLD_NAME             VARCHAR2(200)        not null,
   SAVE_NAME            VARCHAR2(200)        not null,
   SAVE_SIZE            NUMBER(38),
   MIME_TYPE            VARCHAR2(1000)       not null,
   ENCODING             VARCHAR2(200),
   DESCRIPTION          VARCHAR2(4000),
   AUTHOR               VARCHAR2(200),
   constraint PK_RM_AFFIX primary key (ID)
)

/
ALTER TABLE RM_AFFIX ADD (
USABLE_STATUS CHAR(1) DEFAULT '1',
MODIFY_DATE DATE DEFAULT SYSDATE,
MODIFY_IP VARCHAR2(45),
MODIFY_USER_ID NUMBER(19)
);

comment on column RM_AFFIX.MIME_TYPE is
'$RM_MINE_TYPE=内容类型{
application/vnd.adobe.xdp+xml=Adobe Acrobat XML Data Package,
application/vnd.adobe.air-application-installer-package+zip=Adobe AIR,
application/framemaker=Adobe FrameMaker,
application/pagemaker=Adobe PageMaker,
application/pdf=Adobe PDF Document,
application/photoshop=Adobe Photoshop,
audio/x-aiff=AIFF Audio,
application/acp=Alfresco Content Package,
image/x-portable-anymap=Anymap Image,
image/x-dwg=AutoCAD Drawing,
image/x-dwt=AutoCAD Template,
audio/basic=Basic Audio,
image/bmp=Bitmap Image,
image/cgm=CGM Image,
message/rfc822=EMail,
application/eps=EPS Type PostScript,
video/x-flv=Flash Video,
image/gif=GIF Image,
image/x-portable-graymap=Greymap Image,
application/x-gzip=GZIP,
application/x-gtar=GZIP Tarball,
text/html=HTML
}';

/*==============================================================*/
/* Table: RM_CODE_DATA                                          */
/*==============================================================*/
create table RM_CODE_DATA 
(
   ID                   NUMBER(19)           not null,
   CODE_TYPE_ID         NUMBER(19)           not null,
   DATA_KEY             VARCHAR2(200)        not null,
   ENABLE_STATUS        CHAR(1)              not null,
   DATA_VALUE           VARCHAR2(4000),
   TOTAL_CODE           VARCHAR2(4000),
   REMARK               VARCHAR2(4000),
   constraint PK_RM_CODE_DATA primary key (ID)
)

/
ALTER TABLE RM_CODE_DATA ADD (
USABLE_STATUS CHAR(1) DEFAULT '1',
MODIFY_DATE DATE DEFAULT SYSDATE,
MODIFY_IP VARCHAR2(45),
MODIFY_USER_ID NUMBER(19)
);

comment on column RM_CODE_DATA.ENABLE_STATUS is
'1--enable,
0--disable';

/*==============================================================*/
/* Index: IDXU_TYPE_KEY                                         */
/*==============================================================*/
create unique index IDXU_TYPE_KEY on RM_CODE_DATA (
   CODE_TYPE_ID ASC,
   DATA_KEY ASC
);

/*==============================================================*/
/* Table: RM_CODE_TYPE                                          */
/*==============================================================*/
create table RM_CODE_TYPE 
(
   ID                   NUMBER(19)           not null,
   TYPE_KEYWORD         VARCHAR2(200)        not null,
   NAME                 VARCHAR2(200),
   REMARK               VARCHAR2(4000),
   MULTI_VALUE_DESC     VARCHAR2(4000),
   constraint PK_RM_CODE_TYPE primary key (ID)
)

/
ALTER TABLE RM_CODE_TYPE ADD (
USABLE_STATUS CHAR(1) DEFAULT '1',
MODIFY_DATE DATE DEFAULT SYSDATE,
MODIFY_IP VARCHAR2(45),
MODIFY_USER_ID NUMBER(19)
);

/*==============================================================*/
/* Index: IDXU_TYPEKEYWORD                                      */
/*==============================================================*/
create unique index IDXU_TYPEKEYWORD on RM_CODE_TYPE (
   TYPE_KEYWORD ASC
);

alter table RM_CODE_DATA
   add constraint FK_RM_CODE__REFERM_CO_RM_CODE_ foreign key (CODE_TYPE_ID)
      references RM_CODE_TYPE (ID);


/*==============================================================*/
/* Table: RM_QUICK_QUERY                                        */
/*==============================================================*/
create table RM_QUICK_QUERY 
(
   ID                   NUMBER(19)           not null,
   NAME                 VARCHAR2(200)        not null,
   BS_KEYWORD           VARCHAR2(200)        not null,
   IS_ALONE_TABLE       CHAR(1)              not null,
   QQDATA_TABLE_NAME    VARCHAR2(50),
   OLD_RESOURCE_ID_TYPE VARCHAR2(2)          not null,
   CUSTOM_CODE          VARCHAR2(4000)       not null,
   DESCRIPTION          VARCHAR2(1000),
   constraint PK_RM_QUICK_QUERY primary key (ID)
)

/
ALTER TABLE RM_QUICK_QUERY ADD (
USABLE_STATUS CHAR(1) DEFAULT '1',
MODIFY_DATE DATE DEFAULT SYSDATE,
MODIFY_IP VARCHAR2(45),
MODIFY_USER_ID NUMBER(19)
);

comment on column RM_QUICK_QUERY.IS_ALONE_TABLE is
'$RM_YES_NOT=是、否{
0=否,
1=是
}';

comment on column RM_QUICK_QUERY.OLD_RESOURCE_ID_TYPE is
'$RM_OLD_RESOURCE_ID_TYPE=资源原始ID类型{
1=字符,
2=数字
}';

/*==============================================================*/
/* Index: IDXU_REFER_INDEX_BK                                   */
/*==============================================================*/
create unique index IDXU_REFER_INDEX_BK on RM_QUICK_QUERY (
   BS_KEYWORD ASC
);

/*==============================================================*/
/* Table: RM_QUICK_QUERY_DATA                                   */
/*==============================================================*/
create table RM_QUICK_QUERY_DATA 
(
   ID                   NUMBER(19)           not null,
   QUICK_QUERY_ID       NUMBER(19)           not null,
   OLD_RESOURCE_ID_CHAR VARCHAR2(50),
   OLD_RESOURCE_ID_NUMBER NUMBER(38),
   OLD_RESOURCE_NAME    VARCHAR2(200)        not null,
   INDEX_VALUE          VARCHAR2(50)         not null,
   constraint PK_RM_QUICK_QUERY_DATA primary key (ID)
)

/
ALTER TABLE RM_QUICK_QUERY_DATA ADD (
USABLE_STATUS CHAR(1) DEFAULT '1',
MODIFY_DATE DATE DEFAULT SYSDATE,
MODIFY_IP VARCHAR2(45),
MODIFY_USER_ID NUMBER(19)
);

/*==============================================================*/
/* Index: IDXU_QQDATA_INDEXNAMEQQ                               */
/*==============================================================*/
create index IDXU_QQDATA_INDEXNAMEQQ on RM_QUICK_QUERY_DATA (
   INDEX_VALUE ASC,
   QUICK_QUERY_ID ASC
);

/*==============================================================*/
/* Index: IDXU_QQDATA_RESOURCENAMEQQ                            */
/*==============================================================*/
create index IDXU_QQDATA_RESOURCENAMEQQ on RM_QUICK_QUERY_DATA (
   OLD_RESOURCE_NAME ASC,
   QUICK_QUERY_ID ASC
);

alter table RM_QUICK_QUERY_DATA
   add constraint FK_RM_QUICK_REFERENCE_RM_QUICK foreign key (QUICK_QUERY_ID)
      references RM_QUICK_QUERY (ID);


create table RM_ID_POOL
(
   ID                   VARCHAR2(50) not null,
   VERSION              NUMBER(19) not null,
   LAST_ID              NUMBER(19),
   constraint PK_RM_ID_POOL primary key (ID)
);

create table RM_NODE_HEARTBEAT
(
   ID                   VARCHAR2(50) not null,
   VERSION              NUMBER(19) not null,
   SHARDING_PREFIX		NUMBER(19),
   LAST_HEARTBEAT		DATE,
   BASE_URL				VARCHAR2(200),
   constraint PK_RM_NODE_HEARTBEAT primary key (ID)
);