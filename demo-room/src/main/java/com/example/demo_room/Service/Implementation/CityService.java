package com.example.demo_room.Service.Implementation;

import com.example.demo_room.Exception.MyException;
import com.example.demo_room.Model.City;
import com.example.demo_room.Repository.CityRepo;
import com.example.demo_room.Service.Interface.ICityService;
import com.example.demo_room.Utils.Constants;
import com.example.demo_room.Utils.Utils;
import com.example.demo_room.dto.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service

public class CityService implements ICityService {

    @Autowired
    private  CityRepo cityRepo;

    @Override
    public CityResponse addNewLocation( CityRequest cityRequest) {

       CityResponse response=new CityResponse();
        try {
            City city=new City();
            city.setName(cityRequest.getName());
            city.setState(cityRequest.getState());
            city.setTotalSites(cityRequest.getTotalSites());
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
    public List<CityResponse> getAllCityList() {
         List<City> cityList=cityRepo.findAllByOrderByNameAsc();
        return  Utils.mapCityListEntityToCityListDTO(cityList);
    }



    @Override
    public CityResponse updateCity(int id, CityRequest cityRequest) {

       CityResponse response= new CityResponse();
        try{
           City city= cityRepo .findById(id).orElseThrow(() -> new MyException("City Not Found"));
            if(cityRequest.getName() !=null) city.setName(cityRequest.getName());
            if(cityRequest.getState()!=null) city.setState(cityRequest.getState());
            if(cityRequest.getTotalSites()!=0) city.setTotalSites(cityRequest.getTotalSites());
            cityRepo.save( city);

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
    public CityResponse deleteCity(int cityId) {
        CityResponse response=new CityResponse();
        try {
            cityRepo.findById(cityId).orElseThrow(() -> new MyException("city Not Found"));
            cityRepo.deleteById(cityId);
            response.setResponseCode(200);
            response.setResponseMessage("successful");

        } catch (MyException e) {
            response.setResponseCode(404);
            response.setResponseMessage(e.getMessage());
        } catch (Exception e) {
            response.setResponseCode(500);
            response.setResponseMessage("Error saving a city " + e.getMessage());
        }

        return response;
    }
    @Override
    public CityResponse getById(int id)   {
CityResponse response=new CityResponse();



        try {
            City city = cityRepo.findById(id).orElseThrow(() -> new MyException("Site Not Found"));
            CityResponse  cityDto = Utils.mapCityEntityToCityResponse(city);
            response.setStatusCode(200);
            cityDto.setResponseCode(Constants.ResponseCode.SUCCESS.value());
            cityDto.setResponseMessage("found site with these details");


            return cityDto;

        }catch (MyException e) {
            response.setStatusCode(404);
            response.setResponseMessage("no city found");

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setResponseMessage("error"+ e.getMessage());
        }
        return response;

    }
}
