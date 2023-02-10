package com.example.reservation.repository;

import com.example.reservation.model.Client;
import com.example.reservation.model.Facility;
import com.example.reservation.model.Reservation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class ReservationRepositoryTest {

    @Autowired
    private ReservationRepository reservationRepository;

    @Test
    void findByClientId_shouldFindReservationByClientId() throws ParseException {

        this.reservationRepository.deleteAll();
        this.reservationRepository.saveAll(getReservations());
        List<Reservation> result = this.reservationRepository.findByClientId(getReservations().get(0).getClient().getId());

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void findByFacilityId_shouldFindReservationByFacilityId() throws ParseException {
        this.reservationRepository.deleteAll();
        this.reservationRepository.saveAll(getReservations());

        List<Reservation> result = this.reservationRepository.findByFacilityId(getReservations().get(0).getFacility().getId());

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void findByClientName_shouldFindReservationByClientName() throws ParseException {
        this.reservationRepository.deleteAll();
        this.reservationRepository.saveAll(getReservations());

        List<Reservation> result = this.reservationRepository.findByClientName(getReservations().get(0).getClient().getName());

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void isReservationExist_shouldReturnListOfReservationsInDateRange() throws ParseException {
        this.reservationRepository.deleteAll();
        this.reservationRepository.saveAll(getReservations());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = formatter.parse("2022-01-01");
        Date endDate = formatter.parse("2022-02-01");

        List<Reservation> result = this.reservationRepository.isReservationExist(startDate, endDate);

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void isReservationExist_shouldReturnEmptyListOfReservationsInDateRange() throws ParseException {
        this.reservationRepository.deleteAll();
        this.reservationRepository.saveAll(getReservations());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = formatter.parse("2022-01-01");
        Date endDate = formatter.parse("2022-01-02");

        List<Reservation> result = this.reservationRepository.isReservationExist(startDate, endDate);

        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    void isReservationExistWithoutId_shouldReturnListOfReservationsInDateRangeWithoutId() throws ParseException {
        this.reservationRepository.deleteAll();
        this.reservationRepository.saveAll(getReservations());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = formatter.parse("2022-01-11");
        Date endDate = formatter.parse("2022-01-13");

        List<Reservation> result = this.reservationRepository.isReservationExistWithoutId(startDate, endDate, 17);

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    private List<Reservation> getReservations() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return List.of(new Reservation(1000, formatter.parse("2022-01-11"), formatter.parse("2022-01-13"),
                        new Facility(1, "Restaurant", 100, 200, "Restaurant description"), new Client(1, "John"), 300),
                new Reservation(1001, formatter.parse("2022-01-21"), formatter.parse("2022-01-23"),
                        new Facility(2, "Hotel", 200, 300, "Hotel description"), new Client(2, "Mia"), 600));
    }
}
