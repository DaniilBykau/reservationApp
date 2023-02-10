# reservationApp

Information about the number of days reserved for a given
object with information on how many reservations were on it (from init data).

name | days of reservation | number of reservations 
--- | --- | --- 
soccer arena | 16 | 1 
restaurant | 4 | 1 
karting | 7 | 1 
bowling | 3 | 2 
apartment room | 4 | 3 


    select facility.name,
           SUM(DATEDIFF(day, reservation.start_date, reservation.end_date)) as reservedDays,
           count(reservation.facility_id)                                   as reservations
    from facility
             join reservation on reservation.facility_id = facility.id
    where reservation.start_date > '2022-01-01'
      and reservation.end_date < '2023-02-1'
    group by facility.name

Information - how many facilities were booked, how many guests stayed at each facility, and what the profits were.

name | number of clients | income 
--- | --- | --- 
apartment room | 3 | 1200
bowling | 2 | 2400 
karting | 1 | 2100
bowling | 1 | 2000 
bowling | 1 | 24000

    select facility.name,
           count(reservation.client_id) as clients,
           SUM(reservation.cost)        as Income
    
    from facility
             join reservation on reservation.facility_id = facility.id
             join client on reservation.client_id = client.id
    where reservation.start_date > '2022-01-01'
      and reservation.end_date < '2023-02-1'
    group by facility.name

### Endpoints (testing in Postman):
- Get all reservations: http://localhost:8090/all
- Get all reservations by client id: http://localhost:8090/client/{id}
- Get all reservations by client name: http://localhost:8090/client/name/{name}
- Get all reservations by facility id: http://localhost:8090/facility/{id}
- Add new reservation: http://localhost:8090/add
with example json body:
{
    "startDate" : "2023-03-10",
    "endDate" : "2023-03-11",
    "facilityId" : 5,
    "clientId" : 5,
    "cost": 55
}
- Update reservation: http://localhost:8090/update/{id}
with example json body:
{
    "startDate" : "2023-03-09",
    "endDate" : "2023-03-11",
    "facilityId" : 5,
    "clientId" : 5,
    "cost": 55
}
