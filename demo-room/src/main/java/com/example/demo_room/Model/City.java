package com.example.demo_room.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "City")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

  private String name;
    private String state;
    private int totalSites;
@JsonIgnore
   @OneToMany  (mappedBy = "city",cascade = CascadeType.ALL,orphanRemoval = true)

   private List<Site> sites;


}
