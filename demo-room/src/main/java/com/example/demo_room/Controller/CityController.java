package com.example.demo_room.Controller;

import com.example.demo_room.Exception.MyException;
import com.example.demo_room.Model.City;
import com.example.demo_room.Repository.BookingRepo;
import com.example.demo_room.Repository.CityRepo;
import com.example.demo_room.Service.Implementation.BookingService;
import com.example.demo_room.Service.Implementation.CityService;
import com.example.demo_room.dto.CityRequest;
import com.example.demo_room.dto.CityResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@Validated
@RequestMapping("/cities")
@CrossOrigin("*")
public class CityController {
    @Autowired
    private CityRepo cityRepo;
    @Autowired
    private CityService cityService;
    @Autowired
    private BookingService bookingService;
    @Autowired
    private BookingRepo bookingRepo;

    @PostMapping("/add-city")
    public  ResponseEntity<CityResponse> addNewCity (  @Valid  @RequestBody CityRequest city) {
        CityResponse savedCity = cityService.addNewLocation(city);
       return ResponseEntity.ok(savedCity);
    }

    @GetMapping("/citiesList")
    public List<CityResponse> findAllCities(){
        return cityService.getAllCityList();
    }

  @PutMapping("update/{id}")
    public ResponseEntity<CityResponse> updateCityDetails(@PathVariable int id,@Valid @RequestBody CityRequest city){
      CityResponse response = cityService.updateCity(id,city);
      return  ResponseEntity.ok(response) ;

  }
  @DeleteMapping("delete/{id}")
    public  ResponseEntity<CityResponse> deleteCity(@PathVariable int id){
      CityResponse response = cityService.deleteCity(id);


      return ResponseEntity.status(response.getResponseCode()).body(response) ;

  }

  @GetMapping("cityList/{name}")
    public Optional<City> getCityByName(@PathVariable String name){
        return cityRepo.findDistinctByName(name);
  }
  @GetMapping("/byId/{id}")

      public ResponseEntity<?> getCityById(@PathVariable int id) {
          CityResponse cityResponse = cityService.getById(id);
          return new ResponseEntity<>(cityResponse, HttpStatus.OK);
      }



 @DeleteMapping("/{cityId}")
    public ResponseEntity<?> deleteCity(@PathVariable Long cityId) {
        try {
            boolean canDelete = cityService.checkAndDeleteCity(cityId);
            if (canDelete) {
                return ResponseEntity.ok().body("City deleted successfully.");
            } else {
                Map<String, Object> response = new HashMap<>();
                response.put("canDelete", canDelete);
                response.put("message", "City has active bookings. Do you want to go ahead with deletion?");
                return ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body("City has active bookings. Do you want to go ahead with deletion?");
            }
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while deleting the city."+ e.getMessage());
        }
    }
    @DeleteMapping("/force/{cityId}")
    public ResponseEntity<?> forceDeleteCity(@PathVariable Long cityId) {
        try {
            cityService.forcedDeleteCity(cityId);
            return ResponseEntity.ok().body("City and all associated bookings have been deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while forcibly deleting the city."+ e.getMessage());
        }
    }

}

