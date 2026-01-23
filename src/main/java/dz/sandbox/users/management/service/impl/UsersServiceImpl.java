package dz.sandbox.users.management.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dz.sandbox.users.management.configuration.UsersConfiguration;
import dz.sandbox.users.management.dto.KeycloakErrorResponseDto;
import dz.sandbox.users.management.dto.UserDto;
import dz.sandbox.users.management.exception.SandboxException;
import dz.sandbox.users.management.service.UsersService;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;

@Slf4j
@Service
public class UsersServiceImpl implements UsersService {


    private final UsersConfiguration configuration;
    private final ObjectMapper mapper = new ObjectMapper();
    private Keycloak keycloak;

    public UsersServiceImpl(UsersConfiguration configuration) {
        this.configuration = configuration;
        this.keycloak = KeycloakBuilder.builder()
                .serverUrl(configuration.getServerUrl())
                .realm(configuration.getRealm())
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .clientId(configuration.getClientId())
                .clientSecret(configuration.getClientSecret())
                .build();
    }

    @Override
    public UserDto create(UserDto userDto) {

        UserRepresentation user = new UserRepresentation();
        user.setEnabled(true);
        user.setUsername(userDto.getUserName());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(user.getLastName());
        user.setEmail(userDto.getEmail());
        user.setAttributes(Collections.singletonMap("origin", Arrays.asList("test")));

        // Get realm
        RealmResource realmResource = keycloak.realm(configuration.getRealm());
        UsersResource usersRessource = realmResource.users();

        // Create user (requires manage-users role)
        Response response = usersRessource.create(user);

        if (response.getStatus() != 201) {
            KeycloakErrorResponseDto keycloakErrorResponseDto = buildKeycloakError(response);
            log.error("Error while creating user [{}] - message keycloak is: [{}]", userDto.getUserName(), keycloakErrorResponseDto.getErrorMessage());
            throw new SandboxException("Error while creating user", response.getStatus(), keycloakErrorResponseDto.getErrorMessage());
        }

        final String userId = CreatedResponseUtil.getCreatedId(response);

        log.info("Response: {} - {}", response.getStatus(), response.getStatusInfo());
        log.info("Location: {}", response.getLocation());
        log.info("User created with id: {}", userId);

        // Define password credential
        CredentialRepresentation passwordCred = new CredentialRepresentation();
        passwordCred.setTemporary(false);
        passwordCred.setType(CredentialRepresentation.PASSWORD);
        passwordCred.setValue(userDto.getPassword());

        UserResource userResource = usersRessource.get(userId);
        // Set password credential
        userResource.resetPassword(passwordCred);

        return userDto;
    }


    private KeycloakErrorResponseDto buildKeycloakError(Response response) {
        String body = response.readEntity(String.class);
        KeycloakErrorResponseDto errorResponseDto;
        try {
            errorResponseDto =
                    mapper.readValue(body, KeycloakErrorResponseDto.class);

        } catch (JsonProcessingException e) {
            log.error("Error while trying to parse error from Keycloak: {}", e.getMessage());
            throw new RuntimeException("Error while trying to parse error from Keycloack");
        }
        return errorResponseDto;
    }
}
