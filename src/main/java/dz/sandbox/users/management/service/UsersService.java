package dz.sandbox.users.management.service;

import dz.sandbox.users.management.dto.UserDto;

public interface UsersService {

  UserDto getUserById(String id);

  UserDto create(UserDto userDto);
}
