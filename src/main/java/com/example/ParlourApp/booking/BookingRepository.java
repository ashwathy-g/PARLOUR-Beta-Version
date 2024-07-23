package com.example.ParlourApp.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends JpaRepository<BookingRegModel,Long>
{
    List<BookingRegModel> findByParlourIdAndBookingDate(Long parlourId, LocalDate bookingDate);@Query("SELECT b FROM BookingRegModel b WHERE b.parlourId = :parlourId AND b.bookingDate BETWEEN :startDate AND :endDate")
    List<BookingRegModel> findBookingsWithinDateRange(@Param("parlourId") Long parlourId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

}
