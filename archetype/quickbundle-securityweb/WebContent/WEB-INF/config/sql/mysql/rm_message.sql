drop table if exists RM_M_MESSAGE_USER;

drop table if exists RM_MESSAGE_RECEIVER;

drop table if exists RM_MESSAGE;


/*==============================================================*/
/* Table: RM_MESSAGE                                            */
/*==============================================================*/
create table RM_MESSAGE
(
   ID                   BIGINT not null,
   BIZ_KEYWORD          varchar(50) not null,
   SENDER_ID            BIGINT not null default 0,
   PARENT_MESSAGE_ID    BIGINT,
   OWNER_ORG_ID         varchar(50),
   TEMPLATE_ID          BIGINT,
   IS_AFFIX             char(1),
   RECORD_ID            varchar(50),
   MESSAGE_XML_CONTEXT  text,
   primary key (ID)
)
;

ALTER TABLE RM_MESSAGE ADD (
USABLE_STATUS CHAR(1) DEFAULT '1',
MODIFY_DATE DATETIME,
MODIFY_IP VARCHAR(45),
MODIFY_USER_ID BIGINT
);

/*==============================================================*/
/* Table: RM_MESSAGE_RECEIVER                                   */
/*==============================================================*/
create table RM_MESSAGE_RECEIVER
(
   ID                   BIGINT not null,
   MESSAGE_ID           BIGINT not null,
   RECEIVER_ID          BIGINT not null,
   IS_HANDLE            char(1) not null,
   HANDLE_DATE          datetime,
   HANDLE_RESULT        varchar(4000),
   primary key (ID)
)
;

ALTER TABLE RM_MESSAGE_RECEIVER ADD (
USABLE_STATUS CHAR(1) DEFAULT '1',
MODIFY_DATE DATETIME,
MODIFY_IP VARCHAR(45),
MODIFY_USER_ID BIGINT
);

alter table RM_MESSAGE add constraint FK_RM_MESSA_REFERENCE_RM_MESS2 foreign key (PARENT_MESSAGE_ID)
      references RM_MESSAGE (ID) on delete restrict on update restrict;

alter table RM_MESSAGE_RECEIVER add constraint FK_Reference_947 foreign key (MESSAGE_ID)
      references RM_MESSAGE (ID) on delete restrict on update restrict;


/*==============================================================*/
/* Table: RM_M_MESSAGE_USER                                                  */
/*==============================================================*/
create table RM_M_MESSAGE_USER
(
   MESSAGE_ID           BIGINT not null,
   USER_ID              BIGINT not null,
   primary key (MESSAGE_ID, USER_ID)
)
;

alter table RM_M_MESSAGE_USER add constraint FK_Reference_51 foreign key (MESSAGE_ID)
      references RM_MESSAGE (ID) on delete restrict on update restrict;

alter table RM_M_MESSAGE_USER add constraint FK_Reference_52 foreign key (USER_ID)
      references RM_USER (ID) on delete restrict on update restrict;

