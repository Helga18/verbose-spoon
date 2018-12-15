create table user_account( 
id SERIAL PRIMARY KEY, 
name text NOT NULL, 
login varchar(50) NOT NULL, 
password varchar(15) NOT NULL, 
surname varchar(20) NOT NULL
); 


create table comment( 
id SERIAL PRIMARY KEY, 
content text NOT NULL, 
user_id integer references user_account(id), 
restaurant_id integer references restaurant(id) ,
exhibitions_id integer references exhibitions(id) ,
cinema_id integer references cinema(id) ,
sport_id integer references sport(id) 
); 
create table restaurant( 
id SERIAL PRIMARY KEY, 
name text NOT NULL,
price int NOT NULL ,
description text NOT NULL
); 
create table exhibitions( 
id SERIAL PRIMARY KEY, 
name text NOT NULL,
price int NOT NULL ,
description text NOT NULL
); 
create table cinema( 
id SERIAL PRIMARY KEY, 
name text NOT NULL,
price int NOT NULL ,
description text NOT NULL
); 
create table sport( 
id SERIAL PRIMARY KEY, 
name text NOT NULL,
price int NOT NULL ,
description text NOT NULL
); 