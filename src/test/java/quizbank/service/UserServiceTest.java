package quizbank.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import quizbank.dto.UserDTO;
import quizbank.model.User;
import quizbank.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void loadUserByUsernameReturnsUserDetails() {
        String username = "username";
        User user = new User();
        user.setUsername(username);
        user.setPassword("password");

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        UserDetails userDetails = userService.loadUserByUsername(username);

        assertEquals(user.getUsername(), userDetails.getUsername());
    }

    @Test
    public void loadUserByUsernameReturnsNullWhenUserNotFound() {
        String username = "username";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        UserDetails userDetails = userService.loadUserByUsername(username);

        assertNull(userDetails);
    }

    @Test
    public void registerNewUserSavesUser() {
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");

        when(passwordEncoder.encode(user.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(user)).thenReturn(user);

        User savedUser = userService.registerNewUser(user);

        assertEquals("encodedPassword", savedUser.getPassword());
    }

    @Test
    public void findUserByUsernameReturnsUserDTO() {
        String username = "username";
        User user = new User();
        user.setId(1L);
        user.setUsername(username);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        UserDTO userDTO = userService.findUserByUsername(username);

        assertEquals(user.getId(), userDTO.getId());
        assertEquals(user.getUsername(), userDTO.getUsername());
    }

    @Test
    public void findUserByUsernameReturnsNullWhenUserNotFound() {
        String username = "username";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        UserDTO userDTO = userService.findUserByUsername(username);

        assertNull(userDTO);
    }

    @Test
    public void updateUserUpdatesUsernameAndPassword() {
        UserDTO newUserInfo = new UserDTO();
        newUserInfo.setUsername("newUsername");
        newUserInfo.setPassword("newPassword");

        User currentUser = new User();
        currentUser.setUsername("oldUsername");
        currentUser.setPassword("oldPassword");

        when(userRepository.findByUsername(newUserInfo.getUsername())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(newUserInfo.getPassword())).thenReturn("encodedNewPassword");
        when(userRepository.findByUsername(currentUser.getUsername())).thenReturn(Optional.of(currentUser));
        when(userRepository.save(currentUser)).thenReturn(currentUser);

        userService.updateUser(newUserInfo, currentUser.getUsername());

        assertEquals(newUserInfo.getUsername(), currentUser.getUsername());
        assertEquals("encodedNewPassword", currentUser.getPassword());
    }
}