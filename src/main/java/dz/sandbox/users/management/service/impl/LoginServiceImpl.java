package dz.sandbox.users.management.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import dz.sandbox.users.management.configuration.UsersConfiguration;
import dz.sandbox.users.management.dto.AccesTokenDto;
import dz.sandbox.users.management.dto.LoginDto;
import dz.sandbox.users.management.exception.SandboxException;
import dz.sandbox.users.management.service.LoginService;
import jakarta.ws.rs.WebApplicationException;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LoginServiceImpl implements LoginService {

  private final UsersConfiguration configuration;
  private final ObjectMapper mapper = new ObjectMapper();
  private Keycloak keycloak;

  public LoginServiceImpl(UsersConfiguration configuration) {
    this.configuration = configuration;
  }

  @Override
  public AccesTokenDto login(LoginDto loginDto) {

    Keycloak keycloak =
        KeycloakBuilder.builder()
            .serverUrl(configuration.getServerUrl())
            .realm(configuration.getRealm())
            .clientId(configuration.getClientId())
            .clientSecret(configuration.getClientSecret()) // solo si es confidential
            .grantType(OAuth2Constants.PASSWORD)
            .username(loginDto.getUser())
            .password(loginDto.getPassword())
            .build();

    AccessTokenResponse token = null;

    try {
      token = keycloak.tokenManager().getAccessToken();
    } catch (WebApplicationException e) {
      int status = e.getResponse().getStatus();

      if (status == 400) {
        throw new SandboxException(e.getMessage(), status, "Invalid grant");
      } else if (status == 401) {
        throw new SandboxException(e.getMessage(), status, "Invalid user or password");
      }
    }

    return AccesTokenDto.builder().token(token.getToken()).build();
  }
}
