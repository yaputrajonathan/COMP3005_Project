insert into users values ('10010','Jonathan','Yaputra','jo@gee.com','123123123','admin');
insert into users values ('10011','Elon','Musk','Elon@gee.com','098987987','admin');

insert into billing values ('1001','10010','10190192092091','099');
insert into billing values ('1002','10011','99999934500234','111');

insert into book values ('11001','The Little Prince','Antoine de Saint-Exupéry','Reynal & Hitchcock','9780512708007','96','1943','12','30');
insert into book values ('11002','Harry Potter and the Sorcerers Stone','J.K. Rowling','Bloomsbury','9789936805200','309','1997','15','43');
insert into book values ('11003','Harry Potter and the Chamber of Secrets','J.K. Rowling','Bloomsbury','9786992704676','341','1998','15','56');
insert into book values ('11004','Harry Potter and the Prisoner of Azkaban','J.K. Rowling','Bloomsbury','9795847564068','435','1999','15','30');
insert into book values ('11005','Harry Potter and the Goblet of Fire','J.K. Rowling','Bloomsbury','9796754088364','734','2000','15','20');
insert into book values ('11006','Harry Potter and the Order of the Phoenix','J.K. Rowling','Bloomsbury','9785402121737','870','2003','15','16');
insert into book values ('11007','Harry Potter and the Half-Blood Prince','J.K. Rowling','Bloomsbury','9789905100879','652','2005','15','71');
insert into book values ('11008','Harry Potter and the Deathly Hallows','J.K. Rowling','Bloomsbury','9787313399267','759','2007','15','52');
insert into book values ('11009','Harry Potter and the Cursed Child: Parts One and Two','J.K. Rowling','Bloomsbury','9780512708007','343','2016','20','40');
insert into book values ('11010','The Hobbit, or There and Back Again','J.R.R. Tolkien','George Allen & Unwin','9789370660199','366','1937','20','40');
insert into book values ('11011','The Fellowship of the Ring','J.R.R. Tolkien','George Allen & Unwin','9780736316996','527','1954','13','53');
insert into book values ('11012','The Two Towers','J.R.R. Tolkien','George Allen & Unwin','9781204440670','447','1954','11','26');
insert into book values ('11013','The Return of the King','J.R.R. Tolkien','George Allen & Unwin','9783994963216','385','1955','11','19');
insert into book values ('11014','A Tale of Two Cities','Charles Dickens','Chapman & Hall','9793516567792','489','1859','55','22');
insert into book values ('11015','And Then There Were None','Agatha Christie','Collins Crime Club','9789673598274','264','1939','12','41');
insert into book values ('11016','The Dream of the Red Chamber','Cao Xueqin','Cheng Weiyuan and Gao E','9794655986819','96','1791','30','23');
insert into book values ('11017','The Da Vinci Code','Dan Brown','Doubleday','9785850823825','489','2003','23','53');
insert into book values ('11018','Angels & Demons','Dan Brown','Doubleday','9798246869116','736','2000','13','33');

insert into seller (s_id, f_name, l_name, email, phone_no, password) values ('10101','Chrissie','Lainey','chris@gmail.com','6048655227','1234');
insert into seller (s_id, f_name, l_name, email, phone_no, password) values ('10102','Wynter','Jaynie','wynter@gmail.com','4509862901','1234');
insert into seller (s_id, f_name, l_name, email, phone_no, password) values ('10103','Stew','Ramona','stew@gmail.com','5064780896','1234');
insert into seller (s_id, f_name, l_name, email, phone_no, password) values ('10104','Ciera','Destinee','ciera@gmail.com','4037719643','1234');
insert into seller (s_id, f_name, l_name, email, phone_no, password) values ('10105','Milly','Marylu','milly@gmail.com','7057539450','1234');
insert into seller (s_id, f_name, l_name, email, phone_no, password) values ('10106','Symphony','Winston','winston@gmail.com','4165451743','1234');
insert into seller (s_id, f_name, l_name, email, phone_no, password) values ('10107','Blaze','Jae','blaze@gmail.com','9059267676','1234');
insert into seller (s_id, f_name, l_name, email, phone_no, password) values ('10108','Karen','Ciera','karen@gmail.com','7787729583','1234');
insert into seller (s_id, f_name, l_name, email, phone_no, password) values ('10109','Jack','Glenn','jackg@gmail.com','5194657633','1234');
insert into seller (s_id, f_name, l_name, email, phone_no, password) values ('10110','Geneva','Hayes','geneva@gmail.com','2898099191','1234');
insert into seller (s_id, f_name, l_name, email, phone_no, password) values ('10111','Everette','Alesia','alesia@gmail.com','6137660042','1234');

insert into product_seller values ('11002','10101');
insert into product_seller values ('11003','10101');
insert into product_seller values ('11004','10101');
insert into product_seller values ('11005','10101');
insert into product_seller values ('11006','10101');
insert into product_seller values ('11007','10101');
insert into product_seller values ('11008','10101');
insert into product_seller values ('11009','10101');
insert into product_seller values ('11001','10102');

insert into courier values ('10001','USP','USP@USP.ca','8007425877','5','14');
insert into courier values ('10002','PrimeZone','Jeff@Bansos.ca','8882804331','10','1');
insert into courier values ('10003','FedWhy','customer@FY.ca','8004633339','2','30');

insert into address values ('10001','10010','7377 Marvon Street','Canada','QC','J3L 3Y6','Chambly');
insert into address values ('10002','10011' ,'39 Fawn Ave','Canada','ON','L4R 9B6','Midland');

insert into basket values ('10010','11002', '2');
insert into basket values ('10010','11003', '1');
insert into basket values ('10010','11004', '1');
insert into basket values ('10010','11005', '3');
insert into basket values ('10010','11006', '1');
insert into basket values ('10010','11007', '2');
insert into basket values ('10010','11008', '4');
insert into basket values ('10010','11009', '2');
insert into basket values ('10011','11001', '3');