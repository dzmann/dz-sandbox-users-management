package dz.sandbox.users.management.controller;

import dz.sandbox.users.management.dto.AccesTokenDto;
import dz.sandbox.users.management.dto.LoginDto;
import dz.sandbox.users.management.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

  @Autowired LoginService service;

  @PostMapping
  public ResponseEntity<AccesTokenDto> login(@RequestBody LoginDto loginDto) {
    return ResponseEntity.status(HttpStatus.CREATED).body(service.login(loginDto));
  }
}
