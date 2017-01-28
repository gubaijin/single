/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2017/1/28 11:32:11                           */
/*==============================================================*/


drop table if exists stock;

/*==============================================================*/
/* Table: stock                                                 */
/*==============================================================*/
create table stock
(
   id                   integer not null,
   code                 varchar(10),
   symbol               varchar(10),
   name                 varchar(20),
   score                integer,
   trade                varchar(10),
   pricechange          varchar(10),
   changepercent        varchar(10),
   buy                  varchar(10),
   sell                 varchar(10),
   settlement           varchar(10),
   open                 varchar(10),
   high                 varchar(10),
   low                  varchar(10),
   volume               integer,
   amount               integer,
   ticktime             varchar(10),
   create_time          datetime,
   update_time          datetime,
   primary key (id)
);

