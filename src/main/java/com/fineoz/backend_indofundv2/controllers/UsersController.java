package com.fineoz.backend_indofundv2.controllers;

import com.fineoz.backend_indofundv2.exceptions.ResourceNotFoundException;
import com.fineoz.backend_indofundv2.models.Users;
import com.fineoz.backend_indofundv2.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UsersController
{
    @Autowired
    UsersRepository usersRepository;

    //Get All Users
    @GetMapping("/users")
    public List<Users> getAllUsers()
    {
        return usersRepository.findAll();
    }

    //Get Single User
    @GetMapping("/users/{id_user}")
    public Users getUserById(@PathVariable(value = "id_user") Long id_user)
    {
        return usersRepository.findById(id_user)
                .orElseThrow(() -> new ResourceNotFoundException("Users", "id_user", id_user));
    }

    // Add user
    @PostMapping("/users")
    public Users createUser(@Valid @RequestBody Users users)
    {
        return usersRepository.save(users);
    }

    // Update user
    @PutMapping("/users/{id_user}")
    public Users updateUser(@PathVariable(value = "id_user") Long id_user, @Valid @RequestBody Users userDetails)
    {
        Users users = usersRepository.findById(id_user)
                .orElseThrow(() -> new ResourceNotFoundException("Users", "id_user", id_user));

        users.setNama(userDetails.getNama());
        users.setUsername(userDetails.getUsername());
        users.setPassword(userDetails.getPassword());
        users.setApi_key(userDetails.getApi_key());
        users.setRole(userDetails.getRole());
        users.setExpired_on(userDetails.getExpired_on());

        return usersRepository.save(users);
    }

    // Delete user
    @DeleteMapping("/users/{id_user}")
    public ResponseEntity<?> deleteNote(@PathVariable(value = "id_user") Long id_user)
    {
        Users users = usersRepository.findById(id_user)
                .orElseThrow(() -> new ResourceNotFoundException("Users", "id_user", id_user));
        usersRepository.delete(users);

        return ResponseEntity.ok().build();
    }
}
