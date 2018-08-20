DROP DATABASE if EXISTS testdb;
CREATE DATABASE testdb DEFAULT CHARACTER SET utf8;
use testdb;

DROP TABLE if EXISTS tb_customer;
DROP TABLE if EXISTS tb_order;
DROP TABLE if EXISTS tb_person;
DROP TABLE if EXISTS tb_idCard;

CREATE TABLE tb_idCard(
    id INT PRIMARY KEY,
    num bigint
);
CREATE TABLE tb_customer(
    id INT PRIMARY KEY,
    name VARCHAR(20),
    num_id INT,
    CONSTRAINT fk_tb_toNum FOREIGN KEY(num_id) REFERENCES tb_idCard(id)
);
CREATE TABLE tb_order(
    id INT PRIMARY KEY,
    goods VARCHAR(20),
    price INT,
    c_id INT,
    CONSTRAINT fk_toCustomer FOREIGN KEY(c_id) REFERENCES tb_customer(id)
);