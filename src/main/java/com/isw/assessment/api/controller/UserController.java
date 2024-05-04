package com.isw.assessment.api.controller;

import com.isw.assessment.client.sms.dto.SmsStatus;
import com.isw.assessment.persistence.entity.UserEntity;
import com.isw.assessment.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = {"/users"}, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserEntity> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    public UserEntity getUser(@PathVariable Integer id) {
        return userService.getUser(id);
    }

    @PostMapping("/{email}/{sms}")
    public SmsStatus sendSms(@PathVariable("email") String email, @PathVariable("sms") String sms) {
        return userService.sendSms(email, sms);
    }

}