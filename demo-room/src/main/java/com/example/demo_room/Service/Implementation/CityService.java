package com.example.demo_room.Service.Implementation;

import com.example.demo_room.Exception.MyException;
import com.example.demo_room.Model.City;
import com.example.demo_room.Repository.CityRepo;
import com.example.demo_room.Service.Interface.ICityService;
import com.example.demo_room.Utils.Constants;
import com.example.demo_room.Utils.Utils;
import com.example.demo_room.dto.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService implements ICityService {

    @Autowired
    private  CityRepo cityRepo;

    @Override
    public CityResponse addNewLocation(CityRequest cityRequest) {

       CityResponse response=new CityResponse();
        try {
//            city.setId(city.getId());
            City city=new City();
            city.setName(cityRequest.getName());
            city.setState(cityRequest.getState());
            city.setTotalSites(cityRequest.getTotalSites());
         // city.setSites(cityRequest.getSites());
            City savedCity = cityRepo.save(city);
            response=Utils.mapCityEntityToCityResponse(savedCity);
           response.setResponseCode(200);
            response.setResponseMessage("location added Successfully");
            response.setStatusCode(200);


        } catch (Exception e) {
            response.setResponseCode(Constants.ResponseCode.FAILED.value());
            response.setResponseMessage("Error in adding location ");
            response.setStatusCode(500);
        }
return response;
    }

    @Override
    public  Response getAllCitiesWithDetails() {
        Response response=new Response();
        try{
            List<City> cityList =  cityRepo.findAll(Sort.by(Sort.Direction.DESC, "id"));
            List< CityResponse> cityDTOList = Utils.mapCityListEntityToCityListDTO (cityList);
            response.setStatusCode(200);
            response.setMessage("successful");
            response.setCityList(cityDTOList);

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error fetching a cities " + e.getMessage());
        }
        return  response;

    }

    @Override
    public List<CityResponse> getAllCityList() {
         List<City> cityList=cityRepo.findAllByOrderByNameAsc();
        return  Utils.mapCityListEntityToCityListDTO(cityList);
    }

    @Override
    public Response getCityDetailsByName(String cityName) {
        Response response=new Response();
        try{
            City city =  cityRepo.getByName(cityName).orElseThrow(() -> new MyException("City Not Found"));
             CityResponse cityDTO = Utils.mapCityEntityToCityResponse (city);
            response.setStatusCode(200);
            response.setMessage("successful");
            response.setCity(city);

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error fetching a city " + e.getMessage());
        }
        return  response;


    }

    @Override
    public CityResponse updateCity(int id, CityRequest cityRequest) {

       CityResponse response= new CityResponse();
        try{
           City city= cityRepo .findById(id).orElseThrow(() -> new MyException("City Not Found"));
            if(cityRequest.getName() !=null) city.setName(cityRequest.getName());
            if(cityRequest.getState()!=null) city.setState(cityRequest.getState());
            if(cityRequest.getTotalSites()!=0) city.setTotalSites(cityRequest.getTotalSites());
//          if(cityRequest.getSites()!=null) city.setSites(cityRequest.getSites());
//            cityRequest.setSites(cityRequest.getSites());
            cityRepo.save( city);
//
//            response.setStatusCode(200);
//            response.setResponseMessage("successful");
          return   Utils.mapCityEntityToCityResponse (city);
        }catch (MyException e){
            response.setStatusCode(404);
            response.setResponseMessage(e.getMessage());
        }catch (Exception e) {
            response.setStatusCode(500);
            response.setResponseMessage("Error saving a city " + e.getMessage());
        }
        return response;

    }

    @Override
    public Response deleteCity(int cityId) {
        Response response=new Response();
        try {
            cityRepo.findById(cityId).orElseThrow(() -> new MyException("city Not Found"));
            cityRepo.deleteById(cityId);
            response.setStatusCode(200);
            response.setMessage("successful");

        } catch (MyException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error saving a city " + e.getMessage());
        }

        return response;
    }
    @Override
    public CityResponse getById(int id)   {
CityResponse response=new CityResponse();
        Response Response=new Response();


        try {
            City city = cityRepo.findById(id).orElseThrow(() -> new MyException("Site Not Found"));
            CityResponse  cityDto = Utils.mapCityEntityToCityResponse(city);
            response.setStatusCode(200);
            Response.setMessage("successful");
            cityDto.setResponseCode(Constants.ResponseCode.SUCCESS.value());
            cityDto.setResponseMessage("found site with these details");

            Response.setCityResponse(cityDto);

        }catch (MyException e) {
            response.setStatusCode(404);
            response.setResponseMessage("no city found");
            Response.setMessage(e.getMessage());
            Response.setCityResponse(response);
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setResponseMessage("error");
            Response.setCityResponse(response);
            Response.setMessage("Error saving a room " + e.getMessage());
        }
        return Response.getCityResponse();

    }
}
