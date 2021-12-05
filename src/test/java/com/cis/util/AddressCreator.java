package com.cis.util;

import com.cis.model.Address;
import com.cis.service.CepService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class AddressCreator {

  public static Address createAddress(int option) throws Exception {
    switch (option) {
      case 1:
        Address address = CepService.convertCepToAddress("85857600");
        address.setId(UUID.randomUUID());
        return address;

      case 2:
        Address address1 = CepService.convertCepToAddress("85851-010");
        address1.setId(UUID.randomUUID());
        return address1;

      case 3:
        Address address2 = CepService.convertCepToAddress("85960000");
        address2.setId(UUID.randomUUID());
        return address2;

      default:
        Address address3 = CepService.convertCepToAddress("85851000");
        address3.setId(UUID.randomUUID());
        return address3;
    }
  }

  public static Address createAddressNoId(int option) throws Exception {
    switch (option) {
      case 1:
        return CepService.convertCepToAddress("85857600");

      case 2:
        return CepService.convertCepToAddress("85851-010");

      case 3:
        return CepService.convertCepToAddress("85960000");

      default:
        return CepService.convertCepToAddress("85851000");
    }
  }

  public static Address createUpdatedAddress() throws Exception {
    Address address = createAddressNoId(1);
    address.setCity("Toledo");
    address.setNeighborhood("Conjunto Libra");
    address.setStreet("Rua Ipanema");

    return address;
  }

  public static List<Address> createAddressList() throws Exception {
    String[] ceps = {"85851010", "85851-210", "85851000", "85852-000", "85851110"};

    List<Address> addressesToBeSaved = new ArrayList<>();

    for (String cep : ceps) {
      addressesToBeSaved.add(CepService.convertCepToAddress(cep));
    }

    return addressesToBeSaved;
  }
}
