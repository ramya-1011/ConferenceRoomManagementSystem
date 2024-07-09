package com.example.demo_room.Repository;

import com.example.demo_room.Model.City;
import com.example.demo_room.dto.CityResponse;
import com.example.demo_room.dto.Response;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CityRepo extends JpaRepository<City,Integer> {
    @Query(value = "SELECT c From City c WHERE c.name=:cityName ")
    Optional<City> getByName(String cityName);
    @Query(value = "SELECT DISTINCT c.name FROM City c")
    List<String> findAllCityList();
    Optional<City> deleteById(int id);
    Optional<City> findById(int id);
    List<City> findAllByOrderByNameAsc();
}
