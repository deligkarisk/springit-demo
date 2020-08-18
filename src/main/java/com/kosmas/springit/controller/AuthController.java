package com.kosmas.springit.controller;

import com.kosmas.springit.domain.User;
import com.kosmas.springit.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Optional;


@Controller
public class AuthController {

    private UserService userService;
    private final Logger logger = LoggerFactory.getLogger(AuthController.class);

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/profile")
    public String profile() {
        return "auth/profile";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        // model.addAttribute("success", false);
        return "auth/register";
    }

    @PostMapping("/register")
    public String registerNewUser(@Valid User user, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            logger.info("Validation found errors while registering a new user");
            // If there are errors we need to show the completed form the user filled in previously.
            // So, we pass again the user object that was originally passed to this method.
            model.addAttribute("user", user);
            // We also need to send back all the validation errors.
            // We do that from the bindingResult object.
            model.addAttribute("validationErrors", bindingResult.getAllErrors());
            return "auth/register";
        } else {
            User newUser = userService.register(user);
            redirectAttributes
                    .addAttribute("id", newUser.getId())
                    .addFlashAttribute("success", true);
            return "redirect:/register";
        }
    }


    @GetMapping("/activate/{email}/{activationCode}")
    public String activate(@PathVariable String email, @PathVariable String activationCode) {
        Optional<User> user = userService.findbyEmailAndActivationCode(email, activationCode);
        if (user.isPresent()) {
            User newUser = user.get();
            newUser.setEnabled(true);
            // Not sure why we need the below, but it is necessary according to Dan Vega.
            // If confirmPassword is empty then we get errors in saving the new user.
            // I assume therefore there is a better way of implementing this that would
            // spare us the need to set the password every time we need to save our user.
            newUser.setConfirmPassword(newUser.getPassword());
            userService.save(newUser);
            userService.sendWelcomeEmail(newUser);
            return "auth/activated";


        }
        return "redirect:/";
    }
}
