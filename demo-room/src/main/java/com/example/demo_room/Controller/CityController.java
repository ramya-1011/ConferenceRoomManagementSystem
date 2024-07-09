package com.example.demo_room.Controller;

import com.example.demo_room.Model.City;
import com.example.demo_room.Repository.CityRepo;
import com.example.demo_room.Repository.RoomRepo;
import com.example.demo_room.Service.Implementation.CityService;
import com.example.demo_room.dto.CityRequest;
import com.example.demo_room.dto.CityResponse;
import com.example.demo_room.dto.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cities")
@CrossOrigin("*")
public class CityController {
    @Autowired
    private CityRepo cityRepo;
    @Autowired
    private CityService cityService;

    @PostMapping("/add-city")
    public  ResponseEntity<CityResponse> addNewCity(@RequestBody CityRequest city) {
        CityResponse savedCity = cityService.addNewLocation(city);
       return ResponseEntity.ok(savedCity);
    }

    @GetMapping("/citiesList")
    public List<CityResponse> findAllCities(){
        return cityService.getAllCityList();
    }
  @GetMapping("/byName/{cityName}")
    public ResponseEntity<Response> findCityByName(@PathVariable String cityName){
      Response city = cityService.getCityDetailsByName(cityName);
        return  ResponseEntity.status(city.getStatusCode()).body(city) ;
  }
  @PutMapping("update/{id}")
    public ResponseEntity<CityResponse> updateCityDetails(@PathVariable int id, @RequestBody CityRequest city){
      CityResponse response = cityService.updateCity(id,city);
      return  ResponseEntity.ok(response) ;

  }
  @DeleteMapping("delete/{id}")
    public  ResponseEntity<Response> deleteCity(@PathVariable int id){
      Response response = cityService.deleteCity(id);
       cityService.deleteCity(id);
      return ResponseEntity.status(response.getStatusCode()).body(response) ;

  }
  @GetMapping("cityList")
    public List<String> getCityList(){
        return cityRepo.findAllCityList();
  }
  @GetMapping("/byId/{id}")

      public ResponseEntity<?> getCityById(@PathVariable int id) {
          CityResponse cityResponse = cityService.getById(id);
          return new ResponseEntity<>(cityResponse, HttpStatus.OK);
      }

}
