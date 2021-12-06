// package com.cis.config;
//
// import com.cis.model.Address;
// import com.cis.model.Room;
// import com.cis.model.Specialty;
// import com.cis.service.AddressService;
// import com.cis.service.CepService;
// import com.cis.service.RoomService;
// import com.cis.service.SpecialtyService;
// import org.springframework.boot.CommandLineRunner;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.context.annotation.Profile;
//
// import java.util.ArrayList;
// import java.util.Arrays;
// import java.util.List;
//
// @Profile("dev")
// @Configuration
// public class Instantiation implements CommandLineRunner {
//
//  private final AddressService addressService;
//  private final RoomService roomService;
//  private final SpecialtyService specialtyService;
//
//  public Instantiation(
//      AddressService service, RoomService roomService, SpecialtyService specialtyService) {
//    this.addressService = service;
//    this.roomService = roomService;
//    this.specialtyService = specialtyService;
//  }
//
//  @Override
//  public void run(String... args) throws Exception {
//
//    // ## ADDRESS ##
//    addressService.deleteAll();
//
//    Address address = CepService.convertCepToAddress("85857600");
//    Address address1 = CepService.convertCepToAddress("85851000");
//    Address address2 = CepService.convertCepToAddress("85864055");
//    List<Address> addresses = new ArrayList<>(Arrays.asList(address, address1, address2));
//
//    // ## SPECIALTY
//    specialtyService.deleteAll();
//
//    Specialty specialty = Specialty.builder().name("Podologia").build();
//    Specialty specialty1 = Specialty.builder().name("Clínica Geral").build();
//    Specialty specialty2 = Specialty.builder().name("Psicologia").build();
//    List<Specialty> specialties = new ArrayList<>(Arrays.asList(specialty, specialty1,
// specialty2));
//
//    // ## ROOM
//    roomService.deleteAll();
//
//    Room room = Room.builder().roomNumber("1").specialties(specialties).build();
//    Room room1 = Room.builder().roomNumber("2").specialties(specialties).build();
//    Room room2 = Room.builder().roomNumber("3").specialties(specialties).build();
//    List<Room> rooms = new ArrayList<>(Arrays.asList(room, room1, room2));
//
//    addressService.saveAll(addresses);
//    specialtyService.saveAll(specialties);
//    roomService.saveAll(rooms);
//  }
// }
