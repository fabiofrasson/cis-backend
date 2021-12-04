package com.cis.service;

import com.cis.exceptions.BadRequestException;
import com.cis.model.Room;
import com.cis.model.dto.RoomDTO.NewRoomRequestDTO;
import com.cis.model.dto.RoomDTO.RoomResponseDTO;
import com.cis.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RoomService {

    @Autowired
    private RoomRepository repository;

    public List<Room> listAll() {
        return repository.findAll();
    }

    public Optional<Room> findByUUID(UUID uuid) {return repository.findById(uuid);
    }

    public Optional<Room> findByRoomNumber(String roomNumber) {
        return repository.findByRoomNumber(roomNumber);
    }

    public Room create(Room room) {
        Optional<Room> roomProcurada = repository.findByRoomNumber(room.getRoomNumber());

        if (roomProcurada.isPresent()) {
            throw new BadRequestException("A sala já existe.");
        } else {
            return repository.save(room);
        }
    }

    public void delete(UUID uuid) {
        repository.deleteById(uuid);
    }

    public RoomResponseDTO update(UUID id, Room room) {
        Optional<Room> salaParaMudar = repository.findById(id);

        if (salaParaMudar.isEmpty()) {
            throw new BadRequestException("Sala não encontrada.");
        } else {
            return new RoomResponseDTO(repository.save(room));
        }
    }

}
