package com.example.reservation.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "reservation")
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "start_date", nullable = false)
    private Date startDate;
    @Column(name = "end_date", nullable = false)
    private Date endDate;
    @OneToOne()
    @JoinColumn(name = "facility_id")
    private Facility facility;
    @OneToOne
    @JoinColumn(name = "client_id")
    private Client client;
    private Integer cost;

    public Reservation(Date startDate, Date endDate, Facility facility, Client client, Integer cost) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.facility = facility;
        this.client = client;
        this.cost = cost;
    }
}
