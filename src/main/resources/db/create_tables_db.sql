CREATE DATABASE IF NOT EXISTS rent_car;

CREATE TABLE IF NOT EXISTS rent_car.role_users
(
    id   BIGINT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    role VARCHAR(25) NOT NULL
) engine = InnoDB;

CREATE TABLE IF NOT EXISTS rent_car.status_users
(
    id     BIGINT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    status VARCHAR(50) NOT NULL
) engine = InnoDb;

CREATE TABLE IF NOT EXISTS rent_car.users
(
    id          BIGINT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    first_name  VARCHAR(30) NOT NULL,
    last_name   VARCHAR(30) NOT NULL,
    email_login VARCHAR(30) NOT NULL UNIQUE,
    password    VARCHAR(150) NOT NULL,
    role_user   BIGINT DEFAULT 1,
    status_user BIGINT DEFAULT 3,
    phone_user  VARCHAR(25),
    password_authentication VARCHAR(4) ,
    FOREIGN KEY (role_user) REFERENCES role_users (id),
    FOREIGN KEY (status_user) REFERENCES status_users (id)
) engine = InnoDB;

CREATE TABLE IF NOT EXISTS rent_car.status_cars
(
    id     BIGINT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    status varchar(50) not null
) engine = InnODb;

CREATE TABLE IF NOT EXISTS rent_car.transmission_type
(
    id   BIGINT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    type varchar(50) not null
) engine = InnODb;

CREATE TABLE IF NOT EXISTS rent_car.cars
(
    id                  BIGINT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    registration_number VARCHAR(10) NOT NULL UNIQUE,
    cost                DECIMAL     NOT NULL,
    image_url           VARCHAR(250),
    car_status          BIGINT  DEFAULT 1,
    transmission_type   BIGINT  DEFAULT 2,
    conditioner         BOOLEAN DEFAULT FALSE,
    car_description     VARCHAR(50),
    FOREIGN KEY (car_status) REFERENCES status_cars (id),
    FOREIGN KEY (transmission_type) REFERENCES transmission_type (id)

) engine = InnoDB;

CREATE TABLE IF NOT EXISTS rent_car.insurances
(
    id                    BIGINT   NOT NULL AUTO_INCREMENT PRIMARY KEY,
    start_insurance       DATETIME NOT NULL,
    end_insurance         DATETIME NOT NULL,
    is_active             BOOLEAN,
    identification_number BIGINT   NOT NULL UNIQUE,
    car_id                BIGINT   NOT NULL,
    FOREIGN KEY (car_id) REFERENCES cars (id)
) engine = InnoDb;

CREATE TABLE IF NOT EXISTS rent_car.status_orders
(
    id         BIGINT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    order_type varchar(50) not null
) engine = InnODb;

CREATE TABLE IF NOT EXISTS rent_car.orders
(
    id          BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    price       DECIMAL,
    rent_date   DATE   NOT NULL,
    return_date DATE   NOT NULL,
    car_id      BIGINT NOT NULL,
    user_id     BIGINT NOT NULL,
    status_id   BIGINT NOT NULL,
    FOREIGN KEY (car_id) REFERENCES cars (id),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (status_id) REFERENCES status_orders (id)
) engine = InnoDb;

alter table orders
    drop foreign key orders_ibfk_2;

alter table orders
    add constraint orders_ibfk_2
        foreign key (user_id) references users (id)
            on delete cascade;

alter table orders
    add constraint orders_cars_id_fk
        foreign key (car_id) references cars (id)
            on delete cascade;

alter table insurances drop foreign key insurances_ibfk_1;

alter table insurances
    add constraint insurances_ibfk_1
        foreign key (car_id) references cars (id)
            on delete cascade;







