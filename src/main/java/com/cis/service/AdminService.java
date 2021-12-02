package com.cis.service;

import com.cis.exceptions.BadRequestException;
import com.cis.model.Admin;
import com.cis.model.dto.AdminCreationDTO;
import com.cis.model.dto.AdminReturnDTO;
import com.cis.model.dto.AdminUpdateDTO;
import com.cis.repository.AdminRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
public class AdminService {

    private AdminRepository repository;

    public AdminService(AdminRepository repository) {
        this.repository = repository;
    }

    // FIND BY ID
    public Admin findByIdOrThrowError(UUID id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new BadRequestException("Admin não encontrado."));
    }

    // FIND BY EMAIL
    public Optional<Admin> findByEmailOrThrowError(String email) {

        Optional<Admin> foundAdmin = repository.findByEmail(email);

        if (foundAdmin.isPresent()) {
            return foundAdmin;
        } else {
            throw new BadRequestException("Admin não encontrado.");
        }
    }

    // CREATE
    @Transactional
    public AdminReturnDTO save(AdminCreationDTO adminCreationDTO) {
        Optional<Admin> findAdmin = repository.findByEmail(adminCreationDTO.getEmail());
        if (findAdmin.isPresent()) {
            throw new BadRequestException("Email já cadastrado");
        } else {
            Admin adminToBeSaved =
                    repository.save(
                            Admin.builder()
                                    .name(adminCreationDTO.getName())
                                    .phone(adminCreationDTO.getPhone())
                                    .email(adminCreationDTO.getEmail())
                                    .password(adminCreationDTO.getPassword())
                                    .build());
            return new AdminReturnDTO(adminToBeSaved);
        }
    }

    // LIST
    public Page<Admin> listAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    // UPDATE
    public String update(UUID id, AdminUpdateDTO admin) {
        Admin savedAdmin = this.findByIdOrThrowError(id);

        Admin updatedAdmin =
                Admin.builder()
                        .id(savedAdmin.getId())
                        .name(savedAdmin.getName())
                        .email(savedAdmin.getEmail())
                        .build();
        repository.save(updatedAdmin);

        return "Informações alteradas com sucesso!";
    }

    //DELETE
    public String delete(UUID id) {
        repository.delete(findByIdOrThrowError(id));
        return "Admin deletado com sucesso!";
    }
}
