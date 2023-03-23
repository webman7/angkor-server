package com.adplatform.restApi.domain.media.api;

import com.adplatform.restApi.domain.media.dto.MediaDto;
import com.adplatform.restApi.domain.media.dto.placement.MediaPlacementDto;
import com.adplatform.restApi.domain.media.service.MediaPlacementSaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/placement/media")
public class MediaPlacementCommandApi {

    private final MediaPlacementSaveService mediaPlacementSaveService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping
    public void save(@Valid MediaPlacementDto.Request.Save request) {
        this.mediaPlacementSaveService.save(request);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping
    public void update(@Valid MediaPlacementDto.Request.Update request) {
        this.mediaPlacementSaveService.update(request);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/reregister")
    public void updateReRegister(@Valid MediaPlacementDto.Request.Update request) {
        this.mediaPlacementSaveService.updateReRegister(request);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        this.mediaPlacementSaveService.delete(id);
    }

    @PatchMapping("/admin/approve")
    public ResponseEntity<Void> updateAdminApprove(@RequestBody @Valid MediaPlacementDto.Request.Update request) {
        this.mediaPlacementSaveService.updateAdminApprove(request);
        return ResponseEntity.ok().build();
    }
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/admin/delete")
    public ResponseEntity<Void> updateAdminDelete(@RequestBody @Valid MediaPlacementDto.Request.Update request) {
        this.mediaPlacementSaveService.updateAdminDelete(request);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/admin/reject")
    public ResponseEntity<Void> updateAdminReject(@RequestBody @Valid MediaPlacementDto.Request.Update request) {
        this.mediaPlacementSaveService.updateAdminReject(request);
        return ResponseEntity.ok().build();
    }
}
