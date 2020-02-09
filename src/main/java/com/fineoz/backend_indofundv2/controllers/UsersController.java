package com.fineoz.backend_indofundv2.controllers;

import com.fineoz.backend_indofundv2.exceptions.ResourceNotFoundException;
import com.fineoz.backend_indofundv2.models.Users;
import com.fineoz.backend_indofundv2.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;
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
        try
        {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(users.getPassword().getBytes(StandardCharsets.UTF_8));

            StringBuilder sb = new StringBuilder();
            for(byte b : messageDigest)
            {
                sb.append(String.format("%02x", b));
            }
            users.setApi_key(sb.toString());

            //Set expired date
            Date currentDate = new Date();
            Calendar c = Calendar.getInstance();
            c.setTime(currentDate);
            c.add(Calendar.YEAR, 1);
            Date currentDatePlusOne = c.getTime();
            users.setExpired_on(currentDatePlusOne);
        }
        catch (NoSuchAlgorithmException e)
        {
            throw new RuntimeException(e);
        }

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

        return new ResponseEntity<Users>(HttpStatus.NO_CONTENT);
    }
}
