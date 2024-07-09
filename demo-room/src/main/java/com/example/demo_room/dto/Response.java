package com.example.demo_room.dto;

import com.example.demo_room.Model.City;
import com.example.demo_room.Model.Floor;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;
@Data
@Component
public class Response {
    private int status_Id;
    private String message;
    private String confirmationCode;
    private CityResponse cityResponse;
    private RoomResponse roomResponse;
    private BookedRoomResponse booking;
    private SiteResponse siteResponse;
    private FloorResponse floorResponse;
    private Floor floor;
    private int statusCode;
private City city;
    private List<CityResponse> cityList;
    private List<RoomResponse>  RoomsList;
    private List<BookedRoomResponse> bookingsList;
    private List<SiteResponse> siteResponseList;
    private List<Floor> floorList;
    private List<FloorResponse> floorResponseList;



}
