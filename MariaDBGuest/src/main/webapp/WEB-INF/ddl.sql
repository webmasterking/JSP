 	create table guestbook(
 	    idx int primary key auto_increment,
 	    name varchar(40) not null,  
 	    password varchar(40) not null,  
 	    content text not null,
 	    regdate timestamp default now(),  
 	    ip varchar(20) not null
 	);
 	
 delete from guestbook;