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


@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> wrappedUser = userRepository.findByUsername(username);
        if (wrappedUser.isEmpty()) {
            return null;
        }
        User user = wrappedUser.get();
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>());
    }

    public User registerNewUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User toEntity(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        return user;
    }

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

    public Optional<User> findById(Long createdByUserId) {
        return userRepository.findById(createdByUserId);
    }

    public HttpStatus updateUser(UserDTO newUserInfo, String oldUsername) {
        Optional<User> userSearch = userRepository.findByUsername(newUserInfo.getUsername());
        Optional<User> currentUserWrapped = userRepository.findByUsername(oldUsername);
        User currentUser = currentUserWrapped.get();

        if (!newUserInfo.getPassword().isEmpty()) {
            currentUser.setPassword(newUserInfo.getPassword());
        }

        if (userSearch.isEmpty()) { // username available
            currentUser.setUsername(newUserInfo.getUsername());
        } else {
            return HttpStatus.CONFLICT;
        }

        userRepository.save(currentUser);
        return HttpStatus.NO_CONTENT;
    }
}
