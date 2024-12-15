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

CREATE TABLE brand
(
    id   INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE car_model
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    model_name VARCHAR(100) NOT NULL,
    brand_id   INT,
    FOREIGN KEY (brand_id) REFERENCES brand (id) ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE car_status
(
    id     INT AUTO_INCREMENT PRIMARY KEY,
    status VARCHAR(255) NOT NULL
);

CREATE TABLE power_source
(
    id   INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE transmission_type
(
    id   INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE equipment_level
(
    id   INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE car
(
    id                   INT AUTO_INCREMENT PRIMARY KEY,
    image_url            VARCHAR(255),
    vehicle_number       VARCHAR(20)    NOT NULL,
    vin_number           VARCHAR(50)    NOT NULL,
    car_model_id         INT,
    equipment_level_id   INT,
    power_source_id      INT,
    transmission_type_id INT,
    net_price            DECIMAL(10, 2) NOT NULL,
    registration_tax     DECIMAL(10, 2) NOT NULL,
    co2_emissions        DECIMAL(10, 2),
    car_status_id        INT,
    FOREIGN KEY (car_model_id) REFERENCES car_model (id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (equipment_level_id) REFERENCES equipment_level (id) ON DELETE SET NULL ON UPDATE CASCADE,
    FOREIGN KEY (power_source_id) REFERENCES power_source (id) ON DELETE SET NULL ON UPDATE CASCADE,
    FOREIGN KEY (transmission_type_id) REFERENCES transmission_type (id) ON DELETE SET NULL ON UPDATE CASCADE,
    FOREIGN KEY (car_status_id) REFERENCES car_status (id) ON DELETE SET NULL ON UPDATE CASCADE
);


CREATE TABLE car_model_limit
(
    id           INT AUTO_INCREMENT PRIMARY KEY,
    car_model_id INT NOT NULL UNIQUE,
    min_limit    INT NOT NULL,
    FOREIGN KEY (car_model_id) REFERENCES car_model (id) ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE subscription
(
    id                   INT AUTO_INCREMENT PRIMARY KEY,
    base_price           DECIMAL(10, 2),
    subscription_type    VARCHAR(50),
    kilometer_options_id INT,
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
    id            int  NOT NULL AUTO_INCREMENT PRIMARY KEY,
    car_id        int  NOT NULL,
    creation_date date NOT NULL,
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
    id                  INT AUTO_INCREMENT PRIMARY KEY,
    rental_agreement_id INT            NOT NULL,
    damage_report_id    INT            NOT NULL,
    total_km_driven     INT            NOT NULL,
    total_price         DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (rental_agreement_id) REFERENCES rental_agreement (id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (damage_report_id) REFERENCES damage_report (id)
);

CREATE TABLE purchase_agreement
(
    id                  INT AUTO_INCREMENT PRIMARY KEY,
    car_id              INT          NOT NULL,
    final_settlement_id INT          NOT NULL,
    pickup_location     VARCHAR(100) NOT NULL,
    final_price         INT          NOT NULL,
    FOREIGN KEY (car_id) REFERENCES car (id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (final_settlement_id) REFERENCES final_settlement (id) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Populate Location table
INSERT INTO location (address, city, zip_code)
VALUES ('123 Main St', 'Copenhagen', '1000'),
       ('456 Elm St', 'Aarhus', '8000'),
       ('789 Pine Ave', 'Odense', '5000');

INSERT INTO brand (name)
VALUES ('Tesla'),
       ('Toyota'),
       ('BMW'),
       ('Audi'),
       ('Citroën'),
       ('Fiat'),
       ('Peugeot');

INSERT INTO car_model (model_name, brand_id)
VALUES ('Model S', (SELECT id FROM brand WHERE name = 'Tesla')),
       ('Corolla', (SELECT id FROM brand WHERE name = 'Toyota')),
       ('X5', (SELECT id FROM brand WHERE name = 'BMW')),
       ('A6', (SELECT id FROM brand WHERE name = 'Audi')),
       ('C1 Le Mans', (SELECT id FROM brand WHERE name = 'Citroën')),
       ('500 Vita Comfort', (SELECT id FROM brand WHERE name = 'Fiat')),
       ('500 Cabriolet Dolcevita', (SELECT id FROM brand WHERE name = 'Fiat')),
       ('500 Cabriolet Vita Comfort', (SELECT id FROM brand WHERE name = 'Fiat')),
       ('e-2008 GT Line', (SELECT id FROM brand WHERE name = 'Peugeot')),
       ('500e CABRIO La Prima', (SELECT id FROM brand WHERE name = 'Fiat'));


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

-- Insert dummy data into power_source
INSERT INTO power_source (name)
VALUES ('Benzin'),
       ('Diesel'),
       ('Elektrisk'),
       ('Hybrid');

-- Insert dummy data into transmission_type
INSERT INTO transmission_type (name)
VALUES ('Manuel'),
       ('Automatisk'),
       ('Semi-automatisk');

-- Insert dummy data into equipment_level
INSERT INTO equipment_level (name)
VALUES ('Basis'),
       ('Komfort'),
       ('Sport'),
       ('Luksus');

-- Populate Renter table
INSERT INTO Renter (firstname, lastname, email, phone_number, cpr_number, reg_number, account_number, location_id)
VALUES ('John', 'Doe', 'john.doe@example.com', '12345678', '123456-7890', '1001', '5678901234', 1),
       ('Jane', 'Smith', 'jane.smith@example.com', '87654321', '987654-3210', '1002', '4321098765', 2);

INSERT INTO car (image_url, vehicle_number, vin_number, car_model_id, equipment_level_id, power_source_id,
                 transmission_type_id,
                 net_price, registration_tax, co2_emissions, car_status_id)
VALUES ('https://example.com/car2.jpg', 'VN12345', 'VIN0001', (SELECT id FROM car_model WHERE model_name = 'Model S'),
        4, 3, 2, 80000.00, 20000.00, 0.00, 1),
       ('https://example.com/car2.jpg', 'VN67890', 'VIN0002', (SELECT id FROM car_model WHERE model_name = 'Corolla'),
        2, 4, 2, 25000.00, 5000.00, 98.00, 2),
       ('https://example.com/car3.jpg', 'VN54321', 'VIN0003', (SELECT id FROM car_model WHERE model_name = 'X5'), 4, 2,
        1, 70000.00, 15000.00, 150.00, 4),
       ('https://example.com/car4.jpg', 'VN98765', 'VIN0004', (SELECT id FROM car_model WHERE model_name = 'A6'), 2, 1,
        2, 60000.00, 12000.00, 130.00, 5),
       ('/images/Citroën_C1_Le_Mans_72_HK.jpg', 'VN55679', 'VIN0012',
        (SELECT id FROM car_model WHERE model_name = 'C1 Le Mans'), 1, 1, 1, 29999.00, 1000.00, 109.00, 1),
       ('/images/Fiat 500 Cabriolet Vita Comfort 70 HK.jpg', 'VN55680', 'VIN0013',
        (SELECT id FROM car_model WHERE model_name = '500 Vita Comfort'), 2, 1, 1, 29999.00, 1000.00, 105.00, 1),
       ('/images/Fiat 500 Vita Comfort 70 HK.jpg', 'VN55681', 'VIN0014',
        (SELECT id FROM car_model WHERE model_name = '500 Cabriolet Dolcevita'), 2, 1, 1, 32999.00, 1000.00, 116.00, 1),
       ('/images/Fiat 500e CABRIO La Prima 118 HK.jpg', 'VN55682', 'VIN0015',
        (SELECT id FROM car_model WHERE model_name = '500 Cabriolet Vita Comfort'), 2, 1, 1, 33999.00, 1000.00, 108.00,
        1),
       ('/images/Peugeot e-2008 GT Line 136 HK.jpg', 'VN55683', 'VIN0016',
        (SELECT id FROM car_model WHERE model_name = 'e-2008 GT Line'), 3, 3, 2, 42999.00, 1000.00, 0.00, 1),
       ('/images/Fiat 500e CABRIO La Prima 118 HK.jpg', 'VN55684', 'VIN0017',
        (SELECT id FROM car_model WHERE model_name = '500e CABRIO La Prima'), 3, 3, 2, 39999.00, 1000.00, 0.00, 1);


INSERT INTO car_model_limit (car_model_id, min_limit)
VALUES (1, 5),  -- Tesla Model S
       (2, 10), -- Toyota Corolla
       (3, 3),  -- BMW X5
       (4, 2),  -- Audi A6
       (5, 7),  -- Citroën C1 Le Mans
       (6, 8),  -- Fiat 500 Vita Comfort
       (7, 6),  -- Fiat 500 Cabriolet Dolcevita
       (8, 4),  -- Fiat 500 Cabriolet Vita Comfort
       (9, 3),  -- Peugeot e-2008 GT Line
       (10, 5);
-- Fiat 500e CABRIO La Prima


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
VALUES ('Front bumper scratch', 200.00, 1);

