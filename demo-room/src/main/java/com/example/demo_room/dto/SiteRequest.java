package com.example.demo_room.dto;

import com.example.demo_room.Utils.Alphanumeric;
import com.example.demo_room.Utils.JustNumber;
import com.example.demo_room.Utils.Numeric;
import com.example.demo_room.Utils.StringCharacter;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.*;
import lombok.Data;
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class SiteRequest {

    @Size(max = 15, message = "The siteId must be at most 10 letters long.")
    @Alphanumeric
    @NotEmpty(message = "siteId cannot be empty")
    private String siteId;
    @Size(max = 20, message = "The description must be at most 20 letters long.")
    @Alphanumeric
    @NotEmpty(message = "description cannot be empty")
    private String description;
    @Size(max = 6,min = 6,message = "valid pinCode is required of 6  digits")
    @Numeric
    @NotEmpty(message = "pinCode cannot be empty")
    private String pinCode;
//    @Min(value = 1,message = "total floors cant be 0")
//    @Max(value = 30,message = "floors cannot exceed 30")
//    @NotNull(message = "totalFloors cannot be empty")
//    private int totalFloors;
    @JustNumber
    @NotNull(message = "cityId cannot be empty")
    private int cityId;

}
