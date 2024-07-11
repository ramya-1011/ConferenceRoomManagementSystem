package com.example.demo_room.dto;

import com.example.demo_room.Model.ConferenceRoom;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
@Data
@AllArgsConstructor
@NoArgsConstructor



public class BookedRoomResponse extends CommonAPIResponse{
    private long bookingID;
    private long roomId;
    private String employeeName;
    private String employeeId;
    private String employee_ph_no;
    private int attendees;
    private LocalDate bookingDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String confirmationCode;
    private  RoomResponse room;


}
