DROP DATABASE tirian_trains;
CREATE DATABASE tirian_trains;
USE tirian_trains;

-- all deletion of foreign keys is assumed to be restricted
-- Some tables are created in a certain order in order to avoid 
-- having some foreign keys point to uninitialized primary keys

CREATE TABLE TRAIN_MODEL(
	train_model_code INT NOT NULL UNIQUE PRIMARY KEY DEFAULT 0,
	name VARCHAR(255) NOT NULL,
	max_speed FLOAT UNSIGNED NOT NULL,
	no_of_seats INT UNSIGNED NOT NULL,
	no_of_toilets INT UNSIGNED NOT NULL,
	reclining_seats BOOLEAN NOT NULL,
	foldable_table BOOLEAN NOT NULL,
	disability_access BOOLEAN NOT NULL,
	luggage_storage BOOLEAN NOT NULL,
	vending_machines BOOLEAN NOT NULL,
	food_service BOOLEAN NOT NULL,
	series_type ENUM('S-train', 'A-train')
);
CREATE TABLE TRAIN(
	train_id INT NOT NULL UNIQUE PRIMARY KEY DEFAULT 0,
	train_model_code INT NOT NULL,
	FOREIGN KEY (train_model_code) REFERENCES train_model(train_model_code)
	ON DELETE RESTRICT
);
CREATE TABLE CREW(
	crew_id INT NOT NULL UNIQUE PRIMARY KEY,
	given VARCHAR(255),
	mi VARCHAR(255),
	last VARCHAR(255)
);
CREATE TABLE MAINTENANCE_HISTORY(
	maintenance_id INT NOT NULL UNIQUE PRIMARY KEY DEFAULT 0, 
	train_id INT NOT NULL,
	crew_id INT NOT NULL,
	month INT NOT NULL,
	day INT NOT NULL,
	year INT NOT NULL,
	curr_condition VARCHAR(255), -- changed condition to curr_condition due to syntax errors in naming "condition"
	FOREIGN KEY(train_id) REFERENCES TRAIN(train_id) ON DELETE RESTRICT,
	FOREIGN KEY(crew_id) REFERENCES CREW(crew_id) ON DELETE RESTRICT
);
CREATE TABLE CUSTOMER(
	customer_id INT NOT NULL AUTO_INCREMENT UNIQUE PRIMARY KEY,
	given_name VARCHAR(255) NOT NULL,
	middle_initial CHAR(5) NOT NULL,
	last_name VARCHAR(255) NOT NULL,
	birth_month INT NOT NULL,
	day_of_birth INT NOT NULL,
	birth_year INT NOT NULL,
	gender ENUM('Male', 'Female')
);

CREATE TABLE TOWN(
	town_id INT NOT NULL UNIQUE PRIMARY KEY DEFAULT 0,
	name VARCHAR(255) NOT NULL
);

CREATE TABLE STATION(
	station_id INT NOT NULL UNIQUE PRIMARY KEY DEFAULT 0,
	name VARCHAR(255) NOT NULL,
	town_id INT NOT NULL,
	FOREIGN KEY(town_id)
	REFERENCES TOWN(town_id)
);

CREATE TABLE ROUTE(
	route_id INT NOT NULL UNIQUE PRIMARY KEY DEFAULT 0,
	travel_duration INT UNSIGNED NOT NULL,
	travel_cost INT UNSIGNED DEFAULT 2,
    origin_station_id INT NOT NULL,
	destination_station_id INT NOT NULL,
	FOREIGN KEY(origin_station_id)
	REFERENCES STATION(station_id)
    ON DELETE RESTRICT,
	FOREIGN KEY(destination_station_id)
	REFERENCES STATION(station_id)
    ON DELETE RESTRICT
);
CREATE TABLE TRIP_SCHEDULE(
	schedule_id INT NOT NULL UNIQUE PRIMARY KEY DEFAULT 0, 
	month INT NOT NULL,
	day INT NOT NULL,
	year INT NOT NULL,
	departure_hour INT NOT NULL,
	departure_minute INT NOT NULL,
	arrival_hour INT NOT NULL,
	arrival_minute INT NOT NULL,
	route_id INT NOT NULL,
    train_id INT NOT NULL,
	FOREIGN KEY(route_id)
    REFERENCES ROUTE(route_id)
    ON DELETE RESTRICT,
    FOREIGN KEY(train_id)
    REFERENCES TRAIN(train_id)
    ON DELETE RESTRICT
);

CREATE TABLE TICKET(
	ticket_number INT NOT NULL UNIQUE PRIMARY KEY DEFAULT 0,
	customer_id INT NOT NULL,
	date_purchased_month VARCHAR(255) NOT NULL,
	date_purchased_day INT NOT NULL,
	date_purchased_year INT NOT NULL,
	FOREIGN KEY(customer_id)
	REFERENCES CUSTOMER(customer_id)
	ON DELETE RESTRICT
);

CREATE TABLE TASK(
	task_code INT NOT NULL UNIQUE PRIMARY KEY,
	description VARCHAR(255)
);

CREATE TABLE TRIP_ASSIGNMENT(
	ticket_number INT NOT NULL REFERENCES TICKET(ticket_number),
	schedule_id INT NOT NULL REFERENCES TRIP_SCHEDULE(schedule_id),
	PRIMARY KEY(ticket_number, schedule_id)
);

CREATE TABLE TASK_DOING(
	maintenance_id INT NOT NULL REFERENCES MAINTENANCE_HISTORY(maintenance_id),
	task_code INT NOT NULL REFERENCES task(task_code),
	PRIMARY KEY(maintenance_id,task_code)
);