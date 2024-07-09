package com.example.demo_room.Controller;

import com.example.demo_room.Model.ConferenceRoom;
import com.example.demo_room.Repository.CityRepo;
import com.example.demo_room.Repository.RoomRepo;
import com.example.demo_room.Service.Interface.IRoomService;
import com.example.demo_room.dto.Response;
import com.example.demo_room.dto.RoomRequest;
import com.example.demo_room.dto.RoomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rooms")
@CrossOrigin("*")
public class RoomController {
    @Autowired
    private CityRepo cityRepo;
    @Autowired
    private RoomRepo roomRepo;
    @Autowired
    private final IRoomService roomService;
    @PostMapping("/add-room")
public  ResponseEntity< RoomResponse> addNewRoom(@RequestBody RoomRequest conferenceRoom)
       {
    RoomResponse savedRoom = roomService.addNewRoom(  conferenceRoom);
    return  ResponseEntity.status(savedRoom.getResponseCode()).body(savedRoom);

}
    @GetMapping("/types")
    public List<String> getRoomTypes() {
        return roomService.getAllRoomTypes();
    }
    @GetMapping("/types-city")
    public List<String> getCityList() {
        return roomService.getCityList();
    }

    @GetMapping("/allRooms")
    public ResponseEntity<List<RoomResponse>> getAllRooms(){
       List<RoomResponse>  response = roomService.getAllRooms();
        return ResponseEntity.ok(response);
    }
    @GetMapping("/byCity/{cityId}")
    public List<ConferenceRoom> getByCity(@PathVariable int cityId){
        return roomRepo.findRoomByCity(cityId);
    }
    @DeleteMapping("/delete/{roomId}")
    public ResponseEntity<Response> deleteRoom(@PathVariable Long roomId) {
        Response response = roomService.deleteRoom(roomId);
        return ResponseEntity.status(response.getStatusCode()).body(response);

    }

    @GetMapping("/room-by-id/{roomId}")
    public ResponseEntity<RoomResponse> getRoomById(@PathVariable Long roomId) {
        RoomResponse response = roomService.getRoomById(roomId);
        return ResponseEntity.status(response.getResponseCode()).body(response);
    }

    @PutMapping("/update/{roomId}")

    public ResponseEntity<RoomResponse> updateRoom(@PathVariable Long roomId,@RequestBody RoomRequest room) {
        RoomResponse response = roomService.updateRoom( roomId,room);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/filter")
    public ResponseEntity<List<RoomResponse>> getAllRooms(
            @RequestParam(value = "cityId", required = false) Integer cityId,
            @RequestParam(value = "siteId", required = false) Integer siteId,
            @RequestParam(value = "floorId", required = false) Integer floorId)
    {
        List< RoomResponse> rooms = roomService.getRooms(cityId,siteId,floorId);
        return ResponseEntity.ok(rooms);
    }




}
