-- DDL
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

-- Data i create script for location, renter og car er generet af ChatGPT.

INSERT INTO location (address, city, zip_code)
VALUES
    ('Søndergade 12', 'Aarhus', '8000'),
    ('Vesterbrogade 45', 'Copenhagen', '1620'),
    ('H.C. Andersens Boulevard 8', 'Odense', '5000'),
    ('Nørregade 32', 'Aalborg', '9000'),
    ('Strandvejen 89', 'Hellerup', '2900'),
    ('Roskildevej 19', 'Roskilde', '4000'),
    ('Jernbanegade 5', 'Esbjerg', '6700'),
    ('Ny Munkegade 14', 'Randers', '8900'),
    ('Østergade 21', 'Herning', '7400'),
    ('Vestergade 30', 'Vejle', '7100'),
    ('Kirkegade 10', 'Horsens', '8700'),
    ('Gammel Kongevej 50', 'Frederiksberg', '2000'),
    ('Langelinie 2', 'Nyborg', '5800'),
    ('Hovedgaden 15', 'Hillerød', '3400'),
    ('Skagensvej 3', 'Skagen', '9990'),
    ('Toldbodvej 6', 'Svendborg', '5700'),
    ('Bygaden 24', 'Holbæk', '4300'),
    ('Fiskergade 18', 'Kolding', '6000'),
    ('Torpvej 9', 'Aabenraa', '6200'),
    ('Slotsgade 7', 'Fredericia', '7000'),
    ('Brostræde 11', 'Nakskov', '4900'),
    ('Nytorv 23', 'Slagelse', '4200'),
    ('Stationsvej 1', 'Skanderborg', '8660'),
    ('Enghavevej 4', 'Hvidovre', '2650'),
    ('Lyngbyvej 77', 'Lyngby', '2800'),
    ('Jægersborg Alle 55', 'Gentofte', '2820'),
    ('Birkevej 2', 'Viborg', '8800'),
    ('Skovvej 12', 'Holstebro', '7500'),
    ('Åbyvej 3', 'Glostrup', '2600'),
    ('Vestvejen 9', 'Brøndby', '2605'),
    ('Havnevej 8', 'Nykøbing Falster', '4800'),
    ('Lystbådevej 4', 'Rønne', '3700'),
    ('Adelgade 10', 'Thisted', '7700'),
    ('Møllevej 16', 'Grenaa', '8500'),
    ('Højgade 22', 'Frederikshavn', '9900'),
    ('Dalvej 6', 'Haderslev', '6100'),
    ('Vestre Boulevard 14', 'Ringkøbing', '6950'),
    ('Klostervej 9', 'Nørresundby', '9400'),
    ('Ålborgvej 15', 'Hobro', '9500'),
    ('Rådhuspladsen 3', 'Ringsted', '4100'),
    ('Lindevangsvej 7', 'Assens', '5610'),
    ('Kirkevej 20', 'Billund', '7190'),
    ('Havnevej 2', 'Tønder', '6270'),
    ('Grønnevej 13', 'Sorø', '4180'),
    ('Nyvej 5', 'Kalundborg', '4400'),
    ('Skovbakken 3', 'Hjørring', '9800'),
    ('Torvet 8', 'Ballerup', '2750'),
    ('Skovgade 6', 'Ebeltoft', '8400'),
    ('Århusvej 4', 'Samsø', '8305'),
    ('Fælledvej 11', 'Lemvig', '7620');

-- Insert 50 renters with CPR numbers, ensuring the month is valid and the individuals are at least 18 years old
INSERT INTO Renter (firstname, lastname, email, phone_number, cpr_number, reg_number, account_number, location_id)
VALUES
    ('John', 'Doe', 'john.doe@example.com', '12345678', '010598-1234', '1001', '5678901234', 1),
    ('Jane', 'Smith', 'jane.smith@example.com', '87654321', '020697-5678', '1002', '4321098765', 2),
    ('Alice', 'Johnson', 'alice.johnson@example.com', '23456789', '030495-9101', '1003', '3456789012', 3),
    ('Bob', 'Williams', 'bob.williams@example.com', '34567890', '040492-2345', '1004', '1234567890', 4),
    ('Clara', 'Brown', 'clara.brown@example.com', '45678901', '050391-6789', '1005', '2345678901', 5),
    ('David', 'Taylor', 'david.taylor@example.com', '56789012', '060289-3456', '1006', '5678901234', 6),
    ('Emma', 'Anderson', 'emma.anderson@example.com', '67890123', '070188-7890', '1007', '8765432109', 7),
    ('Frank', 'Thomas', 'frank.thomas@example.com', '78901234', '080187-1234', '1008', '7890123456', 8),
    ('Grace', 'Harris', 'grace.harris@example.com', '89012345', '090086-5678', '1009', '6543210987', 9),
    ('Henry', 'Martin', 'henry.martin@example.com', '90123456', '100785-9012', '1010', '5432109876', 10),
    ('Ivy', 'Garcia', 'ivy.garcia@example.com', '12345678', '110684-3456', '1011', '4321098765', 11),
    ('Jack', 'Martinez', 'jack.martinez@example.com', '23456789', '120583-7890', '1012', '3210987654', 12),
    ('Kelly', 'Clark', 'kelly.clark@example.com', '34567890', '130482-1234', '1013', '2109876543', 13),
    ('Liam', 'Lewis', 'liam.lewis@example.com', '45678901', '140381-5678', '1014', '1098765432', 14),
    ('Mia', 'Walker', 'mia.walker@example.com', '56789012', '150280-9012', '1015', '0987654321', 15),
    ('Nathan', 'Allen', 'nathan.allen@example.com', '67890123', '160179-3456', '1016', '9876543210', 16),
    ('Olivia', 'Young', 'olivia.young@example.com', '78901234', '170078-7890', '1017', '8765432109', 17),
    ('Paul', 'King', 'paul.king@example.com', '89012345', '180977-1234', '1018', '7654321098', 18),
    ('Quinn', 'Scott', 'quinn.scott@example.com', '90123456', '190876-5678', '1019', '6543210987', 19),
    ('Rose', 'Miller', 'rose.miller@example.com', '12345678', '200775-9101', '1020', '5432109876', 20),
    ('Sam', 'Davis', 'sam.davis@example.com', '23456789', '210674-2345', '1021', '4321098765', 21),
    ('Tina', 'Lopez', 'tina.lopez@example.com', '34567890', '220573-6789', '1022', '3210987654', 22),
    ('Ursula', 'Gonzalez', 'ursula.gonzalez@example.com', '45678901', '230472-1234', '1023', '2109876543', 23),
    ('Victor', 'Perez', 'victor.perez@example.com', '56789012', '240371-5678', '1024', '1098765432', 24),
    ('Wendy', 'Roberts', 'wendy.roberts@example.com', '67890123', '250270-9012', '1025', '0987654321', 25),
    ('Xander', 'Lopez', 'xander.lopez@example.com', '78901234', '260169-3456', '1026', '9876543210', 26),
    ('Yara', 'Evans', 'yara.evans@example.com', '89012345', '270068-7890', '1027', '8765432109', 27),
    ('Zane', 'Hughes', 'zane.hughes@example.com', '90123456', '280967-1234', '1028', '7654321098', 28),
    ('Adam', 'Reed', 'adam.reed@example.com', '12345678', '290866-5678', '1029', '6543210987', 29),
    ('Beth', 'Carter', 'beth.carter@example.com', '23456789', '300765-9101', '1030', '5432109876', 30),
    ('Caleb', 'Morris', 'caleb.morris@example.com', '34567890', '010665-2345', '1031', '4321098765', 31),
    ('Diana', 'Shaw', 'diana.shaw@example.com', '45678901', '020564-6789', '1032', '3210987654', 32),
    ('Ethan', 'Ward', 'ethan.ward@example.com', '56789012', '030463-1234', '1033', '2109876543', 33),
    ('Fiona', 'King', 'fiona.king@example.com', '67890123', '040362-5678', '1034', '1098765432', 34),
    ('Graham', 'Hall', 'graham.hall@example.com', '78901234', '050261-9012', '1035', '0987654321', 35),
    ('Holly', 'Allen', 'holly.allen@example.com', '89012345', '060160-3456', '1036', '9876543210', 36),
    ('Iris', 'Young', 'iris.young@example.com', '90123456', '070059-7890', '1037', '8765432109', 37),
    ('Jake', 'Scott', 'jake.scott@example.com', '12345678', '080958-1234', '1038', '7654321098', 38),
    ('Kara', 'Robinson', 'kara.robinson@example.com', '23456789', '090857-5678', '1039', '6543210987', 39),
    ('Liam', 'Martinez', 'liam.martinez@example.com', '34567890', '100756-9101', '1040', '5432109876', 40),
    ('Megan', 'Harris', 'megan.harris@example.com', '45678901', '110655-2345', '1041', '4321098765', 41),
    ('Nina', 'Garcia', 'nina.garcia@example.com', '56789012', '120554-6789', '1042', '3210987654', 42),
    ('Oscar', 'Roberts', 'oscar.roberts@example.com', '67890123', '130453-1234', '1043', '2109876543', 43),
    ('Penny', 'Walker', 'penny.walker@example.com', '78901234', '140352-5678', '1044', '1098765432', 44),
    ('Quinn', 'Morris', 'quinn.morris@example.com', '89012345', '150251-9012', '1045', '0987654321', 45),
    ('Rita', 'Adams', 'rita.adams@example.com', '90123456', '160150-3456', '1046', '9876543210', 46),
    ('Steve', 'Davis', 'steve.davis@example.com', '12345678', '170049-7890', '1047', '8765432109', 47),
    ('Tracy', 'Lopez', 'tracy.lopez@example.com', '23456789', '180948-1234', '1048', '7654321098', 48),
    ('Ursula', 'Miller', 'ursula.miller@example.com', '34567890', '190847-5678', '1049', '6543210987', 49),
    ('Vince', 'Perez', 'vince.perez@example.com', '45678901', '200746-9101', '1050', '5432109876', 50);

INSERT INTO subscriptionAddon (name, price_per_month)
VALUES
    ('Afleveringsforsikring', 119.00),
    ('Lav selvrisiko', 89.00),
    ('Dæk leje', 285.00),
    ('Viking- vejhjælp', 49.00);


INSERT INTO kilometer_options (kilometers_per_month, price_per_month)
VALUES (1500, 0.00),
       (1750, 300.00),
       (2000, 590.00),
       (2500, 1160.00),
       (3000, 1710.00),
       (3500, 2240.00),
       (4000, 2750.00),
       (4500, 3240.00);

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

INSERT INTO power_source (name)
VALUES ('Benzin'),
       ('Diesel'),
       ('Elektrisk'),
       ('Hybrid');


INSERT INTO transmission_type (name)
VALUES ('Manuel'),
       ('Automatisk'),
       ('Semi-automatisk');


INSERT INTO equipment_level (name)
VALUES ('Basis'),
       ('Komfort'),
       ('Sport'),
       ('Luksus');


INSERT INTO brand (name) VALUES
                             ('Citroën'),
                             ('Fiat'),
                             ('Peugeot'),
                             ('Polestar');


INSERT INTO car_model (model_name, brand_id) VALUES
                                                 ('C1 Le Mans', 1),
                                                 ('C3 VTR Sport', 1),
                                                 ('C5 Aircross Prestige', 1),
                                                 ('500C Connect', 2),
                                                 ('500 Cabriolet Dolcevita', 2),
                                                 ('500 Cabriolet Vita Comfort', 2),
                                                 ('500 Cabriolet Bellavita', 2),
                                                 ('500e Icon Pack', 2),
                                                 ('500e CABRIO La Prima', 2),
                                                 ('e-2008 GT Line', 3),
                                                 ('2008 Allure', 3),
                                                 ('E-3008 GT', 3),
                                                 ('2 Long Range Dual Motor Performance Pack', 4);


INSERT INTO car (image_url, vehicle_number, vin_number, car_model_id, equipment_level_id, power_source_id, transmission_type_id, net_price, registration_tax, co2_emissions, car_status_id)
VALUES
    ('/images/pnvorrmtnq1wjf42i4rc.jpg', 'PNV123456', 'VIN1234567890', 1, 1, 1, 1, 15000.00, 2000.00, 120.00, 1),
    ('/images/cdfem4jk5dyrlmxqjgix.jpg', 'CDF123456', 'VIN2345678901', 2, 2, 1, 2, 18000.00, 2500.00, 140.00, 1),
    ('/images/slvpchujbacrfltszlns.jpg', 'SLV123456', 'VIN3456789012', 3, 3, 2, 2, 30000.00, 4000.00, 160.00, 1),
    ('/images/wn7o1exkl34h3xm82jxg.jpg', 'FIAT123456', 'VIN4567890123', 4, 1, 1, 1, 12000.00, 1800.00, 110.00, 1),
    ('/images/utxdwfbv2btg4ss5hopj.jpg', 'FIAT123457', 'VIN5678901234', 5, 2, 1, 2, 14000.00, 2100.00, 115.00, 1),
    ('/images/sbsmxmk5hy7tnzcuylt0.jpg', 'FIAT123458', 'VIN6789012345', 6, 3, 1, 2, 14500.00, 2200.00, 120.00, 1),
    ('/images/bdts4d2z09jypedmub8u.jpg', 'FIAT123459', 'VIN7890123456', 7, 2, 2, 2, 15000.00, 2300.00, 125.00, 1),
    ('/images/lhzxdoniininbpmtehs3.jpg', 'FIAT123460', 'VIN8901234567', 8, 3, 3, 2, 25000.00, 3000.00, 80.00, 1),
    ('/images/obi3inumn2ceunaxxy1h.jpg', 'FIAT123461', 'VIN9012345678', 9, 3, 3, 2, 26000.00, 3200.00, 85.00, 1),
    ('/images/yvadaz3k7bqsf4kk6dit.jpg', 'PEUG123456', 'VIN0123456789', 10, 2, 3, 2, 28000.00, 3500.00, 100.00, 1),
    ('/images/dsd7r7ctrc1sxzgzd4al.jpg', 'PEUG123457', 'VIN1234567890', 11, 1, 3, 2, 22000.00, 2700.00, 110.00, 1),
    ('/images/ntvy28bwzctxshinupxz.jpg', 'PEUG123458', 'VIN2345678901', 12, 3, 3, 2, 35000.00, 4200.00, 90.00, 1),
    ('/images/jvlzzkgtqbydaika503u.jpg', 'POL123456', 'VIN3456789012', 13, 3, 3, 2, 60000.00, 5000.00, 60.00, 1),

    ('/images/pnvorrmtnq1wjf42i4rc.jpg', 'PNV35946', 'VIN2394851023', 1, 1, 1, 1, 15000.00, 2000.00, 120.00, 1),
    ('/images/cdfem4jk5dyrlmxqjgix.jpg', 'CDF75253', 'VIN4581023948', 2, 2, 1, 2, 18000.00, 2500.00, 140.00, 1),
    ('/images/slvpchujbacrfltszlns.jpg', 'SLV19742', 'VIN5637284901', 3, 3, 2, 2, 30000.00, 4000.00, 160.00, 1),
    ('/images/wn7o1exkl34h3xm82jxg.jpg', 'FIAT75525', 'VIN8391027465', 4, 1, 1, 1, 12000.00, 1800.00, 110.00, 1),
    ('/images/utxdwfbv2btg4ss5hopj.jpg', 'FIAT15642', 'VIN1029384756', 5, 2, 1, 2, 14000.00, 2100.00, 115.00, 1),
    ('/images/sbsmxmk5hy7tnzcuylt0.jpg', 'FIAT75789', 'VIN6758492031', 6, 3, 1, 2, 14500.00, 2200.00, 120.00, 1),
    ('/images/bdts4d2z09jypedmub8u.jpg', 'FIAT347841', 'VIN3948571026', 7, 2, 2, 2, 15000.00, 2300.00, 125.00, 1),
    ('/images/lhzxdoniininbpmtehs3.jpg', 'FIAT65472', 'VIN5829304716', 8, 3, 3, 2, 25000.00, 3000.00, 80.00, 1),
    ('/images/obi3inumn2ceunaxxy1h.jpg', 'FIAT074256', 'VIN7465839201', 9, 3, 3, 2, 26000.00, 3200.00, 85.00, 1),
    ('/images/yvadaz3k7bqsf4kk6dit.jpg', 'PEUG36753', 'VIN2039485761', 10, 2, 3, 2, 28000.00, 3500.00, 100.00, 1),
    ('/images/dsd7r7ctrc1sxzgzd4al.jpg', 'PEUG37584', 'VIN9173652048', 11, 1, 3, 2, 22000.00, 2700.00, 110.00, 1),
    ('/images/ntvy28bwzctxshinupxz.jpg', 'PEUG235468', 'VIN3859201746', 12, 3, 3, 2, 35000.00, 4200.00, 90.00, 1),
    ('/images/jvlzzkgtqbydaika503u.jpg', 'POL28634', 'VIN4756190832', 13, 3, 3, 2, 60000.00, 5000.00, 60.00, 1),

    ('/images/pnvorrmtnq1wjf42i4rc.jpg', 'PNV812325', 'VIN6502839471', 1, 1, 1, 1, 15000.00, 2000.00, 120.00, 1),
    ('/images/cdfem4jk5dyrlmxqjgix.jpg', 'CDF34815', 'VIN8239450617', 2, 2, 1, 2, 18000.00, 2500.00, 140.00, 1),
    ('/images/slvpchujbacrfltszlns.jpg', 'SLV65126', 'VIN2103948756', 3, 3, 2, 2, 30000.00, 4000.00, 160.00, 1),
    ('/images/wn7o1exkl34h3xm82jxg.jpg', 'FIAT5631', 'VIN3958207461', 4, 1, 1, 1, 12000.00, 1800.00, 110.00, 1),
    ('/images/utxdwfbv2btg4ss5hopj.jpg', 'FIAT234657', 'VIN4819263750', 5, 2, 1, 2, 14000.00, 2100.00, 115.00, 1),
    ('/images/sbsmxmk5hy7tnzcuylt0.jpg', 'FIAT68438', 'VIN5647389201', 6, 3, 1, 2, 14500.00, 2200.00, 120.00, 1),
    ('/images/bdts4d2z09jypedmub8u.jpg', 'FIAT14823', 'VIN8291038476', 7, 2, 2, 2, 15000.00, 2300.00, 125.00, 1),
    ('/images/lhzxdoniininbpmtehs3.jpg', 'FIAT586353', 'VIN7482930165', 8, 3, 3, 2, 25000.00, 3000.00, 80.00, 1),
    ('/images/obi3inumn2ceunaxxy1h.jpg', 'FIAT655243', 'VIN2039487516', 9, 3, 3, 2, 26000.00, 3200.00, 85.00, 1),
    ('/images/yvadaz3k7bqsf4kk6dit.jpg', 'PEUG54386', 'VIN5938271046', 10, 2, 3, 2, 28000.00, 3500.00, 100.00, 1),
    ('/images/dsd7r7ctrc1sxzgzd4al.jpg', 'PEUG978723', 'VIN7482916305', 11, 1, 3, 2, 22000.00, 2700.00, 110.00, 1),
    ('/images/ntvy28bwzctxshinupxz.jpg', 'PEUG18798', 'VIN9238471056', 12, 3, 3, 2, 35000.00, 4200.00, 90.00, 1),
    ('/images/jvlzzkgtqbydaika503u.jpg', 'POL19542', 'VIN3827164509', 13, 3, 3, 2, 60000.00, 5000.00, 60.00, 1),

    ('/images/pnvorrmtnq1wjf42i4rc.jpg', 'PNV78258', 'VIN5648293107', 1, 1, 1, 1, 15000.00, 2000.00, 120.00, 1),
    ('/images/cdfem4jk5dyrlmxqjgix.jpg', 'CDF55587', 'VIN2839471056', 2, 2, 1, 2, 18000.00, 2500.00, 140.00, 1),
    ('/images/slvpchujbacrfltszlns.jpg', 'SLV9572', 'VIN7502938471', 3, 3, 2, 2, 30000.00, 4000.00, 160.00, 1),
    ('/images/wn7o1exkl34h3xm82jxg.jpg', 'FIAT9725', 'VIN9203847561', 4, 1, 1, 1, 12000.00, 1800.00, 110.00, 1),
    ('/images/utxdwfbv2btg4ss5hopj.jpg', 'FIAT97254', 'VIN3748291056', 5, 2, 1, 2, 14000.00, 2100.00, 115.00, 1),
    ('/images/sbsmxmk5hy7tnzcuylt0.jpg', 'FIAT4528', 'VIN8492037651', 6, 3, 1, 2, 14500.00, 2200.00, 120.00, 1),
    ('/images/bdts4d2z09jypedmub8u.jpg', 'FIAT87254', 'VIN9208473156', 7, 2, 2, 2, 15000.00, 2300.00, 125.00, 1),
    ('/images/lhzxdoniininbpmtehs3.jpg', 'FIAT782540', 'VIN5647381029', 8, 3, 3, 2, 25000.00, 3000.00, 80.00, 1),
    ('/images/obi3inumn2ceunaxxy1h.jpg', 'FIAT197254', 'VIN8392041756', 9, 3, 3, 2, 26000.00, 3200.00, 85.00, 1),
    ('/images/yvadaz3k7bqsf4kk6dit.jpg', 'PEUG1278386', 'VIN4756103948', 10, 2, 3, 2, 28000.00, 3500.00, 100.00, 1),
    ('/images/dsd7r7ctrc1sxzgzd4al.jpg', 'PEUG57857', 'VIN2039487564', 11, 1, 3, 2, 22000.00, 2700.00, 110.00, 1),
    ('/images/ntvy28bwzctxshinupxz.jpg', 'PEUG157378', 'VIN5829374106', 12, 3, 3, 2, 35000.00, 4200.00, 90.00, 1),
    ('/images/jvlzzkgtqbydaika503u.jpg', 'POL128727586', 'VIN7465029381', 13, 3, 3, 2, 60000.00, 5000.00, 60.00, 1),

    ('/images/pnvorrmtnq1wjf42i4rc.jpg', 'PNV17528976', 'VIN9384751206', 1, 1, 1, 1, 15000.00, 2000.00, 120.00, 1),
    ('/images/cdfem4jk5dyrlmxqjgix.jpg', 'CDF1782556', 'VIN4938271056', 2, 2, 1, 2, 18000.00, 2500.00, 140.00, 1),
    ('/images/slvpchujbacrfltszlns.jpg', 'SLV1972356', 'VIN8291047563', 3, 3, 2, 2, 30000.00, 4000.00, 160.00, 1),
    ('/images/wn7o1exkl34h3xm82jxg.jpg', 'FIAT196356', 'VIN3209485716', 4, 1, 1, 1, 12000.00, 1800.00, 110.00, 1),
    ('/images/utxdwfbv2btg4ss5hopj.jpg', 'FIAT18753', 'VIN5038472619', 5, 2, 1, 2, 14000.00, 2100.00, 115.00, 1),
    ('/images/sbsmxmk5hy7tnzcuylt0.jpg', 'FIAT87358', 'VIN9103827456', 6, 3, 1, 2, 14500.00, 2200.00, 120.00, 1),
    ('/images/bdts4d2z09jypedmub8u.jpg', 'FIAT1578', 'VIN2839405617', 7, 2, 2, 2, 15000.00, 2300.00, 125.00, 1),
    ('/images/lhzxdoniininbpmtehs3.jpg', 'FIAT154380', 'VIN6573948201', 8, 3, 3, 2, 25000.00, 3000.00, 80.00, 1),
    ('/images/obi3inumn2ceunaxxy1h.jpg', 'FIAT129255', 'VIN5647382910', 9, 3, 3, 2, 26000.00, 3200.00, 85.00, 1);