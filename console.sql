CREATE DATABASE restaurant_management;
USE restaurant_management;

CREATE TABLE Users (
    userID INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL
);


CREATE TABLE Customers (
    customerID INT PRIMARY KEY AUTO_INCREMENT,
    bookingID INT,
    FOREIGN KEY (customerID) REFERENCES Users(userID),
    FOREIGN KEY (bookingID) REFERENCES Bookings(bookingID)
);

CREATE TABLE Waiters (
    waiterID INT PRIMARY KEY AUTO_INCREMENT,
    FOREIGN KEY (waiterID) REFERENCES Users(userID)
);

CREATE TABLE Supervisors (
    supervisorID INT PRIMARY KEY,
    FOREIGN KEY (supervisorID) REFERENCES Users(userID) ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE Tables (
    tableID INT AUTO_INCREMENT PRIMARY KEY,
    tableName VARCHAR(50),
    capacity INT,
    view ENUM('sea view', 'garden view', 'city view') NOT NULL
);

CREATE TABLE TableSlots (
    slotID INT AUTO_INCREMENT PRIMARY KEY,
    tableID INT,
    slotType ENUM('morning', 'afternoon', 'evening') NOT NULL,
    isAvailable BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (tableID) REFERENCES Tables(tableID),
    UNIQUE(tableID, slotType)
);

CREATE TABLE Menu (
    menuID INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    price DECIMAL(10,2) NOT NULL
);


CREATE TABLE Bookings (
    bookingID INT AUTO_INCREMENT PRIMARY KEY,
    customerID INT NOT NULL,
    slotID INT NOT NULL,
    menuID INT DEFAULT NULL,
    paymentAmount DECIMAL(10, 2) DEFAULT NULL,
    isPaid BOOLEAN DEFAULT FALSE,
    status ENUM('active', 'closed', 'cancelled') DEFAULT 'active',
    FOREIGN KEY (customerID) REFERENCES Customers(customerID),
    FOREIGN KEY (slotID) REFERENCES TableSlots(slotID),
    FOREIGN KEY (menuID) REFERENCES Menu(menuID)
);

CREATE TABLE Orders (
    orderID INT AUTO_INCREMENT PRIMARY KEY,
    bookingID INT NOT NULL,
    menuID INT NOT NULL,
    status ENUM('ordered', 'preparing', 'ready', 'delivered') DEFAULT 'ordered',
    FOREIGN KEY (bookingID) REFERENCES Bookings(bookingID),
    FOREIGN KEY (menuID) REFERENCES Menu(menuID)
);
