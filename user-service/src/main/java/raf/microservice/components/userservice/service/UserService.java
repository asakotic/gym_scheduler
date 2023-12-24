package raf.microservice.components.userservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import raf.microservice.components.userservice.dto.UserCreateDto;
import raf.microservice.components.userservice.dto.UserDto;
import raf.microservice.components.userservice.model.User;

public interface UserService {
    UserDto add(UserCreateDto userCreateDto);
    User findUsername(String username);
}
