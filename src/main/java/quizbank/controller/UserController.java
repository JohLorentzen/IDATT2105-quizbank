package quizbank.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import quizbank.dto.UserDTO;
import quizbank.model.User;
import quizbank.service.UserService;
import org.springframework.security.core.Authentication;
import quizbank.util.JwtUtil;

@Tag(
    name = "User Controller",
    description = "Register, login and manage user profiles."
)
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Operation(
        summary = "Authenticates and authorizes user that tries to log in",
        description = "Tries to authenticate user and crates a token that gets returned if log in is successful"
    )
    @ApiResponses( value = {
        @ApiResponse(responseCode = "200", description = "Successfully logged in using provided payload"),
        @ApiResponse(responseCode = "401", description = "Failed to authorize user from payload given")
    })
    @PostMapping(path = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> loginUser(@RequestBody UserDTO user) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            final UserDTO userDetails = userService.findUserByUsername(user.getUsername());
            if (userDetails == null) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            final String jwt = jwtUtil.generateToken(userDetails.getUsername());
            return ResponseEntity.ok(jwt);
        } catch (AuthenticationException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @Operation(
        summary = "Registers a new user",
        description = "Tries to register a new user by using provided payload"
    )
    @ApiResponses( value = {
        @ApiResponse(responseCode = "201", description = "Successfully created new user"),
        @ApiResponse(responseCode = "409", description = "Failed to create a new user because username already exists")
    })
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO user) {
        if (userService.findUserByUsername(user.getUsername()) != null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        userService.registerNewUser(userService.toEntity(user));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(
        summary = "Gets a user profile",
        description = "Provides a userDTO if authentication is successful"
    )
    @ApiResponses( value = {
        @ApiResponse(responseCode = "200", description = "Authentication was successful and userDTO gets returned"),
        @ApiResponse(responseCode = "403", description = "Authentication failed and call is forbidden")
    })
    @GetMapping(path = "/profile", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> getUserProfile(Authentication authentication) {
        if (authentication == null) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        String currentUsername = authentication.getName();
        UserDTO userDTO = userService.findUserByUsername(currentUsername);
        return ResponseEntity.ok(userDTO);
    }

}
