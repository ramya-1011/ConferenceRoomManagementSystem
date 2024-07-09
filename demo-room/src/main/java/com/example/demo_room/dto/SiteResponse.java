package com.example.demo_room.dto;

import com.example.demo_room.Model.City;
import com.example.demo_room.Model.Floor;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SiteResponse extends CommonAPIResponse {
    private int id;
    private String siteId;
    private String description;
    private String pinCode;
    private int totalFloors;
    private String locationName;
    private int statusCode;
    private City city;
    private List<Floor> floors;
}
