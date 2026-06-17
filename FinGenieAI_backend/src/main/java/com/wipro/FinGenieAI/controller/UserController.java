package com.wipro.FinGenieAI.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.FinGenieAI.dto.LoginRequest;
import com.wipro.FinGenieAI.dto.UserDTO;
import com.wipro.FinGenieAI.entity.User;
import com.wipro.FinGenieAI.repository.UserRepository;
import com.wipro.FinGenieAI.service.IUserService;
import com.wipro.FinGenieAI.service.JwtService;

import jakarta.validation.Valid;

@RestController

@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/users")
public class UserController {

    private final IUserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository; 

    @Autowired
    private PasswordEncoder passwordEncoder; 

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    // ✅ Register use
    //@PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public UserDTO addUser(@RequestBody @Valid UserDTO dto) {
        return userService.addUser(dto);
    }

    // ✅ Get user
    @GetMapping("/{id}")
    //@PreAuthorize("hasRole('USER',ADMIN)")
    public UserDTO getUser(@PathVariable Long id) {
        return userService.findUserById(id);
    }

    // ✅ Login (DB based ✅)
    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return jwtService.generateToken(user.getEmail());
    }
}
