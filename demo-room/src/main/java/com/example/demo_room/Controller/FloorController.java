package com.example.demo_room.Controller;

import com.example.demo_room.Model.Floor;
import com.example.demo_room.Repository.FloorRepo;
import com.example.demo_room.Service.Implementation.FloorService;
import com.example.demo_room.dto.FloorRequest;
import com.example.demo_room.dto.FloorResponse;
import com.example.demo_room.dto.RoomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
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
    public ResponseEntity<FloorResponse> addFloor(@RequestBody FloorRequest floor){

        FloorResponse savedFloor = null;
        try {
            savedFloor = floorService.addNewLocation(floor);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.status(savedFloor.getResponseCode()).body(savedFloor);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<FloorResponse> updateFloor(@PathVariable int id,@RequestBody FloorRequest floor){
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
    return floorRepo.getBySite(id);
}

@GetMapping("/filter")
    public ResponseEntity<List<FloorResponse>> getFloors(
        @RequestParam(value = "cityId", required = false) Integer cityId,
        @RequestParam(value = "siteId", required = false) Integer siteId){
    List<FloorResponse> floorList = floorService.getFloors(cityId,siteId);
    return ResponseEntity.ok(floorList);
}


    }


