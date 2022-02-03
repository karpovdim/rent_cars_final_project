
#  TRANSMISSION TYPE
INSERT INTO rent_car.transmission_type (type) VALUES ('MANUAL');
INSERT INTO rent_car.transmission_type (type) VALUES ('AUTOMATIC');
INSERT INTO rent_car.transmission_type (type) VALUES ('CONTINUOUSLY_VARIABLE');
INSERT INTO rent_car.transmission_type (type) VALUES ('SEMI_AUTOMATIC');

#  CARS STATUS
INSERT INTO rent_car.status_cars (status) VALUES ('BOOKED');
INSERT INTO rent_car.status_cars (status) VALUES ('FREE');
INSERT INTO rent_car.status_cars (status) VALUES ('CAR_IS_SERVICED');
INSERT INTO rent_car.status_cars (status) VALUES ('IMPOSSIBLE_TO_RENT');

#  USERS ROLES
INSERT INTO rent_car.role_users (role) VALUES ('CLIENT');
INSERT INTO rent_car.role_users (role) VALUES ('MANAGER');
INSERT INTO rent_car.role_users (role) VALUES ('ADMIN');

#   USERS STATUS
INSERT INTO rent_car.status_users (status) VALUES ('ACTIVE');
INSERT INTO rent_car.status_users (status) VALUES ('BANNED');
INSERT INTO rent_car.status_users (status) VALUES ('CONFIRMATION_AWAITING');

#   ORDERS STATUS
INSERT INTO rent_car.status_orders (order_type) VALUES ('PAID');
INSERT INTO rent_car.status_orders (order_type) VALUES ('AWAITS_PAYMENT');
INSERT INTO rent_car.status_orders (order_type) VALUES ('DECLINED')
