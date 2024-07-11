package com.example.demo_room.Repository;

import com.example.demo_room.Model.BookedRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface BookingRepo extends JpaRepository<BookedRoom,Long> {
    Optional<BookedRoom> findByConfirmationCode(String confirmationCode);
    List<BookedRoom> findByEmployeeName( String employeeName);

    @Query(value= "SELECT b from BookedRoom b WHERE b.roomId=:roomId AND b.bookingDate=:bookingDate AND " +
            " ( (b.startTime<=:startTime AND b.endTime>:startTime) OR (b.startTime<:endTime AND b.endTime>:endTime) OR"
            + "(b.startTime>=:startTime) AND (b.endTime<=:endTime))")
    List<BookedRoom> findConflictBookings(@Param( "bookingDate")LocalDate bookingDate,
                                          @Param("startTime") LocalTime startTime,
                                          @Param("endTime") LocalTime endTime,
                                          @Param("roomId") long roomId);

}