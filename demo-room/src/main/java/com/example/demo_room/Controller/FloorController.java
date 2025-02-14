package com.example.demo_room.Controller;

import com.example.demo_room.Model.Floor;
import com.example.demo_room.Repository.FloorRepo;
import com.example.demo_room.Service.Implementation.FloorService;
import com.example.demo_room.dto.FloorRequest;
import com.example.demo_room.dto.FloorResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@CrossOrigin("*")
@RequestMapping("/floor")
public class FloorController {
    @Autowired
    private FloorRepo floorRepo;
    @Autowired
    private FloorService floorService;
@GetMapping("/getAll")
    public List<Floor> getAllFloors(){
    return floorRepo.findAll();
}
@GetMapping("/byId/{id}")
    public ResponseEntity<?> getFloorById(@PathVariable int id){
    FloorResponse getFloor=floorService.getById(id);
    return new ResponseEntity<>(getFloor, HttpStatus.OK);


}
    @PostMapping("/add")
    public ResponseEntity<FloorResponse> addFloor(@Valid  @RequestBody FloorRequest floor){

        FloorResponse savedFloor = null;
        try {
            savedFloor = floorService.addNewLocation(floor);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.status(savedFloor.getResponseCode()).body(savedFloor);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<FloorResponse> updateFloor(@PathVariable int id,@Valid @RequestBody FloorRequest floor){
        FloorResponse updatedFloor = floorService.updateFloor(id, floor);
        if (updatedFloor == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedFloor);


    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<FloorResponse> deleteFloor(@PathVariable int id){
        FloorResponse response =floorService.deleteFloor(id);
        return ResponseEntity.status(response.getResponseCode()).body(response);
    }
@GetMapping("/getBySite/{id}")
    public List<Floor> getBySiteId(@PathVariable int id){
    return floorRepo.getBySiteId(id);
}

@GetMapping("/filter")
    public ResponseEntity<List<FloorResponse>> getFloors(
        @RequestParam(value = "cityId", required = false) Integer cityId,
        @RequestParam(value = "siteId", required = false) Integer siteId){
    List<FloorResponse> floorList = floorService.getFloors(cityId,siteId);
    return ResponseEntity.ok(floorList);
}
    @DeleteMapping("/force/{floorId}")
    public ResponseEntity<?> forceDeleteFloor(@PathVariable Long floorId) {
        try {
            floorService.forcedDeleteFloor(floorId);
            return ResponseEntity.ok().body("Floor and all associated bookings have been deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while forcibly deleting the floor."+ e.getMessage());
        }
    }

    }


