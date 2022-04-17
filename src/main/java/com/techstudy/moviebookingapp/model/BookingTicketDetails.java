package com.techstudy.moviebookingapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * DTO class of booking ticket details
 * @author souvikdey
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookingTicketDetails {

    private int numberOfTickets;
    private String emailId;
    private String firstName;
    private String lastName;
    private Date bookingDate;
    private String movieTitle;
}
