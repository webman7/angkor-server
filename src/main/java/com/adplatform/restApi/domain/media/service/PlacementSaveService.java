package com.adplatform.restApi.domain.media.service;

import com.adplatform.restApi.domain.media.dao.placement.PlacementRepository;
import com.adplatform.restApi.domain.media.domain.Placement;
import com.adplatform.restApi.domain.media.dto.placement.PlacementDto;
import com.adplatform.restApi.domain.media.dto.placement.PlacementMapper;
import com.adplatform.restApi.domain.media.exception.PlacementUpdateException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class PlacementSaveService {

    private final PlacementRepository placementRepository;

    private final PlacementMapper placementMapper;

    public void save(PlacementDto.Request.Save request) {
        Placement placement = this.placementMapper.toEntity(request);
        this.placementRepository.save(placement);
    }

    public void update(PlacementDto.Request.Update request) {
        try{
            Placement placement = PlacementFindUtils.findByIdOrElseThrow(request.getId(), this.placementRepository).update(request);
        }catch (Exception e){
            throw new PlacementUpdateException();
        }
    }

    public void delete(Integer id) {
        PlacementFindUtils.findByIdOrElseThrow(id, this.placementRepository).delete();
    }
}
