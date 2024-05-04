package com.isw.assessment.service;

import com.isw.assessment.client.sms.SmsClient;
import com.isw.assessment.client.sms.dto.SmsStatus;
import com.isw.assessment.persistence.entity.UserEntity;
import com.isw.assessment.persistence.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UserService {

    private final SmsClient smsClient;

    private final UserRepository userRepository;

    public UserService(SmsClient smsClient, UserRepository userRepository) {
        this.smsClient = smsClient;
        this.userRepository = userRepository;
    }

    public List<UserEntity> getUsers() {
        return userRepository.findAll();
    }

    public UserEntity getUser(int id) {
        var userEntityOptional = userRepository.findById(id);
        return userEntityOptional.orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "User with ID %d not found".formatted(id)));
    }

    public UserEntity getUserByEmail(String email) {
        var userEntityOptional = userRepository.findFirstByEmail(email);
        return userEntityOptional.orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "User with Email %s not found".formatted(email)));
    }

    public SmsStatus sendSms(String email, String smsText) {
        UserEntity user = getUserByEmail(email);
        String phoneNumber = getUserPhoneNumber(user);

        return smsClient.sendSms(phoneNumber, "TEST", smsText);
    }

    private static String getUserPhoneNumber(UserEntity user) {
        String phoneNumber = user.getPhone();
        if(phoneNumber==null || phoneNumber.isBlank())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with ID %d has no saved phone number".
                    formatted(user.getId()));

        return phoneNumber.startsWith("+") ? phoneNumber.substring(1) : phoneNumber;
    }

}
