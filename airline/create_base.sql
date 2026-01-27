\c postgres;
drop database if exists airline_db13;
CREATE DATABASE airline_db13
WITH 
    ENCODING='UTF8'
    LC_COLLATE='fr_FR.UTF-8'
    LC_CTYPE='fr_FR.UTF-8'
    TEMPLATE=template0;
\c airline_db13;
