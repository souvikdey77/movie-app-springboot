package com.techstudy.moviebookingapp.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * Entity class of booking details
 *
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
    @NotBlank(message = "Email is mandatory")
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
