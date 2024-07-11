package com.example.demo_room.Service.Implementation;
import com.example.demo_room.Exception.MyException;
import com.example.demo_room.Model.City;
import com.example.demo_room.Model.ConferenceRoom;
import com.example.demo_room.Model.Floor;
import com.example.demo_room.Model.Site;
import com.example.demo_room.Repository.CityRepo;
import com.example.demo_room.Repository.FloorRepo;
import com.example.demo_room.Repository.RoomRepo;
import com.example.demo_room.Repository.SiteRepo;
import com.example.demo_room.Service.Interface.IRoomService;
import com.example.demo_room.Utils.Utils;
import com.example.demo_room.dto.RoomRequest;
import com.example.demo_room.dto.RoomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


    @Service

    public class RoomService implements IRoomService {
        @Autowired
        private  RoomRepo roomRepo;
        @Autowired
        private FloorRepo floorRepo;
        @Autowired
        private CityRepo cityRepo;
        @Autowired
        private SiteRepo siteRepo;

        @Override
        public RoomResponse addNewRoom(RoomRequest conferenceRoom) {
            RoomResponse response = new  RoomResponse();
            try {
                ConferenceRoom room = new ConferenceRoom();
                room.setCapacity(conferenceRoom.getCapacity());
                room.setDescription(conferenceRoom.getDescription());
                room.setType(conferenceRoom.getType());
                City city = cityRepo.findById(conferenceRoom.getCityId()).orElseThrow(()->new MyException("City not found with this " +
                        "id"));
                Site site=siteRepo.findById(conferenceRoom.getSiteId()).orElseThrow(()-> new MyException("Site not Found"));
                if(!site.getCity().equals(city)){
                    throw new IllegalArgumentException("site id mentioned is not in the city selected");
                }
                Floor floor =floorRepo.findById(conferenceRoom.getFloorId()).orElseThrow(()->new MyException("floor not found with this id"));
                if(!floor.getSite().equals(site)){
                    throw new IllegalArgumentException("floor id mentioned is not in the site selected");
                }
                room.setCity(city);
                room.setSite(site);
                room.setFloor(floor);
                ConferenceRoom savedRoom = roomRepo.save(room);
                response = Utils.mapConferenceRoomEntityToRoomResponse(savedRoom);
                response.setResponseCode(200);
                response.setResponseMessage("successful");

            } catch (Exception e) {
                response.setResponseCode(500);
                response.setResponseMessage("Error saving a room " + e.getMessage());
            }
            return response;

        }

        @Override
        public List<String> getAllRoomTypes() {
            return roomRepo.findByType();
        }


        @Override
        public List<String> getCityList() {

            return roomRepo.findByCity();
        }

        @Override
        public List<RoomResponse> getAllRooms() {
            RoomResponse response=new RoomResponse();
            try{
            List<ConferenceRoom> RoomsList = roomRepo.findAll(Sort.by(Sort.Direction.ASC, "id"));

                response.setResponseCode(200);
                response.setResponseMessage("successful");
             return    Utils.mapCRoomListEntityToCRoomListDTO (RoomsList);
            } catch (Exception e) {
                response.setResponseCode(500);
                response.setResponseMessage("Error saving a room " + e.getMessage());
            }
            return  null;

        }



        @Override
        public RoomResponse deleteRoom(Long roomId) {
                RoomResponse response=new RoomResponse();
            try {
                roomRepo.findById(roomId).orElseThrow(() -> new MyException("Room Not Found"));
                roomRepo.deleteById(roomId);
                response.setResponseCode(200);
                response.setResponseMessage("successfully deleted");

            } catch (MyException e) {
                response.setResponseCode(404);
                response.setResponseMessage(e.getMessage());
            } catch (Exception e) {
                response.setResponseCode(500);
                response.setResponseMessage("Error saving a room " + e.getMessage());
            }
            return response;


        }

        @Override
        public RoomResponse updateRoom(long id,RoomRequest roomRequest) {
            RoomResponse response = new RoomResponse();
            try{
                ConferenceRoom room = roomRepo .findById(id).orElseThrow(() -> new MyException("Room Not Found"));
                if (roomRequest.getCapacity() != 0) room.setCapacity(roomRequest.getCapacity());
                if (roomRequest.getDescription() != null)  room.setDescription( roomRequest.getDescription());
                if(roomRequest.getType()!=null) room.setType(roomRequest.getType());
                System.out.println(roomRequest.getCityId());
                City city = cityRepo.findById(roomRequest.getCityId()).orElseThrow(()->new MyException("City not found with this " +
                        "id" + roomRequest.getCityId()));
                Site site=siteRepo.findById(roomRequest.getSiteId()).orElseThrow(()-> new MyException("Site not Found"));
                if(!site.getCity().equals(city)){
                    throw new IllegalArgumentException("site id mentioned is not in the city selected");
                }
                Floor floor =floorRepo.findById(roomRequest.getFloorId()).orElseThrow(()->new MyException("floor not found with this id"));
                if(floor.getSite().getId()!=roomRequest.getSiteId()){
                    throw new IllegalArgumentException("floor id mentioned is not in the site selected");
                }
                room.setFloor(floor);
                room.setCity(city);
                room.setSite(site);
                response.setResponseMessage("successful");
                response.setResponseCode(200);
                ConferenceRoom updatedRoom = roomRepo .save( room);
                return Utils.mapConferenceRoomEntityToRoomResponse (updatedRoom);

            }catch (MyException e){
                response.setResponseCode(404);
                response.setResponseMessage(e.getMessage());
            }catch (Exception e) {
                response.setResponseCode(500);
                response.setResponseMessage("Error saving a room " + e.getMessage());
            }
            return response;
        }


        @Override
        public RoomResponse getRoomById(Long roomId) {
            RoomResponse response=new RoomResponse();
            try {
                ConferenceRoom cRoom = roomRepo.findById(roomId).orElseThrow(() -> new MyException("Room Not Found"));
                 response = Utils.mapCRoomEntityToCRoomDTOPlusBookings(cRoom);
                response.setResponseCode(200);
                response.setResponseMessage("successful");
            }catch (MyException e) {
                response.setResponseCode(404);
                response.setResponseMessage(e.getMessage());
            } catch (Exception e) {
                response.setResponseCode(500);
                response.setResponseMessage("Error saving a room " + e.getMessage());
            }
            return response;

        }

        @Override
        public List<RoomResponse> getRooms(Integer cityId, Integer siteId, Integer floorId) {
            List<ConferenceRoom> rooms;

            if (cityId != null && siteId != null && floorId != null) {
                rooms = roomRepo.findByCityIdAndSiteIdAndFloorId(cityId, siteId, floorId);
            } else if (cityId != null && siteId != null) {
                rooms = roomRepo.findByCityIdAndSiteId(cityId, siteId);
            } else if (siteId != null) {
                rooms = roomRepo.findBySiteId(siteId);
            } else if (cityId != null) {
                rooms = roomRepo.findByCityId(cityId);

            } else if (floorId!=null) {
                rooms=roomRepo.findByFloorId(floorId);

            } else {
                rooms = roomRepo.findAll();
            }

            return  Utils.mapCRoomListEntityToCRoomListDTO(rooms);

        }

    }














