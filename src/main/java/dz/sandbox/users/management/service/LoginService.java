package dz.sandbox.users.management.service;

import dz.sandbox.users.management.dto.AccesTokenDto;
import dz.sandbox.users.management.dto.LoginDto;

public interface LoginService {

  public AccesTokenDto login(LoginDto loginDto);
}
