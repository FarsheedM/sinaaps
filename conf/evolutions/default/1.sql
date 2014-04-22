# --- !Ups
use sinaaps;
create table user (
  email                     varchar(255) not null,
  f_name                    varchar(255),
  l_name                    varchar(255),
  password                  varchar(255),
  birthdate                 datetime,
  address                   varchar(255),
  constraint pk_user primary key (email))
;







# --- !Downs

SET FOREIGN_KEY_CHECKS=0;


drop table if exists user;


SET FOREIGN_KEY_CHECKS=1;

