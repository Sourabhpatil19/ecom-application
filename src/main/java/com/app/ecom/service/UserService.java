package com.app.ecom.service;

import com.app.ecom.dto.user.UserRequest;
import com.app.ecom.dto.user.UserResponse;
import com.app.ecom.entites.user.User;
import com.app.ecom.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

        private final UserRepository userRepository;

        private final ModelMapper modelMapper;

    public List<UserResponse> fetchAllUser()
        {
          return userRepository.findAll().stream().map(user -> modelMapper.map(user, UserResponse.class)).toList();
        }

    public void addUser(UserRequest userRequestDTO){
           // user.setId(nextId++);
        User user = modelMapper.map(userRequestDTO,User.class);
           userRepository.save(user);
        }
    public Optional<UserResponse> fetchUserById(Long id){

          return userRepository.findById(id).stream().map(user -> modelMapper.map(user, UserResponse.class)).toList().stream().findFirst();


    }
    public boolean updateUser(Long id, UserRequest updatedUser) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    modelMapper.map(updatedUser,User.class);
                    userRepository.save(existingUser);
                    return true;
                })
                .orElse(false);
        }




}



