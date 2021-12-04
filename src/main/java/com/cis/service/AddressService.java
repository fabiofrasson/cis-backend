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

  public List<Address> findByStreet(String street) {
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

    // Caso o endereço já exista
    if (findAddress != null) {
      // validar se rua e bairro são iguais ao registro já existente no BD, para ceps como MCR
      if (!Objects.equals(findAddress.getStreet(), address.getStreet())
          && !Objects.equals(findAddress.getNeighborhood(), address.getNeighborhood())) {

        return repository.save(
            Address.builder()
                .id(UUID.randomUUID())
                .cep(address.getCep())
                .street(address.getStreet())
                .city(address.getCity())
                .uf(address.getUf())
                .neighborhood(address.getNeighborhood())
                .build());
      }
      // repensar lógica daqui pra baixo
      else {
        if (Objects.equals(findAddress, addressToBeSaved)) {
          return findAddress;
        } else {
          if (!Objects.equals(findAddress.getStreet(), "")) {
            String street = findAddress.getStreet();
            findAddress.setStreet(street);
          } else {
            findAddress.setStreet(addressToBeSaved.getStreet());
          }
          findAddress.setCity(addressToBeSaved.getCity());
          findAddress.setUf(addressToBeSaved.getUf());
          if (!Objects.equals(findAddress.getNeighborhood(), "")) {
            String neighborhood = findAddress.getNeighborhood();
            findAddress.setNeighborhood(neighborhood);
          } else {
            findAddress.setNeighborhood(addressToBeSaved.getNeighborhood());
          }
          // fim repensar lógica
          return repository.save(findAddress);
        }
      }
    }
    // Caso o endereço ainda não exista
    else {
      // Casos em que o cep é geral para a cidade, ex Marechal Cândido Rondon (85960-000)
      Address builtAddress =
          Address.builder()
              .cep(address.getCep())
              .street(address.getStreet())
              .city(address.getCity())
              .uf(address.getUf())
              .neighborhood(address.getNeighborhood())
              .build();

      // Verificar se o usuário inseriu logradouro e bairro
      if (!Objects.equals(builtAddress.getNeighborhood(), "")
          && !Objects.equals(builtAddress.getStreet(), "")) {
        return repository.save(builtAddress);
      } else {
        throw new InconsistentDataException("Por favor preencha os campos de logradouro e bairro.");
      }
    }
  }

  public void saveAll(List<Address> addresses) {
    repository.saveAll(addresses);
  }

  public void delete(UUID id) {
    repository.delete(findByIdOrThrowResourceNotFoundException(id));
  }

  public void deleteAll() {
    repository.deleteAll();
  }

  public void update(Address address) throws Exception {

    Address findAddress = repository.findByCep(address.getCep());

    if (findAddress == null) {
      throw new ResourceNotFoundException("Cep não encontrado, por favor tente novamente.");
    } else {
      Address addressToBeSaved =
          CepService.convertCepToAddress(CepService.formatCep(address.getCep()));

      if (!(Objects.equals(address.getCep(), addressToBeSaved.getCep())
          & Objects.equals(address.getStreet(), addressToBeSaved.getStreet())
          & Objects.equals(address.getCity(), addressToBeSaved.getCity())
          & Objects.equals(address.getUf(), addressToBeSaved.getUf())
          & Objects.equals(address.getNeighborhood(), addressToBeSaved.getNeighborhood()))) {
        throw new InconsistentDataException(
            "Os dados de endereço não batem. Por favor, tente novamente.");
      } else {
        repository.save(address);
      }
    }
  }
}
