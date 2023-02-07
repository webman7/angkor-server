package com.adplatform.restApi.user.api;

import com.adplatform.restApi.user.dao.RoleRepository;
import com.adplatform.restApi.user.domain.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("roles")
public class RoleApi {

    private final RoleRepository roleRepository;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/info")
    public List<Role> list() {
        return this.roleRepository.findAll();
    }
}
