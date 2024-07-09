package com.example.demo_room.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class SiteRequest {


    private String siteId;
    private String description;
    private String pinCode;
    private int totalFloors;
    private String locationName;
    private int cityId;

}
