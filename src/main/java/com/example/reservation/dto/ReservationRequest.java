package com.example.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class ReservationRequest {
    private Integer id;
    private Date startDate;
    private Date endDate;
    private Integer facilityId;
    private Integer clientId;
    private Integer cost;
}
