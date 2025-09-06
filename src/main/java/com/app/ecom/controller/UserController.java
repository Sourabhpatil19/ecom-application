package com.app.ecom.controller;

import com.app.ecom.ExpectionHandler.UserNotFoundException;
import com.app.ecom.dto.user.UserRequest;
import com.app.ecom.dto.user.UserResponse;
import com.app.ecom.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/users")
public class UserController {


   private final UserService userService;



@GetMapping
    public ResponseEntity<List<UserResponse>> getAllUser(){
        return new ResponseEntity<>(userService.fetchAllUser(),HttpStatus.OK);
    }
@PostMapping
    public ResponseEntity<String> createUser(@RequestBody UserRequest userRequestDTO){
       userService.addUser(userRequestDTO);
       return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully");

    }
    @GetMapping("/{id}")

    public ResponseEntity<UserResponse> getUser(@PathVariable Long id){



   return    userService.fetchUserById(id).map(ResponseEntity::ok).orElseThrow(()->
         new UserNotFoundException("User Not Found"));


    }
    @PutMapping("/{id}")
        public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody UserRequest user){

        if(userService.updateUser(id,user) == true) {;
        return  ResponseEntity.status(HttpStatus.ACCEPTED).body("User updated successfully");
    }else{return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User Not Found");
}
}}

