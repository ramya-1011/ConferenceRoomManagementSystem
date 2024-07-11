package com.example.demo_room.dto;

import com.example.demo_room.Utils.JustNumber;
import com.example.demo_room.Utils.Numeric;
import com.example.demo_room.Utils.StringCharacter;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
 @AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RoomRequest {
    @Size(max = 15, message = "The type cannot be at most 20 letters long.")
    @StringCharacter
    @NotEmpty(message = "type cannot be empty")
    private String type;
    @Size(max = 20, message = "The description must be at most 20 letters long.")
    @StringCharacter
    @NotEmpty(message = "description cannot be empty")
    private String description;
    @NotNull(message = "capacity cannot be empty")
@JustNumber
    @Min(value = 1)
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
