CREATE SEQUENCE ID;

CREATE TABLE IF NOT EXISTS users (

 id int GENERATED BY DEFAULT AS SEQUENCE ID PRIMARY KEY ,

 firstname varchar(200) NOT NULL,
 lastname varchar(200) NOT NULL,
 email varchar(200) NOT NULL,
 phonenumber varchar(200) NOT NULL,
 jobtitle varchar(200) NOT NULL,
 addressline1 varchar(200) NOT NULL,
 addressline2 varchar(200) NOT NULL,
 town varchar(200) NOT NULL,
 county varchar(200) NOT NULL,
 postcode varchar(200) NOT NULL,
 country varchar(200) NOT NULL,

);