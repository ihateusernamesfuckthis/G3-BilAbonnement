DROP
DATABASE IF EXISTS g3bilabonnement;
CREATE
DATABASE g3bilabonnement;

USE
g3bilabonnement;

CREATE TABLE Location
(
    id       INT AUTO_INCREMENT PRIMARY KEY,
    address  VARCHAR(255) NOT NULL,
    city     VARCHAR(100) NOT NULL,
    zip_code VARCHAR(20)  NOT NULL
);

CREATE TABLE car_status
(
    id      INT AUTO_INCREMENT PRIMARY KEY,
    status  VARCHAR(255) NOT NULL
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

CREATE TABLE Car
(
    id                INT AUTO_INCREMENT PRIMARY KEY,
    image_url         VARCHAR(255),
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
    car_status_id     INT,
    FOREIGN KEY (car_status_id) REFERENCES car_status (id)
        ON DELETE SET NULL ON UPDATE CASCADE
);

CREATE TABLE Subscription
(
    id                   INT AUTO_INCREMENT PRIMARY KEY,
    baseprice            DECIMAL(10, 2),
    subscription_type    VARCHAR(50),
    allowed_km_per_month INT,
    pickup_location      VARCHAR(100),
    return_location      VARCHAR(100),
    price_per_month      DECIMAL(10, 2)
);

CREATE TABLE RentalAgreement
(
    id              INT AUTO_INCREMENT PRIMARY KEY,
    car_id          INT  NOT NULL,
    subscription_id INT  NOT NULL,
    renter_id       INT  NOT NULL,
    start_date      DATE NOT NULL,
    end_date        DATE NOT NULL,
    FOREIGN KEY (car_id) REFERENCES Car (id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (subscription_id) REFERENCES Subscription (id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (renter_id) REFERENCES Renter (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE FinalSettlement
(
    id              INT AUTO_INCREMENT PRIMARY KEY
);

CREATE TABLE PurchaseAgreement
(
    id                  INT AUTO_INCREMENT PRIMARY KEY,
    car_id              INT NOT NULL,
    finalSettlement_id  INT NOT NULL,
    pickup_location     VARCHAR(255),
    final_price         DECIMAL(10, 2)
);
