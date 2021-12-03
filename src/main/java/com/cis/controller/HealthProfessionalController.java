package com.cis.controller;

import com.cis.config.jwtConfig.JwtUtil;
import com.cis.exceptions.BadRequestException;
import com.cis.model.dto.HeathProfessionalDTO.HealthProfessionalCreationDTO;
import com.cis.model.dto.HeathProfessionalDTO.HealthProfessionalResponseDTO;
import com.cis.model.dto.UserDTO.AuthenticationRequest;
import com.cis.model.dto.UserDTO.AuthenticationResponse;
import com.cis.service.HealthProfessionalService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(path = "/api/health-professionals")
public class HealthProfessionalController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtTokenUtil;
    private final HealthProfessionalService healthProfessionalService;

    public HealthProfessionalController(AuthenticationManager authenticationManager,
                                        JwtUtil jwtTokenUtil,
                                        HealthProfessionalService healthProfessionalService){
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.healthProfessionalService = healthProfessionalService;
    }

    @PostMapping
    public ResponseEntity<HealthProfessionalResponseDTO> create(@RequestBody HealthProfessionalCreationDTO entity){
        try{
            HealthProfessionalResponseDTO healthProfessionalResponseDTO = healthProfessionalService.create(entity);
            return new ResponseEntity<>(healthProfessionalResponseDTO, HttpStatus.CREATED);
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }

    @PostMapping(path = "/login")
    public ResponseEntity<AuthenticationResponse> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)
            throws Exception {
        try {

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),
                    authenticationRequest.getPassword()));

            final UserDetails user = healthProfessionalService.loadUserByUsername(authenticationRequest.getEmail());

            Optional<? extends GrantedAuthority> optionalGrantedAuthority = user.getAuthorities().stream().findFirst();

            if(optionalGrantedAuthority.isEmpty()){
                    throw new Exception("Incorrect username or password");
            }

            if(!optionalGrantedAuthority.get().getAuthority().equals("ROLE_PROFESSIONAL")){
                throw new Exception("Incorrect username or password");
            }

            final String jwt = jwtTokenUtil.generateToken(user);

            return ResponseEntity.ok(new AuthenticationResponse(jwt));

        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }
    }



}
