package com.example.demo_room.Model;

import com.example.demo_room.Utils.JustNumber;
import com.example.demo_room.Utils.Numeric;
import com.example.demo_room.Utils.StringCharacter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

@ToString
@Table(name="bookingg")

public class BookedRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long bookingID;
    private long roomId;

    @Size(max = 20, message = "The name must be at most 20 letters long.")
    @StringCharacter
    @NotEmpty(message = "employee name is required")
    private String employeeName;
    @NotEmpty(message = "employeeId name is required")
    @Size(min = 10, max = 10, message = "The employee ID must be exactly 10 characters long.")
    @Numeric
    private String employeeId;
    @NotEmpty(message = "employee phone Number name is required")
    @Numeric
    @Size(min =10,max = 10,message = "Invalid phone number")
    private String employee_ph_no;
    @NotNull
    @JustNumber
    @Min(value=1,message = "Minimum 1 person is required to book a Room")
    private int attendees;
    @NotNull(message="please Enter the date for booking")
    @JsonFormat(pattern = "yyyy-MM-dd")
  //  @FutureOrPresent(message = "date cant be in the past")
    private LocalDate bookingDate;

    private LocalTime startTime;

    private LocalTime endTime;
    private String confirmationCode;
    private String status;

    @ManyToOne
    @JoinColumn(name="cRoom_Id")
    private ConferenceRoom room;
    public BookedRoom(int roomId, BookedRoom bookingRequest) {
    }


}
