package com.example.demo_room.Service.Implementation;

import com.example.demo_room.Exception.MyException;
import com.example.demo_room.Model.City;
import com.example.demo_room.Model.Floor;
import com.example.demo_room.Model.Site;
import com.example.demo_room.Repository.CityRepo;
import com.example.demo_room.Repository.FloorRepo;
import com.example.demo_room.Repository.SiteRepo;
import com.example.demo_room.Utils.Constants;
import com.example.demo_room.Utils.Utils;
import com.example.demo_room.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FloorService {
    @Autowired
    private FloorRepo floorRepo;
    @Autowired
    private CityRepo cityRepo;
    @Autowired
    private SiteRepo siteRepo;

    public FloorResponse addNewLocation( FloorRequest floorRequest) throws Exception {

        FloorResponse response=new FloorResponse( );

        try {
             Floor floor1= new Floor();
             floor1.setFloorId(floorRequest.getFloorId());
             floor1.setTotalRooms(floorRequest.getTotalRooms());
            City city = cityRepo.findById(floorRequest.getCityId()).orElseThrow(()->new MyException("City not found with id"));
            Site site=siteRepo.findById(floorRequest.getSiteId()).orElseThrow(()-> new MyException("Site not Found"));
              if(!site.getCity().equals(city)){
                  throw new IllegalArgumentException("site id mentioned is not in the city selected");
              }
            floor1.setCity(city);
            floor1.setSite(site);
            Floor savedFloor=floorRepo.save(floor1);
            response= Utils.mapFloorEntityToFloorResponse(savedFloor);
            response.setResponseCode(Constants.ResponseCode.SUCCESS.value());
            response.setResponseMessage("Floor added Successfully");

        } catch (Exception e) {
            response.setResponseCode(Constants.ResponseCode.FAILED.value());
            response.setResponseMessage("Error in adding floor "+e.getMessage());
        }
        return response;
    }

        public FloorResponse updateFloor(int id, FloorRequest floorRequest) {
            FloorResponse response = new FloorResponse();

            try{
                Floor floor=floorRepo.findById(id).orElseThrow(()->new MyException("floor not found"));
                if(floorRequest.getFloorId()!=null) floor.setFloorId(floorRequest.getFloorId());
                if(floorRequest.getTotalRooms()!=0) floor.setTotalRooms(floorRequest.getTotalRooms());
                City city =cityRepo.findById(floorRequest.getCityId()).orElseThrow(()->new MyException("city not found"));
                Site site=siteRepo.findById(floorRequest.getSiteId()).orElseThrow(()-> new MyException("Site not Found"));
                if(!site.getCity().equals(city)){
                    throw new IllegalArgumentException("site id mentioned is not in the city selected");
                }
                 floorRepo.save(floor);
                floor.setResponseCode(Constants.ResponseCode.SUCCESS.value());
                floor.setResponseMessage("updated");
                return Utils.mapFloorEntityToFloorResponse(floor);
            }catch (MyException e){
                response.setResponseCode(404);
                response.setResponseMessage(e.getMessage());
            }catch (Exception e) {
                response.setResponseCode(500);
                response.setResponseMessage("Error saving a floor " + e.getMessage());
            }
            return response;


    }
    public FloorResponse getById(int id) {
        FloorResponse floorResponse=new FloorResponse();

        try {
             Floor floor =  floorRepo.findById(id).orElseThrow(() -> new MyException("floor Not Found"));
            floorResponse= Utils.mapFloorEntityToFloorResponse(floor);
            floor.setResponseMessage("fetched successfully");
            floor.setResponseCode( 200);
        }catch (MyException e) {
            floorResponse.setResponseMessage("error ");
            floorResponse.setResponseCode(404);
        } catch (Exception e) {
            floorResponse.setResponseCode(500);
            floorResponse.setResponseMessage("error"+ e.getMessage());
        }
        return  floorResponse;

    }
    public FloorResponse deleteFloor(int id) {
        FloorResponse response=new FloorResponse();
        try {
            floorRepo.findById(id).orElseThrow(() -> new MyException("floor not found"));
            floorRepo.deleteById(id);
            response.setResponseCode(200);
            response.setResponseMessage("successful");
        }catch (MyException e) {
            response.setResponseCode(404);
            response.setResponseMessage("error floor not found");
        }
        catch (Exception e) {

            response.setResponseCode(500);
            response.setResponseMessage("error floor not found" + e.getMessage());

        }
        return response;
    }

public List<FloorResponse> getFloors(Integer cityId,Integer siteId){
        List<Floor> floors;
if(cityId!=null && siteId!=null){
    floors=floorRepo.getByCityIdAndSiteId(cityId,siteId);
}
else if (cityId!=null){
    floors=floorRepo.getByCityId(cityId);
}
else if(siteId!=null){
    floors=floorRepo.getBySiteId(siteId);
}
else {
  floors  =floorRepo.findAll();
}
return Utils.mapFloorListEntityToFloorListDTO(floors);
}

}
