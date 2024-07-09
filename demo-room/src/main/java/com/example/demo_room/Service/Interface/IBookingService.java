package com.example.demo_room.Service.Interface;

import com.example.demo_room.Model.BookedRoom;
import com.example.demo_room.Model.ConferenceRoom;
import com.example.demo_room.dto.BookedRoomResponse;
import com.example.demo_room.dto.Response;

public interface IBookingService {

    Response findBookingByConfirmationCode(String confirmationCode);
    Response getAllBookings();
    Response cancelBooking(Long bookingId);
}
