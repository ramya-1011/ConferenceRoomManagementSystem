package com.example.demo_room.Service.Implementation;

import com.example.demo_room.Exception.MyException;
import com.example.demo_room.Model.City;
import com.example.demo_room.Model.Site;
import com.example.demo_room.Repository.CityRepo;
import com.example.demo_room.Repository.SiteRepo;
import com.example.demo_room.Service.Interface.ISiteService;
import com.example.demo_room.Utils.Utils;
import com.example.demo_room.dto.*;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SiteService implements ISiteService {
    @Autowired
    private final SiteRepo siteRepo;
    @Autowired
    private CityRepo cityRepo;
    private final EntityManager entityManager;

    @Override
    public SiteResponse addNewSite(SiteRequest siteRequest) {
        SiteResponse response =new SiteResponse();
        try{

            Site site1=new Site();
            site1.setSiteId(siteRequest.getSiteId());
            site1.setDescription(siteRequest.getDescription());
            site1.setPinCode(siteRequest.getPinCode());
          //  site1.setTotalFloors(siteRequest.getTotalFloors());
            City city = cityRepo.findById(siteRequest.getCityId()).orElseThrow(()-> new MyException("city not found"));
            site1.setCity(city);
            Site savedSite = siteRepo.save(site1);

            response =Utils.mapSiteEntityToSiteResponse(savedSite);
            response.setStatusCode(200);
            response.setResponseMessage("success");

        }catch (Exception e) {
            response.setStatusCode(500);
            response.setResponseMessage("Error saving a city " + e.getMessage());
        }
        return response;
    }
    @Override
    public SiteResponse deleteSite(int id) {
        SiteResponse response=new SiteResponse();
        try {
            siteRepo.findById(id).orElseThrow(() -> new MyException("site not found"));
            siteRepo.deleteById(id);
            response.setStatusCode(200);
            response.setResponseMessage("successful");
        }catch (MyException e) {
            response.setStatusCode(404);
            response.setResponseMessage("error site not found");
        }
        catch (Exception e) {

            response.setStatusCode(500);
            response.setResponseMessage("error site not found");

        }
        return response;
    }
    @Override
        public SiteResponse updateSite(int id, SiteRequest siteRequest) {
        SiteResponse response= new SiteResponse();

            try {
                Site site = siteRepo.findById(id).orElseThrow(()-> new MyException("site not found"));
                if (siteRequest.getSiteId() != null) site.setSiteId( siteRequest.getSiteId());
             //   if (siteRequest.getTotalFloors() != 0) site.setTotalFloors(  siteRequest.getTotalFloors());
                if (siteRequest.getPinCode() != null) site.setPinCode(  siteRequest.getPinCode());
                if(siteRequest.getDescription()!=null) site.setDescription(siteRequest.getDescription());
                City city=cityRepo.findById(siteRequest.getCityId()).orElseThrow(()-> new MyException("no city"));
                site.setCity(city);
                siteRepo.save(site);

                return Utils.mapSiteEntityToSiteResponse(site);
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
    public SiteResponse getById(int id) {
            SiteResponse response=new SiteResponse();
            try {
                 Site site = siteRepo.findById(id).orElseThrow(() -> new MyException("Site Not Found"));
                response=  Utils.mapSiteEntityToSiteResponse(site);
                response.setStatusCode(200);
            }catch (MyException e) {
                response.setStatusCode(404);
                response.setResponseMessage(e.getMessage());
            } catch (Exception e) {
                response.setStatusCode(500);
                response.setResponseMessage("Error saving a room " + e.getMessage());
            }
            return  response;

        }

    @Override
    public Page<Site> searchSite(int offset, int size) {
     Page<Site> siteList=siteRepo.findAll(PageRequest.of(offset,size));
        return siteList ;
    }
    @Transactional
    public void forcedDeleteSite(Long siteId) {
        // Delete all bookings associated with the city's rooms
        entityManager.createQuery(
                        "DELETE FROM BookedRoom br WHERE br.roomId IN " +
                                "(SELECT r.id FROM ConferenceRoom r WHERE r.site.id = :siteId)")
                .setParameter("siteId", siteId)
                .executeUpdate();

        entityManager.createQuery(
                        "DELETE FROM ConferenceRoom r WHERE r.site.id = :siteId")
                .setParameter("siteId", siteId)
                .executeUpdate();

        Site site = entityManager.find(Site.class, siteId);
        if (site != null) {
            entityManager.remove(site);
        }
    }

}
