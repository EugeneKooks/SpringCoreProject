CREATE TABLE users (
  id         INTEGER AUTO_INCREMENT,
  first_name VARCHAR(30),
  last_name  VARCHAR(30),
  email      VARCHAR(40),
  birthday   TIMESTAMP
);

CREATE TABLE events (
  id         INTEGER AUTO_INCREMENT PRIMARY KEY,
  name       VARCHAR(30),
  base_price DOUBLE,
  rating     INTEGER
);

CREATE TABLE tickets (
  id        INTEGER AUTO_INCREMENT PRIMARY KEY,
  user_id   INTEGER,
  FOREIGN KEY (user_id) REFERENCES users (id),
  event_id  INTEGER,
  FOREIGN KEY (event_id) REFERENCES events (id),
  date_time TIMESTAMP,
  seat      INTEGER,
);

CREATE TABLE event_counter (
  id                 INTEGER PRIMARY KEY,
  event_counter_case INTEGER,
  event_id           INTEGER,
  FOREIGN KEY (event_id) REFERENCES events (id),
  count              BIGINT
);

CREATE TABLE birthday_discount_counter (
  id                INTEGER PRIMARY KEY,
  user_id           INTEGER,
  count             BIGINT,
  FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE tenth_ticket_discount_counter (
  id                INTEGER PRIMARY KEY,
  user_id           INTEGER,
  count             BIGINT,
  FOREIGN KEY (user_id) REFERENCES users (id),
)