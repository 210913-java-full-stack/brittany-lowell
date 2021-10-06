DROP DATABASE IF EXISTS ProjectZero;
CREATE DATABASE ProjectZero;
USE ProjectZero;

DROP TABLE IF EXISTS accounts;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS user_accounts;

#Junction table connecting the tables users and accounts
CREATE TABLE user_accounts 
(
	junction_id INT NOT NULL AUTO_INCREMENT,
	user_id INT,
	account_id 	INT,
	INDEX (account_id),
	INDEX (user_id),
	CONSTRAINT PRIMARY KEY (junction_id)
);

#Table for user information
CREATE TABLE users
(
	user_id INT NOT NULL UNIQUE,
	first_name VARCHAR(30),
	last_name VARCHAR(30),
	username VARCHAR(20),
	password VARCHAR(20),
	CONSTRAINT PRIMARY KEY (user_id),
	CONSTRAINT FOREIGN KEY (user_id) REFERENCES user_accounts (user_id)
);

#Table for account information
CREATE TABLE accounts
(
	account_id INT, #AUTO_INCREMENT,
	account_type VARCHAR(20),
	balance DECIMAL (10,2),
	CONSTRAINT PRIMARY KEY (account_id),
	CONSTRAINT FOREIGN KEY (account_id) REFERENCES user_accounts (account_id)
);


SELECT * FROM user_accounts;
SELECT * FROM users;
SELECT * FROM accounts;
SELECT junction_id FROM user_accounts WHERE account_id = 0;

