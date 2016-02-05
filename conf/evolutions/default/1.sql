# --- !Ups
use sinaaps;
create table user (email varchar(255),f_name varchar(255),l_name varchar(255),password varchar(255),day int,month int,year int,address varchar(255),photo varchar(300),gender bool,primary key (email));




create table author(author_id int,  email varchar(50), photo varchar(300),primary key (author_id) );

create table author_translation(id int, author_id int,language varchar(50),f_name nvarchar(50),
 l_name nvarchar(50),description nvarchar(2000),born nvarchar(50), gender nvarchar(50), primary key (id), foreign key (author_id) references author (author_id));

create table blog_post(post_id int,title varchar(100), content  LongText,image  varchar(300),author_id int,topic  varchar(100),language  varchar(50),published timestamp,aux_img1 varchar(300),aux_img2 varchar(300),primary key (post_id), foreign key (author_id) references author (author_id));



create table book(book_id int, isbn varchar(15),page int, published timestamp, photo varchar(300),dimensions varchar(20),weight float, user_rating int, farsireads_rating int, primary key (book_id));

create table book_translation(id int, book_id int, language varchar(50), title nvarchar(200),summary mediumtext, author_id int, publication nvarchar(200),format nvarchar(30),primary key (id),foreign key (book_id) references book (book_id));

create table book_author(id int,book_id int,author_id int, primary key (id) ,foreign key (book_id) references book (book_id), foreign key (author_id) references author (author_id));

create table blog_comment(comment_id bigint, post_id int, user_email varchar(255), published timestamp, comment nvarchar(2000),likes int, primary key (comment_id),foreign key (post_id) references  blog_post (post_id) ,foreign key (user_email) references user (email));

create table book_review(
review_id bigint, 
book_id int, 
user_email varchar(255), 
published timestamp, 
review nvarchar(2000),
rating int, 
primary key (review_id),
foreign key (book_id) references  book (book_id),
foreign key (user_email) references  user (email)
);

create table topic(topic_id int,  en_name varchar(50), fa_name varchar(50), en_description LongText, fa_description LongText,primary key (topic_id) );
create table topic_book(id int,book_id int,topic_id int, primary key (id) ,foreign key (book_id) references book (book_id), foreign key (topic_id) references topic (topic_id));

create table event(
	event_id int,
	title varchar(100),
	events_date varchar(100),
	location varchar(100),
	speakers varchar(300),
	speaker_img1 varchar(300),
	speaker_img2 varchar(300),
	speaker_img3 varchar(300),
	hosted_by varchar(300),
	image varchar(300),
	description LongText,
	price varchar(100),
	language varchar(50),
	primary key (event_id)
);

create table event_guest (
email varchar(255),
name varchar(255),
contact_number varchar(100),
number_of_adults int,
number_of_kids int,
comment varchar(255),
primary key (email));

# --- !Downs

#--- !SET FOREIGN_KEY_CHECKS=0;


#--- !SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists user;
drop table if exists author;
drop table if exists author_translation;
drop table if exists blog_post;
drop table if exists book;
drop table if exists book_translation;
drop table if exists book_author;
drop table if exists blog_comment;
drop table if exists book_review;
drop table if exists topic;
drop table if exists topic_book;
drop table if exists event;
drop table if exists event_guest;
#--- !SET REFERENTIAL_INTEGRITY TRUE;


#--- !SET FOREIGN_KEY_CHECKS=1;

