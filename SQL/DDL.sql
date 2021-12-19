create table users
	(u_id 		varchar(8),
	 f_name		varchar(15),
	 l_name		varchar(15),
	 email		varchar(15),
	 phone_no	numeric(10,0),
	 password	varchar(20),
	 primary key (u_id)
	);
	
create table billing
	(bill_id	varchar(8),
	 u_id		varchar(8),
	 card_no	numeric(20,0),
	 cvv		numeric(5,0),
	 primary key (bill_id),
	 foreign key (u_id) references users on delete cascade
	);

	
create table book
	(b_id		varchar(8),
	 book_name	varchar(70),
	 author		varchar(30),
	 publisher	varchar(30),
	 isbn		numeric(20,0),
	 pages		numeric(5,0),
	 year		numeric(5,0),
	 price		numeric(5,0),
	 stock		numeric(3,0),
	 primary key (b_id)
	);
	
	
create table seller
	(s_id 		varchar(8),
	 f_name		varchar(15),
	 l_name		varchar(15),
	 email		varchar(30),
	 phone_no	numeric(10,0),
	 password	varchar(20),
	 primary key (s_id)
	);


create table courier
	(cour_id 		varchar(8),
	 cour_name		varchar(15),
	 email			varchar(15),
	 phone_no		numeric(10,0),
	 price			numeric(5,0),
	 delivery_day		numeric(2,0),
	 primary key (cour_id)
	);

	
create table orders
	(o_id 		varchar(8),
	 u_id 		varchar(8),
	 b_id		varchar(8),
	 bill_id	varchar(8),
	 day		numeric(5,0),
	 month		numeric(5,0),
	 year		numeric(5,0),
	 status			varchar(15),
	 cour_id  		varchar(5),
	 book_amount	numeric(5,0),
	 total_cost		numeric(5,0),
	 primary key (o_id, u_id),
	 foreign key (u_id) references users on delete cascade,
	 foreign key (b_id) references book,
	 foreign key (bill_id) references billing
	);


	
create table address
	(a_id 		varchar(8),
	 u_id		varchar(8),
	 street		varchar(20),
	 country	varchar(10),
	 city		varchar(10),
	 province	varchar(10),
	 zip_code	varchar(20),
	 primary key (a_id, u_id),
	 foreign key (u_id) references users on delete cascade
	);
	
create table basket
	(u_id		varchar(8),
	 b_id		varchar(8),
	 book_amount	numeric(5,0),
	 primary key (u_id, b_id),
	 foreign key (u_id) references users on delete cascade,
	 foreign key (b_id) references book
		
	);

create table product_seller
	(b_id		varchar(8),
	 s_id		varchar(8),
	 primary key (b_id, s_id),
	 foreign key (b_id) references book,
	 foreign key (s_id) references seller on delete cascade
	);