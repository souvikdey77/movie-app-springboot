package com.techstudy.moviebookingapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

/**
 * Entity class of booking details
 * @author souvikdey
 */
@Entity
@Table(name = "booking_details")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookingDetails {
    @Id
    @Column(length = 200)
    @NotEmpty(message = "Email is mandatory")
    @Email(message = "Please provide a valid email address")
    private String email;
    @NotEmpty(message = "First name is mandatory")
    @Length(max = 20)
    private String firstName;
    @NotEmpty(message = "Last name is mandatory")
    @Length(max = 20)
    private String lastName;
    @Temporal(TemporalType.DATE)
    private Date bookingDate;
    private String bookingStatus;
    private int numberOfTickets;
    @NotEmpty(message = "Movie title is mandatory")
    private String movieTitle;
}
