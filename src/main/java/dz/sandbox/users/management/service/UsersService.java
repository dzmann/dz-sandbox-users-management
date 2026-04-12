package dz.sandbox.users.management.service;

import dz.sandbox.users.management.dto.ApiResponseDto;
import dz.sandbox.users.management.dto.UserDto;

public interface UsersService {

  UserDto getUserById(String id);

  UserDto create(UserDto userDto);

  ApiResponseDto update(String id, UserDto userDto);

  void delete(String id);
}
