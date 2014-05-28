# --- !Ups
 
insert into user values("info@1.com","far","far","1234",null,null,null);
insert into author values(1,null);

insert into author_translation values(1,1,"english","John","Piper","John Piper is the founder of DesiringGod.org and the senior pastor of Bethlehem Baptist Church for 33 years. He is author of more than 50 books.","Minnesota","Male");



insert into blog_post values(0,"neccesity of Assurence in Faith","1) In order to be saved and spend eternity enjoying God’s presence a person must persevere in faith to the end (1 Corinthians 15:2; Colossians 1:22,23; Luke 8:11-15; Matthew 10:22; Mark 13:13; Matthew 24:13; Hebrews 3:14; Romans 11:20-22; 2 Timothy 2:11,12; Revelation 2:7,10,11,17,25,26; 3:5,11,12,21).

2) An obedient lifestyle which springs from faith is necessary for final salvation (Hebrews 12:14; Romans 8:13; Galatians 5:19-21; Ephesians 5:5; 1 Corinthians 6:10; 1 John 3:4-10,14; 4:20; John 8:31; Luke 10:28; Matthew 6:14,15; 18:35; Genesis 18:19; 22:16-17; 26:45; 2 Timothy 2:19). This is not to say that God demands perfection (Philippians 3:12,13; 1 John 1:8-10; Matthew 6:12).

3) A person who has truly been born again will persevere in faith. God sees to that (1 Peter 1:5; cf. also John 6:37-39; 10:26-30; Philippians 1:6; 1 Thessalonians 3:11-13; 5:24; Romans 8:1-4,28-30; Ephesians 1:4,5).

4) In Scripture we are told of people who appear to be Christians and yet fall away (Luke 8:13,14; Hebrews 6:4-5; 10:26-29). John says that their falling away proves they were never Christians in the first place (1 John 2:19; cf. also Hebrews 6:9; John 8:31; Colossians 1:22,23; Hebrews 3:6,14).

5) Should we entertain the possibility that we might be those who fall away? The answer is yes, if we are persisting in attitudes/actions that spring from unbelief.

The apostle Paul entertained the possibility that he might be disqualified if he was not careful how he “ran the race” (1 Corinthians 9:27); and he commanded the arrogant Corinthians to entertain the idea that they might be those who fall away (1 Corinthians 10:12—read this carefully in the context of 9:24-10:13).

Paul also gives warnings in Romans 11:20-22 and Galatians 5:2-21; Ephesians 5:5.

The writer of Hebrews gives similar warnings: Hebrews 2:1-3; 3:12-14; 4:1,11; 6:4-6; 10:26-31; 12:25.

6) We are commanded to examine ourselves to see whether or not we are in the faith (2 Corinthians 13:5; 2 Peter 1:10).

7) We are expected to enjoy and commanded to experience the assurance of salvation (1 John 5:13; Hebrews 6:11; 10:22).

8) The biblical teaching of the assurance of salvation must be seen in the context of God’s terrible threats to those who fall into persistent unbelief, and the context of God’s glorious promises to those who trust him.

The way in which we persevere in faith and thereby maintain assurance of salvation is by taking seriously the glorious promises and the terrible threats found in Scripture (cf. Hebrews 10:19-31; also Deuteronomy 28).

Pastor John & Pastor Tom","http://image.com",1,"Faith","English");

update blog_post set published="12.01.2012" where post_id=0;

insert into book values(1,"9781563205453",1256, "06.09.2013" ,"http://biblia.com/bibleimg","8.8*5.5*1.3",0.77);

insert into book_translation values(1,1,"english","Persian Bible(Tafsiri)","This is the holy Word of God, translated into Farsi and wrapped in a contemporary, green hardcover. If you are in the advanced stages of learning Farsi, one who speaks it yourself, or a missionary to a region where Farsi is the primary language, this Bible is perfect for you.",1,"biblica","Hard Cover");

insert into book_author values(0,1,1);

# --- !Downs

delete from user;
delete from author_translation;
delete from author;
delete from blog_post;
delete from book;
delete from book_translation;
delete from book_author;