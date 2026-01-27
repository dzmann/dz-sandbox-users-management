package dz.sandbox.users.management.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
  private String userName;
  private String password;
  private String firstName;
  private String lastName;
  private String email;
}
