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

  @GetMapping("/{id}")
  public ResponseEntity<UserDto> getUserById(@PathVariable("id") String id) {
    return ResponseEntity.status(HttpStatus.OK).body(service.getUserById(id));
  }

  @PostMapping
  public ResponseEntity<ApiResponseDto> create(@RequestBody UserDto userDto) {
    final UserDto created = service.create(userDto);
    final ApiResponseDto responseDto =
        ApiResponseDto.builder()
            .message("An activation email has been sent to your email address")
            .details(created)
            .build();
    return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
  }

  @PutMapping("/{id}")
  public ResponseEntity<ApiResponseDto> update(
      @PathVariable("id") String id, @RequestBody UserDto userDto) {
    return ResponseEntity.status(HttpStatus.OK).body(service.update(id, userDto));
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable("id") String id) {
    service.delete(id);
  }
}
