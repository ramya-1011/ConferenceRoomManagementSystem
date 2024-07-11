package com.example.demo_room.Service.Interface;

import com.example.demo_room.dto.BookedRoomResponse;

import java.util.List;

public interface IBookingService {

    BookedRoomResponse findBookingByConfirmationCode(String confirmationCode);
    List<BookedRoomResponse> getAllBookings();
    BookedRoomResponse cancelBooking(Long bookingId);
}
