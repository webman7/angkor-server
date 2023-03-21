package com.adplatform.restApi.domain.media.api;

import com.adplatform.restApi.domain.company.dto.CompanyDto;
import com.adplatform.restApi.domain.media.dto.MediaDto;
import com.adplatform.restApi.domain.media.service.MediaSaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/media")
public class MediaCommandApi {

    private final MediaSaveService mediaSaveService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping
    public void save(@RequestBody @Valid MediaDto.Request.Save request) {
        this.mediaSaveService.save(request);
    }

    @PatchMapping
    public ResponseEntity<Void> update(@RequestBody @Valid MediaDto.Request.Update request) {
        this.mediaSaveService.update(request);
        return ResponseEntity.ok().build();
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/admin")
    public void saveAdmin(@RequestBody @Valid MediaDto.Request.Save request) {
        this.mediaSaveService.saveAdmin(request);
    }

    @PatchMapping("/admin")
    public ResponseEntity<Void> updateAdmin(@RequestBody @Valid MediaDto.Request.Update request) {
        this.mediaSaveService.update(request);
        return ResponseEntity.ok().build();
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        this.mediaSaveService.delete(id);
    }
}
