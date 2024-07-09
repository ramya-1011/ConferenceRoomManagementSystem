package com.example.demo_room.dto;

import com.example.demo_room.Model.City;
import com.example.demo_room.Model.Floor;
import com.example.demo_room.Model.Site;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@AllArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoomResponse extends CommonAPIResponse {
    private long id;
    private int capacity;
    private String description;
    private String type;
    private List<BookedRoomResponse> bookings;
    private Floor floor;
    private Site site;
    private City city;

    public RoomResponse() {

    }
}
