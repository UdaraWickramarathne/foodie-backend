create database food_db;
use food_db;

create table product (id integer not null auto_increment, category varchar(255), description varchar(255), image_url varchar(255), name varchar(255), price float(53) not null, primary key (id))
