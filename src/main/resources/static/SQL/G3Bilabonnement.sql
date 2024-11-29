DROP
DATABASE IF EXISTS G3Bilabonnement;
CREATE
DATABASE G3Bilabonnement;

USE
G3Bilabonnement;

CREATE TABLE Location
(
    id       INT AUTO_INCREMENT PRIMARY KEY,
    address  VARCHAR(255) NOT NULL,
    city     VARCHAR(100) NOT NULL,
    zip_code VARCHAR(20)  NOT NULL
);

CREATE TABLE Renter
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

CREATE TABLE PurchaseAgreement(
    id                  INT AUTO_INCREMENT PRIMARY KEY,
    car_id              INT NOT NULL,
    finalSettlement_id  INT NOT NULL
);


-- Vælg databasen
USE G3Bilabonnement;

-- Indsæt dummy-data i Location-tabellen
INSERT INTO Location (address, city, zip_code)
VALUES
    ('Main Street 1', 'Copenhagen', '1000'),
    ('River Road 5', 'Aarhus', '8000'),
    ('Hilltop Avenue 12', 'Odense', '5000');

-- Indsæt dummy-data i Renter-tabellen
INSERT INTO Renter (firstname, lastname, email, phone_number, cpr_number, reg_number, account_number, location_id)
VALUES
    ('John', 'Doe', 'john.doe@example.com', '12345678', '123456-7890', '1234', '567890', 1),
    ('Jane', 'Smith', 'jane.smith@example.com', '87654321', '234567-8901', '5678', '123456', 2),
    ('Michael', 'Brown', 'michael.brown@example.com', '11223344', '345678-9012', '9012', '345678', 3);

-- Indsæt dummy-data i Car-tabellen
INSERT INTO Car (vehicle_number, vin_number, brand, model, equipment_level, power_source_type, transmission_type, net_price, registration_tax, co2_emissions, car_status)
VALUES
    ('VH001', 'VIN123456789', 'Toyota', 'Corolla', 'Standard', 'Hybrid', 'Automatic', 200000.00, 25000.00, 100.5, 'Available'),
    ('VH002', 'VIN987654321', 'Volkswagen', 'Golf', 'Comfort', 'Petrol', 'Manual', 180000.00, 23000.00, 120.0, 'Rented'),
    ('VH003', 'VIN456123789', 'Tesla', 'Model 3', 'Performance', 'Electric', 'Automatic', 400000.00, 50000.00, 0.0, 'Available');

-- Indsæt dummy-data i Subscription-tabellen
INSERT INTO Subscription (baseprice, subscription_type, allowed_km_per_month, pickup_location, return_location, price_per_month)
VALUES
    (1000.00, 'Limited', 1000, 'Main Street 1', 'Main Street 1', 1500.00),
    (1500.00, 'Limited', 2000, 'River Road 5', 'River Road 5', 2500.00),
    (2000.00, 'Unlimited', 3000, 'Hilltop Avenue 12', 'Hilltop Avenue 12', 3500.00);

-- Indsæt dummy-data i RentalAgreement-tabellen
INSERT INTO RentalAgreement (car_id, subscription_id, renter_id, start_date, end_date)
VALUES
    (1, 1, 1, '2024-01-01', '2024-06-01'),
    (2, 2, 2, '2024-03-01', '2024-09-01'),
    (3, 3, 3, '2024-05-01', '2024-11-01');
