package com.example.demo_room.dto;

import com.example.demo_room.Model.Site;
import com.example.demo_room.Utils.Numeric;
import com.example.demo_room.Utils.StringCharacter;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class CityRequest {
    @NotEmpty(message = "name of city cannot be empty")
    @StringCharacter
    private String name;
    @NotEmpty(message = "name of state cannot be empty")
    @StringCharacter
    @Size(max = 20, message = "The attribute must be at most 20 characters long.")
    private String state;
    @NotEmpty(message = "total sites can't be blank")
    @Min(value = 1,message = "minimum value should be 1")
    @Numeric
    private int totalSites;

}
