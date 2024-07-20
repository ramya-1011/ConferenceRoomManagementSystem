package com.example.demo_room.Controller;

import com.example.demo_room.Repository.SiteRepo;
import com.example.demo_room.Service.Implementation.SiteService;
import com.example.demo_room.dto.APIResponse;
import com.example.demo_room.dto.SiteRequest;
import com.example.demo_room.dto.SiteResponse;
import com.example.demo_room.Model.Site;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/site")
@CrossOrigin("*")
public class SiteController {
    @Autowired
    private SiteRepo siteRepo;
    @Autowired
    private SiteService siteService;


    @GetMapping("/byId/{id}")
    public ResponseEntity<?> getSiteById(@PathVariable int id) {
        SiteResponse site = siteService.getById(id);
        return new ResponseEntity<>(site, HttpStatus.OK);
    }

    @PostMapping("/add-site")
    public  ResponseEntity<SiteResponse> addNewSite(@Valid @RequestBody SiteRequest site) {
         SiteResponse savedSite = siteService.addNewSite(site);
        return ResponseEntity.status(savedSite.getStatusCode()).body(savedSite);
    }
    @GetMapping("/sitesList")
    public List<Site> getAllSites(){
        return siteRepo.findAll();
    }
    @GetMapping("/byLocation/{id}")
    public List<Site> getByLocation(@PathVariable int id){
        return siteRepo.getByCityId(id);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<SiteResponse> deleteSite(@PathVariable int id){
        SiteResponse response =siteService.deleteSite(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
@PutMapping("/update/{id}")
    public ResponseEntity<SiteResponse> updateSite(@PathVariable int id,  @RequestBody SiteRequest site){
    SiteResponse updatedSite = siteService.updateSite(id, site);
    if (updatedSite == null) {
        return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(updatedSite);


}
    @GetMapping("/pagination")
    public APIResponse<Page<Site>> getByPagination(@RequestParam(defaultValue = "0") int offset,
                                                   @RequestParam(defaultValue = "10") int pageSize){
        Page<Site> sitesWithPagination= siteService.searchSite(offset,pageSize);
        return new APIResponse<>(sitesWithPagination.getSize(),sitesWithPagination);
    }

}
