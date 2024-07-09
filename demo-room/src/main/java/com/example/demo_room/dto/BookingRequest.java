package com.example.demo_room.dto;

import com.example.demo_room.Utils.StringCharacter;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class BookingRequest {
    private long roomId;
    @Pattern(regexp = "^[A-Za-z]*$", message = "The name must contain only letters.")
    @Size(max = 20, message = "The name must be at most 20 letters long.")
    @StringCharacter
    private String EmployeeName;
    @Size(min = 10, max = 10, message = "The employee ID must be exactly 10 characters long.")
    private String EmployeeId;
    @Size(min =10,max = 10,message = "Invalid phone number")
    private String Employee_ph_no;
    @Min(value=1,message = "Minimum 1 person is required to book a Room")
    private int attendees;
    @NotNull(message="please Enter the date for booking")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @FutureOrPresent(message = "date cant be in the past")
    private LocalDate bookingDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String confirmationCode;
}
