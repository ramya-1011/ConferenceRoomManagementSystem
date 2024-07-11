package com.example.demo_room.Repository;

import com.example.demo_room.Model.Floor;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FloorRepo extends JpaRepository<Floor,Integer> {

    List<Floor> getByCityId( Integer id);
    List<Floor> getBySiteId( Integer id);
    List<Floor> getByCityIdAndSiteId(Integer cityId,Integer sieId);
}
