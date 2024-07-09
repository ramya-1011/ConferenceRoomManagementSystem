package com.example.demo_room.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
 @AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RoomRequest {
    private String type;
    private String description;
    private int capacity;
    private int cityId;
    private int siteId;
    private int floorId;

}
