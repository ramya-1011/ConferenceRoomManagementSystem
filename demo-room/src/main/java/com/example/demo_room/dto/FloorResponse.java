package com.example.demo_room.dto;

import com.example.demo_room.Model.City;
import com.example.demo_room.Model.ConferenceRoom;
import com.example.demo_room.Model.Site;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FloorResponse extends CommonAPIResponse {
    private int id;
    private String floorId;
  //  private int totalRooms;
    private Site site;
    private City city;
    List<ConferenceRoom> rooms;
}
