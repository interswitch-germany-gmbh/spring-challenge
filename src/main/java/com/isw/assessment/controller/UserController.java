package com.isw.assessment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.isw.assessment.entity.UserEntity;
import com.isw.assessment.repository.UserRepository;
import com.isw.assessment.smsservice.SMSService;
import com.isw.assessment.smsservice.SMSStatus;

@RestController
@RequestMapping(path = {"/users"}, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SMSService smsService;

  
    @GetMapping
    public List<UserEntity> getUsers() 
    {
        return userRepository.findAll();
    }
    
    @GetMapping("/{id}")
    public UserEntity getUser(@PathVariable("id") Integer id) 
    {
        return userRepository.findById(id).get();
    }
    
	@PostMapping("/send/{email}/{sms}")
	public SMSStatus sendSMS(@PathVariable("email") String email, @PathVariable("sms") String sms) {
		SMSStatus result = new SMSStatus();

		try {
			result = smsService.sendSMS(getDigitOnlyPhoneNumber(getUser(email)), sms);
		} catch (NoPhoneNumberForUserException noNumerException) {
			result.setErrorMessage("No phone number stored for given user");
		} catch (NoUserMatchesEMailAdressException noUserException) {
			result.setErrorMessage("No user stored with given e-mail-adress");
		} catch (MultipleUsersWithSameEMailAddressException multipleUserException) {
			result.setErrorMessage("Multiple users stored with given e-mail-adress");
		}

		return result;
	}

	private UserEntity getUser(String email)
			throws MultipleUsersWithSameEMailAddressException, NoUserMatchesEMailAdressException {
		List<UserEntity> users = userRepository.findByEmail(email);

		if (users.isEmpty()) {
			throw new NoUserMatchesEMailAdressException();
		} else if (users.size() > 1) {
			throw new MultipleUsersWithSameEMailAddressException();
		} 
		
		return users.get(0);
	}

	private static String getDigitOnlyPhoneNumber(UserEntity user) throws NoPhoneNumberForUserException {
		if (user.getPhone() == null) {
			throw new NoPhoneNumberForUserException();
		}

		return user.getPhone().replace("+", "");
	}

}