package com.example.reservation.controller;

import com.example.reservation.dto.ReservationRequest;
import com.example.reservation.service.ReservationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReservationControllerTest {

    private static Logger logger = LoggerFactory.getLogger(ReservationService.class);

    @Mock
    private ReservationService reservationService;

    @InjectMocks
    private ReservationController reservationController;

    @Test
    void getAllReservations_shouldCallService() {
        this.reservationController.getAllReservations();

        verify(this.reservationService).getAllReservations();
    }

    @Test
    void getAllReservationsForClient_shouldCallService() {
        this.reservationController.getAllReservationsForClient(1);

        verify(this.reservationService).getAllReservationsForClient(1);
    }

    @Test
    void getAllReservationsForClientByName_shouldCallService() {
        this.reservationController.getAllReservationsForClient("John");

        verify(this.reservationService).getAllReservationsForClientByName("John");
    }

    @Test
    void getAllReservationsForFacility_shouldCallService() {
        this.reservationController.getAllReservationsForFacility(1);

        verify(this.reservationService).getAllReservationsForFacility(1);
    }

    @Test
    void addReservation_shouldCallService() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        var reservation = new ReservationRequest(1, formatter.parse("2022-01-19"), formatter.parse("2022-01-20"),
                1, 1, 300);

        this.reservationController.addReservation(reservation);

        verify(this.reservationService).saveReservation(reservation, null);
    }

    @Test
    void updateReservation_shouldCallService() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        var reservation = new ReservationRequest(1, formatter.parse("2022-01-19"), formatter.parse("2022-01-20"),
                1, 1, 300);

        this.reservationController.updateReservation(reservation, 1);

        verify(this.reservationService).saveReservation(reservation, 1);

    }
}
