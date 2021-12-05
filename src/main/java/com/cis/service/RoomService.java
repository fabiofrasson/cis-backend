package com.cis.service;

import com.cis.exceptions.BadRequestException;
import com.cis.exceptions.ResourceNotFoundException;
import com.cis.model.Room;
import com.cis.repository.RoomRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RoomService {

  private RoomRepository repository;

  public RoomService(RoomRepository repository) {
    this.repository = repository;
  }

  public List<Room> listAll() {
    return repository.findAll();
  }

  public Room findByIdOrThrowError(UUID id) {
    Optional<Room> byId = repository.findById(id);

    if (byId.isEmpty()) {
      throw new ResourceNotFoundException("Sala não encontrada.");
    }
    return byId.get();
  }

  public Room findByNumberOrThrowError(String number) {
    Optional<Room> byId = repository.findByRoomNumber(number);

    if (byId.isEmpty()) {
      throw new ResourceNotFoundException("Sala não encontrada.");
    }
    return byId.get();
  }

  @Transactional
  public Room save(Room room) {
    Optional<Room> findRoom = repository.findByRoomNumber(room.getRoomNumber());

    if (findRoom.isPresent()) {
      throw new BadRequestException("Sala já cadastrada em nosso sistema.");
    }

    return repository.save(
        Room.builder().roomNumber(room.getRoomNumber()).specialties(room.getSpecialties()).build());
  }

  public String delete(UUID id) {
    repository.deleteById(id);
    return "Registro deletado com sucesso!";
  }

  public String update(UUID id, Room room) {

    Optional<Room> savedRoom = repository.findById(id);

    if (savedRoom.isEmpty()) {
      throw new ResourceNotFoundException("Sala não encontrada.");
    }

    savedRoom.get().setRoomNumber(room.getRoomNumber());
    savedRoom.get().setSpecialties(room.getSpecialties());
    repository.save(savedRoom.get());
    return "Registro atualizado com sucesso!";
  }
}
