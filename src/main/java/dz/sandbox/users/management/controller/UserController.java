package dz.sandbox.users.management.controller;

import dz.sandbox.users.management.dto.ApiResponseDto;
import dz.sandbox.users.management.dto.UserDto;
import dz.sandbox.users.management.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

  @Autowired private UsersService service;

  @PostMapping
  public ResponseEntity<ApiResponseDto> create(@RequestBody UserDto userDto) {
    final UserDto created = service.create(userDto);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(ApiResponseDto.builder().message("User created").build());
  }
}
