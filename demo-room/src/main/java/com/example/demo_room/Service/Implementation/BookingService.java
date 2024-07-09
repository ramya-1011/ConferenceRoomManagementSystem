package com.example.demo_room.Service.Implementation;

import com.example.demo_room.Exception.MyException;
import com.example.demo_room.Model.BookedRoom;
import com.example.demo_room.Model.ConferenceRoom;
import com.example.demo_room.Repository.BookingRepo;
import com.example.demo_room.Repository.RoomRepo;
import com.example.demo_room.Service.Interface.IBookingService;
import com.example.demo_room.Service.Interface.IRoomService;
import com.example.demo_room.Utils.Utils;
import com.example.demo_room.dto.BookedRoomResponse;
import com.example.demo_room.dto.BookingRequest;
import com.example.demo_room.dto.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService implements IBookingService {
    @Autowired
    private IRoomService roomService;
    @Autowired
    private RoomRepo roomRepo;
    @Autowired
    private BookingRepo bookingRepo;


    @Override
    public Response findBookingByConfirmationCode(String confirmationCode) {
        Response response = new Response();

        try {
            BookedRoom booking = bookingRepo.findByConfirmationCode(confirmationCode).orElseThrow(() -> new MyException("Booking Not Found"));
            BookedRoomResponse bookingDTO = Utils.mapBookingEntityToBookingDTOPlusBookedRooms(booking, true);
            response.setStatusCode(200);
            response.setMessage("successful");
            response.setBooking(bookingDTO);

        } catch (MyException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error Finding a booking: " + e.getMessage());

        }
        return response;

    }

    @Override
    public Response getAllBookings() {
        Response response = new Response();

        try {
            List<BookedRoom> bookingList = bookingRepo.findAll(Sort.by(Sort.Direction.ASC, "bookingID"));
            List<BookedRoomResponse> bookingDTOList = Utils.mapBookingListEntityToBookingListDTO(bookingList);
            response.setStatusCode(200);
            response.setMessage("successful");
            response.setBookingsList(bookingDTOList);

        } catch (MyException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error Getting all bookings: " + e.getMessage());

        }
        return response;

    }

    @Override
    public Response cancelBooking(Long bookingId) {
        Response response = new Response();

        try {

            bookingRepo.findById(bookingId).orElseThrow(() -> new MyException("Booking Does Not Exist"));
            bookingRepo.deleteById(bookingId);
            response.setStatusCode(200);
            response.setMessage("successful");

        } catch (MyException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error Cancelling a booking: " + e.getMessage());

        }
        return response;

    }
    public BookedRoom addBooking(BookedRoomResponse bookedRoom)  throws Exception {
        Response response = new Response();
        Optional<ConferenceRoom> room = Optional.ofNullable(roomRepo.findById(bookedRoom.getRoomId()).orElseThrow(() -> new MyException("room not found")));
        LocalDate currentDate= LocalDate.now();
        try{
            if (bookedRoom.getEndTime().isBefore(bookedRoom.getStartTime())) {
                throw new IllegalArgumentException("Check in time must come after check out time");
            }
            if(bookedRoom.getBookingDate().isBefore(currentDate)){
                throw new IllegalArgumentException("date selected cannot be in past");
            }ConferenceRoom cRoom =room.get();
            if(bookedRoom.getAttendees()>cRoom.getCapacity()){
                throw new IllegalArgumentException("attendees count is exceeding room capacity!!");
            }
            LocalDate bookingDate = bookedRoom.getBookingDate();
            LocalTime startTime = bookedRoom.getStartTime();
            LocalTime now = LocalTime.now();
            if (bookingDate == null || startTime == null) {
                throw new IllegalArgumentException("Booking date and start time are required");
            }
            List<BookedRoom> ConflictingBookings = bookingRepo.findConflictBookings(
                    bookedRoom.getBookingDate(), bookedRoom.getStartTime(), bookedRoom.getEndTime(), bookedRoom.getRoomId());
            if (!ConflictingBookings.isEmpty()) {
                throw new Exception("booking already exists for given date and time");
            }
            if (bookingDate.isEqual(currentDate) && startTime.isBefore(now)) {
                  throw new IllegalArgumentException("start time must be in future if booking is for same day");
                }
            String bookingConfirmationCode = Utils.generateRandomConfirmationCode(10);
            bookedRoom.setConfirmationCode(bookingConfirmationCode);
            BookedRoom savedBooking=Utils.mapBookingEntityToBookingDTO(bookedRoom)
            return bookingRepo.save(bookedRoom);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }



}
