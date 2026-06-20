package com.wipro.FinGenieAI.service;

import com.wipro.FinGenieAI.dto.UserDTO;

public interface IUserService {

    UserDTO addUser(UserDTO dto);

    UserDTO findUserById(Long id);
}