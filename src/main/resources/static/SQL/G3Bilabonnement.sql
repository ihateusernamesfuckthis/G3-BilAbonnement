DROP DATABASE IF EXISTS G3Bilabonnement;
CREATE DATABASE G3Bilabonnement;

USE G3Bilabonnement;

CREATE TABLE location
(
    id       INT AUTO_INCREMENT PRIMARY KEY,
    address  VARCHAR(255) NOT NULL,
    city     VARCHAR(100) NOT NULL,
    zip_code VARCHAR(20)  NOT NULL
);

CREATE TABLE renter
(
    id             INT AUTO_INCREMENT PRIMARY KEY,
    firstname      VARCHAR(100) NOT NULL,
    lastname       VARCHAR(100) NOT NULL,
    email          VARCHAR(150) NOT NULL UNIQUE,
    phone_number   VARCHAR(20),
    cpr_number     VARCHAR(20) UNIQUE,
    reg_number     VARCHAR(20),
    account_number VARCHAR(20),
    location_id    INT,
    FOREIGN KEY (location_id) REFERENCES Location (id)
        ON DELETE SET NULL ON UPDATE CASCADE
);

CREATE TABLE car
(
    id                INT AUTO_INCREMENT PRIMARY KEY,
    vehicle_number    VARCHAR(20)    NOT NULL UNIQUE,
    vin_number        VARCHAR(50)    NOT NULL UNIQUE,
    brand             VARCHAR(50)    NOT NULL,
    model             VARCHAR(50)    NOT NULL,
    equipment_level   VARCHAR(50),
    power_source_type VARCHAR(50),
    transmission_type VARCHAR(50),
    net_price         DECIMAL(10, 2) NOT NULL,
    registration_tax  DECIMAL(10, 2) NOT NULL,
    co2_emissions     DECIMAL(10, 2),
    car_status        VARCHAR(50)    NOT NULL
);

CREATE TABLE subscription
(
    id                   INT AUTO_INCREMENT PRIMARY KEY,
    base_price            DECIMAL(10, 2),
    subscription_type    VARCHAR(50),
    allowed_km_per_month INT,
    pickup_location_id      INT,
    return_location_id      INT,
    price_per_month      DECIMAL(10, 2),
    FOREIGN KEY (pickup_location_id) REFERENCES Location (id)
        ON DELETE SET NULL ON UPDATE CASCADE,
    FOREIGN KEY (return_location_id) REFERENCES Location (id)
        ON DELETE SET NULL ON UPDATE CASCADE
);

CREATE TABLE rental_agreement
(
    id              INT AUTO_INCREMENT PRIMARY KEY,
    car_id          INT            NOT NULL,
    subscription_id INT            NOT NULL,
    renter_id       INT            NOT NULL,
    start_date      DATE           NOT NULL,
    end_date        DATE           NOT NULL,
    FOREIGN KEY (car_id) REFERENCES Car (id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (subscription_id) REFERENCES Subscription (id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (renter_id) REFERENCES Renter (id) ON DELETE CASCADE ON UPDATE CASCADE
);
