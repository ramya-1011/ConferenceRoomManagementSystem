package com.example.demo_room.Service.Interface;

import com.example.demo_room.Model.BookedRoom;
import com.example.demo_room.dto.BookedRoomResponse;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

import java.util.List;
@Validated
public interface IBookingService {

    BookedRoomResponse findBookingByConfirmationCode(String confirmationCode);
    List<BookedRoomResponse> getAllBookings();
    BookedRoomResponse cancelBooking(Long bookingId);
    BookedRoomResponse addBooking(@Valid BookedRoom bookedRoom);
}
