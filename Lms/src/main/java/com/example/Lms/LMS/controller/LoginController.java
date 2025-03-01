package com.example.Lms.LMS.controller;

import java.util.Objects;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.example.Lms.LMS.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import com.example.Lms.LMS.service.LoginService;

@Controller
public class LoginController {

    @Autowired
    private LoginService userService;

    @GetMapping("/index")
    public String getIndexPage() {
        return "index";  // This should match the name of your HTML file (without .html extension)
    }
    @GetMapping("/homepage")
    public String getHomePage() {
        return "home";  // This should match the name of your HTML file (without .html extension)
    }
    @GetMapping("/student/login")
    public ModelAndView login() {
        ModelAndView mav = new ModelAndView("signup-form");
        mav.addObject("user", new Student());
        return mav;
    }

    @PostMapping("/student/login")
    public String login(@ModelAttribute("user") Student user, HttpServletRequest request) {
        String email = user.getEmail();
        String password = user.getPassword();

        // Check for admin credentials
        if ("admin".equals(email) && "admin".equals(password)) {
            request.getSession().setAttribute("admin", true);
            return "redirect:/index";
        }

        // Authenticate user
        Student oauthUser = userService.login(email, password);

        if (Objects.nonNull(oauthUser)) {
            request.getSession().setAttribute("userId", oauthUser.getId());
            return "redirect:/homepage";
        } else {
            request.setAttribute("error", "Invalid email or password");
            return "signup-form";
        }
    }

    @PostMapping("logout")
    public String logoutDo(HttpServletRequest request, HttpServletResponse response) {
        Long userId = (Long) request.getSession().getAttribute("userId");
        if (userId != null) {
            try {
                userService.logout(userId);
                request.getSession().removeAttribute("userId");
                request.getSession().invalidate();
            } catch (Exception e) {
                System.err.println("Error during logout: " + e.getMessage());
                return "redirect:/error";
            }
        }
        return "redirect:/student/login";
    }
}
