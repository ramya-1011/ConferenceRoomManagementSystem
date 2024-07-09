package com.example.demo_room.Controller;

import com.example.demo_room.Model.BookedRoom;
import com.example.demo_room.Repository.BookingRepo;
import com.example.demo_room.Service.Implementation.BookingService;
import com.example.demo_room.dto.Response;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/booking")
@CrossOrigin("*")
public class BookingController {
    @Autowired
    private BookingService bookingService;
    @Autowired
    private BookingRepo bookingRepo;
    @PostMapping("/book-room" )
    public ResponseEntity<?> saveBookings(@RequestBody BookedRoom room) {
        try{
            BookedRoom createdBooking=bookingService.addBooking( room);
            return ResponseEntity.ok(createdBooking);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
    @GetMapping("/all")
    public ResponseEntity<Response> getAllBookings() {
        Response response = bookingService.getAllBookings();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    @GetMapping("/get-by-confirmation-code/{confirmationCode}")
    public ResponseEntity<Response> getBookingByConfirmationCode(@PathVariable String confirmationCode) {
        Response response = bookingService.findBookingByConfirmationCode(confirmationCode);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    @DeleteMapping("/cancel/{bookingId}")
    public ResponseEntity<Response> cancelBooking(@PathVariable Long bookingId) {
        Response response = bookingService.cancelBooking(bookingId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    @GetMapping("/byEmployee/{employeeName}")
    public List<BookedRoom> getByEmployeeDetails(@PathVariable String employeeName){
        return bookingRepo.findByEmployeeDetails(employeeName);
    }

}
