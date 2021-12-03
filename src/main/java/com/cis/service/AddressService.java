package com.cis.service;

import com.cis.exceptions.InconsistentDataException;
import com.cis.exceptions.ResourceNotFoundException;
import com.cis.model.Address;
import com.cis.repository.AddressRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class AddressService {
  private AddressRepository repository;

  public AddressService(AddressRepository repository) {
    this.repository = repository;
  }

  public List<Address> listAll() {
    return repository.findAll();
  }

  public Address findByIdOrThrowResourceNotFoundException(UUID id) {
    return repository
        .findById(id)
        .orElseThrow(
            () ->
                new ResourceNotFoundException(
                    "Endereço não encontrado. Por favor, tente novamente."));
  }

  public Address findByCep(String cep) {
    return repository.findByCep(CepService.formatCep(cep));
  }

  public Address findByStreet(String street) {
    return repository.findByStreetIgnoreCase(street);
  }

  public Address findByStreetContaining(String streetPattern) {
    return repository.findByStreetContaining(streetPattern);
  }

  // Adicionar user na userList do cep que já está salvo

  @Transactional
  public Address save(Address address) throws Exception {
    Address findAddress = repository.findByCep(address.getCep());
    Address addressToBeSaved =
        CepService.convertCepToAddress(CepService.formatCep(address.getCep()));
    if (findAddress != null) {
      // setar ID
      findAddress.setStreet(addressToBeSaved.getStreet());
      findAddress.setCity(addressToBeSaved.getCity());
      findAddress.setUf(addressToBeSaved.getUf());
      findAddress.setNeighborhood(addressToBeSaved.getNeighborhood());
      repository.save(findAddress);
    } else {
      if (!(Objects.equals(address.getCep(), addressToBeSaved.getCep())
          & Objects.equals(address.getStreet(), addressToBeSaved.getStreet())
          & Objects.equals(address.getCity(), addressToBeSaved.getCity())
          & Objects.equals(address.getUf(), addressToBeSaved.getUf())
          & Objects.equals(address.getNeighborhood(), addressToBeSaved.getNeighborhood()))) {
        throw new InconsistentDataException(
            "Os dados de endereço não batem. Por favor, tente novamente.");
      } else {
        return repository.save(address);
      }
    }
    return repository.save(address);
  }

  // ** cep que só tenha cidade - abrir os campos para a pessoa preencher e salvar após o
  // preenchimento **

  // autocomplete nos campos que o user for digitar, buscando dos registros já existentes no BD

  //  @Transactional
  //  public Address save(Address address) throws Exception {
  //    Address findAddress = repository.findByCep(address.getCep());
  //    if (findAddress != null) {
  //      throw new ResourceAlreadyExistsException("Endereço já existente.");
  //    } else {
  //      Address address1 = CepService.convertCepToAddress(CepService.formatCep(address.getCep()));
  //      if (!(Objects.equals(address.getCep(), address1.getCep())
  //          & Objects.equals(address.getStreet(), address1.getStreet())
  //          & Objects.equals(address.getCity(), address1.getCity())
  //          & Objects.equals(address.getUf(), address1.getUf())
  //          & Objects.equals(address.getNeighborhood(), address1.getNeighborhood()))) {
  //        throw new InconsistentDataException(
  //            "Os dados de endereço não batem. Por favor, tente novamente.");
  //      } else {
  //        return repository.save(address);
  //      }
  //    }
  //  }

  public void delete(UUID id) {
    repository.delete(findByIdOrThrowResourceNotFoundException(id));
  }

  public void update(Address address) throws Exception {
    Address findAddress = repository.findByCep(address.getCep());
    if (findAddress == null) {
      throw new ResourceNotFoundException("Cep não encontrado, por favor tente novamente.");
    } else {
      repository.save(address);
    }
  }
}
