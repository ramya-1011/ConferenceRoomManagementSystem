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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
    public BookedRoomResponse findBookingByConfirmationCode(String confirmationCode) {
        BookedRoomResponse response = new BookedRoomResponse();

        try {
            BookedRoom booking = bookingRepo.findByConfirmationCode(confirmationCode).orElseThrow(() -> new MyException("Booking Not Found"));
            BookedRoomResponse bookingDTO = Utils.mapBookingEntityToBookingDTOPlusBookedRooms(booking, true);
            response.setResponseCode(200);
            response.setResponseMessage("successful");


        } catch (MyException e) {
            response.setResponseCode(404);
            response.setResponseMessage(e.getMessage());

        } catch (Exception e) {
            response.setResponseCode(500);
            response.setResponseMessage("Error Finding a booking: " + e.getMessage());

        }
        return response;

    }

    @Override
    public List<BookedRoomResponse> getAllBookings() {
        BookedRoomResponse response = new BookedRoomResponse();


            List<BookedRoom> bookingList = bookingRepo.findAll(Sort.by(Sort.Direction.ASC, "bookingID"));
            List<BookedRoomResponse> bookingDTOList = Utils.mapBookingListEntityToBookingListDTO(bookingList);
            response.setResponseCode(200);
            response.setResponseMessage("successful");
return bookingDTOList;


    }

    @Override
    public BookedRoomResponse cancelBooking(Long bookingId) {
        BookedRoomResponse response = new BookedRoomResponse();

        try {

            bookingRepo.findById(bookingId).orElseThrow(() -> new MyException("Booking Does Not Exist"));
            bookingRepo.deleteById(bookingId);
            response.setResponseCode(200);
            response.setResponseMessage("successful");

        } catch (MyException e) {
            response.setResponseCode(404);
            response.setResponseMessage(e.getMessage());

        } catch (Exception e) {
            response.setResponseCode(500);
            response.setResponseMessage("Error Cancelling a booking: " + e.getMessage());

        }
        return response;

    }
    public BookedRoomResponse addBooking(BookedRoom bookedRoom)  throws Exception {

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
            bookingRepo.save(bookedRoom);
            BookedRoomResponse savedBooking=Utils.mapBookingEntityToBookingDTO(bookedRoom);
            return savedBooking;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }



}
