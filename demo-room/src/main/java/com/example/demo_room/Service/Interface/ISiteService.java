package com.example.demo_room.Service.Interface;

import com.example.demo_room.Model.Site;
import com.example.demo_room.dto.SiteRequest;
import com.example.demo_room.dto.SiteResponse;
import org.springframework.data.domain.Page;

public interface ISiteService {
    SiteResponse addNewSite(SiteRequest site);
    SiteResponse deleteSite(int id);
    SiteResponse updateSite(int id,SiteRequest site);

    SiteResponse getById(int id);
    Page<Site> searchSite(int page,int size);
}
