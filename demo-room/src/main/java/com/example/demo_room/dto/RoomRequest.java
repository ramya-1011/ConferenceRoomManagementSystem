package com.example.demo_room.dto;

import com.example.demo_room.Utils.Alphanumeric;
import com.example.demo_room.Utils.JustNumber;
import com.example.demo_room.Utils.Numeric;
import com.example.demo_room.Utils.StringCharacter;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.*;
import lombok.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
 @AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RoomRequest {
    @Size(max = 15, message = "The type cannot be at most 20 letters long.")
    @Alphanumeric
    @NotEmpty(message = "type cannot be empty")
    private String type;
    @Size(max = 25, message = "The description must be at most 25 letters long.")
    @Alphanumeric
    @NotEmpty(message = "description cannot be empty")
    private String description;
    @NotNull(message = "capacity cannot be empty")
@JustNumber
    @Min(value = 1)
    @Max(value = 500,message = "maximum capacity can be 500")
    private  int capacity;

    @NotNull(message = "cityId cannot be empty")
@JustNumber
    private int cityId;
    @NotNull(message = "siteId cannot be empty")
    @JustNumber
    private int siteId;
    @NotNull(message = "floorId cannot be empty")
    @JustNumber

    private int floorId;

}
