package com.example.demo_room.Model;

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
@Setter
@Getter
@ToString
@Table(name="bookings")

public class BookedRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long bookingID;
    private long roomId;
    private String EmployeeName;
    private String EmployeeId;
    private String Employee_ph_no;

    private int attendees;

    private LocalDate bookingDate;

    private LocalTime startTime;

    private LocalTime endTime;
    private String confirmationCode;
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="cRoom_id")
    private ConferenceRoom room;
    public BookedRoom(int roomId, BookedRoom bookingRequest) {
    }


}
