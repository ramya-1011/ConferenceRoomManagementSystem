package com.example.demo_room.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity

@Table(name = "sites")
public class Site {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String siteId;
    private String description;
    private String pinCode;
    @Min(value = 1,message = "total floors cant be 0")
    private int totalFloors;
    private String locationName;

    @ManyToOne
    @JoinColumn(name = "city_Id")
    private City city;
    @JsonIgnore
    @OneToMany(mappedBy = "site",cascade = CascadeType.ALL,orphanRemoval = true)
   private List<Floor> floors;

}
