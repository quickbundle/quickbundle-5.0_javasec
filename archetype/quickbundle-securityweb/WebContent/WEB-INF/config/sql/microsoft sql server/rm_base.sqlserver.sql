/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2005                    */
/* Created on:     2011-06-26 23:53:48                          */
/*==============================================================*/

/*
if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('RM_CODE_DATA') and o.name = 'FK_RM_CODE__REFERM_CO_RM_CODE_')
alter table RM_CODE_DATA go
   drop constraint FK_RM_CODE__REFERM_CO_RM_CODE_
go

if exists (select 1
            from  sysobjects
           where  id = object_id('RM_AFFIX')
            and   type = 'U')
   drop table RM_AFFIX
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('RM_CODE_DATA')
            and   name  = 'IDXU_TYPE_KEY'
            and   indid > 0
            and   indid < 255)
   drop index RM_CODE_DATA.IDXU_TYPE_KEY
go

if exists (select 1
            from  sysobjects
           where  id = object_id('RM_CODE_DATA')
            and   type = 'U')
   drop table RM_CODE_DATA
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('RM_CODE_TYPE')
            and   name  = 'IDXU_TYPEKEYWORD'
            and   indid > 0
            and   indid < 255)
   drop index RM_CODE_TYPE.IDXU_TYPEKEYWORD
go

if exists (select 1
            from  sysobjects
           where  id = object_id('RM_CODE_TYPE')
            and   type = 'U')
   drop table RM_CODE_TYPE
go
*/

/*==============================================================*/
/* Table: RM_AFFIX                                              */
/*==============================================================*/
create table RM_AFFIX (
   ID                   bigint               not null,
   BS_KEYWORD           varchar(200)         not null,
   RECORD_ID            varchar(50)          not null,
   ORDER_NUMBER         int                  not null,
   TITLE                varchar(200)         null,
   OLD_NAME             varchar(200)         not null,
   SAVE_NAME            varchar(200)         not null,
   SAVE_SIZE            bigint               null,
   MIME_TYPE            varchar(1000)        not null,
   ENCODING             varchar(200)         null,
   DESCRIPTION          varchar(4000)        null,
   AUTHOR               varchar(200)         null,
   constraint PK_RM_AFFIX primary key nonclustered (ID)
)

go
ALTER TABLE RM_AFFIX ADD
USABLE_STATUS CHAR(1) DEFAULT '1',
MODIFY_DATE DATETIME,
MODIFY_IP VARCHAR(45),
MODIFY_USER_ID BIGINT
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
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
   text/html=HTML,
   application/vnd.oasis.opendocument.text-web=HTML Document Template,
   text/calendar=iCalendar File,
   image/ief=IEF Image,
   application/java=Java Class,
   application/x-javascript=Java Script,
   image/jp2=JPEG 2000 Image,
   image/jpeg=JPEG Image,
   application/json=JSON,
   application/x-latex=Latex,
   application/x-troff-man=Man Page,
   text/mediawiki=MediaWiki Markup,
   application/vnd.excel=Microsoft Excel,
   application/vnd.openxmlformats-officedocument.spreadsheetml.sheet=Microsoft Excel 2007,
   application/vnd.powerpoint=Microsoft PowerPoint,
   application/vnd.openxmlformats-officedocument.presentationml.presentation=Microsoft PowerPoint 2007,
   application/vnd.ms-project=Microsoft Project,
   application/visio=Microsoft Visio,
   application/msword=Microsoft Word,
   application/vnd.openxmlformats-officedocument.wordprocessingml.document=Microsoft Word 2007,
   audio/x-mpeg=MPEG Audio,
   video/mpeg=MPEG Video,
   video/mpeg2=MPEG2 Video,
   video/mp4=MPEG4 Video,
   video/x-ms-wma=MS Streaming Audio,
   video/x-ms-asf=MS Streaming Video,
   video/x-ms-wmv=MS Streaming Video,
   video/x-msvideo=MS Video,
   application/octet-stream=Octet Stream,
   application/vnd.oasis.opendocument.chart=OpenDocument Chart,
   application/vnd.oasis.opendocument.database=OpenDocument Database,
   application/vnd.oasis.opendocument.graphics=OpenDocument Drawing,
   application/vnd.oasis.opendocument.graphics-template=OpenDocument Drawing Template,
   application/vnd.oasis.opendocument.formula=OpenDocument Formula,
   application/vnd.oasis.opendocument.image=OpenDocument Image,
   application/vnd.oasis.opendocument.text-master=OpenDocument Master Document,
   application/vnd.oasis.opendocument.presentation=OpenDocument Presentation,
   application/vnd.oasis.opendocument.presentation-template=OpenDocument Presentation Template,
   application/vnd.oasis.opendocument.spreadsheet=OpenDocument Spreadsheet,
   application/vnd.oasis.opendocument.spreadsheet-template=OpenDocument Spreadsheet Template,
   application/vnd.oasis.opendocument.text=OpenDocument Text (OpenOffice 2.0),
   application/vnd.oasis.opendocument.text-template=OpenDocument Text Template,
   application/vnd.sun.xml.calc=OpenOffice 1.0/StarOffice6.0 Calc 6.0,
   application/vnd.sun.xml.draw=OpenOffice 1.0/StarOffice6.0 Draw 6.0,
   application/vnd.sun.xml.impress=OpenOffice 1.0/StarOffice6.0 Impress 6.0,
   application/vnd.sun.xml.writer=OpenOffice 1.0/StarOffice6.0 Writer 6.0,
   application/vnd.ms-outlook=Outlook MSG,
   image/x-portable-pixmap=Pixmap Image,
   text/plain=Plain Text,
   image/png=PNG Image,
   image/x-portable-bitmap=Portable Bitmap,
   application/postscript=PostScript,
   application/remote-printing=Printer Text File,
   video/quicktime=Quicktime Video,
   video/x-rad-screenplay=RAD Screen Display,
   image/x-cmu-raster=Raster Image,
   image/x-rgb=RGB Image,
   text/richtext=Rich Text,
   application/rtf=Rich Text Format,
   image/svg=Scalable Vector Graphics Image,
   video/x-sgi-movie=SGI Video,
   text/sgml=SGML,
   application/sgml=SGML,
   application/x-sh=Shell Script,
   application/x-shockwave-flash=Shockwave Flash,
   application/vnd.stardivision.chart=StaChart 5.x,
   application/vnd.stardivision.calc=StarCalc 5.x,
   application/vnd.stardivision.draw=StarDraw 5.x,
   application/vnd.stardivision.impress=StarImpress 5.x,
   application/vnd.stardivision.impress-packed=StarImpress Packed 5.x,
   application/vnd.stardivision.math=StarMath 5.x,
   application/vnd.stardivision.writer=StarWriter 5.x,
   application/vnd.stardivision.writer-global=StarWriter 5.x global,
   text/css=Style Sheet,
   text/tab-separated-values=Tab Separated Values,
   application/x-tar=Tarball,
   application/x-tex=Tex,
   application/x-texinfo=Tex Info,
   image/tiff=TIFF Image,
   x-world/x-vrml=VRML,
   audio/x-wav=WAV Audio,
   application/wordperfect=WordPerfect,
   image/x-xbitmap=XBitmap Image,
   application/xhtml+xml=XHTML,
   text/xml=XML,
   image/x-xpixmap=XPixmap Image,
   image/x-xwindowdump=XWindow Dump,
   application/x-compress=Z Compress,
   application/zip=ZIP
   }',
   'user', @CurrentUser, 'table', 'RM_AFFIX', 'column', 'MIME_TYPE'
go

/*==============================================================*/
/* Table: RM_CODE_DATA                                          */
/*==============================================================*/
create table RM_CODE_DATA (
   ID                   bigint               not null,
   CODE_TYPE_ID         bigint               not null,
   DATA_KEY             varchar(200)         not null,
   ENABLE_STATUS        char(1)              not null,
   DATA_VALUE           varchar(4000)        null,
   TOTAL_CODE           varchar(4000)        null,
   REMARK               varchar(4000)        null,
   constraint PK_RM_CODE_DATA primary key nonclustered (ID)
)

go
ALTER TABLE RM_CODE_DATA ADD
USABLE_STATUS CHAR(1) DEFAULT '1',
MODIFY_DATE DATETIME,
MODIFY_IP VARCHAR(45),
MODIFY_USER_ID BIGINT
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '1--enable,
   0--disable',
   'user', @CurrentUser, 'table', 'RM_CODE_DATA', 'column', 'ENABLE_STATUS'
go

/*==============================================================*/
/* Index: IDXU_TYPE_KEY                                         */
/*==============================================================*/
create unique index IDXU_TYPE_KEY on RM_CODE_DATA (
CODE_TYPE_ID ASC,
DATA_KEY ASC
)
go

/*==============================================================*/
/* Table: RM_CODE_TYPE                                          */
/*==============================================================*/
create table RM_CODE_TYPE (
   ID                   bigint               not null,
   TYPE_KEYWORD         varchar(200)         not null,
   NAME                 varchar(200)         null,
   REMARK               varchar(4000)        null,
   MULTI_VALUE_DESC     varchar(4000)        null,
   constraint PK_RM_CODE_TYPE primary key nonclustered (ID)
)

go
ALTER TABLE RM_CODE_TYPE ADD
USABLE_STATUS CHAR(1) DEFAULT '1',
MODIFY_DATE DATETIME,
MODIFY_IP VARCHAR(45),
MODIFY_USER_ID BIGINT
go

/*==============================================================*/
/* Index: IDXU_TYPEKEYWORD                                      */
/*==============================================================*/
create unique index IDXU_TYPEKEYWORD on RM_CODE_TYPE (
TYPE_KEYWORD ASC
)
go

alter table RM_CODE_DATA
   add constraint FK_RM_CODE__REFERM_CO_RM_CODE_ foreign key (CODE_TYPE_ID)
      references RM_CODE_TYPE (ID)
go

create table RM_ID_POOL
(
   ID                   varchar(50) not null,
   VERSION              int not null,
   LAST_ID              bigint,
   constraint PK_RM_ID_POOL primary key nonclustered (ID)
)
go

create table RM_NODE_HEARTBEAT
(
   ID                   varchar(50) not null,
   VERSION              bigint not null,
   SHARDING_PREFIX		bigint,
   LAST_HEARTBEAT		DATETIME,
   BASE_URL				varchar(200),
   constraint PK_RM_NODE_HEARTBEAT primary key (ID)
)
go