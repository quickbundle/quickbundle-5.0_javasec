/*
drop table if exists RM_MESSAGE;

drop table if exists RM_MESSAGE_RECEIVER;
*/
/*==============================================================*/
/* Table: RM_MESSAGE                                            */
/*==============================================================*/
create table RM_MESSAGE
(
   ID                   INTEGER not null,
   BIZ_KEYWORD          varchar2(50) not null,
   SENDER_ID            INTEGER not null ,
   PARENT_MESSAGE_ID    INTEGER,
   OWNER_ORG_ID         varchar2(50),
   TEMPLATE_ID          INTEGER,
   IS_AFFIX             char(1),
   RECORD_ID            varchar2(50),
   MESSAGE_XML_CONTEXT  CLOB,
   primary key (ID)
)
;

ALTER TABLE RM_MESSAGE ADD (
USABLE_STATUS CHAR(1) DEFAULT '1',
MODIFY_DATE DATE,
MODIFY_IP varchar2(45),
MODIFY_USER_ID INTEGER
);

/*==============================================================*/
/* Table: RM_MESSAGE_RECEIVER                                   */
/*==============================================================*/
create table RM_MESSAGE_RECEIVER
(
   ID                   INTEGER not null,
   MESSAGE_ID           INTEGER not null,
   RECEIVER_ID          INTEGER not null,
   IS_HANDLE            char(1) not null,
   HANDLE_DATE          DATE,
   HANDLE_RESULT        varchar2(4000),
   primary key (ID)
)
;

ALTER TABLE RM_MESSAGE_RECEIVER ADD (
USABLE_STATUS CHAR(1) DEFAULT '1',
MODIFY_DATE DATE,
MODIFY_IP varchar2(45),
MODIFY_USER_ID INTEGER
);


alter table RM_MESSAGE add constraint FK_RM_MESSA_REFERENCE_RM_MESS2 foreign key (PARENT_MESSAGE_ID)
      references RM_MESSAGE (ID) ;

alter table RM_MESSAGE_RECEIVER add constraint FK_Reference_947 foreign key (MESSAGE_ID)
      references RM_MESSAGE (ID) ;
