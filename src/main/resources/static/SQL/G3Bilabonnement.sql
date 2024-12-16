DROP
DATABASE IF EXISTS G3Bilabonnement;
CREATE
DATABASE G3Bilabonnement;

USE
G3Bilabonnement;

CREATE TABLE location
(
    id       INT AUTO_INCREMENT PRIMARY KEY,
    address  VARCHAR(255) NOT NULL,
    city     VARCHAR(100) NOT NULL,
    zip_code VARCHAR(20)  NOT NULL
);

CREATE TABLE car_status
(
    id     INT AUTO_INCREMENT PRIMARY KEY,
    status VARCHAR(255) NOT NULL
);

CREATE TABLE kilometer_options
(
    id                   INT AUTO_INCREMENT PRIMARY KEY,
    kilometers_per_month INT            NOT NULL,
    price_per_month      DECIMAL(10, 2) NOT NULL
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
    kilometer_options_id    INT,
    price_per_month      DECIMAL(10, 2),
    FOREIGN KEY (kilometer_options_id) REFERENCES kilometer_options (id) ON DELETE SET NULL ON UPDATE CASCADE
);

CREATE TABLE subscriptionAddon
(
    id              INT AUTO_INCREMENT PRIMARY KEY,
    name            VARCHAR(255),
    price_per_month DECIMAL(10, 2)
);

CREATE TABLE subscription_SubscriptionAddon
(
    subscription_id INT NOT NULL,
    addon_id        INT NOT NULL,
    PRIMARY KEY (subscription_id, addon_id), -- Composite primary key, ensures uniqueness but will not be used
    FOREIGN KEY (subscription_id) REFERENCES Subscription (id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (addon_id) REFERENCES SubscriptionAddon (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE rental_agreement
(
    id                 INT AUTO_INCREMENT PRIMARY KEY,
    car_id             INT  NOT NULL,
    subscription_id    INT  NOT NULL,
    renter_id          INT  NOT NULL,
    start_date         DATE NOT NULL,
    end_date           DATE NOT NULL,
    pickup_location_id INT,
    return_location_id INT,
    FOREIGN KEY (pickup_location_id) REFERENCES Location (id)
        ON DELETE SET NULL ON UPDATE CASCADE,
    FOREIGN KEY (return_location_id) REFERENCES Location (id)
        ON DELETE SET NULL ON UPDATE CASCADE,
    FOREIGN KEY (car_id) REFERENCES Car (id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (subscription_id) REFERENCES Subscription (id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (renter_id) REFERENCES Renter (id) ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE damage_report
(
    id int  NOT NULL AUTO_INCREMENT PRIMARY KEY,
    car_id            int  NOT NULL,
    creation_date    date NOT NULL,
    FOREIGN KEY (car_id) REFERENCES car (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE damage_specification
(
    damage_specification_id int            NOT NULL AUTO_INCREMENT PRIMARY KEY,
    damage_description      varchar(200)   NOT NULL,
    damage_price            decimal(10, 2) NOT NULL,
    damage_report_id        int            NOT NULL,
    FOREIGN KEY (damage_report_id) REFERENCES damage_report (id)
);

CREATE TABLE final_settlement
(
    id 				INT AUTO_INCREMENT PRIMARY KEY,
    rental_agreement_id INT NOT NULL,
    damage_report_id INT NOT NULL,
    total_km_driven INT NOT NULL,
    total_price DECIMAL (10,2) NOT NULL,
    FOREIGN KEY (rental_agreement_id) REFERENCES rental_agreement (id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (damage_report_id) REFERENCES damage_report (id)
);

CREATE TABLE purchase_agreement
(
    id                 	    INT AUTO_INCREMENT PRIMARY KEY,
    car_id             	    INT NOT NULL,
    final_settlement_id 	INT NOT NULL,
    pickup_location		    VARCHAR (100) NOT NULL,
    final_price			    INT NOT NULL,
    FOREIGN KEY (car_id) REFERENCES car (id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (final_settlement_id) REFERENCES final_settlement (id) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Populate Location table
INSERT INTO location (address, city, zip_code)
VALUES ('123 Main St', 'Copenhagen', '1000'),
       ('456 Elm St', 'Aarhus', '8000'),
       ('789 Pine Ave', 'Odense', '5000');

-- Populate car_status table
INSERT INTO car_status (status)
VALUES ('Klar til udlejning'), -- Ready for rental
       ('Udlejet'),            -- Currently rented out
       ('Skadet'),             -- Damaged
       ('Til reparation'),     -- Undergoing repairs
       ('Klar til transport'),-- Ready for transport (includes planned return date)
       ('Klar til salg'),      -- Ready for sale
       ('Solgt'),              -- Sold
       ('Forhåndskøbt');

INSERT INTO kilometer_options (kilometers_per_month, price_per_month)
VALUES (1500, 0.00),
       (1750, 300.00),
       (2000, 590.00),
       (2500, 1160.00),
       (3000, 1710.00),
       (3500, 2240.00),
       (4000, 2750.00),
       (4500, 3240.00);

-- Populate Renter table
INSERT INTO Renter (firstname, lastname, email, phone_number, cpr_number, reg_number, account_number, location_id)
VALUES ('John', 'Doe', 'john.doe@example.com', '12345678', '123456-7890', '1001', '5678901234', 1),
       ('Jane', 'Smith', 'jane.smith@example.com', '87654321', '987654-3210', '1002', '4321098765', 2);

-- Populate Car table
INSERT INTO Car (image_url, vehicle_number, vin_number, brand, model, equipment_level, power_source_type,
                 transmission_type, net_price, registration_tax, co2_emissions, car_status_id)
VALUES ('https://example.com/car2.jpg', 'VN12345', 'VIN0001', 'Tesla', 'Model S', 'Performance', 'Electric',
        'Automatic', 80000.00, 20000.00, 0.00, 1), -- Klar til udlejning
       ('https://example.com/car2.jpg', 'VN67890', 'VIN0002', 'Toyota', 'Corolla', 'Standard', 'Hybrid', 'Automatic',
        25000.00, 5000.00, 98.00, 2),              -- Udlejet
       ('https://example.com/car3.jpg', 'VN54321', 'VIN0003', 'BMW', 'X5', 'Luxury', 'Diesel', 'Manual', 70000.00,
        15000.00, 150.00, 4),                      -- Til reparation
       ('https://example.com/car4.jpg', 'VN98765', 'VIN0004', 'Audi', 'A6', 'Standard', 'Gasoline', 'Automatic',
        60000.00, 12000.00, 130.00, 5),            -- Klar til transport
       ('https://example.com/car5.jpg', 'VN65432', 'VIN0005', 'Volkswagen', 'Golf', 'Comfort', 'Electric', 'Automatic',
        30000.00, 8000.00, 0.00, 6),               -- Klar til salg
       ('https://example.com/car6.jpg', 'VN11223', 'VIN0006', 'Ford', 'Focus', 'Standard', 'Hybrid', 'Manual', 20000.00,
        5000.00, 90.00, 7),                        -- Solgt
       ('https://example.com/car7.jpg', 'VN44556', 'VIN0007', 'Mercedes', 'C-Class', 'Luxury', 'Gasoline', 'Automatic',
        75000.00, 18000.00, 145.00, 8),            -- Forhåndskøbt
       ('/images/Citroën_C1_Le_Mans_72_HK.jpg', 'VN55679', 'VIN0012', 'Citroën', 'C1 Le Mans', '72 HK', 'Benzin',
        'Manuel',
        29999.00, 1000.00, 109.00, 1),
       ('/images/Fiat 500 Cabriolet Vita Comfort 70 HK.jpg', 'VN55680', 'VIN0013', 'Fiat', '500 Vita Comfort', '70 HK',
        'Benzin', 'Manuel',
        29999.00, 1000.00, 105.00, 1),
       ('/images/Fiat 500 Vita Comfort 70 HK.jpg', 'VN55681', 'VIN0014', 'Fiat', '500 Cabriolet Dolcevita', '70 HK',
        'Benzin',
        'Manuel', 32999.00, 1000.00, 116.00, 1),
       ('/images/Fiat 500e CABRIO La Prima 118 HK.jpg', 'VN55682', 'VIN0015', 'Fiat', '500 Cabriolet Vita Comfort',
        '70 HK', 'Benzin',
        'Manuel', 33999.00, 1000.00, 108.00, 1),
       ('/images/Peugeot e-2008 GT Line 136 HK.jpg', 'VN55683', 'VIN0016', 'Peugeot', 'e-2008 GT Line', '136 HK',
        'Elbil',
        'Automatgear', 42999.00, 1000.00, 0.00, 1),
       ('/images/Fiat 500e CABRIO La Prima 118 HK.jpg', 'VN55684', 'VIN0017', 'Fiat', '500e CABRIO La Prima', '118 HK',
        'Elbil',
        'Automatgear', 39999.00, 1000.00, 0.00, 1);

-- Populate Subscription table
INSERT INTO Subscription (base_price, subscription_type, kilometer_options_id, price_per_month)
VALUES (200.00, 'Standard', 1, 500.00),
       (300.00, 'Premium', 2, 800.00),
       (400.00, 'Unlimited', 3, 1200.00);

-- Populate rental_agreement table
INSERT INTO rental_agreement (car_id, subscription_id, renter_id, start_date, end_date, pickup_location_id,
                              return_location_id)
VALUES (1, 1, 1, '2024-01-01', '2024-06-01', 1, 2),
       (2, 2, 2, '2024-02-01', '2024-05-01', 2, 3);

-- Populate damage_report table
INSERT INTO damage_report (car_id, creation_date)
VALUES (1, '2024-03-01');

-- Populate damage_specification table
INSERT INTO damage_specification (damage_description, damage_price, damage_report_id)
VALUES ('Front bumper scratch', 200.00, 1),
       ('Front bumper scratch', 200.00, 1),
       ('Front bumper scratch', 200.00, 1),
       ('Front bumper scratch', 200.00, 1),
       ('Front bumper scratch', 200.00, 1),
       ('Front bumper scratch', 200.00, 1);