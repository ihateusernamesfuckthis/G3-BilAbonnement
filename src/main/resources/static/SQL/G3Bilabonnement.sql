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

CREATE TABLE car
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


-- Inserting dummy data into `location` table
INSERT INTO location (address, city, zip_code) VALUES
                                                   ('123 Elm St', 'Copenhagen', '1000'),
                                                   ('456 Maple Ave', 'Aarhus', '8000'),
                                                   ('789 Oak Blvd', 'Odense', '5000');

INSERT INTO car_status (status)
VALUES
    ('Klar til udlejning'),
    ('Udlejet'),
    ('Skadet'),
    ('Til reparation'),
    ('Klar til transport'),
    ('Klar til salg'),
    ('Solgt');


-- Inserting dummy data into `renter` table
INSERT INTO renter (firstname, lastname, email, phone_number, cpr_number, reg_number, account_number, location_id) VALUES
                                                                                                                       ('John', 'Doe', 'john.doe@example.com', '1234567890', '1234567890', 'AB12345', '100012345', 1),
                                                                                                                       ('Jane', 'Smith', 'jane.smith@example.com', '0987654321', '0987654321', 'CD67890', '100076543', 2),
                                                                                                                       ('Alice', 'Johnson', 'alice.johnson@example.com', '1122334455', '1122334455', 'EF98765', '100089234', 3);

-- Inserting dummy data into `car` table
INSERT INTO car (image_url, vehicle_number, vin_number, brand, model, equipment_level, power_source_type, transmission_type, net_price, registration_tax, co2_emissions, car_status_id)
VALUES
    ('https://res.cloudinary.com/digital-interdan-bilabonnement/image/upload/c_fit,e_trim:0,q_80,w_640/v1/bilabonnement-fleet/utxdwfbv2btg4ss5hopj', 'VH12345', '1HGCM82633A123456', 'Toyota', 'Corolla', 'Standard', 'Gasoline', 'Automatic', 200000, 25000, 95, 1),
    ('https://res.cloudinary.com/digital-interdan-bilabonnement/image/upload/c_fit,e_trim:0,q_80,w_640/v1/bilabonnement-fleet/utxdwfbv2btg4ss5hopj', 'VH67890', '1HGCM82633A654321', 'Ford', 'Focus', 'Luxury', 'Diesel', 'Manual', 250000, 30000, 120, 2),
    ('https://res.cloudinary.com/digital-interdan-bilabonnement/image/upload/c_fit,e_trim:0,q_80,w_640/v1/bilabonnement-fleet/utxdwfbv2btg4ss5hopj', 'VH54321', '1HGCM82633A098765', 'Volkswagen', 'Golf', 'Premium', 'Electric', 'Automatic', 300000, 35000, 0, 4);

-- Inserting dummy data into `subscription` table
INSERT INTO subscription (base_price, subscription_type, allowed_km_per_month, pickup_location_id, return_location_id, price_per_month) VALUES
                                                                                                                                            (2000.00, 'Basic', 1000, 1, 1, 3000.00),
                                                                                                                                            (2500.00, 'Sport', 1500, 2, 2, 3500.00),
                                                                                                                                            (3000.00, 'Luxury', 2000, 3, 3, 4000.00);

-- Inserting dummy data into `rental_agreement` table
INSERT INTO rental_agreement (car_id, subscription_id, renter_id, start_date, end_date) VALUES
                                                                                            (1, 1, 1, '2024-11-01', '2024-11-30'),
                                                                                            (2, 2, 2, '2024-11-10', '2024-12-10'),
                                                                                            (3, 3, 3, '2024-11-20', '2024-12-20');
