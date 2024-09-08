# Hotel Reservation System

This is a simple command-line-based Hotel Reservation System built using Java and JDBC for managing hotel reservations, including room bookings, updates, deletions, and viewing reservations from a MySQL database.

## Features

- **Reserve Room**: Add a reservation for a guest with details like name, room number, and contact information.
- **View Reservations**: Display all current reservations.
- **Update Reservation**: Modify an existing reservation’s details such as guest name, room number, and contact information.
- **Delete Reservation**: Remove a reservation from the system.
- **Find Room by Reservation**: Retrieve room details by reservation number and guest name.
- **Exit**: Gracefully exit the system with a short goodbye message.

## Prerequisites

1. **Java**: Ensure Java 8 or higher is installed.
2. **MySQL**: MySQL server should be installed and running.
3. **JDBC Driver**: The MySQL JDBC connector must be added to your classpath. Download from [here](https://dev.mysql.com/downloads/connector/j/).

## Setup

### Database Setup

1. Install MySQL and create a database named `hotel_db`.
2. Create the required `reservations` table using the following SQL script:
   ```sql
   CREATE DATABASE hotel_db;

   USE hotel_db;

   CREATE TABLE reservations (
       res_id INT AUTO_INCREMENT PRIMARY KEY,
       guest_name VARCHAR(100),
       room_no INT,
       contact_no VARCHAR(15),
       reservation_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
   );
