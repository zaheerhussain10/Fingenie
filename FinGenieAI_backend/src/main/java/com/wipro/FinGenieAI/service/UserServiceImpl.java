package com.wipro.FinGenieAI.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.wipro.FinGenieAI.dto.UserDTO;
import com.wipro.FinGenieAI.entity.Role;
import com.wipro.FinGenieAI.entity.User;
import com.wipro.FinGenieAI.enums.RoleType;
import com.wipro.FinGenieAI.exception.UserNotFoundException;
import com.wipro.FinGenieAI.mapper.UserMapper;
import com.wipro.FinGenieAI.repository.RoleRepository;
import com.wipro.FinGenieAI.repository.UserRepository;

@Service
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    
    @Override
    public UserDTO addUser(UserDTO dto) {

        User user = UserMapper.toEntity(dto);

        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        RoleType roleType = RoleType.valueOf(dto.getRole());

        Role role = roleRepository.findByRoleName(roleType)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        user.setRoles(Set.of(role));

        User saved = userRepository.save(user);

        return UserMapper.toDTO(saved);
    }
    @Override
    public UserDTO findUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));

        return UserMapper.toDTO(user);
    }
    
}