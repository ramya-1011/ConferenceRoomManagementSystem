package com.example.demo_room.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class FloorRequest {
    private String floorId;
    private int totalRooms;
    private int siteId;
    private int cityId;
}
