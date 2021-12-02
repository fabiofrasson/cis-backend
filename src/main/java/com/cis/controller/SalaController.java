package com.cis.controller;

import com.cis.model.Sala;
import com.cis.service.SalaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/salas")
public class SalaController {

    @Autowired
    private SalaService service;

    @Autowired
    public SalaController(SalaService service) {
        this.service = service;
    }

    @GetMapping  //list
    public ResponseEntity<List<Sala>> list() {
        return ResponseEntity.ok(service.listAll());
    }



//  @PostMapping // nova sala
//  public ResponseEntity<RoomResponseDTO> save(@RequestBody NewRoomRequestDTO responseBody)
//      throws Exception {
//    return new ResponseEntity<>(this.service.save(responseBody), HttpStatus.CREATED);
//  }

    //  @GetMapping(path = "/{uuid}") // UUID= "unicos"
    //  public ResponseEntity<RoomResponseDTO> findRoomByUUID(@RequestParam UUID uuid) {
    //    return new ResponseEntity<>(this.service.findByUUID(uuid), HttpStatus.OK);
    //  }



    @PostMapping  //save
    public ResponseEntity<Sala> save(@RequestBody @Valid Sala address) throws Exception {
        return new ResponseEntity<>(service.save(address), HttpStatus.CREATED);
    }

    //  @PutMapping(path = "/{uuid}")
    //  public final ResponseEntity<RoomResponseDTO> updatePatient(
    //      @RequestParam UUID uuid, @RequestBody NewRoomRequestDTO responseBody) {
    //    try {
    //      return new ResponseEntity<>(this.service.update(uuid, responseBody), HttpStatus.OK);
    //    } catch (Exception exception) {
    //      throw new BadRequestException(exception.getMessage());
    //    }
    //  }

    //
    @DeleteMapping(path = "/{id}")  //delete
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping  //update
    public ResponseEntity<Void> replace(@RequestBody @Valid Sala sala) throws Exception {
        service.update(sala);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}