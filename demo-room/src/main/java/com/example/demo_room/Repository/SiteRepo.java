package com.example.demo_room.Repository;

import com.example.demo_room.Model.City;
import com.example.demo_room.Model.Site;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SiteRepo extends JpaRepository<Site,Integer> {


  List<Site>  getByCityId(int id);


    Page<Site> findAll( Pageable pageable);
}
