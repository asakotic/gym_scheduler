package raf.microservice.components.userservice.service.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raf.microservice.components.userservice.dto.*;
import raf.microservice.components.userservice.exceptions.NotFoundException;
import raf.microservice.components.userservice.mapper.ClientMapper;
import raf.microservice.components.userservice.model.Client;
import raf.microservice.components.userservice.model.Token;
import raf.microservice.components.userservice.repository.ClientRepository;
import raf.microservice.components.userservice.repository.TokenRepository;
import raf.microservice.components.userservice.service.JwtService;
import raf.microservice.components.userservice.service.ClientService;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final CustomUserDetailsService customUserDetailsService;
    public ClientServiceImpl(ClientRepository clientRepository, ClientMapper clientMapper, JwtService jwtService, TokenRepository tokenRepository
    , AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, CustomUserDetailsService customUserDetailsService){
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
        this.jwtService = jwtService;
        this.tokenRepository = tokenRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    public AuthenticationResponseDto add(ClientCreateDto clientCreateDto) {
        Client user = clientMapper.clientCreateDtoToClient(clientCreateDto);
        clientRepository.save(user);

        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        AuthenticationResponseDto authenticationResponseDto = new AuthenticationResponseDto();
        authenticationResponseDto.setAccessToken(jwtToken);
        authenticationResponseDto.setRefreshToken(refreshToken);
        saveUserToken(user, refreshToken);

        return authenticationResponseDto;
    }

    @Override
    public AuthenticationResponseDto authenticate(AuthLoginDto authLoginDto) {
        customUserDetailsService.setUserType("CLIENT");
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authLoginDto.getUsername(),
                        authLoginDto.getPassword()
                )
        );

        Client user = clientRepository.findClientByUsername(authLoginDto.getUsername()).orElseThrow();

        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);

        AuthenticationResponseDto authenticationResponseDto = new AuthenticationResponseDto();
        authenticationResponseDto.setAccessToken(jwtToken);
        authenticationResponseDto.setRefreshToken(refreshToken);
        saveUserToken(user, refreshToken);

        return authenticationResponseDto;
    }

    private void revokeAllUserTokens(Client user) { // TODO: transfer to JWT class
        var validUserTokens = tokenRepository.findAllValidTokenByUsersDetails(user);
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    private void saveUserToken(Client user, String jwt){ // TODO: transfer to JWT class
        Token token = new Token();
        token.setToken(jwt);
        token.setUsersDetails(user);
        token.setExpired(false);
        token.setRevoked(false);
        tokenRepository.save(token);
    }

    @Override
    public Client findUsername(String username) {
        return clientRepository.findClientByUsername(username).orElse(null);
    }

    @Override
    public SessionTokenDto refreshToken(String authorization) { // TODO: transfer to JWT class
        String refreshToken;
        String username;
        if(authorization == null ||!authorization.startsWith("Bearer "))
            return null;

        refreshToken = authorization.substring(7);
        username = jwtService.extractUsername(refreshToken);

        if (username != null) {
            Client user = clientRepository.findClientByUsername(username)
                    .orElseThrow();

            boolean check = true;
            boolean exist = false;

            List<Token> allTokens = tokenRepository.findAllValidTokenByUsersDetails(user);
            Token update = null;
            for(Token t : allTokens){
                if (t.token.equals(refreshToken)) {
                    exist = true;
                    update = t;
                    if(t.expired || t.revoked)
                        check = false;

                    break;
                }
            }

            if (jwtService.isTokenValid(refreshToken, user) && check && exist) {
                System.out.println(allTokens);
                String accessToken = jwtService.generateToken(user);
                SessionTokenDto sessionTokenDto = new SessionTokenDto();
                sessionTokenDto.setAccessToken(accessToken);
                return sessionTokenDto;
            }

            if(update != null){  // token does not exist
                update.setRevoked(true);
                update.setExpired(true);
                tokenRepository.save(update);
            }
        }

        return null;
    }

    @Override
    public ClientDto getMe(String authorization) {
        String token = authorization.substring(7);
        Optional<Client> user = clientRepository.findClientByUsername(jwtService.extractUsername(token));
        if(user.isEmpty()) throw new NotFoundException("Client not found");
        return clientMapper.clientToClientDto(user.get());
    }

    @Override
    public ClientDto edit(String authorization, ClientEditDto clientEditDto) {
        String token = authorization.substring(7);
        Optional<Client> user = clientRepository.findClientByUsername(jwtService.extractUsername(token));
        if(user.isEmpty()) throw new NotFoundException("Client not found");

        Client userNew = user.get();
        userNew.setEmail(clientEditDto.getEmail());
        userNew.setName(clientEditDto.getName());
        userNew.setLastName(clientEditDto.getLastName());
        userNew.setDateBirth(clientEditDto.getDateBirth());
        clientRepository.save(userNew);
        return clientMapper.clientToClientDto(userNew);
    }
}