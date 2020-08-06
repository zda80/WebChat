package zda.task.webchat.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Chat Login Controller
 *
 * @author Zhevnov D.A.
 * @ created 2020-08-08
 */
@Controller
public class LoginController {

    @RequestMapping("/chat")
    public String toChatPage(HttpServletRequest request, Model model) {
        model.addAttribute("username", getUserName());
        return "chat";
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/";
    }

    @RequestMapping("/")
    public String toLoginPage() {
        return "redirect:/login";
    }

    public String getUserName() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}