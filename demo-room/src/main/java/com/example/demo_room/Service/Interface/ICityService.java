package com.example.demo_room.Service.Interface;

import com.example.demo_room.Model.City;
import com.example.demo_room.dto.CityRequest;
import com.example.demo_room.dto.CityResponse;
import com.example.demo_room.dto.Response;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ICityService {
    CityResponse addNewLocation(CityRequest cityRequest);
    Response getAllCitiesWithDetails();
    List<CityResponse> getAllCityList();
    Response getCityDetailsByName(String CityName);
    CityResponse updateCity(int id,CityRequest city);
    Response deleteCity(int cityId);
    CityResponse getById(int id);
}
