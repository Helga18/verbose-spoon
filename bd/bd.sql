create table order_phone(
id SERIAL PRIMARY KEY,
order_id integer REFERENCES users_order(id),
phone_id integer REFERENCES phone(id)
);