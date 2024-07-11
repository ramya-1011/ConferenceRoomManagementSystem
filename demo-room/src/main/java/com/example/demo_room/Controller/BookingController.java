package com.example.demo_room.Controller;

import com.example.demo_room.Model.BookedRoom;
import com.example.demo_room.Repository.BookingRepo;
import com.example.demo_room.Service.Implementation.BookingService;
import com.example.demo_room.dto.BookedRoomResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/booking")
@CrossOrigin("*")
public class BookingController {
    @Autowired
    private BookingService bookingService;
    @Autowired
    private BookingRepo bookingRepo;
    @PostMapping("/book-room" )
    public ResponseEntity<?> saveBookings(@Valid @RequestBody BookedRoom room) {
        try{
            BookedRoomResponse createdBooking=bookingService.addBooking( room);
            return ResponseEntity.ok(createdBooking);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
    @GetMapping("/all")
    public List<BookedRoomResponse> getAllBookings() {
        return bookingService.getAllBookings();
    }
    @GetMapping("/get-by-confirmation-code/{confirmationCode}")
    public ResponseEntity<BookedRoomResponse> getBookingByConfirmationCode(@PathVariable String confirmationCode) {
        BookedRoomResponse response = bookingService.findBookingByConfirmationCode(confirmationCode);
        return ResponseEntity.status(response.getResponseCode()).body(response);
    }
    @DeleteMapping("/cancel/{bookingId}")
    public ResponseEntity<BookedRoomResponse> cancelBooking(@PathVariable Long bookingId) {
        BookedRoomResponse response = bookingService.cancelBooking(bookingId);
        return ResponseEntity.status(response.getResponseCode()).body(response);
    }
    @GetMapping("/byEmployee/{employeeName}")
    public List<BookedRoom> getByEmployeeDetails(@PathVariable String employeeName){
        return bookingRepo.findByEmployeeName(employeeName);
    }

}
