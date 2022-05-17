create table comment(
	idx int primary key auto_increment,
	name varchar(40) not null,
	password varchar(40) not null,
	content varchar(200) not null,
	regDate timestamp default now(),
	ip varchar(20) not null
);

desc comment;

select * from comment;
select count(*)  from comment;