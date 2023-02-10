package com.example.reservation.service;

import com.example.reservation.dto.ReservationRequest;
import com.example.reservation.exeption.ReservationException;
import com.example.reservation.model.Reservation;
import com.example.reservation.repository.ClientRepository;
import com.example.reservation.repository.FacilityRepository;
import com.example.reservation.repository.ReservationRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class ReservationService {

    private static Logger logger = LoggerFactory.getLogger(ReservationService.class);

    private final ReservationRepository reservationRepository;
    private final ClientRepository clientRepository;
    private final FacilityRepository facilityRepository;

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public List<Reservation> getAllReservationsForClient(Integer clientId) {
        return this.reservationRepository.findByClientId(clientId);
    }

    public List<Reservation> getAllReservationsForFacility(Integer facilityId) {
        return this.reservationRepository.findByFacilityId(facilityId);
    }

    public List<Reservation> getAllReservationsForClientByName(String clientName) {
        if (this.clientRepository.findByName(clientName).size() > 1) {
            throw new ReservationException("There are more than 1 client with name: " + clientName);
        }
        return this.reservationRepository.findByClientName(clientName);
    }

    public Reservation saveReservation(ReservationRequest reservationRequest, Integer id) {
        Integer reservationDays;
        DateFormat formatWithoutTime = new SimpleDateFormat("yyyy-MM-dd");
        Date startDateWithoutTime = null;
        Date endDateWithoutTime = null;
//      remove time from date object
        try {
            startDateWithoutTime = formatWithoutTime.parse(formatWithoutTime.format(reservationRequest.getStartDate()));
            endDateWithoutTime = formatWithoutTime.parse(formatWithoutTime.format(reservationRequest.getEndDate()));
        } catch (ParseException exception) {
            logger.error(exception.getMessage());
        }
        if (reservationRequest.getStartDate().after(reservationRequest.getEndDate())) {
            throw new ReservationException("The beginning of the reservation cannot be later of the end of the reservation");
        }
//        calculate cost
        if (reservationRequest.getCost() == null) {
            reservationDays = Math.toIntExact(ChronoUnit.DAYS.between(reservationRequest.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                    reservationRequest.getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()));
        } else {
            reservationDays = reservationRequest.getCost();
        }
//      update
        if (id != null) {
            if (this.reservationRepository.isReservationExistWithoutId(startDateWithoutTime, endDateWithoutTime, id).size() > 0) {
                throw new ReservationException("Days in the given booking range are already reserved");
            }
            return this.reservationRepository.save(new Reservation(id, reservationRequest.getStartDate(),
                    reservationRequest.getEndDate(), this.facilityRepository.findById(reservationRequest.getFacilityId()).orElse(null),
                    this.clientRepository.findById(reservationRequest.getClientId()).orElse(null), reservationDays));
        }
//      add new
        if (this.reservationRepository.isReservationExist(startDateWithoutTime, endDateWithoutTime).size() > 0) {
            throw new ReservationException("Days in the given booking range are already reserved");
        }
        return this.reservationRepository.save(new Reservation(startDateWithoutTime, endDateWithoutTime,
                this.facilityRepository.findById(reservationRequest.getFacilityId()).orElse(null),
                this.clientRepository.findById(reservationRequest.getClientId()).orElse(null), reservationDays));
    }
}
