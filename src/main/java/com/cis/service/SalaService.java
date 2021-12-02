package com.cis.service;

import com.cis.exceptions.BadRequestException;
import com.cis.model.Sala;
import com.cis.repository.SalaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SalaService {

    @Autowired
    private SalaRepository repository;

    public List<Sala> listAll() {
        return repository.findAll();
    }

    public Optional<Sala> findById(Long id) {
        return repository.findById(id);
    }

    public Optional<Sala> findBySalaNumber(Integer salaNumber) {
        return repository.findBySalaNumber(salaNumber);
    }

    public Sala save(Sala sala) {
        Optional<Sala> salaProcurada = repository.findBySalaNumber(room.getSalaNumber());

        if (salaProcurada.isPresent()) {
            throw new BadRequestException("A sala já existe.");
        } else {
            return repository.save(sala);
        }
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public void update(Sala sala) {
        Optional<Sala> salaParaMudar = repository.findBySalaNumber(sala.getsalaNumber());

        if (salaParaMudar.isEmpty()) {
            throw new BadRequestException("Sala não encontrada.");
        } else {
            repository.save(sala);
        }
    }
}
