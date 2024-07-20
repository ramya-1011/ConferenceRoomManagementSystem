package com.example.demo_room.dto;

import com.example.demo_room.Utils.Alphanumeric;
import com.example.demo_room.Utils.JustNumber;
import com.example.demo_room.Utils.Numeric;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.*;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class FloorRequest {
    @Size(max = 10, message = "The floorId must be at most 10 letters long.")
    @Alphanumeric
    @NotEmpty(message = "floorId cannot be empty")
    private String floorId;
//    @Min(value =0,message = "rooms cant be less than 0")
//    @Max(value = 30)
//    @NotNull(message = "totalRooms cannot be empty")
//@JustNumber
//    private int totalRooms;
    @NotNull(message = "siteId cannot be empty")

    private int siteId;
    @NotNull(message = "cityId cannot be empty")

    private int cityId;
}
