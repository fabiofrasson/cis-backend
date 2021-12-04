package com.cis.config;

import com.cis.model.Address;
import com.cis.service.AddressService;
import com.cis.service.CepService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Profile("dev")
@Configuration
public class Instantiation implements CommandLineRunner {

  private final AddressService service;

  public Instantiation(AddressService service) {
    this.service = service;
  }

  @Override
  public void run(String... args) throws Exception {

    service.deleteAll();

    Address address = CepService.convertCepToAddress("85857600");
    Address address1 = CepService.convertCepToAddress("85851000");
    Address address2 = CepService.convertCepToAddress("85864055");
    List<Address> addresses = new ArrayList<>(Arrays.asList(address, address1, address2));

    service.saveAll(addresses);
  }
}
