package com.example.demo_room.Model;

import com.example.demo_room.dto.CommonAPIResponse;
import com.example.demo_room.dto.FloorResponse;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Entity
@Getter
@Setter
public class Floor   {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String floorId;
   // private int totalRooms;
    @ManyToOne
    @JoinColumn(name = "site_id")
    private Site site;
    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;
    @JsonIgnore
    @OneToMany(mappedBy = "floor",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<ConferenceRoom> rooms;

}
