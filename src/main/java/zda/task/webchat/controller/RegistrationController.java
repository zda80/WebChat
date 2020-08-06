package zda.task.webchat.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import zda.task.webchat.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import zda.task.webchat.service.UserService;

import javax.validation.Valid;

/**
 * Registration form controller
 *
 * @author Zhevnov D.A.
 * @ created 2020-08-08
 */
@Controller
public class RegistrationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationController.class);

    public static final String BAD_USERNAME="Login and password must contain at least 2 characters";
    public static final String BAD_PASSWORD="Passwords do not match";
    public static final String USER_TAKEN="This user name is already taken";

    @Autowired
    private UserService userService;

    @Lookup
    public User getNewUser() {
        return null;
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("regForm",getNewUser());
        return "registration";
    }

    @RequestMapping(path = "/registration", method = RequestMethod.POST)
    public String newUser(@ModelAttribute("regForm") @Valid User regForm, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("error", BAD_USERNAME);
            return "registration";
        }

        if (!regForm.getPassword().equals(regForm.getPasswordConfirm())) {
            model.addAttribute("error", BAD_PASSWORD);
            return "registration";
        }

        if (!userService.saveUser(regForm)) {
            model.addAttribute("error", USER_TAKEN);
            return "registration";
        }

        LOGGER.info("New user registered: "+regForm.getUsername());

        return "redirect:/";
    }
}