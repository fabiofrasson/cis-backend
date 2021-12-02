package com.cis.controller;

import com.cis.model.Admin;
import com.cis.model.dto.AdminCreationDTO;
import com.cis.model.dto.AdminReturnDTO;
import com.cis.model.dto.AdminUpdateDTO;
import com.cis.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/admins")
@AllArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class AdminContoller {

    private AdminService service;

    @GetMapping
    public ResponseEntity<Page<Admin>> list(Pageable pageable) {
        return ResponseEntity.ok(service.listAll(pageable));
    }

    @PostMapping
    public ResponseEntity<AdminReturnDTO> save(@RequestBody @Valid AdminCreationDTO admin) {
        return new ResponseEntity<>(service.save(admin), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@RequestParam("id")UUID id, @RequestBody AdminUpdateDTO admin) {
        return new ResponseEntity<>(service.update(id, admin), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") UUID id) {
        return new ResponseEntity<>(service.delete(id), HttpStatus.OK);
    }
}
