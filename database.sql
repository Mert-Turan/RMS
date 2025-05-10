CREATE DATABASE restaurant_management;
USE restaurant_management;

CREATE TABLE Users (
    userID INT AUTO_INCREMENT PRIMARY KEY,
    password VARCHAR(50) UNIQUE NOT NULL,
    username VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE Customers (
    customerID INT PRIMARY KEY,
    FOREIGN KEY (customerID) REFERENCES Users(userID)
);

CREATE TABLE Waiters (
    waiterID INT PRIMARY KEY ,
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


CREATE TABLE Reservation (
    reservationID INT AUTO_INCREMENT PRIMARY KEY,
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
    reservationID INT NOT NULL,
    menuID INT NOT NULL,
    status ENUM('ordered', 'preparing', 'ready', 'delivered') DEFAULT 'ordered',
    FOREIGN KEY (reservationID) REFERENCES Reservation(reservationID),
    FOREIGN KEY (menuID) REFERENCES Menu(menuID)
);

-- Rezervasyon yapildiginda rezerve edilen masanin o zaman dilimi dolu olarak guncellenir
DELIMITER $$
CREATE TRIGGER trg_reserve_slot
AFTER INSERT ON Reservation
FOR EACH ROW
BEGIN
    UPDATE TableSlots
    SET isAvailable = FALSE
    WHERE slotID = NEW.slotID;
END$$
DELIMITER ;

-- masa müsait değilse customer rezerve edemesin

DELIMITER $$
CREATE TRIGGER trg_prevent_reservation_unavailable_slot
BEFORE INSERT ON Reservation
FOR EACH ROW
BEGIN
    IF (SELECT isAvailable FROM TableSlots WHERE slotID = NEW.slotID) = FALSE THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'This time slot is not available for reservation.';
    END IF;
END$$
DELIMITER ;


-- supervisior payment ödenmişse rezervasyonu kapatırsa tableın o slotu available olarak güncellenir
CREATE TRIGGER trg_close_reservation
AFTER UPDATE ON Reservation
FOR EACH ROW
BEGIN
    IF NEW.status = 'closed' AND OLD.status != 'closed' THEN
        UPDATE TableSlots
        SET isAvailable = TRUE
        WHERE slotID = NEW.slotID;
    END IF;
END;


-- kullanıcı menü seçince seçilen menünün fiyatı payment amount olarak reservation bilgilerine eklenir
CREATE TRIGGER trg_set_payment_amount
BEFORE UPDATE ON Reservation
FOR EACH ROW
BEGIN
    IF NEW.menuID IS NOT NULL AND (OLD.menuID IS NULL OR OLD.menuID != NEW.menuID) THEN
        SET NEW.paymentAmount = (SELECT price FROM Menu WHERE menuID = NEW.menuID);
    END IF;
END;

-- customer rezervasyonu iptal ederse masanın o time slotu available olarak güncellenir
CREATE TRIGGER trg_reservation_delete
AFTER DELETE ON Reservation
FOR EACH ROW
BEGIN
    UPDATE TableSlots
    SET isAvailable = TRUE
    WHERE slotID = OLD.slotID;
END;


-- customer kendi rezervasyonlarını görüntülemesi
SELECT
    reservationID,
    (SELECT tableName FROM Tables WHERE tableID = (
        SELECT tableID FROM TableSlots WHERE slotID = r.slotID
    )) AS tableName,
    (SELECT slotType FROM TableSlots WHERE slotID = r.slotID) AS slotType,
    (SELECT name FROM Menu WHERE menuID = r.menuID) AS menuName,
    paymentAmount,
    isPaid,
    status
FROM Reservation r
WHERE customerID = ?;


-- customer siparişlerini görüntülemesi
SELECT
    orderID,
    status,
    (SELECT name FROM Menu WHERE menuID = o.menuID) AS menuName
FROM Orders o
WHERE reservationID IN (
    SELECT reservationID FROM Reservation WHERE customerID = ?
);

-- supervisior tüm bookingleri görüntülemesi
SELECT
    reservationID,
    isPaid,
    status
FROM Reservation;


-- waiter order orderin statusu ve aıt oldugu masayı görüntülemesi
SELECT
    o.orderID,
    (SELECT name FROM Menu WHERE menuID = o.menuID) AS menuName,
    o.status,
    (SELECT tableName FROM Tables WHERE tableID = (
        SELECT tableID FROM TableSlots WHERE slotID = (
                SELECT slotID FROM Reservation WHERE reservationID = o.reservationID
        )
    )) AS tableName
FROM Orders o;

-- customer payment ödedikten sonra isPaid true olarak güncellenmesi
UPDATE Reservation
SET isPaid = TRUE
WHERE reservationID = ?;


-- customer table bilgilerini görüntüleyebilmesi
SELECT
    ts.slotID,
    (SELECT tableName FROM Tables WHERE tableID = ts.tableID) AS tableName,
    (SELECT capacity FROM Tables WHERE tableID = ts.tableID) AS capacity,
    (SELECT view FROM Tables WHERE tableID = ts.tableID) AS view,
    ts.slotType,
    ts.isAvailable
FROM TableSlots ts;


-- customer menüyü görüntülemesi
    SELECT menuID, name, price FROM Menu;


-- supervisior ödenmemiş bir rezervasyonu kapamaya çalışırsa uyarı vermeli
DELIMITER $$

CREATE TRIGGER trg_prevent_unpaid_closing
BEFORE UPDATE ON Reservation
FOR EACH ROW
BEGIN
    IF NEW.status = 'closed' AND OLD.status != 'closed' AND OLD.isPaid = FALSE THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Cannot close reservation before payment is made.';
    END IF;
END$$

DELIMITER ;

-- customer table ve menü seçip reservation oluşturduktan sonra order oluşur
DELIMITER $$

CREATE TRIGGER trg_create_order_after_reservation
AFTER INSERT ON Reservation
FOR EACH ROW
BEGIN

    IF NEW.menuID IS NOT NULL THEN
        INSERT INTO Orders (reservationID, menuID)
        VALUES (NEW.reservationID, NEW.menuID);
    END IF;
END$$

DELIMITER ;

-- waiterın orders statusunu güncellemesi
UPDATE Orders SET status = 'preparing' WHERE orderID = ?;
UPDATE Orders SET status = 'ready' WHERE orderID = ?;
UPDATE Orders SET status = 'delivered' WHERE orderID = ?;