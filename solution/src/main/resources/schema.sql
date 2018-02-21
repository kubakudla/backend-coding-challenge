USE expenses;

DROP TABLE IF EXISTS expense;

CREATE TABLE expense (
  id     INTEGER        NOT NULL AUTO_INCREMENT,
  amount DECIMAL(19,4)  NOT NULL,
  date   DATE           NOT NULL,
  reason VARCHAR(255)   NOT NULL,
  PRIMARY KEY (id)
);