-- 1. Register user as a customer
INSERT INTO Users (password, username)
VALUES ("testpass",'testcustomer1') ;

-- 2. Add a new table
INSERT INTO Tables (tableName, capacity, view) VALUES ('T1', 4, 'garden view');
INSERT INTO Tables (tableName, capacity, view) VALUES ('T2', 2, 'garden view');

-- 3. Add a table slot
INSERT INTO TableSlots (tableID, slotType) VALUES (1, 'evening');  -- assuming tableID = 1
INSERT INTO TableSlots (tableID, slotType) VALUES (2, 'morning');
-- 4. Add menu items
INSERT INTO Menu (name, price) VALUES ('Breakfast', 25.99), ('Lunch', 15.50),("Dinner", 33.29), ("Special Dinner", 45.99);
