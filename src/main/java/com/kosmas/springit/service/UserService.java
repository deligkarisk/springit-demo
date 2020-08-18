package com.kosmas.springit.service;

import com.kosmas.springit.domain.User;
import com.kosmas.springit.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final Logger logger =  LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final MailService mailService;
    private BCryptPasswordEncoder encoder;
    private RoleService roleService;

    public UserService(UserRepository userRepository, RoleService roleService, MailService mailService) {
        this.userRepository = userRepository;
        encoder = new BCryptPasswordEncoder();
        this.roleService = roleService;
        this.mailService = mailService;
    }

    public User register(User user) {
        // Take a password and encode
        String secret = "{bcrypt}" + encoder.encode(user.getPassword());
        user.setPassword(secret);

        // We also need to set the confirmPassword here to the encoded value of the password.
        // Since we modified the password property, if we attempt to save it
        // now we would get a validation error when the save is attempted (since the password and confirmPassword
        // fields are different (password has the encoded value, confirmPassword has the raw value by this point).
        // Therefore, we also need to alter the confirmPassword property.
        user.setConfirmPassword(secret);

        // Assign a role to the user
        user.addRole(roleService.findByName("ROLE_USER"));


        // Set activation code
        user.setActivationCode(UUID.randomUUID().toString());


        // Save the user. Disable before we save them. They need to be enabled only after they
        // click on the activation link on the email.
        save(user);

        // Send activation email
        sendActivationEmail(user);


        // Return user
        return user;
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public void sendActivationEmail(User user) {
        mailService.sendActivationEmail(user);
    }

    public void sendWelcomeEmail(User user) {
        mailService.sendWelcomeEmail(user);
    }

    public Optional<User> findbyEmailAndActivationCode(String email, String activationCode) {
        return userRepository.findByEmailAndActivationCode(email, activationCode);
    }
}
