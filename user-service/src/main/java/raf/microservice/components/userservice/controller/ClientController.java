package raf.microservice.components.userservice.controller;

import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import raf.microservice.components.userservice.dto.*;
import raf.microservice.components.userservice.security.CheckExists;
import raf.microservice.components.userservice.service.ClientService;

@RestController
@RequestMapping("/api/client")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @ApiOperation(value = "Register new user")
    @PostMapping("/register")
    @CheckExists  //  TODO: SHOULD SEND AN EMAIL AND RETURN JUST STATUS CREATED!
    public ResponseEntity<AuthenticationResponseDto> saveUser(@RequestBody @Valid ClientCreateDto clientCreateDto){
        return new ResponseEntity<>(clientService.add(clientCreateDto), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Login user")
    @PostMapping("/login")  //  TODO: SHOULD SEND AN EMAIL THAT SOMEONE JUST LOGGED IN ACCOUNT
    public ResponseEntity<AuthenticationResponseDto> authenticate(@RequestBody @Valid AuthLoginDto authLoginDto){
        return new ResponseEntity<>(clientService.authenticate(authLoginDto), HttpStatus.OK);
    }

    @ApiOperation(value = "Refresh token user")
    @PostMapping("/refresh-token")
    public ResponseEntity<SessionTokenDto> refreshToken(@RequestHeader("Authorization") String authorization){
        return new ResponseEntity<>(clientService.refreshToken(authorization), HttpStatus.OK);
    }

    @ApiOperation(value = "Get my information")
    @GetMapping("/me")
    public ResponseEntity<ClientDto> me(@RequestHeader("Authorization") String authorization){
        return new ResponseEntity<>(clientService.getMe(authorization), HttpStatus.OK);
    }

    @ApiOperation(value = "Edit user")
    @PutMapping("/edit")
    public ResponseEntity<ClientDto> edit(@RequestHeader("Authorization") String authorization, @RequestBody @Valid ClientEditDto clientEditDto){
        return new ResponseEntity<>(clientService.edit(authorization, clientEditDto), HttpStatus.OK);
    }
}