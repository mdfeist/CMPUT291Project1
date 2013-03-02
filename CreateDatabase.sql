drop table purchases;
drop table ads;
drop table offers;
drop table reviews;
drop table users;
drop table categories;

create table categories (
  cat 		char(10),
  supercat	char(10),
  primary key (cat),
  foreign key (supercat) references categories
);
create table users (
  email		char(25),
  name		char(25),
  pass		char(25),
  last_login	date,
  primary key (email)
);
create table reviews (
  rno		int,
  rating	int, 
  text		char(30),
  reviewer	char(25),
  reviewee	char(25),
  primary key (rno),
  foreign key (reviewer) references users,
  foreign key (reviewee) references users
);
create table offers (
  ono		int,
  ndays		int,
  price		float,
  primary key (ono)
);
create table ads (
  aid		char(4),
  atype		char(1)
    check (atype='S' OR atype='W'),	-- S (for sale), W (wanted)
  title		char(20),
  price		int,
  descr		char(40),
  location	char(15),
  pdate		date,
  cat		char(10),
  poster	char(25),
  primary key (aid),
  foreign key (cat) references categories,
  foreign key (poster) references users
);
create table purchases (
  pur_id	char(4),
  start_date	date,
  aid		char(4),
  ono		int,
  primary key (pur_id),
  foreign key (aid) references ads,
  foreign key (ono) references offers
);
insert into categories values ('buy/sell', null);
insert into categories values ('services', null);
insert into categories values ('tickets', 'buy/sell');
insert into categories values ('sports', 'buy/sell');
insert into categories values ('cameras', 'buy/sell');
insert into categories values ('computer', 'services');

insert into users values ('joe@ujiji.com','Joe Plumber', 'test', null);
insert into users values ('bob@ujiji.com','Bob Carpenter', 'test', null);
insert into users values ('davood@ujiji.com','Davood Teacher', 'test', null);
insert into users values ('adam@sport.com','Adam Fan', 'test', null);

insert into reviews values (1,5,'good seller, very positive exp' , 'joe@ujiji.com', 'bob@ujiji.com');
insert into reviews values (2,2,'very bad exp' , 'davood@ujiji.com', 'bob@ujiji.com');
insert into reviews values (3,5,'good seller, very positive exp' , 'davood@ujiji.com', 'joe@ujiji.com');
insert into reviews values (4,4,'very positive exp' , 'bob@ujiji.com', 'joe@ujiji.com');
insert into reviews values (5,5,'good' , 'adam@sport.com', 'joe@ujiji.com');
insert into reviews values (6,5,'good' , 'bob@ujiji.com', 'adam@sport.com');

insert into offers values (1,3,5);
insert into offers values (2,7,10);

insert into ads values ('a001','S','oilers ticket',90,'Feb 28, againts Stars','Edmonton','30-JAN-2013','tickets','bob@ujiji.com');
insert into ads values ('a002','S','nikon camera',50,'working condition','Edmonton',sysdate,'cameras','davood@ujiji.com');
insert into ads values ('a003','S','New snowmobile',900,'You can have it','Edmonton','3-JAN-2013','buy/sell','joe@ujiji.com');
insert into ads values ('a004','S','Winter machine',0,'I have a snowmobile for sale','Edmonton','2-JAN-2013','buy/sell','joe@ujiji.com');
insert into ads values ('a005','S','snowmobile',0,'I have a snowmobile thats broken.','Edmonton','2-JAN-2013','buy/sell','joe@ujiji.com');
insert into ads values ('a006','S','snowmobile',400,'Needs repair','Edmonton',sysdate,'buy/sell','joe@ujiji.com');
insert into ads values ('a007','S','camera',50,'working condition','Edmonton',sysdate,'cameras','davood@ujiji.com');
insert into ads values ('a008','S','camera',50,'Nikon working condition','Edmonton','3-JAN-2013','cameras','bob@ujiji.com');
insert into ads values ('a009','S','camera',50,'Nikon working condition','Edmonton','2-FEB-2013','cameras','davood@ujiji.com');
insert into ads values ('a010','S','New snowmobile',900,'You can have it','Edmonton','3-JAN-2013','buy/sell','bob@ujiji.com');
insert into ads values ('a011','S','Snowmobile',100,'You can have it','Edmonton','5-JAN-2013','buy/sell','adam@sport.com');

insert into purchases values ('p001',sysdate-1,'a001',1);
insert into purchases values ('p002',sysdate-1,'a002',2);
insert into purchases values ('p003',sysdate-1,'a001',2);
insert into purchases values ('p004',sysdate-1,'a003',2);
insert into purchases values ('p005',sysdate-1,'a004',2);
insert into purchases values ('p006',sysdate-1,'a008',2);