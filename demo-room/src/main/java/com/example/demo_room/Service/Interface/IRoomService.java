package com.example.demo_room.Service.Interface;

import com.example.demo_room.Model.ConferenceRoom;

import com.example.demo_room.dto.Response;
import com.example.demo_room.dto.RoomRequest;
import com.example.demo_room.dto.RoomResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface IRoomService {
     RoomResponse addNewRoom(RoomRequest roomRequest);
    List<String> getAllRoomTypes();
    List<String> getCityList();

   List<RoomResponse>  getAllRooms();

   Response deleteRoom(Long roomId);

    RoomResponse updateRoom(long id,RoomRequest roomRequest);

    RoomResponse getRoomById(Long roomId);

 List<RoomResponse> getRooms(Integer cityId,Integer siteId,Integer floorId);


}
