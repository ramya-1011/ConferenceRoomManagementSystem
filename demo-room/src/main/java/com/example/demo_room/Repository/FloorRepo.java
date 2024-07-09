package com.example.demo_room.Repository;

import com.example.demo_room.Model.Floor;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FloorRepo extends JpaRepository<Floor,Integer> {
    @Query(value = "SELECT f FROM Floor f WHERE f.site.id=:siteId")
    List<Floor> getBySite(@Param("siteId") int siteId);
    @Query(value = "SELECT f FROM Floor f WHERE f.city.id=:id")
    List<Floor> getByCityId(@Param("id") Integer id);
    @Query(value = "SELECT f FROM Floor f WHERE f.site.id=:id")
    List<Floor> getBySiteId(@Param("id") Integer id);
    List<Floor> getByCityIdAndSiteId(Integer cityId,Integer sieId);
}
