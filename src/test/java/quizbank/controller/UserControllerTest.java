package quizbank.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import quizbank.dto.UserDTO;
import quizbank.service.UserService;
import quizbank.util.JwtUtil;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserService userService;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private Authentication authentication;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void loginUserSuccessfully() {
        UserDTO user = new UserDTO();
        user.setUsername("test");
        user.setPassword("password");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(userService.findUserByUsername(user.getUsername())).thenReturn(user);
        when(jwtUtil.generateToken(user.getUsername())).thenReturn("token");

        ResponseEntity<?> response = userController.loginUser(user);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("token", response.getBody());
    }

    @Test
    public void loginUserWithInvalidCredentials() {
        UserDTO user = new UserDTO();
        user.setUsername("test");
        user.setPassword("password");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new AuthenticationException("Failed to authenticate") {});

        ResponseEntity<?> response = userController.loginUser(user);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    public void registerUserSuccessfully() {
        UserDTO user = new UserDTO();
        user.setUsername("test");
        user.setPassword("password");

        when(userService.findUserByUsername(user.getUsername())).thenReturn(null);
        when(jwtUtil.generateToken(user.getUsername())).thenReturn("token");

        ResponseEntity<?> response = userController.registerUser(user);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("token", response.getBody());
    }

    @Test
    public void registerUserWithExistingUsername() {
        UserDTO user = new UserDTO();
        user.setUsername("test");
        user.setPassword("password");

        when(userService.findUserByUsername(user.getUsername())).thenReturn(user);

        ResponseEntity<?> response = userController.registerUser(user);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    public void getUserProfileSuccessfully() {
        UserDTO user = new UserDTO();
        user.setUsername("test");
        user.setPassword("password");

        when(authentication.getName()).thenReturn(user.getUsername());
        when(userService.findUserByUsername(user.getUsername())).thenReturn(user);

        ResponseEntity<UserDTO> response = userController.getUserProfile(authentication);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    public void getUserProfileWithInvalidAuthentication() {
        ResponseEntity<UserDTO> response = userController.getUserProfile(null);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    public void updateUserProfileSuccessfully() {
        UserDTO user = new UserDTO();
        user.setUsername("test");
        user.setPassword("password");

        when(authentication.getName()).thenReturn(user.getUsername());
        when(userService.updateUser(user, user.getUsername())).thenReturn(HttpStatus.CREATED);
        when(jwtUtil.generateToken(user.getUsername())).thenReturn("token");

        ResponseEntity<?> response = userController.updateUserProfile(authentication, user);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("token", response.getBody());
    }

    @Test
    public void updateUserProfileWithInvalidAuthentication() {
        UserDTO user = new UserDTO();
        user.setUsername("test");
        user.setPassword("password");

        ResponseEntity<?> response = userController.updateUserProfile(null, user);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }
}