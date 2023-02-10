package com.example.reservation.repository;

import com.example.reservation.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    List<Reservation> findByClientId(Integer clientId);

    List<Reservation> findByFacilityId(Integer facilityId);

    List<Reservation> findByClientName(String clientName);

    @Query("select reservation from Reservation reservation where (reservation.startDate <= :startDateNew  and reservation.endDate >= :startDateNew) " +
            " or (reservation.startDate <= :endDateNew  and reservation.endDate >= :endDateNew) or (:startDateNew <= reservation.startDate and :endDateNew >= reservation.endDate) ")
    List<Reservation> isReservationExist(@Param("startDateNew") Date startDateNew, @Param("endDateNew") Date endDateNew);

    @Query("select reservation from Reservation reservation where ((reservation.startDate <= :startDateNew  and reservation.endDate >= :startDateNew) " +
            " or (reservation.startDate <= :endDateNew  and reservation.endDate >= :endDateNew) or (:startDateNew <= reservation.startDate and :endDateNew >= reservation.endDate)) " +
            " and reservation.id != :idNew ")
    List<Reservation> isReservationExistWithoutId(@Param("startDateNew") Date startDateNew, @Param("endDateNew") Date endDateNew, @Param("idNew") Integer id);
}
