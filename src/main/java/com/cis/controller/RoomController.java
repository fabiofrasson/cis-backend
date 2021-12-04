package com.cis.controller;

import com.cis.model.Room;
import com.cis.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/salas")
public class RoomController {

    @Autowired
    private RoomService service;

    @Autowired
    public RoomController(RoomService service) {
        this.service = service;
    }

    @GetMapping  //list
    public ResponseEntity<List<Room>> list() {
        return ResponseEntity.ok(service.listAll());
    }


    @GetMapping(path = "/{uuid}") // UUID= "unicos"
    public ResponseEntity<Optional<Room>> findRoomByUUID(@PathVariable("uuid") UUID uuid) {
        return new ResponseEntity<>(service.findByUUID(uuid), HttpStatus.OK);
    }


    @PostMapping  //create
    public ResponseEntity<Room> save(@RequestBody @Valid Room address) throws Exception {
        return new ResponseEntity<>(service.create(address), HttpStatus.CREATED);
    }


    @PutMapping  //update
    public ResponseEntity<Void> replace(@RequestBody @Valid Room room) throws Exception {
        service.update(room);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "/{id}")  //delete
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}