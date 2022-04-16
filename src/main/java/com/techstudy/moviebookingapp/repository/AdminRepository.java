package com.techstudy.moviebookingapp.repository;

import com.techstudy.moviebookingapp.model.BookingDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;

@Repository
public interface AdminRepository extends JpaRepository<BookingDetails, String> {

    @Query(value = "select * from booking_details where first_name = :input or last_name = :input or email = :input", nativeQuery = true)
    List<BookingDetails> searchBooking(@Param("input") String input);

    BookingDetails findByEmail(String email);

    @Query(value = "select * from booking_details where booking_date between :fromdate and :todate", nativeQuery = true)
    List<BookingDetails> filterBooking(@Param("fromdate") Date fromDate, @Param("todate") Date toDate);
}
