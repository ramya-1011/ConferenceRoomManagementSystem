package com.example.demo_room.Utils;

import com.example.demo_room.Model.*;
import com.example.demo_room.dto.*;

import java.security.SecureRandom;
import java.util.List;
import java.util.stream.Collectors;

public class Utils {
    private static final String ALPHANUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom secureRandom = new SecureRandom();

    public static String generateRandomConfirmationCode(int length) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int randomIndex = secureRandom.nextInt(ALPHANUMERIC_STRING.length());
            char randomChar = ALPHANUMERIC_STRING.charAt(randomIndex);
            stringBuilder.append(randomChar);
        }
        return stringBuilder.toString();
    }
    public static RoomResponse mapConferenceRoomEntityToRoomResponse(ConferenceRoom Room) {
        RoomResponse cRoomDTO = new RoomResponse();
        cRoomDTO.setId(Room.getId());
        cRoomDTO.setCapacity(Room.getCapacity());
        cRoomDTO.setCity(Room.getCity());
        cRoomDTO.setSite(Room.getSite());
        cRoomDTO.setFloor(Room.getFloor());
        cRoomDTO.setType(Room.getType());
        cRoomDTO.setDescription(Room.getDescription());

        return  cRoomDTO;
    }
    public static CityResponse mapCityEntityToCityResponse(City city) {
        CityResponse   response = new CityResponse();
        response.setId(city.getId());
        response.setName(city.getName());
        response.setState(city.getState());
     //   response.setTotalSites(city.getTotalSites());

        return  response;
    }
    public static SiteResponse mapSiteEntityToSiteResponse(Site site) {
        SiteResponse   response = new SiteResponse();
        response.setId(site.getId());
        response.setSiteId(site.getSiteId());
        response.setCity(site.getCity());
        response.setLocationName(site.getLocationName());
        response.setDescription(site.getDescription());
     //   response.setTotalFloors(site.getTotalFloors());
        response.setPinCode(site.getPinCode());
        return  response;
    }
    public static FloorResponse mapFloorEntityToFloorResponse(Floor floor) {
        FloorResponse   response = new FloorResponse();
        response.setId(floor.getId());
        response.setFloorId(floor.getFloorId());
      response.setCity(floor.getCity());
        response.setSite(floor.getSite());
      //  response.setTotalRooms(floor.getTotalRooms());

        return  response;
    }
    public static List< RoomResponse> mapCRoomListEntityToCRoomListDTO(List<ConferenceRoom> RoomsList){
        return RoomsList.stream().map(Utils::mapConferenceRoomEntityToRoomResponse ).collect(Collectors.toList());
    }
    public static List< FloorResponse> mapFloorListEntityToFloorListDTO(List<Floor> FloorList){
        return FloorList.stream().map(Utils::mapFloorEntityToFloorResponse).collect(Collectors.toList());
    }
    public static List< SiteResponse> mapSiteListEntityT0SiteListDTO(List< Site> SitesList){
        return SitesList.stream().map(Utils::mapSiteEntityToSiteResponse ).collect(Collectors.toList());
    }

    public static List<BookedRoomResponse> mapBookingListEntityToBookingListDTO(List<BookedRoom> bookingList) {
        return bookingList.stream().map(Utils::mapBookingEntityToBookingDTO).collect(Collectors.toList());
    }
    public static List<CityResponse> mapCityListEntityToCityListDTO(List<City> cityList) {
        return cityList.stream().map(Utils::mapCityEntityToCityResponse).collect(Collectors.toList());
    }

    public static  RoomResponse mapCRoomEntityToCRoomDTOPlusBookings(ConferenceRoom cRoom) {
         RoomResponse cRoomDTO = new RoomResponse();
        cRoomDTO.setId(cRoom.getId());
        cRoomDTO.setCapacity(cRoom.getCapacity());
        cRoomDTO.setCity(cRoom.getCity());
        cRoomDTO.setFloor(cRoom.getFloor());
        cRoomDTO.setType(cRoom.getType());
        cRoomDTO.setSite(cRoom.getSite());
        cRoomDTO.setDescription(cRoom.getDescription());

        if (cRoom.getBookings() != null) {
            cRoomDTO.setBookings(cRoom.getBookings().stream().map(Utils::mapBookingEntityToBookingDTO).collect(Collectors.toList()));
        }

        return  cRoomDTO;
    }
    public static BookedRoomResponse mapBookingEntityToBookingDTO(BookedRoom booking) {
        BookedRoomResponse bookingDTO = new BookedRoomResponse();
        bookingDTO.setEmployeeName(booking.getEmployeeName());
        bookingDTO.setRoomId(booking.getRoomId());
        bookingDTO.setEmployeeId(booking.getEmployeeId());
        bookingDTO.setEmployee_ph_no(booking.getEmployee_ph_no());
        bookingDTO.setBookingID(booking.getBookingID());
        bookingDTO.setBookingDate(booking.getBookingDate());
        bookingDTO.setStartTime(booking.getStartTime());
        bookingDTO.setAttendees(booking.getAttendees());
        bookingDTO.setEndTime(booking.getEndTime());
        bookingDTO.setConfirmationCode(booking.getConfirmationCode());
        return bookingDTO;
    }

    public static BookedRoomResponse mapBookingEntityToBookingDTOPlusBookedRooms(BookedRoom booking, boolean mapUser) {

        BookedRoomResponse bookingDTO = new BookedRoomResponse();

        bookingDTO.setBookingID(booking.getBookingID());
        bookingDTO.setBookingDate(booking.getBookingDate());
        bookingDTO.setStartTime(booking.getStartTime());
        bookingDTO.setEndTime(booking.getEndTime());
        bookingDTO.setAttendees(booking.getAttendees());
        bookingDTO.setConfirmationCode(booking.getConfirmationCode());
        bookingDTO.setEmployeeId(booking.getEmployeeId());
        bookingDTO.setEmployeeName(booking.getEmployeeName());
        bookingDTO.setEmployee_ph_no(booking.getEmployee_ph_no());

        if (booking.getRoom() != null) {
            RoomResponse roomDTO = new RoomResponse();

            roomDTO.setId(booking.getRoom().getId());
            roomDTO.setType(booking.getRoom().getType());
            roomDTO.setCapacity(booking.getRoom().getCapacity());
            roomDTO.setCity(booking.getRoom().getCity());
            roomDTO.setDescription(booking.getRoom().getDescription());
            roomDTO.setSite(booking.getRoom().getSite());
            roomDTO.setFloor(booking.getRoom().getFloor());
            bookingDTO.setRoom(roomDTO);
        }


        return bookingDTO;
    }

}

