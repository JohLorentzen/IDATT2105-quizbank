package quizbank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import quizbank.model.User;
import quizbank.repository.UserRepository;
import quizbank.dto.UserDTO;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Service class for managing users.
 */
@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Loads a user by their username.
     *
     * @param username the username of the user
     * @return the user details, or null if not found
     * @throws UsernameNotFoundException if the user is not found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> wrappedUser = userRepository.findByUsername(username);
        if (wrappedUser.isEmpty()) {
            return null;
        }
        User user = wrappedUser.get();
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>());
    }

    /**
     * Registers a new user.
     *
     * @param user the user to be registered
     * @return the registered user
     */
    public User registerNewUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    /**
     * Converts a UserDTO to a User entity.
     *
     * @param userDTO the UserDTO
     * @return the converted User entity
     */
    public User toEntity(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        return user;
    }

    /**
     * Finds a user by their username.
     *
     * @param currentUsername the username of the user
     * @return the user, or null if not found
     */
    public UserDTO findUserByUsername(String currentUsername) {
        Optional<User> wrappedUser = userRepository.findByUsername(currentUsername);
        if (wrappedUser.isEmpty()) {
            return null;
        }
        User user = wrappedUser.get();
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(user.getUsername());
        userDTO.setId(user.getId());
        return userDTO;
    }

    /**
     * Finds a user by their ID.
     *
     * @param createdByUserId the ID of the user
     * @return the user, or null if not found
     */
    public Optional<User> findById(Long createdByUserId) {
        return userRepository.findById(createdByUserId);
    }

    /**
     * Updates a user.
     *
     * @param newUserInfo the new user information
     * @param oldUsername the old username of the user
     * @return the status of the update operation
     * @throws UsernameNotFoundException if the user is not found
     */
    public HttpStatus updateUser(UserDTO newUserInfo, String oldUsername) {
        Optional<User> userSearch = userRepository.findByUsername(newUserInfo.getUsername());
        Optional<User> currentUserWrapped = userRepository.findByUsername(oldUsername);
        if (currentUserWrapped.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        User currentUser = currentUserWrapped.get();
        if (!newUserInfo.getPassword().isEmpty()) {
            currentUser.setPassword(passwordEncoder.encode(newUserInfo.getPassword()));
        }

        if (userSearch.isEmpty()) { // username available
            currentUser.setUsername(newUserInfo.getUsername());
        } else {
            return HttpStatus.CONFLICT;
        }

        userRepository.save(currentUser);
        return HttpStatus.CREATED;
    }
}