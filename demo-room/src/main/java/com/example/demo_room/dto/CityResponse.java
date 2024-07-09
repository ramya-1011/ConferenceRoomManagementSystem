package com.example.demo_room.dto;

import com.example.demo_room.Model.City;
import com.example.demo_room.Model.ConferenceRoom;
import com.example.demo_room.Model.Site;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CityResponse extends CommonAPIResponse{
    private long id;
    private String name;
    private String state;
    private int totalSites;
    private List<Site> sites;
    private List<ConferenceRoom> rooms;
    private int statusCode;

}






