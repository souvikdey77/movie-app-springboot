package com.techstudy.moviebookingapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "booking_details")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookingDetails {
    @Id
    @Column(length = 200)
    private String email;
    private String firstName;
    private String lastName;
    private Date bookingDate;
    private String bookingStatus;
    private int numberOfTickets;
    private String movieTitle;
}
