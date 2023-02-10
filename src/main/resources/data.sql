INSERT INTO facility(name, price, area, description)
VALUES ('restaurant', 500, 200, 'restaurant in the city center ');
INSERT INTO facility(name, price, area, description)
VALUES ('bowling', 800, 400, 'one bowling lane');
INSERT INTO facility(name, price, area, description)
VALUES ('soccer arena', 1500, 700, 'indoor soccer arena');
INSERT INTO facility(name, price, area, description)
VALUES ('karting', 1900, 1200, 'karting track');
INSERT INTO facility(name, price, area, description)
VALUES ('apartment room', 300, 40, 'apartment for two near the sea');

INSERT INTO client(name)
VALUES ('John');
INSERT INTO client(name)
VALUES ('Olivia');
INSERT INTO client(name)
VALUES ('Charlotte');
INSERT INTO client(name)
VALUES ('Lucas');
INSERT INTO client(name)
VALUES ('Henry');
INSERT INTO client(name)
VALUES ('William');
INSERT INTO client(name)
VALUES ('Sophia');
INSERT INTO client(name)
VALUES ('Emma');
INSERT INTO client(name)
VALUES ('Ewa');
INSERT INTO client(name)
VALUES ('Liam');
INSERT INTO client(name)
VALUES ('Benjamin');

INSERT INTO reservation(start_date, end_date, facility_id, client_id, cost)
VALUES ('2023-01-11', '2023-01-13', 2, 2, 1600);
INSERT INTO reservation(start_date, end_date, facility_id, client_id, cost)
VALUES ('2023-01-21', '2023-01-22', 2, 3, 800);
INSERT INTO reservation(start_date, end_date, facility_id, client_id, cost)
VALUES ('2023-01-22', '2023-01-23', 5, 6, 300);
INSERT INTO reservation(start_date, end_date, facility_id, client_id, cost)
VALUES ('2023-01-23', '2023-01-25', 5, 4, 600);
INSERT INTO reservation(start_date, end_date, facility_id, client_id, cost)
VALUES ('2023-01-25', '2023-01-26', 5, 1, 300);
INSERT INTO reservation(start_date, end_date, facility_id, client_id, cost)
VALUES ('2023-01-01', '2023-01-05', 1, 8, 2000);
INSERT INTO reservation(start_date, end_date, facility_id, client_id, cost)
VALUES ('2023-01-05', '2023-01-21', 3, 5, 24000);
INSERT INTO reservation(start_date, end_date, facility_id, client_id, cost)
VALUES ('2023-01-07', '2023-01-14', 4, 9, 2100);


-- select facility.name,
--        SUM(DATEDIFF(day, reservation.start_date, reservation.end_date)) as reservedDays,
--        count(reservation.facility_id)                                   as reservations
-- from facility
--          join reservation on reservation.facility_id = facility.id
-- where reservation.start_date > '2022-01-01'
--   and reservation.end_date < '2023-02-1'
-- group by facility.name

-- select facility.name,
--        count(reservation.client_id) as clients,
--        SUM(reservation.cost)        as Income
--
-- from facility
--          join reservation on reservation.facility_id = facility.id
--          join client on reservation.client_id = client.id
-- where reservation.start_date > '2022-01-01'
--   and reservation.end_date < '2023-02-1'
-- group by facility.name




