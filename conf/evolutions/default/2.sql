# --- !Ups
 
insert into user values("info@1.com","far","far","1234",null,null);

# --- !Downs

delete from user where email="info@1.com"