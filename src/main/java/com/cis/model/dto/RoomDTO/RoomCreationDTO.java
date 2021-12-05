package com.cis.model.dto.RoomDTO;

import com.cis.model.Room;
import com.cis.model.dto.SpecialtyDTO.SpecialtyCreationDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomCreationDTO {

  private String roomNumber;
  private List<SpecialtyCreationDTO> specialties;

  public RoomCreationDTO(Room room, List<SpecialtyCreationDTO> specialties) {
    this.roomNumber = room.getRoomNumber();
    this.specialties = specialties;
  }
}
