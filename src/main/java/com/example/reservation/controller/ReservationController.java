package com.example.reservation.controller;

import com.example.reservation.dto.ReservationRequest;
import com.example.reservation.model.Reservation;
import com.example.reservation.service.ReservationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping("/all")
    public ResponseEntity<List<Reservation>> getAllReservations() {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(this.reservationService.getAllReservations());
    }

    @GetMapping("/client/{client_id}")
    public ResponseEntity<List<Reservation>> getAllReservationsForClient(@PathVariable("client_id") Integer clientId) {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(this.reservationService.getAllReservationsForClient(clientId));
    }

    @GetMapping("/client/name/{client_name}")
    public ResponseEntity<List<Reservation>> getAllReservationsForClient(@PathVariable("client_name") String clientName) {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(this.reservationService.getAllReservationsForClientByName(clientName));
    }

    @GetMapping("/facility/{facility_id}")
    public ResponseEntity<List<Reservation>> getAllReservationsForFacility(@PathVariable("facility_id") Integer facilityId) {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(this.reservationService.getAllReservationsForFacility(facilityId));
    }

    @PostMapping("/add")
    public ResponseEntity<Reservation> addReservation(@RequestBody ReservationRequest reservationRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(this.reservationService.saveReservation(reservationRequest, null));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Reservation> updateReservation(@RequestBody ReservationRequest reservationRequest, @PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(this.reservationService.saveReservation(reservationRequest, id));
    }
}
