package com.cis.model.dto.RoomDTO;

import com.cis.model.Room;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomResponseDTO {
    private UUID id;
    private String roomNumber;
   // private String specialties;

   public RoomResponseDTO(Room room){
       this.id = room.getId();
       this.roomNumber = room.getRoomNumber();
   }

}
