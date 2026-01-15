\c postgres;
drop database if exists airline_db1;
CREATE DATABASE airline_db1
WITH 
    ENCODING='UTF8'
    LC_COLLATE='fr_FR.UTF-8'
    LC_CTYPE='fr_FR.UTF-8'
    TEMPLATE=template0;
\c airline_db;