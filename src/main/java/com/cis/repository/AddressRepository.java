package com.cis.repository;

import com.cis.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

  Address findByCep(String cep);

  Address findByStreetIgnoreCase(String street);

  Address findByStreetContaining(String streetPattern);
}
