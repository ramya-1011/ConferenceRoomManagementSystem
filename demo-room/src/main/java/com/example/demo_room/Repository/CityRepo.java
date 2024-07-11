package com.example.demo_room.Repository;

import com.example.demo_room.Model.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CityRepo extends JpaRepository<City,Integer> {

      Optional<City>  findDistinctByName(String name);

    Optional<City> findById(int id);
    List<City> findAllByOrderByNameAsc();
}
