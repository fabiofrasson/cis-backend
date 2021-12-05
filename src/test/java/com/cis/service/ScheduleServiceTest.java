package com.cis.service;

import com.cis.model.dto.ScheduleDTO.ScheduleResponseDTO;
import com.cis.repository.HealthProfessionalRepository;
import com.cis.repository.RoomRepository;
import com.cis.repository.ScheduleRepository;
import com.cis.utils.HealthProfessionalCreator;
import com.cis.utils.RoomCreator;
import com.cis.utils.ScheduleCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class ScheduleServiceTest {

    @InjectMocks
    private ScheduleService scheduleService;

    @Mock
    private ScheduleRepository scheduleRepository;

    @Mock
    private HealthProfessionalRepository healthProfessionalRepository;

    @Mock
    private RoomRepository roomRepository;

    @BeforeEach
    void setup(){
        BDDMockito.when(healthProfessionalRepository.findById(ArgumentMatchers.any()))
                .thenReturn(Optional.of(HealthProfessionalCreator.createHealthProfessionalSaved()));

        BDDMockito.when(roomRepository.findById(ArgumentMatchers.any()))
                .thenReturn(Optional.of(RoomCreator.createRoomSaved()));
    }


    @Test
    @DisplayName("Should create a schedule when successful")
    public void test_create(){
        BDDMockito.when(scheduleRepository.findByProfessional(ArgumentMatchers.any()))
                .thenReturn(List.of());

        BDDMockito.when(scheduleRepository.findByRoom(ArgumentMatchers.any()))
                .thenReturn(List.of());

        BDDMockito.when(scheduleRepository.save(ArgumentMatchers.any()))
                .thenReturn(ScheduleCreator.createValidScheduleSaved());

        ScheduleResponseDTO scheduleResponseDTO = scheduleService.create(ScheduleCreator.createValidScheduleRequestDTOtoBeSaved());

        Assertions.assertThat(scheduleResponseDTO).isNotNull();
    }


}