CREATE TABLE Equipment (
  eq_id integer PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
  name varchar(255) NOT NULL,
  description varchar(512) NOT NULL,
  status varchar(32) NOT NULL,
  listing varchar(32) NOT NULL
);

CREATE TABLE TheUser (
  user_id integer PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
  name varchar(255) NOT NULL,
  pwd varchar(255) NOT NULL,
  role varchar(32) NOT NULL
);

CREATE TABLE BorrowRecord (
  bor_id integer PRIMARY KEY,
  user_id integer NOT NULL,
  status varchar(32) NOT NULL
);

CREATE TABLE BorrowItem (
  bor_id integer,
  eq_id integer,
  status varchar(32) NOT NULL,
  dfrom char(10) NOT NULL,
  dto char(10) NOT NULL,
  PRIMARY KEY (bor_id, eq_id)
);

ALTER TABLE BorrowRecord ADD FOREIGN KEY (user_id) REFERENCES TheUser (user_id);

ALTER TABLE BorrowItem ADD FOREIGN KEY (bor_id) REFERENCES BorrowRecord (bor_id);

ALTER TABLE BorrowItem ADD FOREIGN KEY (eq_id) REFERENCES Equipment (eq_id) /* No semicolon at the end */
