package dz.sandbox.users.management.controller;


import dz.sandbox.users.management.dto.UserDto;
import dz.sandbox.users.management.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired private UsersService service;


    @PostMapping
    public ResponseEntity<UserDto> create(@RequestBody UserDto userDto) {

        UserDto created = service.create(userDto);
        return ResponseEntity.ok(created);
    }
}
