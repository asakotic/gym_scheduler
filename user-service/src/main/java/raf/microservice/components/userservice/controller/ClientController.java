package raf.microservice.components.userservice.controller;

import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import raf.microservice.components.userservice.dto.*;
import raf.microservice.components.userservice.security.CheckExists;
import raf.microservice.components.userservice.service.ClientService;

@RestController
@RequestMapping("/api/client")
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @ApiOperation(value = "Register new user")
    @PostMapping("/register")
    @CheckExists  //  TODO: SHOULD SEND AN EMAIL AND RETURN JUST STATUS CREATED!
    public ResponseEntity<Void> saveUser(@RequestBody @Valid ClientCreateDto clientCreateDto){
        clientService.add(clientCreateDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @ApiOperation(value = "Verify user")
    @GetMapping("/verify/{id}")
    public ResponseEntity<Void> findById(@PathVariable("id") String id) {
        clientService.verify(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "Login user")
    @PostMapping("/login")  //  TODO: SHOULD SEND AN EMAIL THAT SOMEONE JUST LOGGED IN ACCOUNT
    public ResponseEntity<AuthenticationResponseDto> authenticate(@RequestBody @Valid AuthLoginDto authLoginDto){
        return new ResponseEntity<>(clientService.authenticate(authLoginDto), HttpStatus.OK);
    }

    @ApiOperation(value = "Change password")
    @PostMapping("/change-password")
    public ResponseEntity<Void> changePassword(@RequestHeader("Authorization") String authorization,
                                               @RequestBody @Valid ChangePasswordDto changePasswordDto){
        clientService.changePassword(changePasswordDto, authorization);
        return new ResponseEntity<>(HttpStatus.OK);
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

    @ApiOperation(value = "Get user id information")
    @GetMapping("/{id}")
    public ResponseEntity<ClientDto> getClient(@PathVariable("id") Long id){
        return new ResponseEntity<>(clientService.getUserById(id), HttpStatus.OK);
    }

    @ApiOperation(value = "Edit user")
    @PutMapping("/edit")
    public ResponseEntity<ClientDto> edit(@RequestHeader("Authorization") String authorization, @RequestBody @Valid ClientEditDto clientEditDto){
        return new ResponseEntity<>(clientService.edit(authorization, clientEditDto), HttpStatus.OK);
    }

    @ApiOperation(value = "Edit user")
    @PutMapping("/edit/training-count")
    public ResponseEntity<ClientDto> edit(@RequestBody @Valid ClientDto clientDto){
        return new ResponseEntity<>(clientService.editTrainingCount(clientDto), HttpStatus.OK);
    }

    @ApiOperation(value = "Get all users")
    @GetMapping("/all")
    public ResponseEntity<Page<ClientAllDto>> allUsers(@RequestHeader("Authorization") String authorization, Pageable pageable){
        return new ResponseEntity<>(clientService.getAllUsers(pageable), HttpStatus.OK);
    }

}
