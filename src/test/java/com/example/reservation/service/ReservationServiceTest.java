package com.example.reservation.service;

import com.example.reservation.dto.ReservationRequest;
import com.example.reservation.exeption.ReservationException;
import com.example.reservation.model.Client;
import com.example.reservation.model.Facility;
import com.example.reservation.model.Reservation;
import com.example.reservation.repository.ClientRepository;
import com.example.reservation.repository.FacilityRepository;
import com.example.reservation.repository.ReservationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {

    private static Logger logger = LoggerFactory.getLogger(ReservationService.class);

    @Mock
    private FacilityRepository facilityRepository;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ReservationService reservationService;


    @Test
    void getAllReservationsForClient_shouldCallRepository() {
        this.reservationService.getAllReservations();

        verify(this.reservationRepository).findAll();
    }

    @Test
    void getAllReservationsForClientByName_shouldCallRepository() {
        this.reservationService.getAllReservationsForClientByName("John");

        verify(this.reservationRepository).findByClientName("John");
    }

    @Test
    void getAllReservationsForFacility_shouldCallRepository() {
        this.reservationService.getAllReservationsForFacility(1);

        verify(this.reservationRepository).findByFacilityId(1);
    }

    @Test
    void addReservation_shouldCallRepository() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        var reservation = new ReservationRequest(1, formatter.parse("2022-01-19"), formatter.parse("2022-01-20"),
                1, 1, 300);

        this.reservationService.saveReservation(reservation, null);

        verify(this.reservationRepository).save(any(Reservation.class));
    }

    @Test
    void addReservation_shouldThrowException() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        var reservation = new ReservationRequest(1, formatter.parse("2022-01-19"), formatter.parse("2022-01-20"),
                1, 1, 300);

        given(this.reservationRepository.isReservationExist(reservation.getStartDate(), reservation.getEndDate())).willReturn(
                getReservations()
        );

        assertThatThrownBy(() ->  this.reservationService.saveReservation(reservation, null))
                .isInstanceOf(ReservationException.class)
                .hasMessageContaining("Days in the given booking range are already reserved");
    }

    @Test
    void updateReservation_shouldThrowException() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        var reservation = new ReservationRequest(1, formatter.parse("2022-01-19"), formatter.parse("2022-01-20"),
                1, 1, 300);

        given(reservationRepository.isReservationExistWithoutId(reservation.getStartDate(), reservation.getEndDate(), reservation.getId())).willReturn(
                getReservations()
        );

        assertThatThrownBy(() ->  this.reservationService.saveReservation(reservation, 1))
                .isInstanceOf(ReservationException.class)
                .hasMessageContaining("Days in the given booking range are already reserved");
    }

    @Test
    void updateReservation_shouldCallRepository() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        var reservation = new ReservationRequest(1, formatter.parse("2022-01-19"), formatter.parse("2022-01-20"),
                1, 1, 300);
        this.reservationService.saveReservation(reservation, 1);
        verify(this.reservationRepository).save(any(Reservation.class));
    }

    private List<Reservation> getReservations() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return List.of(new Reservation(1000, formatter.parse("2022-01-11"), formatter.parse("2022-01-13"),
                        new Facility(1, "Restaurant", 100, 200, "Restaurant description"), new Client(1, "John"), 300),
                new Reservation(1001, formatter.parse("2022-01-21"), formatter.parse("2022-01-23"),
                        new Facility(2, "Hotel", 200, 300, "Hotel description"), new Client(2, "Mia"), 600));
    }
}
