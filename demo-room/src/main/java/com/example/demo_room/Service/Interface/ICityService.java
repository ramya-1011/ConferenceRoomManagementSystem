package com.example.demo_room.Service.Interface;

import com.example.demo_room.dto.CityRequest;
import com.example.demo_room.dto.CityResponse;

import java.util.List;

public interface ICityService {
    CityResponse addNewLocation(  CityRequest cityRequest);
    List<CityResponse> getAllCityList();

    CityResponse updateCity(int id,CityRequest city);
    CityResponse deleteCity(int cityId);
    CityResponse getById(int id);
}
